package com.boco.SYS.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boco.SYS.entity.GfDict;
import com.boco.SYS.global.Dic.Type;
import com.boco.SYS.mapper.FdUnichangeinfoMapper;
import com.boco.SYS.mapper.GfDictMapper;
import com.boco.util.JsonUtils;

/**
 * 
 * 
 * �ֵ��cache
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2014-9-15    	     ����    �½�
 * </pre>
 */
@Component
public class DicCache {
	private Logger log = Logger.getLogger(DicCache.class);
	@Autowired
	public GfDictMapper gfDictMapper;
	@Autowired
	public FdUnichangeinfoMapper fdUnichangeinfoMapper;
	public static Map<String, List<Map<String,Object>>> DicMap = null;

	/**
	 * 
	 * 
	 * TODO �����ֵ��
	 * 
	 * 
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2014-9-15    	     ����    �½�
	 * </pre>
	 */
	@PostConstruct
	public void start() {
		try {
			// �ֵ�����ݼ���
			DicMap = new HashMap<String, List<Map<String,Object>>>();
			setDic();
			log.info("==================�ֵ����سɹ�,��������" + DicMap.size() + "�����==================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * TODO ���ֵ�����ݴ���cache
	 * 
	 * 
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2014-9-15    	     ����    �½�
	 * </pre>
	 */
	public void setDic() {
		try {
			GfDict dict = new GfDict();
			dict.setStatus(Type.YES.getValue());
			dict.setSortColumn("DICT_NO,DICT_NO_ORDER");
			List<GfDict> diclList = gfDictMapper.selectByAttr(dict);
			Map<String, Object> dic = new HashMap<String, Object>();
			List<Map<String, Object>> groupList = new ArrayList<Map<String, Object>>();
			String groupCode = "null";
			for (int i = 0; i < diclList.size(); i++) {
				if (!groupCode.equals(diclList.get(i).getDictNo())) {
					DicMap.put(groupCode, groupList);
					groupList = new ArrayList<Map<String, Object>>();
					groupCode = diclList.get(i).getDictNo();
				}
				dic = new HashMap<String, Object>();
				dic.put("DICT_KEY_IN", diclList.get(i).getDictKeyIn());
				dic.put("DICT_VALUE_IN", diclList.get(i).getDictValueIn());
				dic.put("DICT_KEY_OUT", diclList.get(i).getDictKeyOut());
				dic.put("DICT_VALUE_OUT", diclList.get(i).getDictValueOut());
				groupList.add(dic);
				if (i == diclList.size() - 1) {
					Collections.reverse(groupList);
					DicMap.put(groupCode, groupList);
				}
			}
		} catch (Exception e) {
			log.error("�ֵ�����ʧ��", e);
		}
	}

	/**
	 * 
	 *
	 * TODO ���ݷ��������ֵ����Ʋ����ֵ����.
	 *
	 * @param dicName
	 * @param groupCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��2��    	    ����    �½�
	 * </pre>
	 */
	public static String getKeyByName(String dicName, String groupCode) {
		List<Map<String,Object>> list = (List<Map<String,Object>>) DicCache.getDicMap().get(groupCode);
		String dicVal = "";
		if (list.size() > 0) {
			for (Map<String,Object> map : list) {
				if (map.get("DICT_VALUE_IN").toString().equals(dicName)) {
					dicVal = map.get("DICT_KEY_IN").toString();
					break;
				}
			}
		}
		return JsonUtils.toJson(dicVal);
	}
	/**
	 * 
	 *
	 * TODO ���ݷ�����뼰���ƣ���ѯ�ֶδ��룬������jsonת��
	 *
	 * @param dicName
	 * @param groupCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��20��    	    ������   �½�
	 * </pre>
	 */
	public static String getKeyByName_(String dicName, String groupCode) {
		List<Map<String,Object>> list = (List<Map<String,Object>>) DicCache.getDicMap().get(groupCode);
		String dicVal = "";
		if (list!=null && list.size() > 0 ) {
			for (Map<String,Object> map : list) {
				if (map.get("DICT_VALUE_IN").toString().equals(dicName)) {
					dicVal = map.get("DICT_KEY_IN").toString();
					break;
				}
			}
		}
		return dicVal;
	}
	
	
	
	
	/**
	 * 
	 *
	 * TODO ���ݷ�����뼰���ƣ���ѯ�ֶδ��룬������jsonת��
	 *
	 * @param dicName
	 * @param groupCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��20��    	    ������   �½�
	 * </pre>
	 */
	public static String getKeyByName_Out(String dicName, String groupCode) {
		List<Map<String,Object>> list = (List<Map<String,Object>>) DicCache.getDicMap().get(groupCode);
		String dicVal = "";
		if (list.size() > 0) {
			for (Map<String,Object> map : list) {
				if (map.get("DICT_KEY_IN").toString().equals(dicName)) {
					dicVal = map.get("DICT_KEY_OUT").toString();
					break;
				}
			}
		}
		return dicVal;
	}
	
	/**
	 * 
	 *
	 * TODO ���ݷ��������ֵ��������ֵ�����.
	 *
	 * @param dicVal
	 * @param groupCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��2��    	    ����    �½�
	 * </pre>
	 */
	public static String getNameByKey(String dicVal, String groupCode) {
		List<Map<String,Object>> list = (List<Map<String,Object>>) DicCache.getDicMap().get(groupCode);
		String dicName = "";
		if (list.size() > 0) {
			for (Map<String,Object> map : list) {
				if (map.get("DICT_KEY_IN") != null) {
					if (map.get("DICT_KEY_IN").toString().equals(dicVal)) {
						dicName = map.get("DICT_VALUE_IN").toString();
						break;
					}
				}
			}
		}
		return JsonUtils.toJson(dicName);
	}
	/**
	 * 
	 *
	 * TODO ������jsonת��
	 *
	 * @param dicVal
	 * @param groupCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��9��6��    	   ������  �½�
	 * </pre>
	 */
	public static String getNameByKey_(String dicVal, String groupCode) {
		List<Map<String,Object>> list = (List<Map<String,Object>>) DicCache.getDicMap().get(groupCode);
		String dicName = "";
		if (list.size() > 0) {
			for (Map<String,Object> map : list) {
				if (map.get("DICT_KEY_IN") != null) {
					if (map.get("DICT_KEY_IN").toString().equals(dicVal)) {
						dicName = map.get("DICT_VALUE_IN").toString();
						break;
					}
				}
			}
		}
		return dicName;
	}
	/**
	 * 
	 *
	 * TODO ���ݷ��������������.
	 *
	 * @param groupCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��27��    	    ����    �½�
	 * </pre>
	 */
	public static List<Map<String,Object>> getGroupByCode(String groupCode) {
		List<Map<String,Object>> list = (List<Map<String,Object>>) DicCache.getDicMap().get(groupCode);
		return list;
	}

	public static Map<String, List<Map<String,Object>>> getDicMap() {
		return DicMap;
	}
}
