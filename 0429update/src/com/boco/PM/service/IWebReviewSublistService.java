package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.entity.WebReviewSublist;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * WebReviewSublist业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebReviewSublistService extends IGenericService<WebReviewSublist,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO 按照orderNo进行排序查询
	 *
	 * @param appNo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年12月18日    	   谷立羊  新建
	 * </pre>
	 */
	public List<WebReviewSublist> selectAppNo(String appNo) throws Exception;
}