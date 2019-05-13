package com.ls.stockforecast.service.stockforecast;

import com.ls.stockforecast.dao.base.stockforecast.StockInfoMapper;
import com.ls.stockforecast.entity.model.base.stockforecast.StockInfo;
import com.ls.stockforecast.feignclient.StockForecastClient;
import com.ls.stockforecast.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class StockQuoteService {
    private static Logger logger = LoggerFactory.getLogger(StockQuoteService.class);

    @Autowired
    private StockInfoMapper stockInfoMapper;
    @Autowired
    private StockForecastClient stockForecastClient;

    public void insertDailyQuote() {
        // 1.获取行情
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        String date = DateUtils.getFormatDateStr(calendar.getTime(), DateUtils.DATE_PATTERN_);
        String year = date.substring(0, 4);

        List<StockInfo> stockInfoList = stockInfoMapper.selectAll();
        try {
            for (StockInfo stockInfo : stockInfoList) {
                String scode = stockInfo.getScode();
                String mktcode = stockInfo.getMktcode();
                String qqResult = stockForecastClient.getTecentStockQuoteDaily(scode, mktcode);
                if (StringUtils.isNotEmpty(qqResult)) {
                    System.out.println(qqResult);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

}