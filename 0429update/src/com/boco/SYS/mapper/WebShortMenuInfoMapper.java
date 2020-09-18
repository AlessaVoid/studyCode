package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebShortMenuInfo;
/**
 * 
 * 
 * WebShortMenuInfo数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebShortMenuInfoMapper extends GenericMapper<WebShortMenuInfo,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO 根据柜员号删除快捷菜单信息.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	public Integer deleteByOperCode(String operCode) throws Exception;

	/**
	 * 
	 *
	 * TODO 查询菜单码.
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
	 * TODO 查询所有有下级菜单的菜单码.
	 *
	 * @param webShortMenuInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月21日    	     代策    新建
	 * </pre>
	 */
	public List<String> selectUpMenuNo() throws Exception;
}