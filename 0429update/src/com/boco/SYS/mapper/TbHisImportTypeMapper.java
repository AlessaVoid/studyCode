package com.boco.SYS.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TbHisImportTypeMapper {

    List<Map<String, Object>> selectByType(String type);

    List<Map<String, Object>> selectByTypeList(@Param("types") List<String> types);
}
