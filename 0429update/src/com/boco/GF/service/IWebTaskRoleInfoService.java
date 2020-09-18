package com.boco.GF.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.WebTaskRoleInfo;

/**
 * 
 * 
 * 任务节点角色对应信息表||任务节点角色对应信息表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebTaskRoleInfoService extends IGenericService<WebTaskRoleInfo,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO 获取启动流程审批人的角色代码.
	 * processDefinitionKey : 流程定义key
	 * processDefinitionId : 流程定义id
	 * taskId ： 当前活动节点
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月6日    	  杜旭    新建
	 * </pre>
	 */
	public String getAppUserRole(String processDefinitionKey,String ProcessDefinitionId,String taskId,Map<String,Object> map) throws Exception;
	/**
	 * 
	 *
	 * TODO 获取启动流程审批人的角色代码.
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月27日    	  杜旭    新建
	 * </pre>
	 * 
	 */
	public String getAppUserRoleByAttr(String processDefinitionKey,WebTaskRoleInfo webTaskRoleInfo) throws Exception;
	/**
	 * 
	 *
	 * TODO 更新流程节点角色配置信息
	 *
	 * @param webTaskRoleInfoList
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年6月13日    	   谷立羊  新建
	 * </pre>
	 */
	public void updateRoleInfoByBatch(List<WebTaskRoleInfo> webTaskRoleInfoList,String procDefId)throws Exception;
	/**
	 * 
	 *
	 * TODO 根据流程定义id查询记录
	 *
	 * @param procDefId
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年6月13日    	   谷立羊  新建
	 * </pre>
	 */
	public List<WebTaskRoleInfo> selectByProcDefId(String procDefId)throws Exception;

	/**
	 * 
	 *
	 * TODO 流程节点角色配置
	 *
	 * @param procDefId
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月15日    	   谷立羊  新建
	 * </pre>
	 */
	public void nodeConfig(String procDefId,String type)throws Exception;
}