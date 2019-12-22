package com.wan.noveldownloader.view

import com.jfoenix.controls.JFXButton
import com.wan.noveldownloader.model.DownloadedItem
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import kfoenix.jfxbutton
import tornadofx.*
import java.awt.Desktop
import java.io.File

/**
 *
 * @author StarsOne
 * @date Create in  2019/10/15 0015 20:17
 * @description
 *
 */
class DownloadedItemView() : View() {
    var novelNameLabel: Label by singleAssign()
    var image: ImageView by singleAssign()
    var downloadedItem: DownloadedItem? = null
    var fileSizeLable by singleAssign<Text>()
    var dateLabel by singleAssign<Text>()
    var deleteBtn by singleAssign<JFXButton>()
    var fileBtn by singleAssign<JFXButton>()

    constructor(downloadedItem: DownloadedItem) : this() {
        this.downloadedItem = downloadedItem
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

            dateLabel = text("") {
                style {
                    fontWeight = FontWeight.BOLD
                    fontSize = Dimension(18.0, Dimension.LinearUnits.px)
                }
            }

        }
        hbox {
            spacing = 30.0
            paddingTop = 50.0
            fileSizeLable = text("") {
                style {

                    fill = c("#60d9e4")
                    fontWeight = FontWeight.BOLD
                    fontSize = Dimension(18.0, Dimension.LinearUnits.px)
                }
            }
            fileBtn = jfxbutton("打开文件夹") {
                setMinSize(JFXButton.USE_PREF_SIZE, JFXButton.USE_PREF_SIZE)
                action {
                    Desktop.getDesktop().open(File(downloadedItem?.filePath))
                }
            }
            deleteBtn = jfxbutton("删除") {
                setMinSize(JFXButton.USE_PREF_SIZE, JFXButton.USE_PREF_SIZE)
            }

        }

    }

     fun refreshUi() {
        if (downloadedItem != null) {
            novelNameLabel.text = downloadedItem?.novelName
            novelNameLabel.tooltip(text = downloadedItem?.novelName)
            fileSizeLable.text = downloadedItem?.fileSize
            dateLabel.text = downloadedItem?.date
            val imgName = downloadedItem?.imgUrl?.substringAfterLast("/")
            val imgFile = File(downloadedItem?.filePath,"img${File.separator}$imgName")
            if (imgFile.exists()) {
                image.image = Image(imgFile.toURI().toURL().toString())
            }
        }
    }
}
