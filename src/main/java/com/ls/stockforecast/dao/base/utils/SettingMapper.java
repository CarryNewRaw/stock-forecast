package com.ls.stockforecast.dao.base.utils;

import com.ls.stockforecast.entity.model.base.utils.Setting;
import org.apache.ibatis.annotations.Param;

public interface SettingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Setting record);

    int insertSelective(Setting record);

    Setting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Setting record);

    int updateByPrimaryKey(Setting record);

    Setting getSettingByModuleAndName(@Param("module") String module, @Param("name") String name);

    Setting getSettingByModuleAndValue(@Param("module") String module, @Param("value") String value);

    Object getSettingValueByModuleAndName(@Param("module") String module, @Param("name") String name);
}