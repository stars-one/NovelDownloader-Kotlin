package com.wan.noveldownloader.app

import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val MyTab by cssclass()
    }

    init {
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
        MyTab{
            backgroundColor += c("#64b7ea")
            and(hover){
                backgroundColor+=c("#6495ED")
            }

        }
    }
}