package com.jintao.example.nettytest.timeserver;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/4/18
 * Time:13:12
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务端 channel.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 方法将会在连接被建立并且准备进行通信时被调用
     *
     * @param ctx
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        final ChannelFuture f = ctx.writeAndFlush(time);
        f.addListener(channelFuture -> {
            assert f == channelFuture;
            ctx.close();

//            @Override
//            public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                assert f == channelFuture;
//                ctx.close();
//            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
