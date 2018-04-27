package com.jintao.example.lambdatest;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 参考文档 http://www.importnew.com/16436.html
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/4/26
 * Time:16:09
 */
public class LambdaTest {

    @Test
    public void testThread() {
        //java 8之前
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before Java8, too much code for too little to do");
            }
        }).start();

        //java8之后
        new Thread(() -> System.out.println("In Java8, Lambda expression rocks !!")).start();
    }

    @Test
    public void testList() {
        // Java 8之前：
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        for (String feature : features) {
            System.out.println(feature);
        }

        // Java 8之后：
        features.forEach(s -> System.out.println(s));

        // 使用Java 8的方法引用更方便，方法引用由::双冒号操作符标示，
        // 看起来像C++的作用域解析运算符
        features.forEach(System.out::println);
    }
}
