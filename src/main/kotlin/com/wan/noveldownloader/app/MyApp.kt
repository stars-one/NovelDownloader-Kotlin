package com.wan.noveldownloader.app

import com.wan.noveldownloader.view.MainView
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*

class MyApp: App(MainView::class,Styles::class){
    override fun start(stage: Stage) {
        super.start(stage)
        stage.icons += Image("img/icon.png")
    }
}