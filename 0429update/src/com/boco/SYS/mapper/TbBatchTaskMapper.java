package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbBatchTask;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * 日终任务表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbBatchTaskMapper extends GenericMapper<TbBatchTask, String>{

    /**
     * 查询任务列表
     * @param date
     * @return
     */
    List<Map<String, Object>> selectTaskList(String date);
}