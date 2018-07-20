package netty.delimiter;


import common.util.JsonMessageDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class DelimiterServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) {

        byte delimiter = 0x03;
        ByteBuf byteBuf = Unpooled.buffer(1);
        byteBuf.writeByte(delimiter);

        ch.pipeline().addLast(
                //分隔符解码器，以JSON后的0x03为分隔符进行帧分割，最大长度6KB
                new DelimiterBasedFrameDecoder(6144,byteBuf),
                //字符串识别，将比特流转换成字符串，可选传入字符串编码参数
                new StringDecoder(),
                //JSON处理器，用于把JSON字符串转成对象
                new JsonMessageDecoder(),
                //处理网关注册请求、连接后登录网关、心跳信息以及session和连接池管理
                new DelimiterServerHandler()

        );
    }
}
