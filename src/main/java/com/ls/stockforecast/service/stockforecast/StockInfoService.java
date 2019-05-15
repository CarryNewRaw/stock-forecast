package com.ls.stockforecast.service.stockforecast;

import com.ls.stockforecast.dao.base.stockforecast.StockInfoMapper;
import com.ls.stockforecast.entity.model.base.stockforecast.StockInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StockInfoService {
    @Autowired
    private StockInfoMapper stockInfoMapper;

    public List<StockInfo> selectAll() {
        return stockInfoMapper.selectAll();
    }

    public StockInfo selectByScode(String scode) {
        return stockInfoMapper.selectByScode(scode);
    }
}