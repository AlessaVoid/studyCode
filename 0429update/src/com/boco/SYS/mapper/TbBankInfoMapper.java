package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

public interface TbBankInfoMapper {

    List<Map<String, Object>> selectBankList();

    String selectCodeByName(String name);
}
