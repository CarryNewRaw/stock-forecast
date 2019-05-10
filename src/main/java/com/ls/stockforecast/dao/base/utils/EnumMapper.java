package com.ls.stockforecast.dao.base.utils;

import com.ls.stockforecast.entity.model.base.utils.Enum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnumMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Enum record);

    int insertSelective(Enum record);

    Enum selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Enum record);

    int updateByPrimaryKey(Enum record);

    List<Enum> getByModuleAndName(@Param("module") String module, @Param("name") String name);
}