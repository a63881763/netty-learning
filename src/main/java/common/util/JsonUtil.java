package common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import common.util.JsonDeserializers.ByteDeserializer;
import common.util.JsonDeserializers.LocalTimeDeserializer;
import common.util.JsonSerializers.ByteSerializer;
import common.util.JsonSerializers.LocalDateSerializer;
import common.util.JsonSerializers.LocalDateTimeSerializer;
import common.util.JsonSerializers.LocalTimeSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class JsonUtil {

	private static final ObjectMapper mapper;

	static {

		mapper = new ObjectMapper();

		SimpleModule module = new SimpleModule();
		module.addSerializer(LocalDate.class, new LocalDateSerializer());
		module.addSerializer(LocalTime.class, new LocalTimeSerializer());
		module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
		module.addSerializer(Byte.class, new ByteSerializer());
		module.addDeserializer(LocalTime.class,new LocalTimeDeserializer());
		module.addDeserializer(Byte.class,new ByteDeserializer());
		mapper.registerModule(module);
		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("转换json字符失败!");
		}
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException("将json字符转换为对象时失败!");
		}
	}

	/**
     * 获取Json映射器
	 *
	 * @return
	 */
	public static ObjectMapper getMapper() {

		// 日期格式
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 序列化时忽略值为null的字段
		mapper.setSerializationInclusion(Include.NON_NULL);
		// 反序列化时忽略对象没有的字段
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper;
	}

	/**
	 * 获取Json映射器，禁用注解
	 * 
	 * @return
	 */
	@Deprecated
	public static ObjectMapper getSimpleMapper() {

		ObjectMapper disableAnnotationsMapper = new ObjectMapper();
		// 日期格式
		disableAnnotationsMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 序列化时忽略值为null的字段
		disableAnnotationsMapper.setSerializationInclusion(Include.NON_NULL);
		// 反序列化时忽略对象没有的字段
		disableAnnotationsMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 禁用注解
		disableAnnotationsMapper.configure(MapperFeature.USE_ANNOTATIONS, false);

		return disableAnnotationsMapper;
	}
}