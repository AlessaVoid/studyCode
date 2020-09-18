package com.boco.GF.service;

import java.util.HashMap;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebTradeTime;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebTradeTime业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebTradeTimeService extends IGenericService<WebTradeTime,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 修改交易时间节点信息.
	 *
	 * @param webTradeTime
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月1日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public Json updateWebTradeTime(WebTradeTime webTradeTime,FdOper fdOper) throws Exception;
	/**
	 * 
	 *
	 * TODO 重置交易时间节点信息
	 *
	 * @param webTradeTime
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年8月24日    	   谷立羊  新建
	 * </pre>
	 */
	public void reSetTradeTime(WebTradeTime webTradeTime);
}