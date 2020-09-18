package com.boco.SYS.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebPublicPromptTable;
/**
 * 
 * 
 * 公共提示表||公共提示表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebPublicPromptTableMapper extends GenericMapper<WebPublicPromptTable,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 查询公告信息
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年11月11日    	   谷立羊  新建
	 * </pre>
	 */
	public List<WebPublicPromptTable> selectPromptMsg() throws DataAccessException;
}