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
 * 字典表cache
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2014-9-15    	     杨滔    新建
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
	 * TODO 加载字典表
	 * 
	 * 
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2014-9-15    	     杨滔    新建
	 * </pre>
	 */
	@PostConstruct
	public void start() {
		try {
			// 字典表数据加载
			DicMap = new HashMap<String, List<Map<String,Object>>>();
			setDic();
			log.info("==================字典表加载成功,共加载了" + DicMap.size() + "组参数==================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * TODO 将字典表数据存入cache
	 * 
	 * 
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2014-9-15    	     杨滔    新建
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
			log.error("字典表加载失败", e);
		}
	}

	/**
	 * 
	 *
	 * TODO 根据分组代码和字典名称查找字典代码.
	 *
	 * @param dicName
	 * @param groupCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月2日    	    杨滔    新建
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
	 * TODO 根据分组代码及名称，查询字段代码，不进行json转换
	 *
	 * @param dicName
	 * @param groupCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月20日    	    谷立羊   新建
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
	 * TODO 根据分组代码及名称，查询字段代码，不进行json转换
	 *
	 * @param dicName
	 * @param groupCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月20日    	    谷立羊   新建
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
	 * TODO 根据分组代码和字典代码查找字典名称.
	 *
	 * @param dicVal
	 * @param groupCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月2日    	    杨滔    新建
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
	 * TODO 不进行json转换
	 *
	 * @param dicVal
	 * @param groupCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月6日    	   谷立羊  新建
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
	 * TODO 根据分组代码查分组内容.
	 *
	 * @param groupCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月27日    	    杨滔    新建
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
