package netty.delimiter;

import common.constants.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class DelimiterClientHandler extends ChannelInboundHandlerAdapter {

    private static final String text = "白日依山尽，\n黄河入海流。\n欲穷千里目，\n更上一层楼。\n";

//    private static final String text = "{a:1,b:2}{c:3,d:4}";
    /**
     * 建立连接时
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("建立连接时："+new Date());
        ctx.fireChannelActive();
        int length = text.length();
        for(int i = 0; i < length; i++){
            byte[] req  = (text.charAt(i) + "").getBytes();
            ByteBuf message = Unpooled.buffer(req.length).writeBytes(req);
            ctx.writeAndFlush(message);
        }
        byte[] del = Constants.DELIMITER.getBytes();
        ByteBuf msg = Unpooled.buffer(del.length).writeBytes(del);
        ctx.writeAndFlush(msg);

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
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
