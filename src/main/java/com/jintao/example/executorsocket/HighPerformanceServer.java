package com.jintao.example.executorsocket;

import io.netty.util.internal.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 高性能服务器
 * https://blog.csdn.net/lmj623565791/article/details/26938985
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/5/4
 * Time:15:32
 */
public class HighPerformanceServer {
    private static final int THREAD_COUNT = 100;
    private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9991);
        while (true) {
            final Socket client = serverSocket.accept();
            //可同时并发100个线程，无需等待，无需多次开启线程，利用线程池提高效率
            executor.execute(() -> handleReq(client));
        }
    }

    private static void handleReq(Socket client) {
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String result = buffer.readLine();
            while (!StringUtil.isNullOrEmpty(result)) {
                System.out.println(result);
                result = buffer.readLine();
                Thread.sleep(2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (null != buffer)
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
