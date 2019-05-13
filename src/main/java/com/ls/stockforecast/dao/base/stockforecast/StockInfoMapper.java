package com.ls.stockforecast.dao.base.stockforecast;

import com.ls.stockforecast.entity.model.base.stockforecast.StockInfo;

import java.util.List;

public interface StockInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockInfo record);

    int insertSelective(StockInfo record);

    StockInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockInfo record);

    int updateByPrimaryKey(StockInfo record);

    List<StockInfo> selectByDate(Integer date);
}