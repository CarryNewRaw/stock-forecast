package com.ls.stockforecast.service.stockforecast;

import com.ls.stockforecast.dao.base.stockforecast.StockInfoMapper;
import com.ls.stockforecast.dao.base.stockforecast.StockQuoteMapper;
import com.ls.stockforecast.entity.model.base.stockforecast.StockInfo;
import com.ls.stockforecast.entity.model.base.stockforecast.StockQuote;
import com.ls.stockforecast.utils.CommonUtils;
import com.ls.stockforecast.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public abstract class StockQuoteService {
    protected static Logger logger = LoggerFactory.getLogger(StockQuoteService.class);

    @Autowired
    protected StockQuoteMapper stockQuoteMapper;
    @Autowired
    protected StockInfoMapper stockInfoMapper;

    abstract public StockQuote getStockQuote(String scode, String mktcode, String date);

    abstract public List<StockQuote> getStockQuoteYear(String scode, String mktcode, String year);

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
                    StockQuote quote = this.getStockQuote(scode, mktcode, date);
                    if (quote != null) {
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
        List<StockQuote> quoteList = getStockQuoteYear(scode, mktcode, year);
        if(CommonUtils.isNotEmpty(quoteList)) {
            for(StockQuote quote : quoteList) {
                if(quote==null) continue;
                stockQuoteMapper.insert(quote);
                logger.debug("日期：" + quote.getDate() + " 代码：" + scode + "行情数据插入  " + DateUtils.getCurrentTime());
            }
        } else {
            logger.debug("**********************" + scode + "  year=" + year + "行情数据为空  " + DateUtils.getCurrentTime());
        }
        logger.debug("**********************" + scode + "  year=" + year + "行情数据插入结束  " + DateUtils.getCurrentTime());
        return null;
    }
}