package com.jintao.example.lambdatest;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

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

    @Test
    public void testPredicate() {
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        System.out.println("Languages which starts with J");
        filter(languages, (str) -> ((String) str).startsWith("J"));
        System.out.println("Languages which starts with a");
        filter(languages, (str) -> ((String) str).startsWith("a"));
        System.out.println("Print all laguage");
        filter(languages, (str) -> true);
        System.out.println("Print no laguage");
        filter(languages, (str) -> false);
        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str) -> ((String) str).length() > 4);

        System.out.println("Use filter2 And EndWith +:");
        filter2(languages, (str) -> ((String) str).endsWith("+"));


        // 甚至可以用and()、or()和xor()逻辑函数来合并Predicate，
        // 例如要找到所有以J开始，长度为四个字母的名字，你可以合并两个Predicate并传入
        Predicate<String> startsWithJ = (n) -> n.startsWith("J");
        Predicate<String> fourLetterLong = (n) -> n.length() == 4;
        languages.stream().filter(startsWithJ.and(fourLetterLong))
                .forEach((n) -> System.out.print("nName, which starts with 'J' and four letter long is : " + n));

    }

    public static void filter(List<String> names, Predicate condition) {
        for (String name : names) {
            if (condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }

    public static void filter2(List<String> names, Predicate condition) {
        names.stream().filter(name -> condition.test(name)).forEach(System.out::println);
    }

}
