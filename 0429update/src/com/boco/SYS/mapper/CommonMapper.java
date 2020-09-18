package com.boco.SYS.mapper;

import org.springframework.dao.DataAccessException;

/**
 * 
 * 
 * 公共Mapper
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface CommonMapper {
	/**
	 * 
	 * 
	 * TODO 获取自增主键.
	 * 
	 * @return
	 * @throws Exception
	 * 
	 *             <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月7日    	    杨滔    新建
	 * </pre>
	 */
	public String getNextId() throws DataAccessException;
}