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
    String getTecentStockQuote(@Param("year") String year, @Param("scode") String scode, @Param("mktcode") String mktcode);

    @RequestLine("GET flashdata/hushen/daily/{mktcode}{scode}.js")
    String getTecentStockQuoteDaily(@Param("year") String year, @Param("scode") String scode, @Param("mktcode") String mktcode);

}