package com.ls.stockforecast.utils;

import com.ls.stockforecast.entity.model.base.utils.Enum;
import com.ls.stockforecast.entity.model.base.utils.Setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 懒加载缓存类
 * @author sheng.li
 * @date 2018/12/5
 */
public class CacheUtils {
    private static Map<String, Map<String, List<Enum>>> enumMap = new HashMap<>();  // Map<module, Map<name, List<Enum>>>
    private static Map<String, Map<String, Setting>> settingMap = new HashMap<>(); // Map<module, Map<name, Setting>>
    private CacheUtils(){}

    /**
     * 获取缓存中的枚举列表
     * @param module
     * @param name
     * @return
     */
    public synchronized static List<Enum> getEnumList(String module, String name) {
        List<Enum> enumList = null;
        Map<String, List<Enum>> moduleMap = enumMap.get(module);
        if(moduleMap==null) {
            enumList = EnumUtils.getByModuleAndName(module, name);
            Map<String, List<Enum>> nameMap = new HashMap<>();
            nameMap.put(name, enumList);
            enumMap.put(module, nameMap);
        } else {
            enumList = moduleMap.get(name);
            if(enumList==null || enumList.size()==0) {
                enumList = EnumUtils.getByModuleAndName(module, name);
                moduleMap.put(name, enumList);
                enumMap.put(module, moduleMap);
            }
        }
        return enumList;
    }

    /**
     * 获取缓存中的枚举列表
     * @param module
     * @param name
     * @return
     */
    public synchronized static Map<String, String> getEnumMap(String module, String name) {
        Map<String, String> resultMap = new HashMap<>();
        List<Enum> enumList = getEnumList(module, name);
        for(Enum e : enumList) {
            resultMap.put(e.getCode(), e.getText());
        }
        return resultMap;
    }

    public static String getEnumText(String module, String name, String code) {
        getEnumList(module, name);
        List<Enum> enumList = enumMap.get(module).get(name);
        return EnumUtils.getEnumTextByCode(enumList, code);
    }

    /**
     * 获取缓存中的配置表信息
     * @param module
     * @param name
     * @return
     */
    public synchronized static Setting getSetting(String module, String name) {
        Setting setting = null;
        Map<String, Setting> moduleMap = settingMap.get(module);
        if(moduleMap==null) {
            setting = SettingUtils.getByModuleAndName(module, name);
            Map<String, Setting> nameMap = new HashMap<>();
            nameMap.put(name, setting);
            settingMap.put(module, nameMap);
        } else {
            setting = moduleMap.get(name);
            if(setting==null) {
                setting = SettingUtils.getByModuleAndName(module, name);
                moduleMap.put(name, setting);
                settingMap.put(module, moduleMap);
            }
        }
        return setting;
    }

    /**
     * 下面是各种方便查询的对外接口
     * @param module
     * @param name
     * @return
     */
    public static String getSettingStringValue(String module, String name) {
        String stringValue = null;
        Setting setting = getSetting(module, name);
        if(setting!=null) {
            stringValue = setting.getValue();
        }
        return stringValue;
    }

    public static Integer getSettingIntegerValue(String module, String name) {
        Integer intValue = null;
        String strValue = getSettingStringValue(module, name);
        if(strValue!=null) {
            try {
                intValue = Integer.parseInt(strValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return intValue;
    }

    public static Long getSettingLongValue(String module, String name) {
        Long longValue = null;
        String strValue = getSettingStringValue(module, name);
        if(strValue!=null) {
            try {
                longValue = Long.parseLong(strValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return longValue;
    }

    public static Double getSettingDoubleValue(String module, String name) {
        Double doubleValue = null;
        String strValue = getSettingStringValue(module, name);
        if(strValue!=null) {
            try {
                doubleValue = Double.parseDouble(strValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return doubleValue;
    }

//    private static String getEnumTextByCode(List<Enum> enumList, Object code) {
//        for(Enum e : enumList) {
//            if(e.getCode().equals(code)) {
//                return e.getText();
//            }
//        }
//        return null;
//    }
}