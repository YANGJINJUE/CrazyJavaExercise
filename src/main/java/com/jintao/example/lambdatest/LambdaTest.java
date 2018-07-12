package com.jintao.example.lambdatest;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        new Thread(() -> testList()).start();
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

    @Test
    public void testMapToInt() {
        List<Dish> menu =
                Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
                        new Dish("beef", false, 700, Dish.Type.MEAT),
                        new Dish("chicken", false, 400, Dish.Type.MEAT),
                        new Dish("rice", true, 350, Dish.Type.OTHER),
                        new Dish("pizza", true, 550, Dish.Type.OTHER),
                        new Dish("prawns", false, 400, Dish.Type.FISH),
                        new Dish("salmon", false, 450, Dish.Type.FISH));
        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

        Arrays.stream(numbers.toArray()).forEach(System.out::println);
        System.out.println("--------分割--------");
        numbers.forEach(System.out::println);
        // max and OptionalInt
        OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();

        int maxInt = menu.stream().map(dish -> dish.getCalories()).sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);

        System.out.println("Number of calories:" + maxCalories);
        System.out.println("Number of calories:" + maxCalories.getAsInt());
        System.out.println(maxInt);
    }

    @Test
    public void testInitMapByLambda() throws Exception {
        HashMap<Integer, Callable<String>> m = new HashMap<Integer, Callable<String>>() {
            {
                put(0, () -> {
                    return "n";
                });
                put(1, () -> {
                    return "m";
                });
            }
        };
        System.out.println(m.get(0).call());
    }

    /**
     * Stream的map和flatMap的区别:
     * map会将一个元素变成一个新的Stream
     * 但是flatMap会将结果打平，得到一个单个元素
     */
    @Test
    public void testMapFlatMap() {
        /**获取单词，并且去重**/
        List<String> list = Arrays.asList("hello welcome", "world hello", "hello world",
                "hello world welcome");

        //map和flatmap的区别
        list.stream().map(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("---------- ");
        list.stream().flatMap(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList()).forEach(System.out::println);

        //实际上返回的类似是不同的
        List<Stream<String>> listResult = list.stream().map(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList());
        List<String> listResult2 = list.stream().flatMap(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList());

        System.out.println("---------- ");

        //也可以这样
        list.stream().map(item -> item.split(" ")).flatMap(Arrays::stream).distinct().collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("================================================");

        /**相互组合**/
        List<String> list2 = Arrays.asList("hello", "hi", "你好");
        List<String> list3 = Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu");

        list2.stream().map(item -> list3.stream().map(item2 -> item + " " + item2)).collect(Collectors.toList()).forEach(System.out::println);
        list2.stream().flatMap(item -> list3.stream().map(item2 -> item + " " + item2)).collect(Collectors.toList()).forEach(System.out::println);

        //实际上返回的类似是不同的
        List<Stream<String>> list2Result = list2.stream().map(item -> list3.stream().map(item2 -> item + " " + item2)).collect(Collectors.toList());
        List<String> list2Result2 = list2.stream().flatMap(item -> list3.stream().map(item2 -> item + " " + item2)).collect(Collectors.toList());
    }
}
