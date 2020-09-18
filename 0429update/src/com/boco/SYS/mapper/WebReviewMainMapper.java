package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.entity.WebReviewMain;
import com.boco.SYS.entity.WebRoleFun;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * WebReviewMain数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebReviewMainMapper extends GenericMapper<WebReviewMain,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 获取对应序列.
	 *
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月26日    	  杜旭    新建
	 * </pre>
	 */
	public Integer getPubSQ() throws RuntimeException;
	/**
	 * 
	 *
	 * TODO 申请复核之前判断申请的操作是否已存在未处理完成的复核记录.
	 *
	 * @param webReviewMain
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月26日    	  杜旭    新建
	 * </pre>
	 */
	public List<Map<String,String>> checkRep(WebReviewMain webReviewMain) throws RuntimeException;
}