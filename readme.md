# 星之小说下载器Kotlin版
基于星之小说下载器Java版重构的Kotlin版本

[github地址](https://github.com/Stars-One/NovelDownloader-Kotlin)
## 使用说明
确保电脑有jdk8+以上的环境，双击即可运行（win10系统)，win7则需要输入命令`java -jar d:\test\NovelDownloader.jar`

**小说下载目录在jar同目录下的`星之小说下载器`目录下**

目前只支持铅笔小说网,后续添加更多书源，还有安卓版，敬请期待。

喜欢的话，不妨打赏一波！

软件交流QQ群：**690380139**

断点下载暂未实现，小说下载途中，一定不要关闭软件，否则再次打开软件，之前的正在下载的任务会清空，只能重新下载。

**这段请忽略**	
测试jar包代码：
```
java -Xdebug -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=y -jar Q:\JavaProject\NovelDownloader\out\artifacts\NovelDownloader_jar\NovelDownloader.jar
```

## 预览

![](https://img2018.cnblogs.com/blog/1210268/201912/1210268-20191222171424590-132491536.gif)

## 蓝奏云下载地址
[https://www.lanzous.com/b0cpux0bc](https://www.lanzous.com/b0cpux0bc)

## 功能实现
- [x] 单章节下载
- [x] 支持铅笔小说网（已完成）
- [x] 暂停/开始（已完成）
- [x] 全部暂停和全部开始（已完成）
- [x] 下载完成界面（已完成）
- [x] 合并章节（已完成）
- [ ] 取消正在下载的任务
- [ ] 文件夹选择，设置下载链接
- [ ] 读取存在的历史任务
- [ ] 后台剪切板监听（似乎没有API）
- [ ] 读取已下载记录
- [ ] 删除下载任务
- [ ] 支持其他网站（笔趣阁..)后续添加

## 支持书源
[铅笔小说网](https://www.x23qb.com/)
