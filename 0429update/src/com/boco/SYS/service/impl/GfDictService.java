package com.boco.SYS.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.base.GenericService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.GfDict;
import com.boco.SYS.mapper.GfDictMapper;
import com.boco.SYS.service.IGfDictService;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * GfDictҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class GfDictService extends GenericService<GfDict,HashMap<String,Object>> implements IGfDictService{
	@Autowired
	private GfDictMapper gfDictMapper;

	/**
	 * �޸ķ���
	 * @throws Exception 
	 */
	@Override
	public Json update(GfDict gfDict) throws Exception {
			int count = updateByPK(gfDict);
			//�������ݿ�ʧ��
			if(count != 1){
				return this.json.returnMsg("false", "�޸�ʧ��!");
			}else
			return this.json.returnMsg("true", "�޸ĳɹ�!");
		}
	
	
	/**
	 * ��������
	 * @throws Exception 
	 */
	@Override
	public Json insert(GfDict gfDict) throws Exception {
		//��������У�����
		boolean check = checkInsertData(gfDict);
		//��֤ʧ��
		if(check == false){
			return this.json ;
		}
		
		int count = insertEntity(gfDict);
		//�������ݿ�ʧ��
				if(count != 1){
					return this.json.returnMsg("false", "����ʧ��!");
				}else
				return this.json.returnMsg("true", "�����ɹ�!");
		}
	
	//���Զ��巽��ʱʹ��
	//@Autowired
	//private GfDictMapper mapper;
	/**
	 * 
	 *
	 * TODO ������֤
	 *
	 * @param gfDict
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��18��    	    �غ���   �½�
	 * </pre>
	 */
	private boolean checkInsertData(GfDict gfDict) throws Exception {
		
		//��֤��Ϣ�����ݿ����Ƿ��Ѵ���
		GfDict dict = new GfDict();
		dict.setDictNo(gfDict.getDictNo());
		dict.setDictName(gfDict.getDictName());
		dict.setDictKeyIn(gfDict.getDictKeyIn());
		int existCount = countByAttr(dict);
		if(existCount > 0){
			this.json.returnMsg("false", "��Ϣ�Ѵ��ڣ�����������!");
			return false;
		}
		return true;
	}
	/**
	 * 
	 * 
	 * TODO ��ȡָ������ָ��ֵ���ֵ��ֵ
	 * 
	 * @param accountName
	 * @param type
	 * @return
	 * 
	 *         <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016-02-22      ����     �½�
	 * </pre>
	 */
	public String getValueByName(String accountName, String type) {
		List<Map> list = (List) DicCache.getDicMap().get(type);
		String value = "";
		if (list.size() > 0) {
			for (Map map : list) {
				if (map.get("DICT_VALUE_IN").toString().equals(accountName)) {
					value = map.get("DICT_KEY_IN").toString();
					break;
				}
			}
		}
		return value;
	}

	public String getNameByValue(String value, String type) {
		List<Map> list = (List) DicCache.getDicMap().get(type);
		String str = "";
		if (list.size() > 0) {
			for (Map map : list) {
				if (map.get("DICT_KEY_IN").toString().equals(value)) {
					str = map.get("DICT_VALUE_IN").toString();
					break;
				}
			}
		}
		return str;
	}
	/**
	 * 
	 *
	 * TODO ��ȡ��ʶ���.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public Integer selectOrder(GfDict gfDict){
		return gfDictMapper.selectOrder(gfDict);
	}

	/**
	 * ����Ӣ������������
	 */
	@Override
	public List<Map<String, String>> selectDictNo(GfDict gfDict) {
		List<Map<String, String>> list = gfDictMapper.selectDictNo(gfDict);
		return list;
	}

	/**
	 * ����������
	 */
	@Override
	public List<Map<String, String>> selectDictName(GfDict gfDict) {
		List<Map<String, String>> list = gfDictMapper.selectDictName(gfDict);
		return list;
	}

	/**
	 * ���鴴����Ա
	 */
	@Override
	public List<Map<String, String>> selectCreateOper(GfDict gfDict) {
		List<Map<String, String>> list = gfDictMapper.selectCreateOper(gfDict);
		return list;
	}

	/**
	 * 
	 *
	 * TODO ��ѯDICT_KEY_IN,DICT_VAALUE_IN
	 *
	 * @param gfDict
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��25��    ���ǽ�   �½�
	 * </pre>
	 */
	@Override
	public List<GfDict> getDictKeyValue() {
		List<GfDict> list =gfDictMapper.getDictKeyValue();
		return list;
	}

	/**
	 * 
	 *
	 * TODO ��ѯ���������б�
	 * @param gfDict 
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��16��    	  ������    �½�
	 * </pre>
	 */
	@Override
	public List<GfDict> selectAreaList(GfDict gfDict) {
		List<GfDict> list =gfDictMapper.selectAreaList(gfDict);
		return list;
	}


	@Override
	public List<GfDict> selectByCodes(String[] channels) {
		return gfDictMapper.selectByCodes(channels);
	}


	@Override
	public List<GfDict> findByValINKeys(Map<String, Object> map) {
		return gfDictMapper.findByValINKeys(map);
	}


	@Override
	public String selectKeyByNoAndVal(String ketIn, String dictNo) {
		return gfDictMapper.selectKeyByNoAndVal(ketIn,dictNo);
	}

	
}