package com.ls.stockforecast.service.stockforecast;

import com.ls.stockforecast.entity.model.base.stockforecast.StockInfo;
import com.ls.stockforecast.entity.model.base.stockforecast.StockQuote;
import com.ls.stockforecast.feignclient.TecentClient;
import com.ls.stockforecast.utils.CommonUtils;
import com.ls.stockforecast.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TecentQuoteService extends StockQuoteService {

    @Autowired
    private TecentClient tecentClient;

    public String insertQuoteByDate(String date) {
        logger.info("**********************" + date + "行情数据插入开始  " + DateUtils.getCurrentTime());
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
        logger.debug("删除原有行情数量：" + deleteCount);
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
                        logger.debug("日期：" + date + " 代码：" + scode + "行情数据插入  " + DateUtils.getCurrentTime());
                    }
                } catch (Exception e) {
                    logger.error(scode + "行情获取失败");
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        logger.debug("**********************" + date + "行情数据插入结束  " + DateUtils.getCurrentTime());
        return null;
    }

    public String insertQuoteByScodeAndYear(String scode, String mktcode, String year) {
        logger.info("**********************" + scode + "  year=" + year + "行情数据插入开始  " + DateUtils.getCurrentTime());
        // 1. 删除原有数据
        int deleteCount = stockQuoteMapper.deleteByScodeAndYear(scode, mktcode, year);
        logger.debug("删除原有行情数量：" + deleteCount);
        // 2. 获取行情
        List<String[]> quoteList = getQQStockQuoteYear(scode, mktcode, year);
        if(CommonUtils.isNotEmpty(quoteList)) {
            for(String[] data : quoteList) {
                StockQuote quote = new StockQuote();
                quote.setScode(scode);
                quote.setMktcode(mktcode);
                quote.setDate(Integer.parseInt(year.substring(0,2) + data[0]));
                quote.setOpen(Double.parseDouble(data[1]));
                quote.setClose(Double.parseDouble(data[2]));
                quote.setHigh(Double.parseDouble(data[3]));
                quote.setLow(Double.parseDouble(data[4]));
                quote.setVolume(Double.parseDouble(data[5]));
                quote.setCreateTime(new Date());
                stockQuoteMapper.insert(quote);
                logger.debug("日期：" + year.substring(0,2) + data[0] + " 代码：" + scode + "行情数据插入  " + DateUtils.getCurrentTime());
            }
        }
        logger.debug("**********************" + scode + "  year=" + year + "行情数据插入结束  " + DateUtils.getCurrentTime());
        return null;
    }

    private String[] getQQStockQuote(String scode, String mktcode, String date) {
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

    private List<String[]> getQQStockQuoteYear(String scode, String mktcode, String year) {
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