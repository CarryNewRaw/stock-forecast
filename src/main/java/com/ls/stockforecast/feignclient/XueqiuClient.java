package com.ls.stockforecast.feignclient;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author sheng.li
 * @date 2019/5/13
 */
@FeignClient(value = "xueqiuClient", url = "${feign.stock-forecast.xueqiu-url}")
public interface XueqiuClient {

    @RequestLine("GET ")
    String connect();

    @RequestLine("GET /stock/forchartk/stocklist.json?symbol={scodeInfo}&period=1day&type=before&begin={begin}&end={end}&_={end}")
    @Headers({"Content-Type: application/json;charset=UTF-8","Cookie: xq_a_token=08f4dfd2f3103859dd6f4f2e95316bc748da319e"})
    String getXueqiuStockQuote(@Param("scodeInfo") String scodeInfo, @Param("begin") Long begin, @Param("end") Long end);


}