package netty.heartbeat;

import common.constants.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        EventLoopGroup wordgroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group, wordgroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new NettyServerInitializer());
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(Constants.HEARTBEATPORT).sync();
            System.out.println("服务端启动成功,端口是:"+Constants.HEARTBEATPORT);
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
            wordgroup.shutdownGracefully();
        }
    }
}
