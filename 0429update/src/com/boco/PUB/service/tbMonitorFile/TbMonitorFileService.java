package com.boco.PUB.service.tbMonitorFile;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.entity.TbMonitorFile;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * 文件监控业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbMonitorFileService extends IGenericService<TbMonitorFile,HashMap<String,Object>>{

    /**
     * 查询文件监控
     * @param tbMonitorFile
     * @return
     */
    List<TbMonitorFile> selectTbMonitorFile(TbMonitorFile tbMonitorFile);
}