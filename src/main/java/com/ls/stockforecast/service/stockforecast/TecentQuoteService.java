package com.ls.stockforecast.service.stockforecast;

import com.ls.stockforecast.feignclient.TecentClient;
import com.ls.stockforecast.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TecentQuoteService extends StockQuoteService {

    @Autowired
    private TecentClient tecentClient;


    @Override
    public String[] getStockQuote(String scode, String mktcode, String date) {
        String[] result = null;
        try {
            String qqResult = "";
            if (date == null) {
                qqResult = tecentClient.getTecentStockQuoteDaily(scode, mktcode);
            } else {
                if (DateUtils.getFormatDate(date, DateUtils.DATE_PATTERN_) == null) {
                    return null;
                }
                qqResult = tecentClient.getTecentStockQuote(scode, mktcode, date.substring(2, 4));
            }
            if (StringUtils.isEmpty(qqResult)) {
                return null;
            }
            qqResult = qqResult.replaceAll("\\\\n\\\\", "");
            String[] str = qqResult.split("\n");
            String record = null;
            if (date == null) {    // 取最新一条
                record = str[str.length - 1];
            } else {
                String formatDate = date.substring(2, 8);
                for (String data : str) {
                    if (data.startsWith(formatDate)) {
                        record = data;
                        break;
                    }
                }
            }
            // 解析出行情结果
            if (StringUtils.isEmpty(record)) {
                return null;
            }
            result = record.split(" ");
        } catch (Exception e) {
            logger.error("", e);
        }
        return result;
    }

    @Override
    public List<String[]> getStockQuoteYear(String scode, String mktcode, String year) {
        List<String[]> result = new ArrayList<>();
        try {
            String qqResult = "";
            qqResult = tecentClient.getTecentStockQuote(scode, mktcode, year.substring(2, 4));
            if (StringUtils.isEmpty(qqResult)) {
                return null;
            }
            qqResult = qqResult.replaceAll("\\\\n\\\\", "");
            String[] str = qqResult.split("\n");
            for (String data : str) {
                String[] record = data.split(" ");
                if(record.length==6) {
                    result.add(record);
                }
            }
        } catch (Exception e) {
            logger.error("scode获取行情异常： " +  e);
        }
        return result;
    }
}