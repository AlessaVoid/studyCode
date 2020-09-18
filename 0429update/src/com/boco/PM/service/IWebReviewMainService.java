package com.boco.PM.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.boco.SYS.base.IGenericService;
//import com.boco.SYS.entity.GfProdBaseInfo;
import com.boco.SYS.entity.WebReviewMain;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebReviewMain业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebReviewMainService extends IGenericService<WebReviewMain,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 请输入你的方法说明.
	 *
	 * @param appDto   添加或修改时表单元素值组成的dto对象
	 * @param dtoBeforeUpdate  仅修改申请需要的参数，表示修改前的dto对象，其他操作为null
	 * @param
	 * @throws Exception
	 *
	 ** TODO 复核申请公共方法.
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月26日    	  杜旭    新建
	 * </pre>
	 */
	@Transactional
	public void insertApproval(Object appDto, Object dtoBeforeUpdate, WebReviewMain webReviewMain) throws Exception;
	/**
	 * 
	 *
	 * TODO 复核处理公共方法.
	 *
	 * @param webReviewMain
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月7日    	  杜旭    新建
	 * </pre>
	 */
	public void doApproval(WebReviewMain webReviewMain) throws Exception;
	/**
	 * 
	 *
	 * TODO 根据主键更新或者添加一条记录.
	 *
	 * @param
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月26日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public boolean saveOrUpdate(WebReviewMain webReviewMain) throws RuntimeException, Exception;
	/**
	 * 
	 *
	 * TODO 修改复核主表的信息.
	 *
	 * @param appNo
	 * @param type
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月12日    	  杜旭    新建
	 * </pre>
	 */
	public Json updateWebReviewMain(String appNo, String type);
	/**
	 * 
	 *
	 * TODO 获取待办信息提示.
	 *
	 * @param
	 * @param
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月12日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebReviewMain> getReviewMsgs() throws Exception;
//	/**
//	 *
//	 *
//	 * TODO 获取汇率信息提示.
//	 *
//	 * @param
//	 * @param
//	 *
//	 * <pre>
//	 * 修改日期        修改人    修改原因
//	 * 2016年5月12日    	张兴帅    新建
//	 * </pre>
//	 */
//	public List<GfProdBaseInfo> getExchangeRate() throws Exception;
}