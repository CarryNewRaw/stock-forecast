package com.ls.stockforecast.web.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ls.stockforecast.core.web.response.ErrorBody;
import com.ls.stockforecast.core.web.response.ResponseBody;
import com.ls.stockforecast.core.web.response.SuccessBody;
import com.ls.stockforecast.utils.EnumUtils;
import com.ls.stockforecast.utils.SettingUtils;
import com.ls.stockforecast.utils.SysConstant;
import com.ls.stockforecast.utils.constant.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class WxUtils {
	
	private static final Logger log = LoggerFactory.getLogger(WxUtils.class);

	/**
	 * 获取模版详细列表
	 * @param wxid
	 * @param templateId
	 * @return
	 */
	public static String getWxTemplate(String wxid, String templateId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String responseBody = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> paramMap = new HashMap<>();
			if (wxid != null) paramMap.put("wx_id", wxid);
			if (templateId != null) paramMap.put("template_id", templateId);
			String jsonParam = objectMapper.writeValueAsString(paramMap);

			HttpEntity entity = new StringEntity(jsonParam, Charset.forName("UTF-8"));

			// 设置请求超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(2000).setConnectionRequestTimeout(1000)
					.setSocketTimeout(2000).build();

			HttpUriRequest httpPost = RequestBuilder
					.post(SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_PLATFORM_URL) + "/wx/template/getList")
					.addHeader("Content-type", "application/json; charset=utf-8")
					.setConfig(requestConfig)
					.setEntity(entity)
					.build();


			//	处理请求结果
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					//System.out.println(status);
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}
			};
			//	发起API调用
			responseBody = httpClient.execute(httpPost, responseHandler);

//			System.out.println(responseBody);
			return responseBody;

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException ioe) {
				log.error(ioe.getMessage());
			}
		}
		return null;
	}

	/**
	 * 获取模版KeyValue
	 * @param wxid
	 * @return
	 */
	public static String getWxTemplateList(String wxid, Integer mayManual) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String responseBody = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> paramMap = new HashMap<>();
			if (wxid != null) paramMap.put("wx_id", wxid);
			if (mayManual != null) paramMap.put("may_manual", mayManual);
			String jsonParam = objectMapper.writeValueAsString(paramMap);

			HttpEntity entity = new StringEntity(jsonParam, Charset.forName("UTF-8"));

			// 设置请求超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(2000).setConnectionRequestTimeout(1000)
					.setSocketTimeout(2000).build();

			HttpUriRequest httpPost = RequestBuilder
					.post(SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_PLATFORM_URL) + "/wx/template/getKeyValue")
					.addHeader("Content-type", "application/json; charset=utf-8")
					.setConfig(requestConfig)
					.setEntity(entity)
					.build();


			//	处理请求结果
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					//System.out.println(status);
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}
			};
			//	发起API调用
			responseBody = httpClient.execute(httpPost, responseHandler);

//			System.out.println(responseBody);
			return responseBody;

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException ioe) {
				log.error(ioe.getMessage());
			}
		}
		return null;
	}

	/**
	 * 发送模版消息
	 * @param template_id 模版id
	 * @param open_ids 微信openids 数组
	 * @param values 模版值 数组
	 * @param url 模版跳转url，选填
	 * @return
	 */
	public static String sendTemplateMessage(String wxid, String template_id, String[] open_ids, String[] values, String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String responseBody = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("template_id", template_id);
			paramMap.put("open_ids", open_ids);
			paramMap.put("values", values);
			if (StringUtils.isNotEmpty(url)) paramMap.put("url", url);
			String jsonParam = objectMapper.writeValueAsString(paramMap);
			HttpEntity entity = new StringEntity(jsonParam, Charset.forName("UTF-8"));

			// 设置请求超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(2000).setConnectionRequestTimeout(1000)
					.setSocketTimeout(2000).build();

			HttpUriRequest httpPost = RequestBuilder
					.post(SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_PLATFORM_URL) + "/wx/template/sendTemplateMessage")	// todo 地址修改
					.addHeader("account_sid", SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_ACCOUNT_SID)) //$NON-NLS-1$
					.addHeader("auth_token", SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_AUTH_TOKEN)) //$NON-NLS-1$
					.addHeader("app_code", switchAppCode(wxid)) //$NON-NLS-1$
					.addHeader("Content-type", "application/json; charset=utf-8")
					.setConfig(requestConfig)
					.setEntity(entity)
					.build();


			//	处理请求结果
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					//System.out.println(status);
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}
			};
			//	发起API调用
			responseBody = httpClient.execute(httpPost, responseHandler);

