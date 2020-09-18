package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.WebSublicenseInfo;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * WebSublicenseInfo数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebSublicenseInfoMapper extends GenericMapper<WebSublicenseInfo,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 获取转出角色的记录数(转入的柜员不是当前转入柜员).
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月2日    	  杜旭    新建
	 * </pre>
	 */
	public Integer countOutRole(WebSublicenseInfo webSublicenseInfo);
	/**
	 * 
	 *
	 * TODO 根据转出柜员、转入柜员、角色代码、未收回记录修改记录信息.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月2日    	  杜旭    新建
	 * </pre>
	 */
	public Integer updateByAttr(WebSublicenseInfo webSublicenseInfo);
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
	/**
	 * 
	 *
	 * TODO 复杂流程：与角色功能表关联查询授权下拉列表数据.
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月5日    	  杜旭    新建
	 * </pre>
	 */
	public List<Map<String,String>> getPowerListByRole(Map<String, Object> query) throws Exception;
	
	/**
	 * 
	 *
	 * TODO 收回该柜员转出的指定角色.
	 *
	 * @param webSublicenseInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月11日    	  杜旭    新建
	 * </pre>
	 */
	public Integer backRoleAll(WebSublicenseInfo webSublicenseInfo);
}