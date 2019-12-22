package com.wan.noveldownloader.util

import com.wan.noveldownloader.model.NovelMessage
import org.jsoup.Jsoup
import kotlin.collections.set

/**
 *
 * @author StarsOne
 * @date Create in  2019/10/5 0005 17:03
 * @description
 *
 */


class NovelHandler() {
    //todo 添加书源
    val bookSourceMap = mapOf(
            "铅笔小说网" to "www.x23qb.com"
            )

    val QIBI = "www.x23qb.com"

    private var novelUrl = ""

    constructor(novelUrl: String) : this() {
        this.novelUrl = novelUrl
    }

    fun getMessage(): NovelMessage? {
        return when {
            novelUrl.contains(QIBI) -> getMessageFromQibi()
            else -> null
        }
    }

    fun getContent(chacterUrl: String): String {
        return when {
            novelUrl.contains(QIBI) -> getContentFromQibi(chacterUrl)
            else -> ""
        }
    }

    /**
     * 获得小说信息（铅笔小说网）
     */
    private fun getMessageFromQibi(): NovelMessage {
        val document = Jsoup.connect(novelUrl).timeout(5000).get()
        val div = document.getElementById("bookimg")
        val img = div.selectFirst("img")

        val imgUrl = img.attr("src")//小说图片
        val name = img.attr("alt")//小说名
        val chacterMap = hashMapOf<Int, String>()//小说全部章节的url

        val elements = document.select("#chapterList li")
        for (i in 0 until elements.size) {
            val chacterUrl = "https://www.x23qb.com" + elements[i].selectFirst("a").attr("href")
            chacterMap[i] = chacterUrl
        }

        //缓存文件开头，之后需要重复用到
        val end = novelUrl.lastIndexOf("/")
        val start = novelUrl.indexOf("k") + 2
        val tempFileHead = novelUrl.substring(start, end) + "_"
        return NovelMessage(name, imgUrl, tempFileHead, chacterMap)
    }

    /**
     * 获取某章节的内容（铅笔小说网）
     */
    private fun getContentFromQibi(chacterUrl: String): String {
        //获得章节标题和内容
        //设置连接时长为5s
        val document = Jsoup.connect(chacterUrl).timeout(5000).get()
        val mainTextDiv = document.selectFirst("#mlfy_main_text")
        val title = mainTextDiv.selectFirst("h1").text()//章节标题

        val element = document.selectFirst("#TextContent")
        var content = element.text().replace(" ", "\n").replace("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").replace("&nbsp;", "");
        content = content.replace("本章未完，点击下一页继续阅读", "").replace("＞＞", "");//章节内容
        return "$title\n$content\n"
    }
}