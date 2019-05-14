package com.ls.stockforecast.dao.base.stockforecast;

import com.ls.stockforecast.entity.model.base.stockforecast.StockQuote;

public interface StockQuoteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockQuote record);

    int insertSelective(StockQuote record);

    StockQuote selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockQuote record);

    int updateByPrimaryKey(StockQuote record);

    int deleteByDate(Integer date);
}