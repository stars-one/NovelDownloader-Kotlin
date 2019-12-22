package com.wan.noveldownloader.view

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXProgressBar
import com.wan.noveldownloader.model.DownloadedItem
import com.wan.noveldownloader.model.DownloadingItem
import com.wan.noveldownloader.util.NovelDownloadTool
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import kfoenix.jfxbutton
import kfoenix.jfxprogressbar
import tornadofx.*
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

/**
 *
 * @author StarsOne
 * @date Create in  2019/10/15 0015 20:17
 * @description
 *
 */
class DownloadingItemView() : Fragment() {
    var novelNameLabel: javafx.scene.control.Label by singleAssign()
    var progressDetailText: Text by singleAssign() //  15/20
    var progressText: Text by singleAssign()
    var flagText: Text by singleAssign()
    var image: ImageView by singleAssign()
    var progressBar: JFXProgressBar by singleAssign()

    var pauseBtn: JFXButton by singleAssign()
    var stopBtn: JFXButton by singleAssign()
    var downloadedView: DownloadedView? = null
    var url = ""

    constructor(url: String, downloadedView: DownloadedView) : this() {
        this.url = url
        this.downloadedView = downloadedView
    }

    override val root = hbox {

        spacing = 30.0
        image = imageview(url = "img/default_img.png") {
            fitHeight = 120.0
            fitWidth = 120.0
            paddingTop = 10
            paddingLeft = 10
            isPreserveRatio = true
        }
        vbox {
            paddingTop = 25.0
            spacing = 20.0

            novelNameLabel = label("") {
                prefWidth = 300.0
                style {
                    fontWeight = FontWeight.BOLD
                    //字体大小，第二个参数是单位，一个枚举类型
                    fontSize = Dimension(18.0, Dimension.LinearUnits.px)
                }
            }
            progressBar = jfxprogressbar {
            }
            hbox {
                alignment = Pos.CENTER
                spacing = 20.0
                progressDetailText = text("1/14") {
                    style {
                        fontWeight = FontWeight.BOLD
                        fontSize = Dimension(18.0, Dimension.LinearUnits.px)
                    }
                }
                progressText = text("21.43%") {
                    style {
                        fontWeight = FontWeight.BOLD
                        fontSize = Dimension(18.0, Dimension.LinearUnits.px)
                    }
                }
            }
        }
        hbox {
            spacing = 30.0
            paddingTop = 50.0
            flagText = text("解析信息中") {
                style {

                    fill = c("#60d9e4")
                    fontWeight = FontWeight.BOLD
                    fontSize = Dimension(18.0, Dimension.LinearUnits.px)
                }
            }
            pauseBtn = jfxbutton("暂停") {
                setMinSize(JFXButton.USE_PREF_SIZE, JFXButton.USE_PREF_SIZE)
                action {

                }
            }
            stopBtn = jfxbutton("停止") {
                setMinSize(JFXButton.USE_PREF_SIZE, JFXButton.USE_PREF_SIZE)
            }

        }


    }

    fun startTask() {
        val novelDownloadTool = NovelDownloadTool(url)
        var downloadedItem: DownloadedItem? = null
        runAsync {

            runLater {
                flagText.text = "解析信息中"
            }
            val bean = novelDownloadTool.getMessage()
            runLater {
                updateUI(bean)
            }
            //下载图片
            val imgPath = novelDownloadTool.downloadImage().toString()
            //更新封面
            runLater {
                image.image = Image(imgPath)
            }
            val threadCount = 5
            val chacterMap = novelDownloadTool.chacterMap
            val countDownLatch = CountDownLatch(threadCount)
            val step = chacterMap.size / threadCount
            val yu = chacterMap.size % threadCount
            thread {
                for (i in 0 until step) {
                    val item = novelDownloadTool.downloadChacter(i)
                    runLater {
                        updateUI(item)
                    }
                }
                countDownLatch.countDown()
            }
            thread {
                for (i in chacterMap.size - yu until chacterMap.size) {
                    val item = novelDownloadTool.downloadChacter(i)
                    runLater {
                        updateUI(item)
                    }
                }
                countDownLatch.countDown()
            }
            for (i in 1..threadCount - 2) {
                thread {
                    for (j in i * step until (i + 1) * step) {
                        val item = novelDownloadTool.downloadChacter(j)
                        runLater {
                            updateUI(item)
                        }
                    }
                    countDownLatch.countDown()
                }
            }
            countDownLatch.await()

            downloadedItem = novelDownloadTool.mergeFile()
            println(downloadedItem!!.filePath)

        } ui {
            //下载完毕移除item
            removeFromParent()
            addDownloadedItem(downloadedItem!!)
        }

    }

    fun addDownloadedItem(downloadedItem: DownloadedItem) {
        downloadedView?.addDownloadedItem(downloadedItem)
    }

    fun updateUI(itemBean: DownloadingItem) {
        progressBar.progress = itemBean.progress
        novelNameLabel.text = itemBean.novelName
        novelNameLabel.tooltip(text = itemBean.novelName)
        flagText.text = itemBean.flag
        progressDetailText.text = itemBean.progressDetail
        progressText.text = itemBean.progressText
    }


}
