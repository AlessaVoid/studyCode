package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebRoleFun;
import com.boco.SYS.entity.WebShortMenuInfo;
/**
 * 
 * 
 * 角色功能对照表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebRoleFunMapper extends GenericMapper<WebRoleFun,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO 查询某个操作的功能权限集.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月1日    	    杨滔    新建
	 * </pre>
	 */
	public List<WebRoleFun> selectOperFuns(String opercode) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO  查询某个操作的功能权限集.
	 *
	 * @param opercode
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebRoleFun> selectOperRoleFun(@Param(value = "operdegrees")String operdegrees) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 根据角色代码删除功能集.
	 *
	 * @param roleCode
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月2日    	    杨滔    新建
	 * </pre>
	 */
	public int deleteFunsByRole(String roleCode) throws DataAccessException;
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