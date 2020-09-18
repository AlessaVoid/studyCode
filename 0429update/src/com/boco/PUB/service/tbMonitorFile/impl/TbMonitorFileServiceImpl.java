package com.boco.PUB.service.tbMonitorFile.impl;

import com.boco.PUB.service.tbMonitorFile.TbMonitorFileService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbMonitorFile;
import com.boco.SYS.mapper.TbMonitorFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 
 * 文件监控业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbMonitorFileServiceImpl extends GenericService<TbMonitorFile,HashMap<String,Object>> implements TbMonitorFileService {

    //有自定义方法时使用
    @Autowired
    private TbMonitorFileMapper tbMonitorFileMapper;


    @Override
    public List<TbMonitorFile> selectTbMonitorFile(TbMonitorFile tbMonitorFile) {
        List<TbMonitorFile> list = tbMonitorFileMapper.selectTbMonitorFile(tbMonitorFile);
        return list;
    }

}