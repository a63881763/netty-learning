package netty.delimiter;


import common.constants.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class DelimiterClient {


    public void connect(String host, int port) throws InterruptedException {

        ByteBuf byteBuf = Unpooled.copiedBuffer(Constants.DELIMITER.getBytes());

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline p = ch.pipeline();
//                            p.addLast(new DelimiterBasedFrameDecoder(6144, byteBuf));
                            p.addLast(new StringDecoder());
                            p.addLast(new DelimiterClientHandler());
                        }
                    });

            Channel channel = b.connect(host, port).sync().channel();

            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {

        new DelimiterClient().connect("127.0.0.1", Constants.DELIMITERPORT);
    }
}