package com.wan.noveldownloader.model


/**
 *
 * @author StarsOne
 * @date Create in  2019/10/5 0005 10:58
 * @description
 *
 */

/**
 * @param progress：进度条的进度 int
 * @param progressText：进度 25.65% String
 * @param progressDetail： 具体进度  20/52 String
 */
data class DownloadingItem(var novelName: String, var imgUrl: String,var progress: Double, var progressText: String, var progressDetail: String, var flag: String)

/**
 * fileSize:文件大小
 * itemPosition: 在rv_downloading中列表的顺序，便于下载任务完成移除item
 */
data class DownloadedItem(var novelName: String, var imgUrl: String, var fileSize: String,var filePath: String, var date: String)

data class NovelMessage(var novelName: String, var imgUrl: String,var tempFileHead: String,var chacterMap: HashMap<Int,String>)