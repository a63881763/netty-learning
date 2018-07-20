package common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by jiangyuyi on 2017/10/21.
 * 将通过0x03分隔符分割帧之后解码成字符串的数据包传入，进行初步处理，将转换成对应的对象并送入后面的handler处理
 */
public class JsonMessageDecoder extends MessageToMessageDecoder<String> {


    private static ObjectMapper mapper = JsonUtil.getMapper();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) throws Exception {


        if (s.length() > 1) {
            //去掉开头的一个不可见字符0x20
            s = s.substring(1);
        } else {
            return;
        }

        JSONObject jsonObject = new JSONObject(s);


        channelHandlerContext.write(jsonObject);



    }



}
