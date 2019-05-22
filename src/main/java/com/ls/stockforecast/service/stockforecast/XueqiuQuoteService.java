package com.ls.stockforecast.service.stockforecast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ls.stockforecast.entity.model.base.stockforecast.StockQuote;
import com.ls.stockforecast.feignclient.XueqiuClient;
import com.ls.stockforecast.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class XueqiuQuoteService extends StockQuoteService {

    @Autowired
    private XueqiuClient xueqiuClient;

    @Override
    public StockQuote getStockQuote(String scode, String mktcode, String date) {
        StockQuote quote = null;
        try {
            String xueqiuResult = "";
            Calendar calendar = Calendar.getInstance();
            if (date != null) {
                if (DateUtils.getFormatDate(date, DateUtils.DATE_PATTERN_) == null) {
                    return null;
                }
                calendar.setTime(DateUtils.getFormatDate(date, DateUtils.DATE_PATTERN_));
            }
            long end = calendar.getTime().getTime();
            calendar.add(Calendar.MONTH, -3);
            long begin = calendar.getTime().getTime();
            // 1.连接
//            xueqiuClient.connect();
            // 2.开始查询
            xueqiuResult = xueqiuClient.getXueqiuStockQuote(mktcode+scode, begin, end);
            if (StringUtils.isEmpty(xueqiuResult)) {
                return null;
            }

        } catch (Exception e) {
            logger.error("", e);
        }
        return quote;
    }

    @Override
    public List<StockQuote> getStockQuoteYear(String scode, String mktcode, String year) {
        List<StockQuote> result = new ArrayList<>();
        try {
            String xueqiuResult = "";
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.getFormatDate(year+"1231", DateUtils.DATE_PATTERN_));
            long end = calendar.getTime().getTime();
            calendar.setTime(DateUtils.getFormatDate(year+"0101", DateUtils.DATE_PATTERN_));
            long begin = calendar.getTime().getTime();
            // 1.连接
//            System.out.println(xueqiuClient.connect());
            // 2.开始查询
            xueqiuResult = xueqiuClient.getXueqiuStockQuote(mktcode+scode, begin, end);
            if (StringUtils.isEmpty(xueqiuResult)) {
                return null;
            }
            JSONObject json = JSONObject.parseObject(xueqiuResult);
            String errorCode = json.getString("error_code");
            if(StringUtils.isNotEmpty(errorCode)) {
                String errorDescription = json.getString("error_description");
                logger.info(errorDescription);
                return null;
            }
            JSONArray jsonArray = (JSONArray) json.get("chartlist");
            for(Object o : jsonArray) {
                Map m = (Map) o;
                StockQuote quote = new StockQuote();
                quote.setScode(scode);
                quote.setMktcode(mktcode);
                quote.setDate(Integer.valueOf(DateUtils.getDateStr(DateUtils.getDateFromLong((Long) m.get("timestamp")), DateUtils.DATE_PATTERN_)));
                quote.setOpen(((BigDecimal)m.get("open")).doubleValue());
                quote.setClose(((BigDecimal)m.get("close")).doubleValue());
                quote.setHigh(((BigDecimal)m.get("high")).doubleValue());
                quote.setLow(((BigDecimal)m.get("low")).doubleValue());
                quote.setVolume(Double.valueOf(String.valueOf(m.get("volume"))));
                quote.setMacd(((BigDecimal)m.get("macd")).doubleValue());
                quote.setMa5(((BigDecimal)m.get("ma5")).doubleValue());
                quote.setMa10(((BigDecimal)m.get("ma10")).doubleValue());
                quote.setMa20(((BigDecimal)m.get("ma20")).doubleValue());
                quote.setMa30(((BigDecimal)m.get("ma30")).doubleValue());
                quote.setDiff(((BigDecimal)m.get("dif")).doubleValue());
                quote.setDea(((BigDecimal)m.get("dea")).doubleValue());
                quote.setCreateTime(new Date());
                result.add(quote);
            }
        } catch (Exception e) {
            logger.error("scode获取行情异常： ", e);
        }
        return result;
    }
}