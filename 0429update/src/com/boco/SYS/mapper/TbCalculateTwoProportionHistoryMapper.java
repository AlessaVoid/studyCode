package com.boco.SYS.mapper;

import java.util.HashMap;

import com.boco.SYS.entity.TbCalculateTwoProportionHistory;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * 测算二 权重参数 历史表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbCalculateTwoProportionHistoryMapper extends GenericMapper<TbCalculateTwoProportionHistory, String>{
    void deleteByMonthAndType(HashMap<String, Object> map);
}