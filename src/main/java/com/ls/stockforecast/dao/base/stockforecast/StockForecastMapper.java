package com.ls.stockforecast.dao.base.stockforecast;

import com.ls.stockforecast.entity.model.base.stockforecast.StockForecast;

public interface StockForecastMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockForecast record);

    int insertSelective(StockForecast record);

    StockForecast selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockForecast record);

    int updateByPrimaryKey(StockForecast record);
}