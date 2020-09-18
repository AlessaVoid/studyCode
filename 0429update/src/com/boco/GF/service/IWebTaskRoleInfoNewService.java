package com.boco.GF.service;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.WebTaskRoleInfoNew;

/**
 * 
 * 
 * WebTaskRoleInfoNew业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebTaskRoleInfoNewService extends IGenericService<WebTaskRoleInfoNew,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO 批量更新流程中驳回流转控制信息
	 *
	 * @param webTaskRoleInfoList
	 * @param procDefId
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年8月8日    	   谷立羊  新建
	 * </pre>
	 */
public	void updateRoleInfoByBatch(List<WebTaskRoleInfoNew> webTaskRoleInfoList,String procDefId) throws Exception;
	/**
	 * 
	 *
	 * TODO 根据流程定义查询记录
	 *
	 * @param procDefId
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年8月8日    	   谷立羊  新建
	 * </pre>
	 */
	public List<WebTaskRoleInfoNew> selectByProcDefId(String procDefId)throws Exception;
	/**
	 * 
	 *
	 * TODO 更新审批驳回后产品状态
	 *
	 * @param taskId
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月6日    	   谷立羊  新建
	 * </pre>
	 */
	public void updateProdStatus(String prodCode,String taskId,String custType) throws Exception;
	
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