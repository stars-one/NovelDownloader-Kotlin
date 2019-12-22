package com.wan.noveldownloader.util

import com.wan.noveldownloader.model.DownloadedItem
import com.wan.noveldownloader.model.DownloadingItem
import java.io.File
import java.net.URL
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

/**
 *
 * @author StarsOne
 * @date Create in  2019/12/6 0006 15:39
 * @description
 *
 */
class NovelDownloadTool(var url: String) {

    var chacterMap = hashMapOf<Int, String>()
    var novelHandler: NovelHandler? = null
    private var name = ""
    private var imgUrl = ""
    private var tempFileHead = ""
    private var downloadedCount = 0
    var beanItems = arrayListOf<DownloadingItem>()
    fun getMessage(): DownloadingItem {
        //NovelHandler中会根据网址不同，使用不同的解析方法获取网站内容
        novelHandler = NovelHandler(url)
        val (novelName, imgUrl, tempFileHead, chacterMap) = NovelHandler(url).getMessage()!!
        this.name = novelName
        this.imgUrl = imgUrl
        this.tempFileHead = tempFileHead
        this.chacterMap = chacterMap
        return DownloadingItem(name, imgUrl, 0.0, "0.00%", "0/${chacterMap.size}", "解析信息中")
    }

    fun download(threadCount: Int = 5): Boolean {
        val countDownLatch = CountDownLatch(threadCount)
        val step = chacterMap.size / threadCount
        val yu = chacterMap.size % threadCount
        thread {
            for (i in 0 until step) {
                downloadChacter(i)
            }
            countDownLatch.countDown()
        }
        thread {
            for (i in chacterMap.size - yu until chacterMap.size) {
                downloadChacter(i)
            }
            countDownLatch.countDown()
        }
        for (i in 1..threadCount - 2) {
            thread {
                for (j in i * step until (i + 1) * step) {
                    downloadChacter(j)
                }
                countDownLatch.countDown()
            }
        }
        countDownLatch.await()
        return true
    }

    fun downloadImage(): URL {
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

    fun downloadChacter(index: Int): DownloadingItem {
        //jar包当前目录
        val dir = File(".", "星之小说下载器")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        //缓存文件名处理
        val fileName = tempFileHead + index

        //缓存文件File
        val file = File(dir, "$fileName.txt")
        //章节文件已下载，跳过
        if (!file.exists()) {
            val content = novelHandler?.getContent(chacterMap[index] as String)
            //文件写入操作
            file.writeText(content as String, charset("GBK"))
        }
        val temp = (downloadedCount + 1) / (chacterMap.size * 1.0)
        val df = DecimalFormat("0.00")
        val progressText = "${df.format(temp * 100)}%"
        downloadedCount++
        if (temp == 1.0) {
            val item = DownloadingItem(name, imgUrl, temp, progressText, "${downloadedCount + 1}/${chacterMap.size}", "合并中")
            beanItems.add(item)
            return item
        }
        val item = DownloadingItem(name, imgUrl, temp, progressText, "${downloadedCount + 1}/${chacterMap.size}", "下载中")
        beanItems.add(item)
        return item
    }

    /**
     * 合并文件
     */
    fun mergeFile(): DownloadedItem {
        //.表示当前路径
        val dir = File(File("."), "星之小说下载器").path
        val fileName = if (name.contains(":")){
            name.replace(":","")+".txt"
        }else{
            "${name.trim()}.txt"
        }
        val file = File(dir, fileName)
        val bf = StringBuffer("")
        for (i in 0 until chacterMap.size) {
            val tempFile = File(dir, "$tempFileHead$i.txt")
            //获得每个缓存txt文件的文本内容
            if (tempFile.exists()) {
                val bytes = tempFile.readText(charset("gbk"))
                bf.append(bytes)
                //获得内容删除文件
                tempFile.delete()
            }
        }
        //写入
        file.writeText(bf.toString(), charset("gbk"))
        val date = SimpleDateFormat("yyyy/MM/dd HH:mm").format(Date(file.lastModified()))
        val fileSize = file.length() / (1024 * 1024 * 1.0)
        val df = DecimalFormat("0.00")
        return DownloadedItem(name, imgUrl, "大小：${df.format(fileSize)}MB", file.parentFile.canonicalPath, date)
    }
}