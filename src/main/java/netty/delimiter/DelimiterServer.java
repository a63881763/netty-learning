package netty.delimiter;


import common.constants.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 *
 * @author Ricky
 *
 */
public class DelimiterServer implements Runnable{


    /**
     * HomeDot的Netty Socket服务器的主入口，运行run即可，可以传入一个端口号参数
     */
    @Override
    public void run(){

        Integer homeDotPort = Constants.DELIMITERPORT;
        //Integer homeDotPort = 5600;

        System.err.println("Start server at port:" + homeDotPort);

        // Configure the bootstrap.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new DelimiterServerInitializer())
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .bind(homeDotPort).sync().channel().closeFuture().sync();
        }catch (Exception e){

        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args){

        DelimiterServer delimiterServer = new DelimiterServer();
        delimiterServer.run();

    }
}