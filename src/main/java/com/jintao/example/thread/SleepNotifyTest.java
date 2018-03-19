package com.jintao.example.thread;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/3/19
 * Time:17:18
 */
public class SleepNotifyTest {
    public static void main(String[] args) {
        ThreadA t1 = new ThreadA("t1");

        synchronized (t1) {
            t1.start();

            //主线程等待t1通过notify() 唤醒
            System.out.println(Thread.currentThread().getName() + " wait");

            try {
                t1.wait();
                System.out.println(Thread.currentThread().getName() + " continue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    static class ThreadA extends Thread {

        public ThreadA(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + " run");
                //唤醒当前的wait线程
                notify();
                System.currentTimeMillis();
            }
        }
    }
}


/**
 * (01) 注意，图中"主线程" 代表“主线程main”。"线程t1" 代表WaitTest中启动的“线程t1”。 而“锁” 代表“t1这个对象的同步锁”。
 * (02) “主线程”通过 new ThreadA("t1") 新建“线程t1”。随后通过synchronized(t1)获取“t1对象的同步锁”。然后调用t1.start()启动“线程t1”。
 * (03) “主线程”执行t1.wait() 释放“t1对象的锁”并且进入“等待(阻塞)状态”。等待t1对象上的线程通过notify() 或 notifyAll()将其唤醒。
 * (04) “线程t1”运行之后，通过synchronized(this)获取“当前对象的锁”；接着调用notify()唤醒“当前对象上的等待线程”，也就是唤醒“主线程”。
 * (05) “线程t1”运行完毕之后，释放“当前对象的锁”。紧接着，“主线程”获取“t1对象的锁”，然后接着运行。
 **/
