package com.wan.noveldownloader.view

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import com.wan.noveldownloader.util.NovelHandler
import javafx.scene.layout.VBox
import kfoenix.jfxbutton
import kfoenix.jfxtextfield
import tornadofx.*

/**
 *
 * @author StarsOne
 * @date Create in  2019/10/15 0015 20:17
 * @description
 *
 */
class DownloadingView : Fragment {
    private var contentVbox by singleAssign<VBox>()
    var textInput by singleAssign<JFXTextField>()
    var downloadedView: DownloadedView? = null

    constructor(downloadedView: DownloadedView) {
        this.downloadedView = downloadedView
    }

    override val root = scrollpane {
        vbox {

            hbox {
                textInput = jfxtextfield(promptText = "在此输入小说网址(暂时只支持铅笔小说网）") {
                    prefWidth = 300.0
                }
                hbox {
                    jfxbutton("添加小说") {
                        setMinSize(JFXButton.USE_PREF_SIZE, JFXButton.USE_PREF_SIZE)
                        action {
                            val urlInput = textInput.text
                            if (urlInput.isNotBlank()) {
                                if (urlInput.contains(NovelHandler().QIBI)) {
                                    val downloadingItemView = DownloadingItemView(urlInput, downloadedView!!)
                                    contentVbox.add(downloadingItemView)
                                    downloadingItemView.startTask()
                                }
                            }


                        }
                    }
                    jfxbutton("全部暂停") {
                        setMinSize(JFXButton.USE_PREF_SIZE, JFXButton.USE_PREF_SIZE)
                        action {

                        }
                    }
                    jfxbutton("全部删除") {
                        setMinSize(JFXButton.USE_PREF_SIZE, JFXButton.USE_PREF_SIZE)
                        action { }
                    }
                }

            }
            contentVbox = vbox()
        }
    }
}

