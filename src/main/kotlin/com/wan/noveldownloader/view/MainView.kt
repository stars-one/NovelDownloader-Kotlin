package com.wan.noveldownloader.view

import com.wan.noveldownloader.app.Styles
import kfoenix.jfxtabpane
import tornadofx.*

class MainView : View("星之小说下载器 by stars-one") {
    
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


}