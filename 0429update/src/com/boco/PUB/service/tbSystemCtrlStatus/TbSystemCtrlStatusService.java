package com.boco.PUB.service.tbSystemCtrlStatus;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbSystemCtrlStatus;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * TbSystemCtrlStatus业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbSystemCtrlStatusService extends IGenericService<TbSystemCtrlStatus, String>{
    /*查询列表页*/
    List<Map<String, Object>> selectTbSystemCtrlStatus(TbSystemCtrlStatus tbSystemCtrlStatus);
}