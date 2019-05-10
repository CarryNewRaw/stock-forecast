package com.ls.stockforecast.utils;

import com.ls.stockforecast.entity.model.base.utils.Enum;
import com.ls.stockforecast.service.utils.EnumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class EnumUtils {
    private EnumUtils() {}

    private static EnumUtils enumUtils;

    @Autowired
    private EnumService enumService;

    @PostConstruct
    public void init() {
        enumUtils = this;
        enumUtils.enumService = this.enumService;
    }

    public static List<Enum> getByModuleAndName(String module, String name) {
        return enumUtils.enumService.getByModuleAndName(module, name);
    }

    public static String getEnumTextByCode(List<Enum> enumList, Object code) {
        for(Enum e : enumList) {
            if(e.getCode().equals(code)) {
                return e.getText();
            }
        }
        return null;
    }

    public static String getEnumText(String module, String name, String code) {
        List<Enum> enumList = getByModuleAndName(module, name);
        return EnumUtils.getEnumTextByCode(enumList, code);
    }
}