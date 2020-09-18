package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbCalculateOneProportionHistory;

import java.util.HashMap;

/**
 * 
 * 
 * 测算 权重历史表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbCalculateOneProportionHistoryMapper extends GenericMapper<TbCalculateOneProportionHistory, String>{
    /**
     * @Description 根据月份和类型删除测算历史参数表
     * @Author liujinbo
     * @Date 2020/3/6
     * @param map
     * @Return void
     */
    void deleteByMonthAndType(HashMap<String, Object> map);
}