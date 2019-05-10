package com.ls.stockforecast.web.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ls.stockforecast.utils.CacheUtils;
import com.ls.stockforecast.utils.SysConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationUtils {
    private static Logger logger = LoggerFactory.getLogger(AuthorizationUtils.class);

    public static Object[] getAuthorizationInfo(HttpServletRequest request, String... wxIds) {
        Object[] authInfos = new Object[2];
        try {
            String authorization = request.getHeader("Authorization");
            if(StringUtils.isEmpty(authorization)) {
                return null;
            }
            String[] authArray = authorization.split(" ");
            DecodedJWT jwt = JWT.decode(authArray[1]);
            Long _id = Long.parseLong(jwt.getSubject());    // 验证id是否正确
            String _wxId = jwt.getClaim("username").asString();
            if(StringUtils.isEmpty(_wxId)) {
                return null;
            }
            if(wxIds!=null && wxIds.length>0) {   // 公众号id匹配
                boolean matchWxid = false;
                for (String wxId : wxIds) {
                    if(_wxId.equals(CacheUtils.getSettingStringValue(SysConstant.WECHAT_MODULE, wxId))) {
                        matchWxid = true;
                        break;
                    }
                }
                if(!matchWxid) {    // 公众号id不满足要求
                    return null;
                }
            }
            authInfos[0] = _id;
            authInfos[1] = _wxId;
        } catch (Exception e) {
            logger.error("登录信息获取失败", e);
        }
        return authInfos;
    }
}
