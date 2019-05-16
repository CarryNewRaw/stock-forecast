package com.ls.stockforecast.feignclient;

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

    @RequestLine("GET /stock/forchartk/stocklist.json?symbol={mktcode}{scode}&period=1day&type=before&begin={begin}&end={end}&_={end}")
    String getXueqiuStockQuote(@Param("scode") String scode, @Param("mktcode") String mktcode, @Param("begin") Long begin, @Param("end") Long end);


}