//			System.out.println(responseBody);
			return responseBody;

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException ioe) {
				log.error(ioe.getMessage());
			}
		}
		return null;
	}

	/**
	 * 获取模版消息记录（for web）
	 * @param jsonParam
	 * @return
	 */
	public static String getTemplateMsgRecord(String jsonParam) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String responseBody = null;
		try {
			HttpEntity entity = new StringEntity(jsonParam, Charset.forName("UTF-8"));

			// 设置请求超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(2000).setConnectionRequestTimeout(1000)
					.setSocketTimeout(2000).build();

			HttpUriRequest httpPost = RequestBuilder
					.post(SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_PLATFORM_URL) + "/wx/template/getTemplateMsgList")
					.addHeader("Content-type", "application/json; charset=utf-8")
					.setConfig(requestConfig)
					.setEntity(entity)
					.build();


			//	处理请求结果
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					//System.out.println(status);
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}
			};
			//	发起API调用
			responseBody = httpClient.execute(httpPost, responseHandler);

//			System.out.println(responseBody);
			return responseBody;

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException ioe) {
				log.error(ioe.getMessage());
			}
		}
		return null;
	}

	/**
	 * 获取模版消息历史记录（for mobile）
	 * @param jsonParam
	 * @param wxid
	 * @param openid
	 * @return
	 */
	public static String getTemplateMsgHistory(String jsonParam, String wxid, String openid) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String responseBody = null;
		try {
			HttpEntity entity = new StringEntity(jsonParam, Charset.forName("UTF-8"));

			// 设置请求超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(2000).setConnectionRequestTimeout(1000)
					.setSocketTimeout(2000).build();

			HttpUriRequest httpPost = RequestBuilder
					.post(SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_PLATFORM_URL) + "/wx/template/getTemplateMsgHistory")
					.addHeader("wx_id", wxid)
					.addHeader("open_id", openid)
					.addHeader("Content-type", "application/json; charset=utf-8")
					.setConfig(requestConfig)
					.setEntity(entity)
					.build();


			//	处理请求结果
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					//System.out.println(status);
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}
			};
			//	发起API调用
			responseBody = httpClient.execute(httpPost, responseHandler);

//			System.out.println(responseBody);
			return responseBody;

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException ioe) {
				log.error(ioe.getMessage());
			}
		}
		return null;
	}


	/**
	 * 获取各地区gps边界数据
	 * @param pname 省市名称
	 * @return
	 */
	public static String getGpsBorder(String pname) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = "http://commonplatform.oss-cn-shanghai.aliyuncs.com/wechat/datav/gps_border_"+pname;
		try {

			// 设置请求超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(2000).setConnectionRequestTimeout(1000)
					.setSocketTimeout(2000).build();

			HttpUriRequest httpGet = RequestBuilder
					.get(url)
					.build();

			//	处理请求结果
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}
			};
			//	发起API调用
			String responseBody = httpClient.execute(httpGet, responseHandler);
			String jsonStr = new String(responseBody.getBytes("ISO-8859-1"), "UTF-8");
			return jsonStr;

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException ioe) {
				log.error(ioe.getMessage());
			}
		}
		return "";
	}


	/**
	 * 解析微信发来的请求（XML）
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}

	private static String switchAppCode(String wxId){
		if(SettingUtils.getStringValueByModuleAndName(SysConstant.WECHAT_MODULE, SysConstant.WXID_DRIVER).equals(wxId)) {
			return SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_APP_CODE_DRIVER);
		} else if(SettingUtils.getStringValueByModuleAndName(SysConstant.WECHAT_MODULE, SysConstant.WXID_CAPTAIN).equals(wxId)) {
			return SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_APP_CODE_CAPTAIN);
		} else if(SettingUtils.getStringValueByModuleAndName(SysConstant.WECHAT_MODULE, SysConstant.WXID_COLONEL).equals(wxId)) {
			return SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_APP_CODE_COLONEL);
		} else {
			return SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_APP_CODE_DRIVER);
		}
	}
	/**
	 * 生成6位或10位随机数
	 * param codeLength(多少位)
	 * @return
	 */
	public static String createCode(int codeLength) {
		String code="";
		for(int i=0; i<codeLength; i++) {
			code += (int)(Math.random() * 9);
		}
		return code;
	}

