package com.boco.PM.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.WebPublicPromptTable;

/**
 * 
 * 
 * 公共提示表||公共提示表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebPublicPromptTableService extends IGenericService<WebPublicPromptTable,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 保存方法
	 *
	 * @param publicPromptTable
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年11月11日    	   谷立羊  新建
	 * </pre>
	 */
	public void saveInfo(WebPublicPromptTable webPublicPromptTable) throws Exception;
	/**
	 * 
	 *
	 * TODO 刷新公告信息 
	 *
	 * @param publicPromptTable
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年11月11日    	   谷立羊  新建
	 * </pre>
	 */
	public void refresh(WebPublicPromptTable webPublicPromptTable) throws Exception;
}