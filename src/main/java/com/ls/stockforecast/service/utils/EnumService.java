package com.ls.stockforecast.service.utils;

import com.ls.stockforecast.dao.base.utils.EnumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EnumService {
    @Autowired
    private EnumMapper enumMapper;

    public List<com.ls.stockforecast.entity.model.base.utils.Enum> getByModuleAndName(String module, String name) {
        return enumMapper.getByModuleAndName(module, name);
    }
}