//	public static boolean isValidChar(char ch) {
//		if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z')|| (ch >= 'a' && ch <= 'z'))
//			return true;
//		if ((ch >= 0x4e00 && ch <= 0x7fff) || (ch >= 0x8000 && ch <= 0x952f))
//			return true;// 简体中文汉字编码
//		return false;
//	}


	/**
	 * 除去数组中的空值和签名参数
	 * @param sArray 签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {


		Map<String, String> result = new HashMap<String, String>();


		if (sArray == null || sArray.size() <= 0) {
			return result;
		}


		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}


		return result;
	}


	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {


		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);


		String prestr = "";


		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);


			if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}


		return prestr;
	}

	/**
	 * map转换成xml
	 * @param params
	 * @return
	 */
	public static String convertMapToXmlString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String xmlStr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			xmlStr = xmlStr + "<" + key + "><![CDATA[" + value + "]]></" + key + ">";
		}

		return xmlStr;
	}

	/**
	 * 实体类转换为xml
	 * @param obj
	 * @return
	 */
	public static String convertEntityToXmlString(Object obj) {
		// 创建输出流
		StringWriter sw = new StringWriter();
		try {
			// 利用jdk中自带的转换类实现
			JAXBContext context = JAXBContext.newInstance(obj.getClass());

			Marshaller marshaller = context.createMarshaller();
			// 格式化xml输出的格式
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			// 将对象转换成输出流形式的xml
			marshaller.marshal(obj, sw);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return sw.toString();
	}

	/**
	 * 获取微信accesstoken
	 * @param wxid
	 * @return
	 */
	public static String getWxToken(String wxid) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String responseBody = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> paramMap = new HashMap<>();
			if (StringUtils.isNotEmpty(wxid)) {
				paramMap.put("url", SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_URL));
			}else {
				return null;
			}

			String appCode = "";
			if(wxid.equalsIgnoreCase(SettingUtils.getStringValueByModuleAndName(SysConstant.WECHAT_MODULE, SysConstant.WXID_COLONEL))){
				appCode = SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_APP_CODE_COLONEL);
			}else {
				return null;
			}
			String jsonParam = objectMapper.writeValueAsString(paramMap);

			HttpEntity entity = new StringEntity(jsonParam, Charset.forName("UTF-8"));

			// 设置请求超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(2000).setConnectionRequestTimeout(1000)
					.setSocketTimeout(2000).build();

			HttpUriRequest httpPost = RequestBuilder
					.post(SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_PLATFORM_URL) + "/wx/getWxJsSdkAccessToken")
					.addHeader("Content-type", "application/json; charset=utf-8")
					.addHeader("account-sid", SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_ACCOUNT_SID))
					.addHeader("auth-token", SettingUtils.getStringValueByModuleAndName(SysConstant.COMMON_MODULE, SysConstant.COMMON_AUTH_TOKEN))
					.addHeader("app-code", appCode)
					.setConfig(requestConfig)
					.setEntity(entity)
					.build();


			//	处理请求结果
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					//System.out.println(status);
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}
			};
			//	发起API调用
			responseBody = httpClient.execute(httpPost, responseHandler);

