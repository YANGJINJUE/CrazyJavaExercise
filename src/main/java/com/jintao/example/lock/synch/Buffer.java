package com.jintao.example.lock.synch;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/4/11
 * Time:10:35
 * 使用synchronized/wait()实现生产者消费者模式
 */
public class Buffer {
    private int maxSize;
    private List<Date> storage;

    public Buffer(int size) {
        this.maxSize = size;
        storage = new LinkedList<Date>();
    }

    /**
     * 生产方法
     */
    public synchronized void put() {
        try {
            while (storage.size() == maxSize) {
                //如果队列满了
                System.out.println(Thread.currentThread().getName() + " :wait\n");
                wait();//阻塞
            }
            storage.add(new Date());
            System.out.println(Thread.currentThread().getName() + ": put" + storage.size() + "\n");
            Thread.sleep(1000);
            notifyAll();//唤起线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费方法
     */
    public synchronized void take(){
        try {
            while (storage.size() == 0) {
                //如果队列空了
                System.out.println(Thread.currentThread().getName() + " :wait\n");
                wait();//阻塞
            }
            ((LinkedList)storage).poll();//移除队列头部元素
            System.out.println(Thread.currentThread().getName() + ": take" + storage.size() + "\n");
            Thread.sleep(1000);
            notifyAll();//唤起线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
