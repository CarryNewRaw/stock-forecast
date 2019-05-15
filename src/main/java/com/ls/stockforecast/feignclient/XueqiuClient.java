package com.ls.stockforecast.feignclient;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author sheng.li
 * @date 2019/5/13
 */
@FeignClient(value = "stockForecastClient", url = "${feign.stock-forecast.xueqiu-url}")
public interface XueqiuClient {


}