//			System.out.println(responseBody);
			String accessToken = null;
			JsonNode jsonNode = objectMapper.readTree(responseBody);
			String status = jsonNode.get("status").asText();
			if(status.equalsIgnoreCase("ok")){
				accessToken = jsonNode.get("result").get("access_token").asText();
			}else {
				return null;
			}
			return accessToken;

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException ioe) {
				log.error(ioe.getMessage());
			}
		}
		return null;
	}

	/**
	 * 设置用户标签
	 * @param wxid
	 * @return
	 */
	public static ResponseBody setUserTag(String wxid, List<String> openidList, int type) {
		String wxToken = getWxToken(wxid);
		if (wxToken == null){
			return new ErrorBody(ErrorCode.ERROR_400, "ACCESS_TOKEN_NOT_EXIST");
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String responseBody = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> paramMap = new HashMap<>();
            String tagId = EnumUtils.getEnumText(SysConstant.WECHAT_MODULE, SysConstant.COLONEL_TYPE, String.valueOf(type));
            if (StringUtils.isNotEmpty(wxid) && openidList.size() > 0 && tagId != null) {
				paramMap.put("openid_list", openidList);
				paramMap.put("tagid", tagId);
			}else {
				return new ErrorBody(ErrorCode.ERROR_400, "PARAM_ERROR");
			}

			String jsonParam = objectMapper.writeValueAsString(paramMap);

			HttpEntity entity = new StringEntity(jsonParam, Charset.forName("UTF-8"));

			// 设置请求超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(2000).setConnectionRequestTimeout(1000)
					.setSocketTimeout(2000).build();

			HttpUriRequest httpPost = RequestBuilder
					.post("https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token="+wxToken)
					.addHeader("Content-type", "application/json; charset=utf-8")
					.setConfig(requestConfig)
					.setEntity(entity)
					.build();


			//	处理请求结果
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					//System.out.println(status);
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}
			};
			//	发起API调用
			responseBody = httpClient.execute(httpPost, responseHandler);

			JsonNode jsonNode = objectMapper.readTree(responseBody);
			int errcode = jsonNode.get("errcode").asInt();
			return processErrorResult(errcode);

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException ioe) {
				log.error(ioe.getMessage());
			}
		}
		return new ErrorBody(ErrorCode.ERROR_400, "SERVER_ERROR");
	}

	/**
	 * 删除用户标签
	 * @param wxid
	 * @return
	 */
	public static ResponseBody clearUserTag(String wxid,List<String> openidList,Integer type) {
		String wxToken = getWxToken(wxid);
		if (wxToken == null){
			return new ErrorBody(ErrorCode.ERROR_400, "ACCESS_TOKEN_NOT_EXIST");
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String responseBody = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> paramMap = new HashMap<>();
			String tagId = EnumUtils.getEnumText(SysConstant.WECHAT_MODULE, SysConstant.COLONEL_TYPE, String.valueOf(type));
			if (StringUtils.isNotEmpty(wxid) && openidList.size() > 0 && tagId != null) {
				paramMap.put("openid_list", openidList);
				paramMap.put("tagid", tagId);
			}else {
				return new ErrorBody(ErrorCode.ERROR_400, "PARAM_ERROR");
			}

			String jsonParam = objectMapper.writeValueAsString(paramMap);

			HttpEntity entity = new StringEntity(jsonParam, Charset.forName("UTF-8"));

			// 设置请求超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(2000).setConnectionRequestTimeout(1000)
					.setSocketTimeout(2000).build();

			HttpUriRequest httpPost = RequestBuilder
					.post("https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token="+wxToken)
					.addHeader("Content-type", "application/json; charset=utf-8")
					.setConfig(requestConfig)
					.setEntity(entity)
					.build();


			//	处理请求结果
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					//System.out.println(status);
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}
			};
			//	发起API调用
			responseBody = httpClient.execute(httpPost, responseHandler);

			JsonNode jsonNode = objectMapper.readTree(responseBody);
			int errcode = jsonNode.get("errcode").asInt();
			return processErrorResult(errcode);

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException ioe) {
				log.error(ioe.getMessage());
			}
		}
		return new ErrorBody(ErrorCode.ERROR_400, "SERVER_ERROR");
	}

	private static ResponseBody processErrorResult(int errcode) {

		if (errcode == 0){
			return new SuccessBody();
		} else {
			if (errcode == -1){
				return new ErrorBody(ErrorCode.ERROR_400, "SERVER_BUSY");
			}else if (errcode == 40032){
				return new ErrorBody(ErrorCode.ERROR_400, "OPENID_OUT_SIZE");
			}else if (errcode == 45159){
				return new ErrorBody(ErrorCode.ERROR_400, "ERROR_TAG");
			}else if (errcode == 45059){
				return new ErrorBody(ErrorCode.ERROR_400, "USER_TAG_OUT_SIZE");
			}else if (errcode == 40003){
				return new ErrorBody(ErrorCode.ERROR_400, "ERROR_OPENID");
			}else if (errcode == 49003){
				return new ErrorBody(ErrorCode.ERROR_400, "APP_NOT_EXIST_OPENID");
			}
		}
		return new ErrorBody(ErrorCode.ERROR_400, "SERVER_BUSY");
	}



}
