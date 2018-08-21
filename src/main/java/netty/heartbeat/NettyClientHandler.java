package netty.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.util.Date;

public class NettyClientHandler extends  ChannelInboundHandlerAdapter{
    /** 客户端请求的心跳命令  */
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("heartbeat_request",
            CharsetUtil.UTF_8));

    /** 空闲次数 */
    private int idle_count = 1;

    /** 发送次数 */
    private int count = 1;


    /**
     * 建立连接时
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("建立连接时：" + new Date());
        ctx.fireChannelActive();
        String str = "Hello Netty";
        ctx.writeAndFlush(str);
        System.out.println("客户端发送数据:" + str);
    }

    /**
     * 关闭连接时
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("关闭连接时：" + new Date());
    }

    /**
     * 心跳请求处理
     * 每4秒发送一次心跳请求;
     *
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {

        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            //如果写通道处于空闲状态,就发送心跳命令
            if (IdleState.WRITER_IDLE.equals(event.state())) {
                //设置发送次数
                if(idle_count <= 3){
                    ctx.channel().writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());
                    System.out.println("发送心跳，次数"+idle_count);
                    idle_count++;
                }else{
                    System.out.println("不再发送心跳请求了！");
                }

            }
        }
    }

    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("第" + count + "次" + ",客户端接受的消息:" + msg);
        count++;
    }
}
