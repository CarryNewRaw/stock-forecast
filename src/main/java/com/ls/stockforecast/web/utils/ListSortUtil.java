package com.ls.stockforecast.web.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ListSortUtil {
    /**
     * @param targetList 目标排序List
     * @param sortField 排序字段(Map Key)
     * @param sortMode 排序方式（asc or  desc）
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void sortMap(List<Map<String,Object>> targetList, final String sortField, final String sortMode) {

        Collections.sort(targetList, new Comparator<Map<String,Object>>() {
            public int compare(Map<String,Object> obj1, Map<String,Object> obj2) {
                try {
                    if (sortMode != null && "desc".equals(sortMode)) {
                        return Integer.parseInt(obj2.get(sortField).toString()) > Integer.parseInt(obj1.get(sortField).toString())?1:-1; // 倒序
                    } else {
                        return Integer.parseInt(obj2.get(sortField).toString()) > Integer.parseInt(obj1.get(sortField).toString())?-1:1; // 正序
                    }
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
        });
    }
}