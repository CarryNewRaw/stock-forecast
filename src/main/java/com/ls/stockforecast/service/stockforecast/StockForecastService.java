package com.ls.stockforecast.service.stockforecast;

import com.ls.stockforecast.dao.base.stockforecast.StockForecastMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StockForecastService {
    @Autowired
    private StockForecastMapper stockForecastMapper;

}