package com.jintao.example.nettytest.echoserver;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/4/18
 * Time:13:12
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务端 channel.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //回应客户端,这里并没有调用release这是因为我们在写入的时候,netty已经帮我们释放了
        ctx.write(msg);
        ctx.flush();
//      ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
