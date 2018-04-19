package com.jintao.example.nettytest.discardserver;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/4/18
 * Time:13:12
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 处理服务端 channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 默默地丢弃收到的数据
        ByteBuf in = ((ByteBuf) msg);
        try {
//            System.out.print((char) in.readByte());
            System.out.print(in.toString(CharsetUtil.US_ASCII));
            System.out.flush();//可保证输出立即打印
        } finally {
//            ReferenceCountUtil.release(msg);
            in.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
