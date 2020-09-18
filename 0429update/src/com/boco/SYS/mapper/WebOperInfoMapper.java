package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * WebOperInfo数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebOperInfoMapper extends GenericMapper<WebOperInfo,HashMap<String,Object>>{

	List<WebOperInfo> select(WebOperInfo webOperInfo);

	List<Map<String, String>> selectDeptCode(WebOperInfo webOperInfo);

	List<Map<String, String>> selectOperCodeLike(WebOperInfo webOperInfo);

	List<Map<String, String>> selectOperName(WebOperInfo webOperInfo);
	/**
	 * 
	 *
	 * TODO 根据操作员号查询
	 *
	 * @param webOperInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月22日    	   谷立羊  新建
	 * </pre>
	 */
	public WebOperInfo selectOperCode(WebOperInfo webOperInfo);

	public String selectOperCodeByName(@Param(value="operName")String operName);
}