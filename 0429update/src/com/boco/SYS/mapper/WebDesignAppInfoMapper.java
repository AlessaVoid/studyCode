package com.boco.SYS.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.boco.SYS.entity.WebDesignAppInfo;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * 该表存储理财产品设计审批信息记录数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebDesignAppInfoMapper extends GenericMapper<WebDesignAppInfo,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 根据档期编号查询产品
	 *
	 * @param scheduleCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月19日    	   谷立羊  新建
	 * </pre>
	 */
	List<WebDesignAppInfo> selectByScheduleCode(@Param(value="scheduleCode")String scheduleCode);
}