package com.wan.noveldownloader.view

import com.wan.noveldownloader.model.DownloadedItem
import javafx.scene.layout.VBox
import kfoenix.jfxbutton
import tornadofx.*

/**
 *
 * @author StarsOne
 * @date Create in  2019/12/16 0016 21:37
 * @description
 *
 */
class DownloadedView : Fragment("My View") {
    var listView by singleAssign<VBox>()

    override val root = vbox {
        hbox {
            jfxbutton("删除记录") {
                action {  }
            }
        }

        listView = vbox {

        }
    }

    fun addDownloadedItem(downloadedItem: DownloadedItem) {
        val view = DownloadedItemView(downloadedItem)
        view.refreshUi()
        listView.add(view)

    }
}


