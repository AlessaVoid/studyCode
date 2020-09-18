package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbMonitor;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * 服务监控状态数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbMonitorMapper extends GenericMapper<TbMonitor,HashMap<String,Object>>{

    /**
     * 获取监控页面的汇总值
     * @return
     */
    List<Map<String, Object>> selectRunCount();

}