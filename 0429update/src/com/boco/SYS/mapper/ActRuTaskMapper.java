package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.ActRuTask;
/**
 * 
 * 
 * ActRuTask数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface ActRuTaskMapper extends GenericMapper<ActRuTask,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 产品设计审批：某个柜员，某个阶段，要审批的信息列表.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月4日    	  杜旭    新建
	 * </pre>
	 */
	public List<ActRuTask> getActRuTaskDesign(Map map);
	/**
	 * 
	 *
	 * TODO 档期安排审批：某个柜员，某个阶段，要审批的信息列表.
	 *
	 * @param processDefinitionKey
	 * @param assignee
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月3日    	  杜旭    新建
	 * </pre>
	 */
	public List<Map<String,String>> getActRuTaskSchedule(Map map);
}