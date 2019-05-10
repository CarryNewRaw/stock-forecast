package com.ls.stockforecast.web.utils;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by CharloYao on 2017/6/14.
 */
public class JsonUtils {

    /**
     * 判断JsonNode是否有参数
     * @param node
     * @param paramName
     * @return
     */
    public static boolean hasJsonParam (JsonNode node, String paramName){
        if(node==null || org.apache.commons.lang3.StringUtils.isEmpty(paramName)) return false;
        if(node.has(paramName) && node.get(paramName)!=null){
            return true;
        }
        return false;
    }

    public static boolean hasNotJsonParam(JsonNode node, String paramName){
        if(hasJsonParam(node,paramName)){
            return false;
        }
        return true;
    }
}
