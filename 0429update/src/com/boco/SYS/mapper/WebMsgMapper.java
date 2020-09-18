package com.boco.SYS.mapper;


import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebMsg;
/**
 * 
 * 
 * 消息列表
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2016-04-23   宁智杰      批量新建
 * </pre>
 */
public interface WebMsgMapper extends GenericMapper<WebMsg,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 查询交易操作类型种类
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2016-04-23       宁智杰      批量新建
	 * </pre>
	 */
	public List<String> getMsgType(String opercode) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 根据审批用户分组查询待办记录数.
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月27日    	    杨滔    新建
	 * </pre>
	 */
	public List<Map<String,String>> countByRepuser() throws DataAccessException;
	/**
	 * 
	 *
	 * TODO  查询交易操作类型，处理详细页面.
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月03日    宁智杰    新建
	 * </pre>
	 */
	public List<WebMsg> getMsgTypeUrl(WebMsg webMsg) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO  查询每个的记录数
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月03日    宁智杰    新建
	 * </pre>
	 */
	public String getMsgCount(WebMsg webMsg) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO   查询跳转地址
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月03日    宁智杰    新建
	 * </pre>
	 */
	public String fingUrl(String msgTypeCode) throws DataAccessException;
}