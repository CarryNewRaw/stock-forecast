package com.ls.stockforecast.utils;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author yaochenglong
 * @date 2018/3/16
 */
public class ValidUtils {

    /**
     * 正则表达式：验证手机号
     * 只验证第一位是1，后面10位是数字
     */
    public static final String REGEX_MOBILE = "^[1][0-9]{10}$";


    /**
     * 验证手机号是否合法
     * @param telephone
     * @return
     */
    public static boolean isTelephoneValid(String telephone){
        return Pattern.matches(REGEX_MOBILE, telephone);
    }

    public static boolean isNameValid(String name) {
        return name != null && name.length() <= 40;
    }

    public static boolean isOrganizationValid(String organization, Map<String, Long> orgNameMap) {
        return organization != null && organization.length() <= 40 && orgNameMap.containsKey(organization);
    }

    public static boolean isLicenseCardValid(String licenseCard) {
        return licenseCard != null && licenseCard.length() <= 12 && licenseCard.length()>=7;    // todo 这里的长度不准确，但是在这个范围内
    }

    public static boolean isLicenseColorValid(String licenseColor, Map<String, String> colorMap) {
        return licenseColor != null && colorMap.containsKey(licenseColor);
    }

}
