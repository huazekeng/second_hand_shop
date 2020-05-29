package com.mrli.second_shop.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.StringWriter;

/**
 * @author narvik.su
 */
public class JsonUtils {
    //线程安全，可以单例
    private static ObjectMapper commonMapper;
    //带驼峰转换的mapper,尽管Jackson线程安全,但是为了方便配置暂时新起一个实例去做
    private static ObjectMapper camelMapper;

    static {
        commonMapper = new ObjectMapper();
        //忽略实体没定义的字段
        commonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        camelMapper = new ObjectMapper();
        //忽略实体没定义的字段
        camelMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //驼峰配置
        camelMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public static ObjectMapper getObjectMapper(boolean castCamel) {
        if (castCamel) {
            return camelMapper;
        } else {
            return commonMapper;
        }
    }

    /**
     * 创建一个{}
     */
    public static ObjectNode createJsonObject() {
        return commonMapper.createObjectNode();
    }

    /**
     * 创建[]
     */
    public static ArrayNode createJsonArray() {
        return commonMapper.createArrayNode();
    }

    /**
     * https://stackoverflow.com/questions/11828368/convert-java-object-to-jsonnode-in-jackson
     * 先转String再转JsonNode，如果需要转ObjectNode或者ArrayNode，外面自己强转
     * String类型看{@link #json2Bean(String, Class)}
     */
    public static JsonNode bean2JsonNode(Object obj) {
        try {
            if (obj instanceof String) {
                throw new IllegalAccessException("source must not be String");
            }
            String jsonStr = bean2Json(obj);
            return json2Bean(jsonStr, JsonNode.class);
        } catch (Exception e) {
            throw new RuntimeException("convert error", e);
        }
    }

    /**
     * String类型看{@link #json2Bean(String, Class)}
     */
    public static String bean2Json(Object obj) {
        try {
            if (obj instanceof String) {
                throw new IllegalAccessException("source must not be String");
            }
            StringWriter sw = new StringWriter();
            JsonGenerator gen = new JsonFactory().createGenerator(sw);
            commonMapper.writeValue(gen, obj);
            gen.close();
            return sw.toString();
        } catch (Exception e) {
            throw new RuntimeException("convert error", e);
        }
    }

    public static <T> T bean2Bean(Object obj, Class<T> objClass) {
        return json2Bean(bean2Json(obj), objClass, false);
    }

    public static <T> T bean2Bean(Object obj, Class<T> objClass, boolean castCamel) {
        return json2Bean(bean2Json(obj), objClass, castCamel);
    }

    public static <T> T bean2Bean(Object obj, TypeReference<?> valueTypeRef) {
        return json2Bean(bean2Json(obj), valueTypeRef, false);
    }

    public static <T> T bean2Bean(Object obj, TypeReference<?> valueTypeRef, boolean castCamel) {
        return json2Bean(bean2Json(obj), valueTypeRef, castCamel);
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return json2Bean(jsonStr, objClass, false);
    }

    public static <T> T json2Bean(String jsonStr, TypeReference<?> valueTypeRef) {
        return json2Bean(jsonStr, valueTypeRef, false);
    }

    /**
     * 带泛型复杂类型转换
     */
    @SuppressWarnings("Duplicates")
    public static <T> T json2Bean(String jsonStr, TypeReference<?> valueTypeRef, boolean castCamel) {
        try {
            if (castCamel) {
                return camelMapper.readValue(jsonStr, valueTypeRef);
            } else {
                return commonMapper.readValue(jsonStr, valueTypeRef);
            }
        } catch (Exception e) {
            throw new RuntimeException("convert error", e);
        }
    }

    /**
     * 一般的转换
     */
    @SuppressWarnings("Duplicates")
    public static <T> T json2Bean(String jsonStr, Class<T> objClass, boolean castCamel) {
        try {
            if (castCamel) {
                return camelMapper.readValue(jsonStr, objClass);
            } else {
                return commonMapper.readValue(jsonStr, objClass);
            }
        } catch (Exception e) {
            throw new RuntimeException("convert error", e);
        }
    }


}