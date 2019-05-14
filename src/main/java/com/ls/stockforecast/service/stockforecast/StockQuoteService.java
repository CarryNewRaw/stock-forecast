package com.ls.stockforecast.service.stockforecast;

import com.ls.stockforecast.dao.base.stockforecast.StockInfoMapper;
import com.ls.stockforecast.dao.base.stockforecast.StockQuoteMapper;
import com.ls.stockforecast.entity.model.base.stockforecast.StockInfo;
import com.ls.stockforecast.entity.model.base.stockforecast.StockQuote;
import com.ls.stockforecast.feignclient.StockForecastClient;
import com.ls.stockforecast.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StockQuoteService {
    private static Logger logger = LoggerFactory.getLogger(StockQuoteService.class);

    @Autowired
    private StockQuoteMapper stockQuoteMapper;
    @Autowired
    private StockInfoMapper stockInfoMapper;
    @Autowired
    private StockForecastClient stockForecastClient;

    public String insertDailyQuote(String date) {
        logger.info("**********************行情数据插入开始" + DateUtils.getCurrentTime());
        if(StringUtils.isEmpty(date)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            date = DateUtils.getFormatDateStr(calendar.getTime(), DateUtils.DATE_PATTERN_);
        } else {
            Date d = DateUtils.getFormatDate(date, DateUtils.DATE_PATTERN_);
            if(d==null)
                return "日期错误";
        }
        // 1.删除原有数据
        int deleteCount = stockQuoteMapper.deleteByDate(Integer.parseInt(date));
        logger.info("删除原有行情数量：" + deleteCount);
        // 2.获取行情
        List<StockInfo> stockInfoList = stockInfoMapper.selectAll();
        try {
            for (StockInfo stockInfo : stockInfoList) {
                String scode = stockInfo.getScode();
                String mktcode = stockInfo.getMktcode();
                try {
                    String[] data = this.getQQStockQuote(scode, mktcode, date);
                    if (data != null) {
                        StockQuote quote = new StockQuote();
                        quote.setScode(scode);
                        quote.setMktcode(mktcode);
                        quote.setDate(Integer.parseInt(date));
                        quote.setOpen(Double.parseDouble(data[1]));
                        quote.setClose(Double.parseDouble(data[2]));
                        quote.setHigh(Double.parseDouble(data[3]));
                        quote.setLow(Double.parseDouble(data[4]));
                        quote.setVolume(Double.parseDouble(data[5]));
                        quote.setCreateTime(new Date());
                        stockQuoteMapper.insert(quote);
                        logger.info(scode + "行情数据插入  " + DateUtils.getCurrentTime());
                    }
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        logger.info("**********************行情数据插入结束  " + DateUtils.getCurrentTime());
        return null;
    }

    public String[] getQQStockQuote(String scode, String mktcode, String date) {
        String[] result = null;
        try {
            String qqResult = "";
            if (date == null) {
                qqResult = stockForecastClient.getTecentStockQuoteDaily(scode, mktcode);
            } else {
                if (DateUtils.getFormatDate(date, DateUtils.DATE_PATTERN_) == null) {
                    return null;
                }
                qqResult = stockForecastClient.getTecentStockQuote(scode, mktcode, date.substring(2, 4));
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

}