package com.jintao.example.nettytest;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
/***
 * 可以在命令行里 telnet 127.0.0.1 8099
 * 然后输入一些字符，观察服务端的控制台日志
 * 不建议使用3.x的版本，以后测试采用4.x版本以上
 */

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/4/18
 * Time:11:26
 */
public class NettyServer {
    final static int port = 8099;

    public static void main(String[] args) {
        Server server = new Server();
        server.config(port);
        server.start();
    }


    private static class Server {
        ServerBootstrap bootstrap;
        Channel parentChannel;
        InetSocketAddress localAddress;
        MyChannelHandler channelHandler = new MyChannelHandler();

        Server() {
            bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                    Executors.newCachedThreadPool(), Executors
                    .newCachedThreadPool()));
            bootstrap.setOption("reuseAddress", true);
            bootstrap.setOption("child.tcpNoDelay", true);
            bootstrap.setOption("child.soLinger", 2);
            bootstrap.getPipeline().addLast("servercnfactory", channelHandler);
        }

        void config(int port) {
            this.localAddress = new InetSocketAddress(port);
        }

        void start() {
            parentChannel = bootstrap.bind(localAddress);
        }

        class MyChannelHandler extends SimpleChannelHandler {

            @Override
            public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
                    throws Exception {
                System.out.println("Channel closed " + e);
            }

            @Override
            public void channelConnected(ChannelHandlerContext ctx,
                                         ChannelStateEvent e) throws Exception {
                System.out.println("Channel connected " + e);
            }

            @Override
            public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
                    throws Exception {
                try {
                    e.getMessage();
                    System.out.println("New message " + e.toString() + " from "
                            + ctx.getChannel());
                    processMessage(e);
                    byte[] bytes = ((ChannelBuffer) e.getMessage()).array();
//                    ((ChannelBuffer) e.getMessage()).clear();
                    System.out.println("Received Byte:" + new String(bytes,"utf-8"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }

            private void processMessage(MessageEvent e) {
                Channel ch = e.getChannel();
                ch.write(e.getMessage());
            }
        }
    }
}

