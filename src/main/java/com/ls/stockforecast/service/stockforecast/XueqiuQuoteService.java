package com.ls.stockforecast.service.stockforecast;

import com.ls.stockforecast.entity.model.base.stockforecast.StockInfo;
import com.ls.stockforecast.entity.model.base.stockforecast.StockQuote;
import com.ls.stockforecast.feignclient.TecentClient;
import com.ls.stockforecast.feignclient.XueqiuClient;
import com.ls.stockforecast.utils.CommonUtils;
import com.ls.stockforecast.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class XueqiuQuoteService extends StockQuoteService {

    @Autowired
    private XueqiuClient xueqiuClient;

    @Override
    public String[] getStockQuote(String scode, String mktcode, String date) {
        String[] result = null;
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
            xueqiuResult = xueqiuClient.getXueqiuStockQuote(scode, mktcode, begin, end);
            if (StringUtils.isEmpty(xueqiuResult)) {
                return null;
            }

        } catch (Exception e) {
            logger.error("", e);
        }
        return result;
    }

    @Override
    public List<String[]> getStockQuoteYear(String scode, String mktcode, String year) {
        List<String[]> result = new ArrayList<>();
        try {
            String xueqiuResult = "";
            Calendar calendar = Calendar.getInstance();
            long end = calendar.getTime().getTime();
            calendar.add(Calendar.YEAR, -1);
            long begin = calendar.getTime().getTime();
            xueqiuResult = xueqiuClient.getXueqiuStockQuote(scode, mktcode, begin, end);
            if (StringUtils.isEmpty(xueqiuResult)) {
                return null;
            }

        } catch (Exception e) {
            logger.error("scode获取行情异常： " +  e);
        }
        return result;
    }
}