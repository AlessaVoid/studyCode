package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.WebSublicenseInfo;
import com.boco.SYS.util.Json;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * WebSublicenseInfo业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebSublicenseInfoService extends IGenericService<WebSublicenseInfo,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 维护转授权信息.
	 *
	 * @param webSublicenseInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月2日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public Json outWebSub(WebSublicenseInfo webSublicenseInfo, String roles) throws Exception;
	/**
	 * 
	 *
	 * TODO 与角色功能表关联查询授权用户下拉列表数据.
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月7日    	  杜旭    新建
	 * </pre>
	 */
	public List<Map<String,String>> getPowerList(Map<String,String> query) throws Exception;
}