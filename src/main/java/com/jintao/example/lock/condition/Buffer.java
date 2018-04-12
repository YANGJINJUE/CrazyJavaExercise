package com.jintao.example.lock.condition;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/4/11
 * Time:10:35
 * 使用lock/condition实现生产者消费者模式
 */
public class Buffer {
    private final Lock lock;
    private final Condition notFull;
    private final Condition notEmpty;
    private int maxSize;
    private List<Date> storage;

    public Buffer(int size) {
        //使用锁lock，并且创建两个condition，相当于两个阻塞队列
//        lock = new ReentrantLock();
        lock = new ReentrantLock(true);//可以试图使用公平锁
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
        this.maxSize = size;
        this.storage = new LinkedList<Date>();
    }

    /**
     * 生产方法
     */
    public void put() {
        lock.lock();
        try {
            while (storage.size() == maxSize) {//如果队列满了
                System.out.print(Thread.currentThread().getName() + ": wait \n");
                notFull.await();//阻塞生产线程
            }
            storage.add(new Date());
            System.out.print(Thread.currentThread().getName() + ": put:" + storage.size() + "\n");
            Thread.sleep(1000);
            notEmpty.signalAll();//唤醒消费线程
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 消费方法
     */
    public void take() {
        lock.lock();
        try {
            while (storage.size() == 0) {//如果队列空了
                System.out.print(Thread.currentThread().getName() + ": wait \n");
                notEmpty.await();//阻塞消费线程
            }
            Date d = ((LinkedList<Date>) storage).poll();
            System.out.print(Thread.currentThread().getName() + ": take:" + storage.size() + "\n");
            Thread.sleep(1000);
            notFull.signalAll();//唤醒生产线程

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


/**
 * Condition
 * 那么引入本篇的主角，Condition，Condition 将 Object的通信方法（wait、notify 和 notifyAll）分解成截然不同的对象，以便通过将这些对象与任意 Lock 实现组合使用，
 * 为每个对象提供多个等待 set （wait-set）。其中，Lock 替代了 synchronized 方法和语句的使用，Condition 替代了 Object 通信方法的使用。
 * 在Condition中，用await()替换wait()，用signal()替换notify()，用signalAll()替换notifyAll()，传统线程的通信方式，Condition都可以实现，
 * 这里注意，Condition是被绑定到Lock上的，要创建一个Lock的Condition必须用newCondition()方法。
 * Condition的强大之处在于它可以为多个线程间建立不同的Condition， 使用synchronized/wait()只有一个阻塞队列，notifyAll会唤起所有阻塞队列下的线程，
 * 而使用lock/condition，可以实现多个阻塞队列，signalAll只会唤起某个阻塞队列下的阻塞线程。这样的话效率和对cpu的消耗要好很多。不会无辜唤醒一些不需要唤醒的一些线程。
 * 比如放入数据后，只需要唤醒消费者可以取数据了，不会像那种synchronized下的对象锁。会唤醒所有的生产者，和消费者线程，
 **/