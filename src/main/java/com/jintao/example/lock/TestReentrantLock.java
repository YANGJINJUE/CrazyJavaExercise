package com.jintao.example.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/4/11
 * Time:15:24
 */
public class TestReentrantLock {
    private static int a = 0;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        System.out.println("add:" + (++a));
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
//                        System.out.println("unlock: " + a);
                    }
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    lock.lock();
                    try {
                        System.out.println("sub:" + (--a));
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }).start();
    }
}

