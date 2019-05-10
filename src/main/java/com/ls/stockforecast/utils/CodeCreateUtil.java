package com.ls.stockforecast.utils;


import java.util.*;

public class CodeCreateUtil {

	public static Random random = new Random(System.currentTimeMillis());

	public static String[] prov_Code = { "11", "12", "13", "14", "15", "21",
			"22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42",
			"43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",
			"63", "64", "65", "99" };
	public static String[] prov_Name = { "京", "津", "冀", "晋", "蒙", "辽",
			"吉","黑", "沪", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂",
			"湘", "粤", "桂","琼", "渝", "川", "贵", "云", "藏", "陕", "甘",
			"青", "宁", "新", "测" };

	public static String[] suffix_code = {"GUA", "XUE", "JING", "GANG", "AO", "SHI", "LING"};

	public static String[] suffix_name = {"挂", "学", "警", "港", "澳", "使", "领"};

	public static String[] prov_totalName = { "北京", "天津", "河北", "山西", "内蒙古", "辽宁",
			"吉林","黑龙江", "上海", "江苏", "浙江", "安徽", "福建", "江西", "山东", "河南", "湖北",
			"湖南", "广东", "广西","海南", "重庆", "四川", "贵州", "云南", "西藏", "陕西", "甘肃",
			"青海", "宁夏", "新疆", "未知" };

	public static String[] color_Code = { "1", "2", "3", "4","5","6","9" };
	public static String[] color_Name = { "蓝", "黄", "黑", "白","黄绿双拼","绿", "其他" };


	public static Map<String, String> codeMap = new HashMap<String, String>();
	public static Map<String, String> suffixMap = new HashMap<String, String>();
	public static Map<String, String> colorMap = new HashMap<String, String>();
	public static Map<String, String> colorNameMap = new HashMap<String, String>();
	static {

		for (int i = 0; i < color_Name.length; i++) {
			colorNameMap.put(color_Code[i], color_Name[i]);
		}
	}

	public static String getColorName(String color) {
		String code = "";
		if (colorNameMap.containsKey(color)) {
			code += colorNameMap.get(color);
		} else {
			code += colorNameMap.get("9");
		}
		return code;
	}
	public static String getColorCode(String color) {
		String code = "";
		if (colorMap.containsKey(color)) {
			code += colorMap.get(color);
		} else {
			code += colorMap.get("其他");
		}
		return code;
	}
	static {
		for (int i = 0; i < prov_Code.length; i++) {
			codeMap.put(prov_Name[i], prov_Code[i]);
		}
		for (int i = 0; i < suffix_code.length; i++) {
			suffixMap.put(suffix_name[i], suffix_code[i]);
		}
		for (int i = 0; i < color_Name.length; i++) {
			colorMap.put(color_Name[i], color_Code[i]);
		}
	}

	/**
	 * 获取车辆ID
	 * @param license 车牌
	 * @param licenseColor 车牌颜色Code
	 * @return
	 */
	public static String getVehicleId(String license, int licenseColor) {
		if (license == null)
			return null;
		String code = null;
		String name = license.trim().replaceAll("-", "");
		name = name.replaceAll(" ", "");
		String pri = "";
		String suffix = "";
		if (license.length() > 1) {
			pri = license.substring(0, 1);
			suffix = name.substring(name.length() - 1);
		}
		if (codeMap.containsKey(pri)) {
			code = codeMap.get(pri);
			code += name.substring(1, name.length() - 1);
			if (suffixMap.containsKey(suffix)) {
				code += suffixMap.get(suffix);
			} else {
				code += suffix;
			}
			code += licenseColor;
//			if (colorMap.containsKey(color)) {
//				code += colorMap.get(color);
//			} else {
//				code += colorMap.get("其他");
//			}
		} else {
			code = name;
		}
		return code;
	}

