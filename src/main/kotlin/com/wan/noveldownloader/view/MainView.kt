package com.wan.noveldownloader.view

import com.wan.noveldownloader.app.Styles
import javafx.scene.image.ImageView
import kfoenix.jfxtabpane
import tornadofx.*
import java.io.File
import java.net.URL

class MainView : View("星之小说下载器 by stars-one") {

    private var image by singleAssign<ImageView>()
    override val root = hbox {
        val downloadedView = DownloadedView()
        setPrefSize(800.0, 500.0)

        jfxtabpane {
            tab("下载中") {
                this += DownloadingView(downloadedView)
            }
            tab("已下载") {
                this += downloadedView
            }
            tab("设置") {
                hbox {
                    text("helo tab4")
                }
            }
            tab("关于") {
                this += AboutView()
            }
            tabMinWidth = 197.0
            for (tab in tabs) {
                tab.addClass(Styles.MyTab)
            }
        }
    }

    fun downloadImage(imgUrl: String): URL {
        //jar包当前目录
        val dir = File(".", "星之小说下载器${File.separator}img")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        //缓存文件名处理
        val fileName = imgUrl.substringAfterLast("/")
        val file = File(dir, fileName)
        if (!file.exists()) {
            val conn = URL(imgUrl).openConnection()
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)")
            val bytes = conn.getInputStream().readBytes()
            file.writeBytes(bytes)
        }
        return file.toURI().toURL()
    }
}