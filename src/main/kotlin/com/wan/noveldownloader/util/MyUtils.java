package com.wan.noveldownloader.util;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.scene.control.Hyperlink;

/**
 * @author StarsOne
 * @date Create in  2019/6/5 0005 14:01
 * @description
 */
public class MyUtils {

    /**
     * 设置链接可以自动跳转资源管理器，浏览器或者打开QQ添加好友界面（保证hyperlink的文字是正确的目录路径，网址，QQ号）
     * QQ添加好友测试不通过，还有bug
     *
     * @param hyperlink hyperlink
     */
    public static void setLinkAutoAction(Hyperlink hyperlink) {
        String text = hyperlink.getText();
        //使用默认的浏览器打开
        if (text.contains("www") || text.contains("com") || text.contains(".")) {
            try {
                Desktop.getDesktop().browse(new URI(text));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (text.contains(File.separator)) {
            try {
                Desktop.getDesktop().open(new File(text));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //打开QQ
            try {
                Desktop.getDesktop().browse(new URI("http://wpa.qq.com/msgrd?v=3&amp;uin=" + text + "&amp;site=qq&amp;menu=yes"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

}
