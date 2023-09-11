package com.demo01;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

// 实现多线程下载图片
public class TestThread2 extends Thread {
    private String name;
    private String url;

    public TestThread2(String url, String name){
        this.url = url;
        this.name = name;
    }

    public void run(){
        WebDownloader webDownloader = new WebDownloader();
        webDownloader.downloader(url, name);
        System.out.println("下载文件名为: " + name);

    }

    public static void main(String[] args) {
        TestThread2 t1 = new TestThread2("https://cn.bing.com/images/search?q=%E5%9B%BE%E7%89%87&FORM=IQFRBA&id=B33EDDD4B70A8010E22B0F97BA60CB4FD398A3EA", "1");
        t1.start();
    }
}

// 下载器
class WebDownloader{
    public void downloader(String url, String name){
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("io异常");
        }
    }
}