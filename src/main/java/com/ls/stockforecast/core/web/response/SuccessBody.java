package com.ls.stockforecast.core.web.response;

import java.util.HashMap;

/**
 * @author yaochenglong
 * @date 2018/10/29
 *
 * 逻辑执行成功后的返回体
 *
 *
 */
public class SuccessBody extends HashMap implements ResponseBody{

    public SuccessBody() {
    }

    public SuccessBody(String key, Object value) {
        super();
        super.put(key, value);
    }

}
