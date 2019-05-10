package com.ls.stockforecast.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.util.*;

public class CommonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * 将对象转换成map.
     * @param object
     * @return
     * 返回转换后的map
     */
    public static Map toMap(Object object) {
        return toMap(object, DateUtils.DATE_PATTERN_OPEN_API, false);
    }

    public static Map toMap(Object object, String dateFormat) {
        return toMap(object, dateFormat, false);
    }

    public static Map toMap(Object object, boolean fieldNotNull) {
        return toMap(object, DateUtils.DATE_PATTERN_OPEN_API, fieldNotNull);
    }

    public static List<Map> toListMap(List<?> list) {
        return toListMap(list, DateUtils.DATE_PATTERN_OPEN_API, false, null);
    }

    public static List<Map> toListMap(List<?> list, String[] needFields) {
        return toListMap(list, DateUtils.DATE_PATTERN_OPEN_API, false, needFields);
    }

    public static List<Map> toListMap(List<?> list, String dateFormat, boolean fieldNotNull, String[] needFields) {
        if (list == null || list.size() <= 0)
            return new ArrayList<>();
        List<Map> result = new ArrayList<>();
        for (Object object : list) {
            Map map = toMap(object, dateFormat, fieldNotNull);
            if (map != null) {
                if (needFields != null) {
                    List<String> fields = Arrays.asList(needFields);
                    Set set = new HashSet();
                    set.addAll(map.keySet());
                    for (Object key : set) {
                        if (!fields.contains(key)) {
                            map.remove(key);
                        }
                    }
                }
                result.add(map);
            }
        }
        return result;
    }

    public static Map toMap(Object object, String dateFormat, boolean fieldNotNull) {
        if(object==null) return null;
        Map _result = new HashMap<>();
        if (object instanceof Map) {
            _result.putAll((Map) object);
        } else {
            PropertyDescriptor[] _props = PropertyUtils.getPropertyDescriptors(object);
            for (int i = 0, length = _props.length; i < length; i++) {
                PropertyDescriptor _prop = _props[i];
                try {
                    String type = _prop.getPropertyType().getName();
                    if("java.lang.Class".equals(type)) {
                        continue;
                    }
                    if (_prop.getReadMethod() != null) {
                        Object v = PropertyUtils.getProperty(object, _prop.getName());
                        String propName = "";
                        // 处理key，驼峰转下划线风格
                        for(char c : _prop.getName().toCharArray()) {
                            if(c>=65 && c<=90) {
                                propName += "_" + String.valueOf((char)(c+32));
                            } else {
                                propName += String.valueOf(c);
                            }
                        }
                        // 处理value
                        if("java.util.Date".equals(type)) {
                            if(v!=null) {
                                if(DateUtils.DATE_PATTERN.equals(dateFormat)) {
                                    _result.put(propName, DateUtils.getDateStr((Date) v));
                                } else {
                                    _result.put(propName, DateUtils.getDateStr((Date) v, dateFormat));
                                }
                            }
                        } else if(fieldNotNull && v==null) {   // 没值的话塞默认值
                            if("java.lang.Integer".equals(type)) {
                                v = 0;
                            } else if("java.lang.Long".equals(type)) {
                                v = 0;
                            } else if("java.lang.Double".equals(type)) {
                                v = 0;
                            } else if("java.lang.String".equals(type)) {
//                                v = "";
                            }
                            _result.put(propName, v);
                        } else {
                            _result.put(propName, v);
                        }
                    }
                } catch (Exception ex) {
                    LOGGER.error("map转换异常 ", ex);
                }
            }
        }
        return _result;
    }

    public static Integer parseInt(Object o) {
        Integer i = null;
        try {
            i = Integer.parseInt((String) o);
        } catch (Exception e) {

        }
        return i;
    }

    public static Long parseLong(Object o) {
        Long i = null;
        try {
            i = Long.parseLong((String) o);
        } catch (Exception e) {

        }
        return i;
    }

    public static boolean isEmpty(List<?> list) {
        return list==null || list.size()==0;
    }

    public static boolean isNotEmpty(List<?> list) {
        return list!=null && list.size()>0;
    }

    public static boolean isJsonEmpty(String json) {
        return StringUtils.isEmpty(json) || "{}".equals(json);
    }

    public static boolean isJsonNotEmpty(String json) {
        return StringUtils.isNotEmpty(json) && !"{}".equals(json);
    }

    /**
     * 根据list里的vehicle_id，加上license_color和license_card
     * @param vehicleList
     */
    public static void setLicenseCardAndColor(List<Map<String, Object>> vehicleList) {
        setLicenseCardAndColor(vehicleList, false);
    }

    /**
     * 根据list里的vehicle_id，加上license_color和license_card（license_color是中文）
     * @param vehicleList
     */
    public static void setLicenseCardAndColor(List<Map<String, Object>> vehicleList, boolean chn) {
        for(Map<String, Object> vehicleMap : vehicleList) {
            try {
                String[] vehicleInfo = CodeCreateUtil.getVehicleCodeAndColor((String) vehicleMap.get("vehicle_id"));
                if (vehicleInfo != null) {
                    vehicleMap.put("license_card", vehicleInfo[0]);
                    if(chn) {
                        vehicleMap.put("license_color", CacheUtils.getEnumText(SysConstant.COMMON_MODULE, SysConstant.VEHICLE_COLOR, vehicleInfo[1]));
                    } else {
                        vehicleMap.put("license_color", Integer.parseInt(vehicleInfo[1]));
                    }
                }
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
    }

    /**
     * 把list中的颜色转成中文
     * @param vehicleList
     */
    public static void setLicenseColorCHN(List<Map<String, Object>> vehicleList) {
        for(Map<String, Object> vehicleMap : vehicleList) {
            try {
                String color = String.valueOf(vehicleMap.get("license_color"));
                vehicleMap.put("license_color", CacheUtils.getEnumText(SysConstant.COMMON_MODULE, SysConstant.VEHICLE_COLOR, color));
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
    }

    public static void putErrorInfo(String errorInfo, int errorLine, List<Map<String, Object>> errorList) {
        if(errorList!=null && errorList.size()<=30) {  // 错误只记录前30条
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error_info", errorInfo);
            errorMap.put("error_line", errorLine);
            errorList.add(errorMap);
        }
    }

    /**
     * 隐藏list中的手机号中间4位
     * @param list
     * @return
     */
    public static void hideTelephone(List<Map> list, String phoneField) {
        if(isNotEmpty(list)) {  // 电话中间四位隐藏 159****1494
            for(Map map : list) {
                map.put(phoneField, hideTelephone((String) map.get(phoneField)));
            }
        }
    }
    public static String hideTelephone(String telephone) {
        String resultPhone = null;
        if(StringUtils.isNotEmpty(telephone) && telephone.length()>7) {
            resultPhone = telephone.substring(0, 3);
            for(int i=0;i<telephone.length()-7;i++) {   // 如果手机号不规范，只要大于7位，也只显示前三位和后四位
                resultPhone += "*";
            }
            resultPhone += telephone.substring(telephone.length()-4);
        } else {
            resultPhone = telephone;
        }
        return resultPhone;
    }

    public static void deleteMapField(List<Map> list, String[] fields) {
        if(isNotEmpty(list)) {
            for(Map map : list) {
                for(String field : fields)
                    map.remove(field);
            }
        }
    }

    /**
     * 获取一个自然数是由哪些2的幂次方组成
     * @param tagSum
     * @return
     */
    public static List<Integer> decryptTag(int tagSum) {
        List<Integer> tagList = new ArrayList<>();
        int n = 0;
        while(tagSum > 0) {
            for(int i=0;Math.pow(2, i) <= tagSum;i++) {
                n = (int)Math.pow(2, i);
            }
            tagSum -= n;
            tagList.add(n);
        }
        return tagList;
    }

//    /**
//     * @param fileUrl
//     * @return
//     * @描述：获取视频时长
//     * @时间：2019年4月8日 下午14:11:59
//     */
//    public static long getVideoDuration(String fileUrl) {
//        File source = new File(fileUrl);
//        long duration = 0;
//        try {
//            MultimediaObject instance = new MultimediaObject(source);
//            MultimediaInfo result = instance.getInfo();
//            duration = result.getDuration();
//        } catch (Exception e) {
//            LOGGER.error("", e);
//        }
//        return duration;
//    }
//
//    public static void main(String args[]) {
////        List<Integer> a = CommonUtils.decryptTag(12345);
////        System.out.println(a.toString());
//
//        long a = CommonUtils.getVideoDuration("C:\\迅雷下载\\02_06_6501_0_12890752.mp4");
//        long b = CommonUtils.getVideoDuration("C:\\迅雷下载\\ALARM_2_030403.mp4");
//        System.out.println(a);
//        System.out.println(b);
//    }
}