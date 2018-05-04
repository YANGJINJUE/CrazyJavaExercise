package com.jintao.example.executorsocket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/5/4
 * Time:15:37
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        OutputStream out = null;
        try {
            socket = new Socket("localhost", 9991);
            out = socket.getOutputStream();

            for (int i = 0; i < 100; i++) {
                out.write(("haha" + i+"\n").getBytes());
            }
        } finally {
            if (out != null)
                out.close();
            if(socket!=null)
                socket.close();
        }

    }
}
