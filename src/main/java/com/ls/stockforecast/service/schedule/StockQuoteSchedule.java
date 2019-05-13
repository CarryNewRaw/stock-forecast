package com.ls.stockforecast.service.schedule;

import com.ls.stockforecast.feignclient.StockForecastClient;
import com.ls.stockforecast.service.stockforecast.StockInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author sheng.li
 * @date 2019/5/13
 */
@Service
@Lazy(false)
public class StockQuoteSchedule {
    private static Logger logger = LoggerFactory.getLogger(StockQuoteSchedule.class);

    @Autowired
    private StockInfoService stockInfoService;
    @Autowired
    private StockForecastClient stockForecastClient;

    /**
     * 行情入库
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void dailyQuoteInsertSchedule() {

    }

}
