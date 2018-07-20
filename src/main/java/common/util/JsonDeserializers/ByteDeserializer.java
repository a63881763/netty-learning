package common.util.JsonDeserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by jiangyuyi on 2017/11/1.
 */
public class ByteDeserializer extends JsonDeserializer<Byte> {

    @Override
    public Byte deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Byte t = 1;
        Byte f = 0;
        return jsonParser.getBooleanValue()?t:f;
    }
}
