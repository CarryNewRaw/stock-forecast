package com.ls.stockforecast.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ls.stockforecast.core.web.response.ErrorBody;
import com.ls.stockforecast.core.web.response.SuccessBody;
import com.ls.stockforecast.utils.constant.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaokai on 2016/10/1.
 */
public class BaseController {

    protected ResponseEntity handleResult() {
        return new ResponseEntity(HttpStatus.OK);
    }

    protected ResponseEntity handleResult(Object result) {
        if(result instanceof ErrorBody) {
            ErrorBody errorBody = (ErrorBody) result;
            switch (errorBody.getErrcode()) {
                case ErrorCode.ERROR_400:
                    return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
                case ErrorCode.ERROR_401:
                    return new ResponseEntity(result, HttpStatus.UNAUTHORIZED);
                case ErrorCode.ERROR_403:
                    return new ResponseEntity(result, HttpStatus.FORBIDDEN);
                case ErrorCode.ERROR_404:
                    return new ResponseEntity(result, HttpStatus.NOT_FOUND);
                case ErrorCode.ERROR_409:
                    return new ResponseEntity(result, HttpStatus.CONFLICT);
                case ErrorCode.ERROR_410:
                    return new ResponseEntity(result, HttpStatus.GONE);
                case ErrorCode.ERROR_500:
                    return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
            }
        } else if(result instanceof SuccessBody) {
            return ResponseEntity.ok(result);
        } else if(result==null || result instanceof Map) {
            if(result==null || ((Map) result).isEmpty()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }
        return ResponseEntity.ok(result);
    }

//    protected ResponseEntity handleResult(Object result, HttpStatus errStatus) {
//        if(result instanceof ErrorBody) {
//            return new ResponseEntity(result, errStatus);
//        }
//        return ResponseEntity.ok(result);
//    }

    protected ResponseEntity handleResult(HttpStatus httpStatus) {
        return new ResponseEntity(httpStatus);
    }

    /**
     * 判断JsonNode是否有参数
     * @param node
     * @param paramName
     * @return
     */
    protected boolean hasJsonParam(JsonNode node, String paramName){
        if(node==null || StringUtils.isEmpty(paramName)) return false;
        if(node.has(paramName) && !node.get(paramName).isNull()){
            return true;
        }
        return false;
    }

    /**
     * 判断JsonNode是否有参数
     * @param node
     * @param paramName
     * @return
     */
    protected boolean hasJsonNotEmptyStringParam(JsonNode node, String paramName){
        if(node==null || org.apache.commons.lang3.StringUtils.isEmpty(paramName)) return false;
        if(node.has(paramName) && !node.get(paramName).isNull() && !"".equals(node.get(paramName).asText())){
            return true;
        }
        return false;
    }

    /**
     * map形式接收request
     * @param request
     * @return
     */
    public static Map<String, Object> getParamMapByRequest(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        Enumeration<String> enumeration = request.getParameterNames();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getParameter(key);
                paramMap.put(key, value);
            }
        }
        return paramMap;
    }
}
