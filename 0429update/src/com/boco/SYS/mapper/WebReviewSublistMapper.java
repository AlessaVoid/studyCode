package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.boco.SYS.entity.WebReviewSublist;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * WebReviewSublist数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebReviewSublistMapper extends GenericMapper<WebReviewSublist,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO 根据复核编号删除记录.
	 *
	 * @param opercode
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月26日    	  杜旭    新建
	 * </pre>
	 */
	public void deleteByAppNo(String appNo) throws RuntimeException;
	/**
	 * 
	 *
	 * TODO 按照orderNo进行排序查询
	 *
	 * @param appNo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年12月18日    	   谷立羊  新建
	 * </pre>
	 */
	public List<WebReviewSublist> selectAppNo(@Param(value="appNo")String appNo) throws DataAccessException;
}