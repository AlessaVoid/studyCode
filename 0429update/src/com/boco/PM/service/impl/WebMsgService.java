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
 * ��Ϣ�б�||��Ϣ�б�ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ���ǽ�      �����½�
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
	 * TODO ������Ϣҳ��
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2016-04-23       ���ǽ�      �����½�
	 * </pre>
	 */
	public List<Map<String, String>> getWebMsgList(String opercode) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		//���ֵ���в�����еĽ��ײ�������
		List<Map<String,Object>> typeList = DicCache.getGroupByCode("MSG_TYPE");
		//��WebMsg�в�����߹�Ա��Ҫ����Ĵ��콻�ײ������ʹ���
		List<String> msgTypeList = this.getMsgType(opercode);
		//��gfDictList���ҳ���Ա�Ĵ��콻�ײ������ʹ���ʹ��콻�ײ�������
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
	 * TODO ��ȡ��ҳ������Ϣ�б�
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2016-04-23       ���ǽ�      �����½�
	 * </pre>
	 */
	public Map<String, List<WebMsg>> getHomePageWebMsg(String opercode) throws Exception {
		List<Map<String, String>> listTypeName = new ArrayList<Map<String, String>>();
		Map<String, List<WebMsg>> mapList=new LinkedHashMap<String, List<WebMsg>>();
		//���ֵ���в�����еĽ��ײ�������
		List<GfDict> gfDict = gfDictService.getDictKeyValue();
		//��WebMsg�в�����߹�Ա��Ҫ����Ĵ��콻�ײ������ʹ���
		List<String> msgTypeList = this.getMsgType(opercode);
		//��gfDictList���ҳ���Ա�Ĵ��콻�ײ�������
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
			//��ѯ��ת��ַ
			String url = this.findUrl(msgTypeCode);
			WebMsg webMsg = new WebMsg();
			webMsg.setWebMsgStatus("1");//����״̬||����״̬1-������ 2-���� 3-����ͨ�� 4-�������� 5-�ر�����
			webMsg.setRepUserCode(opercode);//������Ա
			webMsg.setMsgType(msgTypeCode);//��Ϣ����
			//��ѯÿ���ļ�¼��
			String count = this.getMsgCount(webMsg);
			// ��ѯ���ײ������ͣ�������ϸҳ��
			List<WebMsg> list = this.getMsgTypeUrl(webMsg);
			mapList.put(msgTypeName+"_"+count+"_"+url,list);
		}
		return mapList;
		
	}
	/**
	 * 
	 *
	 * TODO ��ȡ���ײ������ʹ���
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2016-04-23       ���ǽ�      �����½�
	 * </pre>
	 */
	public List<String> getMsgType(String opercode) throws Exception{
		return webMsgMapper.getMsgType(opercode);
	}
		
	
	/**
	 * 
	 *
	 * TODO  ��ѯ���ײ������ͣ�������ϸҳ��.
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��03��    ���ǽ�    �½�
	 * </pre>
	 */
	public List<WebMsg> getMsgTypeUrl(WebMsg webMsg) throws Exception {
		return webMsgMapper.getMsgTypeUrl(webMsg);
	}
	/**
	 * 
	 *
	 * TODO  ��ѯÿ�б�ļ�¼��
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��03��    ���ǽ�    �½�
	 * </pre>
	 */
	public String getMsgCount(WebMsg webMsg) throws Exception {
		return webMsgMapper.getMsgCount(webMsg);
	}
	/**
	 * 
	 *
	 * TODO   ��ѯ��ת��ַ
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��03��    ���ǽ�    �½�
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