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
 * GfDict业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class GfDictService extends GenericService<GfDict,HashMap<String,Object>> implements IGfDictService{
	@Autowired
	private GfDictMapper gfDictMapper;

	/**
	 * 修改方法
	 * @throws Exception 
	 */
	@Override
	public Json update(GfDict gfDict) throws Exception {
			int count = updateByPK(gfDict);
			//插入数据库失败
			if(count != 1){
				return this.json.returnMsg("false", "修改失败!");
			}else
			return this.json.returnMsg("true", "修改成功!");
		}
	
	
	/**
	 * 新增方法
	 * @throws Exception 
	 */
	@Override
	public Json insert(GfDict gfDict) throws Exception {
		//调用新增校验规则
		boolean check = checkInsertData(gfDict);
		//验证失败
		if(check == false){
			return this.json ;
		}
		
		int count = insertEntity(gfDict);
		//插入数据库失败
				if(count != 1){
					return this.json.returnMsg("false", "新增失败!");
				}else
				return this.json.returnMsg("true", "新增成功!");
		}
	
	//有自定义方法时使用
	//@Autowired
	//private GfDictMapper mapper;
	/**
	 * 
	 *
	 * TODO 新增验证
	 *
	 * @param gfDict
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月18日    	    秦海舟   新建
	 * </pre>
	 */
	private boolean checkInsertData(GfDict gfDict) throws Exception {
		
		//验证信息在数据库中是否已存在
		GfDict dict = new GfDict();
		dict.setDictNo(gfDict.getDictNo());
		dict.setDictName(gfDict.getDictName());
		dict.setDictKeyIn(gfDict.getDictKeyIn());
		int existCount = countByAttr(dict);
		if(existCount > 0){
			this.json.returnMsg("false", "信息已存在，请重新输入!");
			return false;
		}
		return true;
	}
	/**
	 * 
	 * 
	 * TODO 获取指定类型指定值的字典表值
	 * 
	 * @param accountName
	 * @param type
	 * @return
	 * 
	 *         <pre>
	 * 修改日期        修改人    修改原因
	 * 2016-02-22      杜旭     新建
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
	 * TODO 获取标识序号.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	public Integer selectOrder(GfDict gfDict){
		return gfDictMapper.selectOrder(gfDict);
	}

	/**
	 * 分组英文名联想输入
	 */
	@Override
	public List<Map<String, String>> selectDictNo(GfDict gfDict) {
		List<Map<String, String>> list = gfDictMapper.selectDictNo(gfDict);
		return list;
	}

	/**
	 * 分组中文名
	 */
	@Override
	public List<Map<String, String>> selectDictName(GfDict gfDict) {
		List<Map<String, String>> list = gfDictMapper.selectDictName(gfDict);
		return list;
	}

	/**
	 * 分组创建人员
	 */
	@Override
	public List<Map<String, String>> selectCreateOper(GfDict gfDict) {
		List<Map<String, String>> list = gfDictMapper.selectCreateOper(gfDict);
		return list;
	}

	/**
	 * 
	 *
	 * TODO 查询DICT_KEY_IN,DICT_VAALUE_IN
	 *
	 * @param gfDict
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月25日    宁智杰   新建
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
	 * TODO 查询地区管理列表
	 * @param gfDict 
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月16日    	  李沐阳    新建
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