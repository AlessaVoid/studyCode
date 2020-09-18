package com.boco.SYS.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbMonitor;
import com.boco.SYS.base.IGenericService;

/**
 *
 *
 * 服务监控状态业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbMonitorService extends IGenericService<TbMonitor,HashMap<String,Object>>{

    /**
     * 获取监控页面的汇总值
     * @return
     */
    List<Map<String, Object>> selectRunCount();


}