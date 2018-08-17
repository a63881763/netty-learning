import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;
import pojo.SubscribeReqProto;

import java.util.ArrayList;
import java.util.List;

public class TestProto {

    private static byte[] encode(SubscribeReqProto.SubscribeReq req){
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq(){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("liwenliang");
        builder.setProductName("Max book");
        List<String> address = new ArrayList<>();
        address.add("Beijing a");
        address.add("Shanghai b");
        address.add("Hangzhou c");
        builder.addAllAddress(address);
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("Before encode : " + req.toString());
        SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
        System.out.println("After decode : " + req.toString());
        System.out.println("Assert equal :  --> " + req2.equals(req));
    }

    @Test
    public void test1(){

    }
}
