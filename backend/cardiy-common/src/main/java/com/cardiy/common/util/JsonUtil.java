/**
 * Project Name: spms-client-java
 * File Name: JSONUtils.java
 * Package Name: com.migu.spms.util
 * Class Name: com.migu.spms.util.JSONUtils
 * Date: 2018年8月8日 下午5:23:11
 *
 * All rights Reserved, Designed By MiGu
 * Copyright: Copyright(C) 2016-2020
 * Company: MiGu  Co., Ltd.
*/
package com.cardiy.common.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * JSON转换工具类.
 * @date 2018年8月8日 下午5:23:11
 * @since JDK 1.8
 * @author ge.fangyu
 * @version 1.0
*/
@Slf4j
public final class JsonUtil {
	private static final int MAX_LOG_LENGTH = 2048;

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * parseObj2JSON: 指定的对象实例转成json.
	 *
	 * @param obj
	 * @return
	 * @date 2018年8月8日 下午5:23:43
	 * @author ge.fangyu
	 */
	public static String parseObj2JSON(Object obj) {
		if (obj == null) {
			return "";
		}
		try {
			return OBJECT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("=> parse obj to json ===> 发生异常: {}", e.getMessage(), e);
		}
		return "";
	}

	/**
	 * parseJSON2Bean: 将给定的字符串转换成对应的类对象,并返回这个类的实例.
	 *
	 * @param jsonStr
	 * @param cls
	 * @return
	 * @date 2018年8月8日 下午6:48:23
	 * @author ge.fangyu
	 */
	public static <T> T parseJSON2Bean(String jsonStr, Class<T> cls) {
		if (StringUtils.isEmpty(jsonStr) || cls == null) {
			return null;
		}
		try {
			return OBJECT_MAPPER.readValue(jsonStr, cls);
		} catch (Exception e) {
			log.error("parse json to bean ===> 发生异常: {}", e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Description: 将json字符串转换成对应的list对象集合.
	 *
	 * @param jsonData
	 * @param beanType
	 * @return
	 * @date 2020年12月17日 下午2:57:12
	 * @author ge.fangyu
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			List<T> list = objectMapper.readValue(jsonData, javaType);
			return list;
		} catch (Exception e) {
			log.error("jsonToList ===> 发生异常: {}", e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Description: 将json字符串转换为对应的泛型对象.(parameterClasses是具体的不能是泛型,是基础具体的类).
	 * 	返回类型是: List<Set<Integer>>}, you could
	 *  	JavaType inner = TypeFactory.constructParametricType(Set.class, Set.class, Integer.class);
	 *  	TypeFactory.constructParametricType(ArrayList.class, List.class, inner);
	 *
	 *   例子: WalleAppReponse<String> result = JsonTool.jsonToObject(data, WalleAppReponse.class, String.class)
	 * @param data
	 * @param parametrized
	 * @param parameterClasses
	 * @return
	 * @date 2021年1月13日 下午4:38:57
	 * @author ge.fangyu
	 */
	public static <T> T jsonToObject(String data, Class<?> parametrized, Class<?>... parameterClasses) {
		ObjectMapper objectMapper = new ObjectMapper();
		T result = null;
		try {
			objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			JavaType javaType = objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
			result = objectMapper.readValue(data, javaType);
		} catch (Exception e) {
			log.error("jsonToObject ===> 发生异常: {}", e.getMessage(), e);
		}
		return result;
	}

	/**
	 *  将json字符串转化为复杂对象.
	 *  例如: GKSystemCommonResp<List<GKActivityResp>>
	 *         JsonTool.jsonToObject(str, new TypeReference<AllmediaSynchMsg<SynchMcnPoms>>(){})
	 * @param data
	 * @param valueTypeRef
	 * @return T
	 * @author ge.fangyu
	 * @date 2021-04-25 09:49:13
	 **/
	public static <T> T jsonToObject(String data, TypeReference<T> valueTypeRef) {
		ObjectMapper objectMapper = new ObjectMapper();
		T result = null;
		try {
			objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			result = objectMapper.readValue(data, valueTypeRef);
		} catch (Exception e) {
			log.error("jsonToObject ===> 发生异常: {}", e.getMessage(), e);
		}
		return result;
	}

	public static String dataToJson(Object data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, data);
			return sw.toString();
		} catch (IOException e) {
			log.error("dataToJson ==> 发生异常: {}", e.getMessage(), e);
			throw new RuntimeException("IOException from a StringWriter?", e);
		}
	}

	/**
	 * Desc: map Class to json String with null
	 * @return json string
	 */
	public static String dataToJsonWithNull(Object data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			//mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw,data);
			return sw.toString();
		} catch (IOException e) {
			log.error("dataToJsonWithNull ===> 发生异常: {}", e.getMessage(), e);
			throw new RuntimeException("IOException from a StringWriter?" , e);
		}
	}

	/**
	 　*  指定对象生成Json，排除null与空字符串。即对应属性值为null或空字符串不会输出该属性.
	 * @param data
	　* @return java.lang.String
	　* @author ge.fangyu
	　* @date 2021-12-20 17:31:02
	 **/
	public static String dataToNotBlankJson(Object data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, data);
			return sw.toString();
		} catch (IOException e) {
			log.error("=> dataToNotBlankJson ===> 发生异常: {}", e.getMessage(), e);
			throw new RuntimeException("IOException from a StringWriter?" , e);
		}
	}

	/**
	 * Desc: parse json string to map
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(String data) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(data, Map.class);
		} catch (Exception e) {
			log.error("readValue ===> 发生异常: {}", e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Desc: parse Json to dedicated class
	 *
	 * @param data:  Json string
	 * @param valueClass: object.Class
	 * @param fullmap: ignore empty properties if false
	 * @return
	 */
	public static <V> V jsonToObject(String data, Class<V> valueClass, boolean fullmap) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			if (!fullmap) {
				objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
				objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			}
			return objectMapper.readValue(data, valueClass);
		} catch (Exception e) {
			log.error("jsonToObject ===> 发生异常: {}", e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Desc: parse Json to dedicated class
	 *
	 * @param data:  Json string
	 * @param valueClass: object.Class
	 * @return
	 */
	public static <V> V jsonToObject(String data, Class<V> valueClass) {
		return jsonToObject(data, valueClass,false);
	}

	/**
	 * Desc:
	 *
	 * @param typeReference
	 * @param fullmap
	 * @return
	 */
	public static Object setJsonToObject(String data, TypeReference<?> typeReference, boolean fullmap) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			if (!fullmap) {
				objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			}
			return objectMapper.readValue(data, typeReference);
		} catch (Exception e) {
			log.error("=> setJsonToObject ===> 发生异常: {}", e.getMessage(), e);
		}
		return null;
	}

	/**
	 * @Author li.hui
	 * 日志打印对象截取
	 * @Date 2025/11/21 11:45
	 * @Param
	 * @Return
	 */
	public static String getJsonSubString(Object obj) {
		if (Objects.isNull(obj)) {
			return null;
		}
		String body = JSON.toJSONString(obj);
		return body.length() > MAX_LOG_LENGTH ? "[log too long]! " + body.substring(0, MAX_LOG_LENGTH) : body;
	}
}
