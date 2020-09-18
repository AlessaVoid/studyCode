package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebShortMenuInfo;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebShortMenuInfo业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebShortMenuInfoService extends IGenericService<WebShortMenuInfo,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO 维护快捷菜单信息.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	public Json updateWebShortMenuInfo(WebShortMenuInfo webShortMenuInfo,String funCode) throws Exception;
	/**
	 * 
	 *
	 * TODO 根据柜员角色查询柜员拥有的功能(动态生成where条件中的or).
	 *
	 * @param roleCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebShortMenuInfo> selectByRoleList(List<String> roleCode)throws Exception;
	
	/**
	 * 
	 *
	 * TODO 根据条件查询菜单码.
	 *
	 * @param webShortMenuInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月21日    	     代策    新建
	 * </pre>
	 */
	public List<String> selectMenuNoByAttr(WebShortMenuInfo webShortMenuInfo) throws Exception;
	
	/**
	 * 
	 *
	 * TODO 查询所有父菜单.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月21日    	     代策    新建
	 * </pre>
	 */
	public List<String> selectUpMenuNo() throws Exception;
}