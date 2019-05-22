package com.ls.stockforecast.service.stockforecast;

import com.ls.stockforecast.entity.model.base.stockforecast.StockQuote;
import com.ls.stockforecast.feignclient.TecentClient;
import com.ls.stockforecast.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TecentQuoteService extends StockQuoteService {

    @Autowired
    private TecentClient tecentClient;


    @Override
    public StockQuote getStockQuote(String scode, String mktcode, String date) {
        StockQuote quote = null;
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
            String[] d = record.split(" ");
            quote = new StockQuote();
            quote.setScode(scode);
            quote.setMktcode(mktcode);
            quote.setDate(Integer.parseInt(date));
            quote.setOpen(Double.parseDouble(d[1]));
            quote.setClose(Double.parseDouble(d[2]));
            quote.setHigh(Double.parseDouble(d[3]));
            quote.setLow(Double.parseDouble(d[4]));
            quote.setVolume(Double.parseDouble(d[5]));
            quote.setCreateTime(new Date());
        } catch (Exception e) {
            logger.error("", e);
        }
        return quote;
    }

    @Override
    public List<StockQuote> getStockQuoteYear(String scode, String mktcode, String year) {
        List<StockQuote> result = new ArrayList<>();
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
                    StockQuote quote = new StockQuote();
                    quote.setScode(scode);
                    quote.setMktcode(mktcode);
                    quote.setDate(Integer.parseInt(year.substring(0,2) + record[0]));
                    quote.setOpen(Double.parseDouble(record[1]));
                    quote.setClose(Double.parseDouble(record[2]));
                    quote.setHigh(Double.parseDouble(record[3]));
                    quote.setLow(Double.parseDouble(record[4]));
                    quote.setVolume(Double.parseDouble(record[5]));
                    quote.setCreateTime(new Date());
                    result.add(quote);
                }
            }
        } catch (Exception e) {
            logger.error("scode获取行情异常： " +  e);
        }
        return result;
    }
}