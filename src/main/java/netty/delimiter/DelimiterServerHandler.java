package netty.delimiter;

import common.constants.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class DelimiterServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 建立连接时
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("建立连接时："+new Date());
//        ctx.fireChannelActive();
    }

    /**
     * 关闭连接时
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("关闭连接时："+new Date());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("=============");
        System.out.println(msg.toString());
        byte[] req  = ("Hello Client"+Constants.DELIMITER).getBytes();
        ByteBuf message = Unpooled.buffer(req.length).writeBytes(req);
        ctx.writeAndFlush(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
