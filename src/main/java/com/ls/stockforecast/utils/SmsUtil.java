package com.ls.stockforecast.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class SmsUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);
	public static final String SMS_MODULE_ID = null;
	
	public static boolean send(String telephone,String[] params){
        return send(telephone,params,SMS_MODULE_ID);
    }

    public static boolean send(String telephone,String[] params,String moduleId){
        return send(telephone, params, moduleId, CacheUtils.getSettingStringValue(SysConstant.COMMON_MODULE, SysConstant.COMMON_APP_SMS_CODE));
    }

    public static boolean send(String telephone,String[] params,String moduleId, String appCode){
        boolean result = true;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("telephone", telephone);
            paramMap.put("module_code", moduleId);
            paramMap.put("message", params);
            String jsonParam = objectMapper.writeValueAsString(paramMap);
            logger.info("SmsUtil send param:" + jsonParam);

            HttpEntity entity = new StringEntity(jsonParam, Charset.forName("UTF-8"));
            HttpUriRequest httpPost = RequestBuilder
                    .post(CacheUtils.getSettingStringValue(SysConstant.COMMON_MODULE, SysConstant.COMMON_PLATFORM_SMS_URL))
                    .addHeader("account_sid", CacheUtils.getSettingStringValue(SysConstant.COMMON_MODULE, SysConstant.COMMON_ACCOUNT_SID))
                    .addHeader("auth_token", CacheUtils.getSettingStringValue(SysConstant.COMMON_MODULE, SysConstant.COMMON_AUTH_TOKEN))
                    .addHeader("app_code", appCode)
                    .addHeader("Content-type", "application/json")
                    .setEntity(entity)
                    .build();

            //	处理请求结果
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    logger.info("SmsUtil status:" + status);
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                }
            };
            //	发起API调用
            String responseBody = httpClient.execute(httpPost, responseHandler);
            logger.info("SmsUtil send response:" + responseBody);
            Map<String,Object> tmpMap = objectMapper.readValue(responseBody, Map.class);
            Map<String,String> resultMap = (Map<String, String>) tmpMap.get("result");
            if("000000".equals(resultMap.get("statusCode"))){
                logger.info("SmsUtil send responseOK:" + resultMap.toString());
            }else{
                result = false;
                //异常返回输出错误码和错误信息
                logger.info("SmsUtil send response:" + "errorCode= " + resultMap.get("statusCode") + " errorMsg= " + resultMap.get("statusMsg"));
            }
        } catch (Exception ignore) {
            result = false;
        } finally {
            try {
                httpClient.close();
            } catch (IOException ignore) {
            }
        }
        return result;
    }
	
	public static void sendSMS(){
		send(null,null);
	}
}
