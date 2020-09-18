package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebAreaOrganInfo;
/**
 * 
 * 
 * WebAreaOrganInfo数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebAreaOrganInfoMapper extends GenericMapper<WebAreaOrganInfo,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO 查询其他区域所选择的机构信息.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebAreaOrganInfo> selectNotEqualOrgan(String areaCode);
	/**
	 * 
	 *
	 * TODO 根据地区编码删除记录.
	 *
	 * @param areaCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	public Integer deleteByAreaCode(String areaCode);
}