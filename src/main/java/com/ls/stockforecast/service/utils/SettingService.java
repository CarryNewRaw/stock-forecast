package com.ls.stockforecast.service.utils;

import com.ls.stockforecast.dao.base.utils.SettingMapper;
import com.ls.stockforecast.entity.model.base.utils.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SettingService {

    @Autowired
    private SettingMapper settingMapper;

    public Setting getSettingByModuleAndName(String module, String name) {
        return settingMapper.getSettingByModuleAndName(module, name);
    }

    public Setting getSettingByModuleAndValue(String module, String value) {
        return settingMapper.getSettingByModuleAndValue(module, value);
    }

    public Object getValueByModuleAndName(String module, String name) {
        return settingMapper.getSettingValueByModuleAndName(module, name);
    }
}