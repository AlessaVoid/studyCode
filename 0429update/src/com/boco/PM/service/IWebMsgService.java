package com.boco.PM.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.entity.WebMsg;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * 消息列表
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2016-04-23   宁智杰     批量新建
 * </pre>
 */
public interface IWebMsgService extends IGenericService<WebMsg,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 待办消息页面
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2016-04-23       宁智杰      批量新建
	 * </pre>
	 */
	public List<Map<String, String>> getWebMsgList(String opercode) throws Exception;
	/**
	 * 
	 *
	 * TODO 获取首页待办消息列表
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2016-04-23       宁智杰      批量新建
	 * </pre>
	 */
	public Map<String, List<WebMsg>> getHomePageWebMsg(String opercode) throws Exception;
	/**
	 * 
	 *
	 * TODO 获取交易操作类型代码
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2016-04-23       宁智杰      批量新建
	 * </pre>
	 */
	public List<String> getMsgType(String opercode) throws Exception;
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
	public List<WebMsg> getMsgTypeUrl(WebMsg webMsg) throws Exception;
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
	public String getMsgCount(WebMsg webMsg) throws Exception;
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
	public String findUrl(String msgTypeCode) throws Exception;
	/**
	 * 
	 *
	 * TODO 插入导出参数模板提示
	 *
	 * @param newMsg
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月15日    	   谷立羊  新建
	 * </pre>
	 */
	public void insertExportNotice(WebMsg newMsg)throws Exception;
	/**
	 * 
	 *
	 * TODO 立即刷新
	 *
	 * @param newMsg
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月20日    	   谷立羊  新建
	 * </pre>
	 */
	public void refreshNow()throws Exception;
}