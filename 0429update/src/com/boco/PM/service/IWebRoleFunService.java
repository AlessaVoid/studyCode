package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.entity.WebRoleFun;
import com.boco.SYS.entity.WebShortMenuInfo;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * 角色功能对照表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebRoleFunService extends IGenericService<WebRoleFun,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO 配置交易权限.
	 *
	 * @param roleCode
	 * @param funCodes
	 * @param updateUser
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月7日    	    杨滔    新建
	 * </pre>
	 */
	public void auth(String roleCode, String funCodes,String opercode) throws RuntimeException;
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
	public List<WebRoleFun> selectByRoleList(List<String> roleCode);
}