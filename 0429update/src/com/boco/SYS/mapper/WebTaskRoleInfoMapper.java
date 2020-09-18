package com.boco.SYS.mapper;

import java.util.HashMap;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebTaskRoleInfo;
/**
 * 
 * 
 * 任务节点角色对应信息表||任务节点角色对应信息表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebTaskRoleInfoMapper extends GenericMapper<WebTaskRoleInfo,HashMap<String,Object>>{
 /**
  * 
  *
  * TODO 通过流程定义id批量数据
  *
  * @param procDefId
  *
  * <pre>
  * 修改日期        修改人    修改原因
  * 2016年6月13日    	   谷立羊  新建
  * </pre>
  */
	void deleteByProcDefId(String procDefId);
}