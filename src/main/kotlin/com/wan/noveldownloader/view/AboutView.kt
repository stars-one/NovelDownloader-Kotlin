package com.wan.noveldownloader.view

import com.wan.noveldownloader.util.NovelHandler
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.text.FontWeight
import tornadofx.*
import java.awt.Desktop
import java.net.URI

class AboutView : View("星之小说下载器 by stars-one") {

    override val root = scrollpane {
        //不显示水平滚动条
        hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER

        vbox {
            paddingTop = 10.0
            spacing = 10.0
            setPrefSize(800.0, 500.0)
            text("星之小说下载器v1.0") {
                alignment = Pos.TOP_CENTER

                style {
                    fontWeight = FontWeight.BOLD
                    //字体大小，第二个参数是单位，一个枚举类型
                    fontSize = Dimension(18.0, Dimension.LinearUnits.px)
                }
            }
            text("基于Jsoup的小说下载工具") {
                alignment = Pos.TOP_CENTER
            }
            form {
                hbox(20) {
                    fieldset {
                        alignment = Pos.CENTER
                        field("软件作者：") {
                            text("stars-one")
                        }
                        field("项目地址：") {
                            hyperlink("www.baidu.com") {
                                setOnMouseClicked {
                                    Desktop.getDesktop().browse(URI(this.text.toString()))
                                }
                            }
                        }

                        field("博客地址：") {
                            hyperlink("www.cnblogs.com/kexing/noveldownloader") {
                                tooltip(this.text.toString())
                                maxWidth = 300.0
                                setOnMouseClicked {
                                    Desktop.getDesktop().browse(URI(this.text.toString()))
                                }
                            }
                        }
                        field("联系QQ：") {
                            text("1053894518")
                        }
                        field("软件交流群：") {
                            text("124572245")
                        }
                    }
                    fieldset {
                        vbox(20) {
                            text("对你有帮助的话，不妨打赏一波") {
                                alignment = Pos.TOP_CENTER
                                style {
                                    fontWeight = FontWeight.BOLD
                                    //字体大小，第二个参数是单位，一个枚举类型
                                    fontSize = Dimension(18.0, Dimension.LinearUnits.px)
                                }
                            }
                            hbox(20) {
                                vbox(15) {
                                    text("微信") {
                                        alignment = Pos.TOP_CENTER
                                    }
                                    imageview(url = "img/weixin.jpg") {
                                        alignment = Pos.TOP_CENTER
                                        fitHeight = 160.0
                                        fitWidth = 160.0
                                        isPreserveRatio = true
                                    }
                                }
                                vbox(15) {
                                    text("支付宝") {
                                        alignment = Pos.TOP_CENTER
                                    }
                                    imageview(url = "img/zhifubao.jpg") {
                                        alignment = Pos.TOP_CENTER
                                        fitHeight = 160.0
                                        fitWidth = 160.0
                                        isPreserveRatio = true
                                    }
                                }
                            }
                        }
                    }
                }

            }
            form {
                fieldset("目前支持的书源") {
                    val map = NovelHandler().bookSourceMap
                    val keys = map.keys.toList()
                    for (i in 0 until keys.size step 3) {
                        field {
                            for (j in 0..2) {
                                if (i + j < keys.size) {
                                    val key = keys[i + j]
                                    text(key)
                                    hyperlink(map[key].toString()) {
                                        setOnMouseClicked {
                                            Desktop.getDesktop().browse(URI(this.text.toString()))
                                        }
                                    }
                                } else {
                                    break
                                }

                            }
                        }
                    }
                }

                fieldset("版本更新说明") {
                    vbox(20) {
                        field("v1.0(2019.12.22)") {
                            vbox {
                                paddingLeft = 30.0
                                text("1.启用多线程下载，提高下载速度")
                                text("2.修复Java版本的线程错误")
                                text("3.优化输入框输入（双击可全选内容，输入内容按下Enter即可添加小说)")
                            }
                        }
                        //todo 版本更新定位

                    }

                }
            }

        }

    }

}