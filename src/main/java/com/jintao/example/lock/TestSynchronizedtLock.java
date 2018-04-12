package com.jintao.example.lock;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/4/11
 * Time:15:24
 */
public class TestSynchronizedtLock {
    private static int a = 0;
    private static Object lockObj = new Object();

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (lockObj){
                        try {
                            System.out.println("add:" + (++a));
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    synchronized (lockObj){
                        try {
                            System.out.println("sub:" + (--a));
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}

