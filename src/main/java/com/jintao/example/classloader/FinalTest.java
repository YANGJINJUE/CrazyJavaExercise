package com.jintao.example.classloader;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/3/20
 * Time:14:39
 */

class MyTest {
    static {
        System.out.println("静态初始化代码块.....");
    }

    static final String compileConstant = "常量";
    static final String dynamicConstant = System.currentTimeMillis() + "常量";
}

public class FinalTest {
    public static void main(String[] args) {
        System.out.println(MyTest.compileConstant);
//        System.out.println(MyTest.dynamicConstant);
    }
}

/**
 * 当某个类变量(静态常量)被final修饰，而且它的值可以在编译阶段就确定下来，那么程序在其它地方使用时，
 * 实际上并没有使用该类变量，而是相当于使用常量。
 * MyTest.compileConstant该行代码在编译时都被替换成常量
 * MyTest.dynamicConstant是一个动态的值，在编译阶段不可确定，调用该变量会导致类初始化
 **/