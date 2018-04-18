package com.jintao.example.lock.my_condition;

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
    private final Lock putLock;
    private final Lock takeLock;
    private final Condition notFull;
    private final Condition notEmpty;
    private int maxSize;
    private List<Date> storage;

    public Buffer(int size) {
        //使用锁lock，并且创建两个condition，相当于两个阻塞队列
        putLock = new ReentrantLock();
        takeLock = new ReentrantLock();
        notFull = putLock.newCondition();
        notEmpty = takeLock.newCondition();
        this.maxSize = size;
        this.storage = new LinkedList<Date>();
    }

    /**
     * 生产方法
     */
    public void put() {
        putLock.lock();
        try {
            while (storage.size() == maxSize) {//如果队列满了
                System.out.print(Thread.currentThread().getName() + ": wait \n");
                notFull.await();//阻塞生产线程
            }
            storage.add(new Date());
            System.out.print(Thread.currentThread().getName() + ": put:" + storage.size() + "\n");
            Thread.sleep(1000);
            takeLock.lock();
            notEmpty.signalAll();//唤醒消费线程
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            putLock.unlock();
            takeLock.unlock();
        }
    }

    /**
     * 消费方法
     */
    public void take() {
        takeLock.lock();
        try {
            while (storage.size() == 0) {//如果队列空了
                System.out.print(Thread.currentThread().getName() + ": wait \n");
                notEmpty.await();//阻塞消费线程
            }
            Date d = ((LinkedList<Date>) storage).poll();
            System.out.print(Thread.currentThread().getName() + ": take:" + storage.size() + "\n");
            Thread.sleep(1000);
            putLock.lock();
            notFull.signalAll();//唤醒生产线程

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            takeLock.unlock();
            putLock.unlock();
        }
    }
}


/**
 *这里把putLock,和takeLock进行了分离，不仅可以提高效率而且不会导致非公平锁带来的一直执行某个线程，比如一直put,直到达到队列满了，才执行take
 **/