package com.jintao.example.lock.condition;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/4/11
 * Time:10:35
 * 使用lock/condition实现生产者消费者模式
 */
public class Buffer {
}


/**
 * Condition
 * 那么引入本篇的主角，Condition，Condition 将 Object的通信方法（wait、notify 和 notifyAll）分解成截然不同的对象，以便通过将这些对象与任意 Lock 实现组合使用，
 * 为每个对象提供多个等待 set （wait-set）。其中，Lock 替代了 synchronized 方法和语句的使用，Condition 替代了 Object 通信方法的使用。
 * 在Condition中，用await()替换wait()，用signal()替换notify()，用signalAll()替换notifyAll()，传统线程的通信方式，Condition都可以实现，
 * 这里注意，Condition是被绑定到Lock上的，要创建一个Lock的Condition必须用newCondition()方法。
 * Condition的强大之处在于它可以为多个线程间建立不同的Condition， 使用synchronized/wait()只有一个阻塞队列，notifyAll会唤起所有阻塞队列下的线程，
 * 而使用lock/condition，可以实现多个阻塞队列，signalAll只会唤起某个阻塞队列下的阻塞线程。
 **/