package com.jintao.example.classloader;

import sun.misc.Launcher;

import java.net.URL;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/3/20
 * Time:14:59
 */
public class BootstrapTest {
    public static void main(String[] args) {

        //获取根类加载器所加载的全部URL数组
        URL[] urls = Launcher.getBootstrapClassPath().getURLs();

        //遍历输出根类加载器加载的全部URL
        for(int i = 0; i < urls.length; i++){
            System.out.println(urls[i].toExternalForm());
        }

    }
}
/**
 * file:/D:/java/jdk/jdk1.8/jdk1.8.0_111/jre/lib/resources.jar
 file:/D:/java/jdk/jdk1.8/jdk1.8.0_111/jre/lib/rt.jar
 file:/D:/java/jdk/jdk1.8/jdk1.8.0_111/jre/lib/sunrsasign.jar
 file:/D:/java/jdk/jdk1.8/jdk1.8.0_111/jre/lib/jsse.jar
 file:/D:/java/jdk/jdk1.8/jdk1.8.0_111/jre/lib/jce.jar
 file:/D:/java/jdk/jdk1.8/jdk1.8.0_111/jre/lib/charsets.jar
 file:/D:/java/jdk/jdk1.8/jdk1.8.0_111/jre/lib/jfr.jar
 file:/D:/java/jdk/jdk1.8/jdk1.8.0_111/jre/classes
 */

/**
 * 根类加载器非常特殊，它并不是java.lang.ClassLoader的子类，而是由JVM自身实现的。
**/