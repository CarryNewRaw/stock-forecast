package com.ls.stockforecast.feignclient;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author sheng.li
 * @date 2019/5/13
 */
@FeignClient(value = "stockForecastClient", url = "${feign.stock-forecast.tecent-url}")
public interface StockForecastClient {

    @RequestLine("GET flashdata/hushen/daily/{year}/{mktcode}{scode}.js")
    String getTecentStockQuote(@Param("scode") String scode, @Param("mktcode") String mktcode, @Param("year") String year);

    @RequestLine("GET flashdata/hushen/latest/daily/{mktcode}{scode}.js")
    String getTecentStockQuoteDaily(@Param("scode") String scode, @Param("mktcode") String mktcode);

}