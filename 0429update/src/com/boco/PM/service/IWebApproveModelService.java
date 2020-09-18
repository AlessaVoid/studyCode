package com.boco.PM.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebApproveModel;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebApproveModel业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebApproveModelService extends IGenericService<WebApproveModel,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 新增常用审批意见模板信息.
	 *
	 * @param webApproveModel
	 * @param fdOper
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月9日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public Json insertWebApproveMode(WebApproveModel webApproveModel,FdOper fdOper) throws Exception;
	/**
	 * 
	 *
	 * TODO 修改常用审批意见模板信息.
	 *
	 * @param webApproveModel
	 * @param fdOper
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月9日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public Json updateWebApproveMode(WebApproveModel webApproveModel,FdOper fdOper) throws Exception;
}