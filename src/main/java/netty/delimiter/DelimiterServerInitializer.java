package netty.delimiter;


import common.constants.Constants;
import common.util.JsonMessageDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class DelimiterServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) {

        ByteBuf byteBuf = Unpooled.copiedBuffer(Constants.DELIMITER.getBytes());

        ch.pipeline().addLast(
                //分隔符解码器，以JSON后的0x03为分隔符进行帧分割，最大长度6KB
                new DelimiterBasedFrameDecoder(6144,byteBuf),

                //字符串识别，将比特流转换成字符串，可选传入字符串编码参数
                new StringDecoder(),
                new StringEncoder(),
                new DelimiterServerHandler()

        );
    }
}
