package netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import pojo.SubscribeReqProto;
import pojo.SubscribeRespProto;

import java.util.ArrayList;
import java.util.List;

public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

    private static volatile int index = 0;

    public SubReqClientHandler(){
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(subReq(index));
    }

    private SubscribeReqProto.SubscribeReq subReq(int i){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("liwenliang");
        builder.setProductName("Max book");
        List<String> address = new ArrayList<>();
        address.add("Beijing a");
        address.add("Shanghai b");
        address.add("Hangzhou c");
        builder.addAllAddress(address);
        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeRespProto.SubscribeResp resp = (SubscribeRespProto.SubscribeResp) msg;
        System.out.println("Receive server response : [" + msg + "]");
        if(index < 10){
            ctx.writeAndFlush(subReq(++index));
            Thread.sleep(1000);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
