package com.ls.stockforecast.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by CharloYao on 2017/6/5.
 */
public class JsonNodeUtils {

    /**
     * JsonNode中的paramName是否为空
     *
     * @param jsonNode
     * @param paramName
     * @return
     */
    public static boolean isEmptyValue(JsonNode jsonNode, String paramName) {
        if (jsonNode == null || !jsonNode.has(paramName) || !jsonNode.hasNonNull(paramName)) return true;
        if (jsonNode.get(paramName).getNodeType() == JsonNodeType.NULL) return true;
        return false;
    }

    /**
     * 判断是否为空
     * @param jsonNode
     * @param paramName1
     * @param paramName2
     * @return
     */
    public static boolean isEmptyValue(JsonNode jsonNode, String paramName1, String paramName2) {
        return isEmptyValue(jsonNode,paramName1) || isEmptyValue(jsonNode, paramName2);
    }

    public static boolean isEmptyValue(JsonNode jsonNode, String paramName1, String paramName2, String paramName3) {
        return (isEmptyValue(jsonNode,paramName1) || isEmptyValue(jsonNode, paramName2) || isEmptyValue(jsonNode, paramName3));
    }

    public static boolean isEmptyValue(JsonNode jsonNode, String paramName1, String paramName2, String paramName3, String... otherName){
        if(isEmptyValue(jsonNode, paramName1, paramName2, paramName3)) return true;
        for(String paraName : otherName){
            if(isEmptyValue(jsonNode, paraName)) return true;
        }
        return false;
    }

    /**
     * JsonNode中的paramName类型是否为ArrayNode并且不为空
     *
     * @return
     */
    public static boolean isEmptyArray(JsonNode jsonNode, String paramName) {
        if (jsonNode == null || !jsonNode.has(paramName)) return true;
        if (!(jsonNode.get(paramName) instanceof ArrayNode) || ((ArrayNode) jsonNode.get(paramName)).size() < 1) return true;
        return false;
    }

    /**
     * JsonNode中的paramName是否不为空
     *
     * @param jsonNode
     * @param paramName
     * @return
     */
    public static boolean  isNotEmptyValue(JsonNode jsonNode, String paramName) {
        return !isEmptyValue(jsonNode, paramName);
    }

    /**
     * 获取JsonNode中的值，如果没有则返回null
     * @param jsonNode
     * @param paramName
     * @return
     */
    public static String getValueAsString(JsonNode jsonNode, String paramName){
        if(JsonNodeUtils.isNotEmptyValue(jsonNode, paramName)){
            return jsonNode.get(paramName).asText();
        }
        return null;
    }

    /**
     * 获取JsonNode中的值，如果没有则返回null
     * @param jsonNode
     * @param paramName
     * @return
     */
    public static Integer getValueAsInteger(JsonNode jsonNode, String paramName){
        if(JsonNodeUtils.isNotEmptyValue(jsonNode, paramName)){
            return jsonNode.get(paramName).asInt();
        }
        return null;
    }

    public static Long getValueAsLong(JsonNode jsonNode, String paramName){
        if(JsonNodeUtils.isNotEmptyValue(jsonNode, paramName)){
            return jsonNode.get(paramName).asLong();
        }
        return null;
    }

    /**
     * 获取JsonNode中的Array类型的值（字符串），如果没有则返回空的List
     * @param jsonNode
     * @param paramName
     * @return
     */
    public static List<String> getValueAsStringList(JsonNode jsonNode, String paramName){
        List<String> resultList = Lists.newArrayList();
        if(JsonNodeUtils.isEmptyValue(jsonNode, paramName)){
            return resultList;
        }
        ArrayNode arrayNode = (ArrayNode) jsonNode.get(paramName);
        for(JsonNode node : arrayNode){
            resultList.add(node.asText());
        }
        return resultList;
    }
}
