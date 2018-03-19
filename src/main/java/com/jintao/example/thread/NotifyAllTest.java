package com.jintao.example.thread;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/3/19
 * Time:17:17
 */
public class NotifyAllTest {
    private static Object obj = new Object();

    public static void main(String[] args) {
        ThreadB t1 = new ThreadB("t1");
        ThreadB t2 = new ThreadB("t2");
        ThreadB t3 = new ThreadB("t3");
        t1.start();
        t2.start();
        t3.start();

        try {
            System.out.println(Thread.currentThread().getName() + " sleep (3000)");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (obj) {
            System.out.println(Thread.currentThread().getName() + " notifyAll()");
            obj.notifyAll();
        }
    }


    static class ThreadB extends Thread {

        public ThreadB(String name) {
            super(name);
        }

        public void run() {
            synchronized (obj) {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait");
                    // 阻塞当前的线程
                    obj.wait();
                    // 打印输出结果
                    System.out.println(Thread.currentThread().getName() + " continue");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/**
 * (01) 主线程中新建并且启动了3个线程"t1", "t2"和"t3"。
 * (02) 主线程通过sleep(3000)休眠3秒。在主线程休眠3秒的过程中，我们假设"t1", "t2"和"t3"这3个线程都运行了。以"t1"为例，
 * 当它运行的时候，它会执行obj.wait()等待其它线程通过notify()或额nofityAll()来唤醒它；相同的道理，"t2"和"t3"也会等待其它线程
 * 通过nofity()或nofityAll()来唤醒它们。
 * (03) 主线程休眠3秒之后，接着运行。执行 obj.notifyAll() 唤醒obj上的等待线程，即唤醒"t1", "t2"和"t3"这3个线程。 紧接着，
 * 主线程的synchronized(obj)运行完毕之后，主线程释放“obj锁”。这样，"t1", "t2"和"t3"就可以获取“obj锁”而每隔三秒输出一个的继续运行了！
 **/
