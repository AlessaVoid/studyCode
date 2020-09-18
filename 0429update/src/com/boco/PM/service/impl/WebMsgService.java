package com.boco.PM.service.impl;

import com.boco.PM.service.IWebMsgService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.cache.WebMsgCache;
import com.boco.SYS.entity.GfDict;
import com.boco.SYS.entity.WebMsg;
import com.boco.SYS.mapper.WebMsgMapper;
import com.boco.SYS.service.IGfDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 
 * 
 * 消息列表||消息列表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      宁智杰      批量新建
 * </pre>
 */
@Service
public  class WebMsgService extends GenericService<WebMsg,java.lang.String> implements IWebMsgService{
	@Autowired
	private WebMsgMapper webMsgMapper;
	@Autowired
	private IGfDictService gfDictService;
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
	public List<Map<String, String>> getWebMsgList(String opercode) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		//从字典表中查出所有的交易操作类型
		List<Map<String,Object>> typeList = DicCache.getGroupByCode("MSG_TYPE");
		//从WebMsg中查出在线柜员需要处理的待办交易操作类型代码
		List<String> msgTypeList = this.getMsgType(opercode);
		//从gfDictList中找出柜员的待办交易操作类型代码和待办交易操作类型
		for(int i=0;i<typeList.size();i++){
			if(msgTypeList.contains(typeList.get(i).get("DICT_KEY_IN"))){
				String dictKey = typeList.get(i).get("DICT_KEY_IN").toString();
				String dictValue = typeList.get(i).get("DICT_VALUE_IN").toString();
				Map<String, String> map = new HashMap<String, String>();
				map.put("msgTypeCode", dictKey);
				map.put("msgTypeName", dictValue);
				list.add(map);
			}
		}
		return list;
	} 
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
	public Map<String, List<WebMsg>> getHomePageWebMsg(String opercode) throws Exception {
		List<Map<String, String>> listTypeName = new ArrayList<Map<String, String>>();
		Map<String, List<WebMsg>> mapList=new LinkedHashMap<String, List<WebMsg>>();
		//从字典表中查出所有的交易操作类型
		List<GfDict> gfDict = gfDictService.getDictKeyValue();
		//从WebMsg中查出在线柜员需要处理的待办交易操作类型代码
		List<String> msgTypeList = this.getMsgType(opercode);
		//从gfDictList中找出柜员的待办交易操作类型
		for(int i=0;i<gfDict.size();i++){
			if(msgTypeList.contains(gfDict.get(i).getDictKeyIn())){
				String dictKey = gfDict.get(i).getDictKeyIn().toString();
				String dictValue = gfDict.get(i).getDictValueIn().toString();
				Map<String, String> map = new HashMap<String, String>();
				map.put("msgTypeCode", dictKey);
				map.put("msgTypeName", dictValue);
				listTypeName.add(map);
			}
		}
		for(int i=0;i<listTypeName.size();i++){
			String msgTypeCode = listTypeName.get(i).get("msgTypeCode");
			String msgTypeName = listTypeName.get(i).get("msgTypeName");
			//查询跳转地址
			String url = this.findUrl(msgTypeCode);
			WebMsg webMsg = new WebMsg();
			webMsg.setWebMsgStatus("1");//复核状态||复核状态1-待审批 2-撤销 3-审批通过 4-审批驳回 5-关闭申请
			webMsg.setRepUserCode(opercode);//复核人员
			webMsg.setMsgType(msgTypeCode);//消息类型
			//查询每个的记录数
			String count = this.getMsgCount(webMsg);
			// 查询交易操作类型，处理详细页面
			List<WebMsg> list = this.getMsgTypeUrl(webMsg);
			mapList.put(msgTypeName+"_"+count+"_"+url,list);
		}
		return mapList;
		
	}
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
	public List<String> getMsgType(String opercode) throws Exception{
		return webMsgMapper.getMsgType(opercode);
	}
		
	
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
	public List<WebMsg> getMsgTypeUrl(WebMsg webMsg) throws Exception {
		return webMsgMapper.getMsgTypeUrl(webMsg);
	}
	/**
	 * 
	 *
	 * TODO  查询每列表的记录数
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月03日    宁智杰    新建
	 * </pre>
	 */
	public String getMsgCount(WebMsg webMsg) throws Exception {
		return webMsgMapper.getMsgCount(webMsg);
	}
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
	@Override
	public String findUrl(String msgTypeCode) throws Exception {
		return webMsgMapper.fingUrl(msgTypeCode);
	}
	@Override
	public void insertExportNotice(WebMsg newMsg) throws Exception {
		newMsg.setRepDate("");
		newMsg.setRepTime("");
		newMsg.setRepRoleName("");
		newMsg.setRepUserName("");
		newMsg.setRepUserOrganCode("");
		newMsg.setRepUserOrganName("");
		webMsgMapper.insertEntity(newMsg);
	}
	@Override
	public void refreshNow() throws Exception {
		WebMsgCache.setRepMap(webMsgMapper.countByRepuser());
	}
}