package com.jintao.example.classloader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/3/20
 * Time:15:38
 */
public class ClassLoaderPropTest {

    public static void main(String[] args) throws IOException {
        //获取系统类加载器
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        System.out.println("系统类加载器:"+systemLoader);
        //系统类加载器路径通常是环境变量CLASSPATH，如果没有指定默认是当前路径
        Enumeration<URL> em =  systemLoader.getResources("");

        while (em.hasMoreElements()){
            System.out.println(em.nextElement());
        }

        //获取系统类加载器的父类加载器，扩展类加载器
        ClassLoader extLoader = systemLoader.getParent();
        System.out.println("扩展类加载器:"+extLoader);
        System.out.println("扩展类加载器的加载路径:"+System.getProperty("java.ext.dirs"));
        System.out.println("扩展类加载器的parent:"+extLoader.getParent());
    }
}

/**
 * 系统类加载器:sun.misc.Launcher$AppClassLoader@18b4aac2
 file:/E:/zhiweiproject/Git_Test/CrazyJavaExercise/target/classes/
 扩展类加载器:sun.misc.Launcher$ExtClassLoader@5680a178
 扩展类加载器的加载路径:D:\java\jdk\jdk1.8\jdk1.8.0_111\jre\lib\ext;C:\Windows\Sun\Java\lib\ext
 扩展类加载器的parent:null
 **/

/**
 *根类加载器<------扩展类加载器<------系统类加载器<---------用户类加载器
 * JVM的根类加载器并不是java实现的，而且程序通常无需访问根类加载器，因此访问扩展类加载器的父类加载器返回null
* */

