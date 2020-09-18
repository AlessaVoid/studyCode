package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbBankRmbBusi;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 商业银行人民币业务统计表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbBankRmbBusiMapper extends GenericMapper<TbBankRmbBusi, String>{


    /**
     * 查询当前月份最新数据的日期
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectDataDate(Map<String, Object> paramMap);
}