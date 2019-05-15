package com.ls.stockforecast.dao.base.stockforecast;

import com.ls.stockforecast.entity.model.base.stockforecast.StockQuote;
import org.apache.ibatis.annotations.Param;

public interface StockQuoteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockQuote record);

    int insertSelective(StockQuote record);

    StockQuote selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockQuote record);

    int updateByPrimaryKey(StockQuote record);

    int deleteByDate(Integer date);

    int deleteByScodeAndYear(@Param("scode") String scode, @Param("mktcode") String mktcode, @Param("year") String year);
}