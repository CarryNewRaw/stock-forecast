package com.ls.stockforecast.service.stockforecast;

import com.ls.stockforecast.dao.base.stockforecast.StockInfoMapper;
import com.ls.stockforecast.dao.base.stockforecast.StockQuoteMapper;
import com.ls.stockforecast.entity.model.base.stockforecast.StockInfo;
import com.ls.stockforecast.entity.model.base.stockforecast.StockQuote;
import com.ls.stockforecast.feignclient.TecentClient;
import com.ls.stockforecast.utils.CommonUtils;
import com.ls.stockforecast.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StockQuoteService {
    protected static Logger logger = LoggerFactory.getLogger(StockQuoteService.class);

    @Autowired
    protected StockQuoteMapper stockQuoteMapper;
    @Autowired
    protected StockInfoMapper stockInfoMapper;

}