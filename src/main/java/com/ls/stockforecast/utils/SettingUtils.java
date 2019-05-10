package com.ls.stockforecast.utils;

import com.ls.stockforecast.entity.model.base.utils.Setting;
import com.ls.stockforecast.service.utils.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SettingUtils {
    private SettingUtils() {}

    private static SettingUtils settingUtils;

    @Autowired
    private SettingService settingService;

    @PostConstruct
    public void init() {
        settingUtils = this;
        settingUtils.settingService = this.settingService;
    }

    public static Setting getByModuleAndName(String module, String name) {
        return settingUtils.settingService.getSettingByModuleAndName(module, name);
    }

    public static Setting getByModuleAndValue(String module, String value) {
        return settingUtils.settingService.getSettingByModuleAndValue(module, value);
    }

    public static String getStringValueByModuleAndName(String module, String name) {
        String stringValue = null;
        try {
            stringValue = (String) getValueByModuleAndName(module, name);
        } catch (Exception e) {

        }
        return stringValue;
    }

    public static Integer getIntValueByModuleAndName(String module, String name) {
        Integer intValue = null;
        try {
            intValue = Integer.parseInt((String)getValueByModuleAndName(module, name));
        } catch (Exception e) {

        }
        return intValue;
    }

    public static Double getDoubleValueByModuleAndName(String module, String name) {
        Double doubleValue = null;
        try {
            doubleValue = Double.parseDouble((String)getValueByModuleAndName(module, name));
        } catch (Exception e) {

        }
        return doubleValue;
    }

    public static Long getLongValueByModuleAndName(String module, String name) {
        Long longValue = null;
        try {
            longValue = Long.parseLong((String)getValueByModuleAndName(module, name));
        } catch (Exception e) {

        }
        return longValue;
    }

    public static Object getValueByModuleAndName(String module, String name) {
        return settingUtils.settingService.getValueByModuleAndName(module, name);
    }

}