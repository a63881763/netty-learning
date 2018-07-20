package common.util.JsonSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by jiangyuyi on 2017/11/1.
 */
public class ByteSerializer extends JsonSerializer<Byte> {

    @Override
    public void serialize(Byte value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeBoolean(value==1);
    }
}
