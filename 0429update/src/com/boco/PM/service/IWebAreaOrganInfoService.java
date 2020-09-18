package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebAreaOrganInfo;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebAreaOrganInfo业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebAreaOrganInfoService extends IGenericService<WebAreaOrganInfo,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO 新增地区信息.
	 *
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public Json insertWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo,String organs,FdOper fdOper) throws Exception;
	/**
	 * 
	 *
	 * TODO 修改地区信息.
	 *
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public Json updateWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo,String organs,FdOper fdOper) throws Exception;
	/**
	 * 
	 *
	 * TODO 删除地区信息.
	 *
	 * @param areaCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public void deleteWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo) throws Exception;
	/**
	 * 
	 *
	 * TODO 查询其他区域所选择的机构信息.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebAreaOrganInfo> selectNotEqualOrgan(String areaCode);
}