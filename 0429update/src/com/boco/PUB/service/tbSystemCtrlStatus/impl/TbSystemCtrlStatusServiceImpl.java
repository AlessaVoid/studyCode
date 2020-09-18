package com.boco.PUB.service.tbSystemCtrlStatus.impl;

import com.boco.PUB.service.tbSystemCtrlStatus.TbSystemCtrlStatusService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbSystemCtrlStatus;
import com.boco.SYS.mapper.TbSystemCtrlStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * TbSystemCtrlStatus业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class TbSystemCtrlStatusServiceImpl extends GenericService<TbSystemCtrlStatus, String> implements TbSystemCtrlStatusService {
    //有自定义方法时使用
    @Autowired
    private TbSystemCtrlStatusMapper tbSystemCtrlStatusMapper;

    @Override
    public List<Map<String, Object>> selectTbSystemCtrlStatus(TbSystemCtrlStatus tbSystemCtrlStatus) {
        List<Map<String, Object>> list = tbSystemCtrlStatusMapper.selectTbSystemCtrlStatus(tbSystemCtrlStatus);
        return list;

    }

}