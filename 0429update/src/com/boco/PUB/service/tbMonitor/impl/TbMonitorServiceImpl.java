package com.boco.PUB.service.tbMonitor.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.mapper.TbMonitorMapper;
import com.boco.SYS.service.TbMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbMonitor;
import com.boco.SYS.base.GenericService;

/**
 * 
 * 
 * 服务监控状态业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbMonitorServiceImpl extends GenericService<TbMonitor,HashMap<String,Object>> implements TbMonitorService {
    //有自定义方法时使用
    @Autowired
    private TbMonitorMapper tbMonitorMapper;


    /*获取监控页面的汇总值*/
    @Override
    public List<Map<String, Object>> selectRunCount() {
        List<Map<String, Object>> monitorList = tbMonitorMapper.selectRunCount();
        return monitorList;
    }
}