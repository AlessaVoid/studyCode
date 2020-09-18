package com.boco.SYS.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.GfDict;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * GfDict业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IGfDictService extends IGenericService<GfDict,HashMap<String,Object>>{

	Json insert(GfDict gfDict) throws Exception;

	Json update(GfDict gfDict) throws Exception;
	/**
	 * 
	 * 
	 * TODO 获取指定类型指定值的字典表值
	 * 
	 * @param organCode
	 * @param operCode
	 * @return
	 * 
	 *         <pre>
	 * 修改日期        修改人    修改原因
	 * 2014-12-1    	     雷大鹏    新建
	 * </pre>
	 */
	public String getValueByName(String accountName, String type);
	
	public String getNameByValue(String value, String type);
	
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
	public Integer selectOrder(GfDict gfDict);
	/**
	 * 
	 *
	 * TODO 模糊查询英文名.
	 *
	 * @param gfDict
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月30日    	    秦海舟   新建
	 * </pre>
	 */
	List<Map<String, String>> selectDictNo(GfDict gfDict);
	/**
	 * 
	 *
	 * TODO 模糊查询中文名.
	 *
	 * @param gfDict
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月30日    	    秦海舟   新建
	 * </pre>
	 */
	List<Map<String, String>> selectDictName(GfDict gfDict);
	/**
	 * 
	 *
	 * TODO 模糊查询创建人员.
	 *
	 * @param gfDict
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月30日    	    秦海舟   新建
	 * </pre>
	 */
	List<Map<String, String>> selectCreateOper(GfDict gfDict);
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
	List<GfDict> getDictKeyValue();
	
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
	List<GfDict> selectAreaList(GfDict gfDict);
	/**
	 * 
	 *
	 * TODO 查询渠道
	 *
	 * @param channels
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月9日    	   谷立羊  新建
	 * </pre>
	 */
	List<GfDict> selectByCodes(String[] channels);
	/**
	 * 
	 *
	 * TODO 通过字典类型及字典属性值查询
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月12日    	   谷立羊  新建
	 * </pre>
	 */
	public List<GfDict> findByValINKeys(Map<String, Object> map);

	public String selectKeyByNoAndVal(String ketIn, String dictNo);
}