	/**
	 * 获取车辆ID
	 * @param license 车牌
	 * @param color 车牌颜色（中文）
	 * @return
	 */
	public static String getVehicleId(String license, String color) {
		if (license == null)
			return null;
		String code = null;
		String name = license.trim().replaceAll("-", "");
		name = name.replaceAll(" ", "");
		String pri = "";
		String suffix = "";
		if (license != null) {
			if (license.length() > 1) {
				pri = license.substring(0, 1);
				suffix = name.substring(name.length() - 1);
			}
		}
		if (codeMap.containsKey(pri)) {
			code = codeMap.get(pri);
			code += name.substring(1, name.length() - 1);
			if (suffixMap.containsKey(suffix)) {
				code += suffixMap.get(suffix);
			} else {
				code += suffix;
			}
			if (colorMap.containsKey(color)) {
				code += colorMap.get(color);
			} else {
				code += colorMap.get("其他");
			}
		} else {
			code = name;
		}
		return code;
	}

	/**
	 * 获取车辆车牌号和车辆颜色
	 * @param vehicleId 车牌id
	 * @return String[0] 车牌号； String[1] 车牌颜色
	 */
	public static String[] getVehicleCodeAndColor(String vehicleId) {
		if (vehicleId == null || vehicleId.length()<5)
			return null;
		String[] vehicle = new String[2];
		String licenseColor = vehicleId.substring(vehicleId.length()-1, vehicleId.length());    // 车牌颜色
        String licenseCard1 = vehicleId.substring(0,2); // 车牌第一个中文
        String licenseCard2 = vehicleId.substring(2,3); // 车牌横杠前的英文字母
        String licenseCard3 = vehicleId.substring(3,vehicleId.length()-1);  // 车牌号
        for(int i=0;i<prov_Code.length;i++) {
            if(prov_Code[i].equals(licenseCard1)) {
                licenseCard1 = prov_Name[i];
                break;
            }
        }
		for(int i=0;i<suffix_code.length;i++) {
            String code = suffix_code[i];
            if(licenseCard3.contains(code)) {
                licenseCard3.replace(code, suffix_name[i]);
                break;
            }
		}
        vehicle[0] = licenseCard1 + licenseCard2 + licenseCard3;
        vehicle[1] = licenseColor;
		return vehicle;
	}

	/**
	 * 获取一个模拟车牌号
	 * @return	沪XXDDDD
	 */
	public static String getSimulateLicenseCard() {
		String licensecard = "沪";
		String letters = "abcdefghijklmn0123456789";
		licensecard += letters.charAt(random.nextInt(14)) + "" + letters.charAt(random.nextInt(letters.length()));
		for(int i=0; i<4; i++) {
			licensecard += letters.substring(14).charAt(random.nextInt(10));
		}
		return licensecard.toUpperCase();
	}
	public static void main(String[] args){

//        int i=0;
//		String[] vehicleStrs = {"45KK11012"};
//        for(String vehicleStr : vehicleStrs) {
//            String a = getSimulateLicenseCard();
////            String a = "45BG088GUA2";
//            System.out.println("1--------" + getVehicleCodeAndColor(vehicleStr)[0] + " 2-------" + getVehicleCodeAndColor(vehicleStr)[1]);
            System.out.println(getVehicleId("浙H-C8163", 2));
            System.out.println(getVehicleId("浙H-C8136", 2));
            System.out.println(getVehicleId("浙H-C8276", 2));
            System.out.println(getVehicleId("浙H-C8006", 2));
//            System.out.println(getVehicleId("京A-AS033",2));
//        }

//        Map<String, Object> map = new HashMap<String, Object>();
//        List<Long> list = new ArrayList<>();
//        for(long i=0;i<10;i++) {
//            list.add(i);
//        }
//        map.put("key", list);
//        String a = JSON.toJSONString(map);
//        JSON json = JSONObject.parseObject(a);
//        System.out.println(json.toString());
//        System.out.println(json.toJSONString());
//        Map<String, List<Long>> map = (Map<String, List<Long>>) JSONObject.parse("{\"captain_id\":[2]}");
//        System.out.println();
//		String licenseCard = "45KK1101";
//		int licenseColor = 2;
//		System.out.println(CodeCreateUtil.getVehicleId(licenseCard, licenseColor));
	}
}
