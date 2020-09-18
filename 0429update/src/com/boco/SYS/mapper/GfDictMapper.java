package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.entity.GfDict;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * GfDict数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface GfDictMapper extends GenericMapper<GfDict,HashMap<String,Object>>{
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

	public List<Map<String, String>> selectDictNo(GfDict gfDict);

	public List<Map<String, String>> selectDictName(GfDict gfDict);

	public List<Map<String, String>> selectCreateOper(GfDict gfDict);
	public int countBy(GfDict gfDict) throws DataAccessException;

	/**
	 * 
	 */
	/**
	 * 
	 *
	 * TODO 查询DICT_KEY_IN,DICT_VAALUE_IN
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月4日    宁智杰   新建
	 * </pre>
	 */
	public List<GfDict> getDictKeyValue();
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
	public List<GfDict> selectAreaList(GfDict gfDict);

	public List<GfDict> selectByCodes(String[] channels);
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