package netty.im;

import common.util.NameReader;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 处理从客户端发来的消息，核心方法
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel incoming = ctx.channel();
        String name = incoming.attr(AttributeKey.valueOf("username")).get().toString();
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush(new TextWebSocketFrame("[" + name + "]：" + msg.text()));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[本地发送]：" + msg.text()));
                // Console打印，可以删除
                StringBuffer sb = new StringBuffer();
                sb.append(name).append("->").append(msg.text());
                System.out.println(sb.toString());
            }
        }
    }

    // 有新通道加入的时候响应
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        String name = NameReader.getInstance().getName();
        incoming.attr(AttributeKey.valueOf("username")).set(name);
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + name + " 加入"));
        }
        channels.add(ctx.channel());
        System.out.println("Client:" + incoming.remoteAddress() + name + "加入");
    }

    // 有通道关闭时响应
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        String name = incoming.attr(AttributeKey.valueOf("username")).get().toString();
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + name + " 离开"));
        }
        System.out.println("Client:" + incoming.remoteAddress() + name + "离开");
        channels.remove(ctx.channel());
    }

    // 通道激活时响应
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:" + incoming.remoteAddress() + "在线");
    }

    // 通道关闭时响应
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:" + incoming.remoteAddress() + "掉线");
    }

    // 异常时响应
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:" + incoming.remoteAddress() + "异常");

        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
