package com.jintao.example.lock.condition;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/4/11
 * Time:10:47
 * 使用lock/condition实现生产者消费者模式测试
 */
public class BufferTest {
    public static void main(String[] arg) {
        Buffer buffer = new Buffer(30);
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        //创建线程执行生产和消费
        for (int i = 0; i < 3; i++) {
            new Thread(producer, "producer-" + i).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(consumer, "consumer-" + i).start();
        }

    }
}


//生产者
class Producer implements Runnable {
    private Buffer buffer;

    Producer(Buffer b) {
        buffer = b;
    }

    @Override
    public void run() {
        while (true) {
            buffer.put();
        }
    }
}

//消费者
class Consumer implements Runnable {
    private Buffer buffer;

    Consumer(Buffer b) {
        buffer = b;
    }

    @Override
    public void run() {
        while (true) {
            buffer.take();
        }
    }
}

