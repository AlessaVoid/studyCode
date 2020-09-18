package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebScheduleAppInfo;
/**
 * 
 * 
 * WebScheduleAppInfo数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebScheduleAppInfoMapper extends GenericMapper<WebScheduleAppInfo,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 查询档期审批信息.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月15日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebScheduleAppInfo> selectByScheduleAudit(Map map);
	/**
	 * 
	 *
	 * TODO 档期审批记录数.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月15日    	  杜旭    新建
	 * </pre>
	 */
	public int countByScheduleAudit(Map map);
	/**
	 * 
	 *
	 * TODO 查询档期已审批信息.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月15日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebScheduleAppInfo> selectByScheduleAudited(Map map);
	/**
	 * 
	 *
	 * TODO 档期已审批记录数.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月15日    	  杜旭    新建
	 * </pre>
	 */
	public int countByScheduleAudited(Map map);
	/**
	 * 
	 *
	 * TODO 查询档期已提交信息.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月15日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebScheduleAppInfo> selectByScheduleSubmit(Map map);
	/**
	 * 
	 *
	 * TODO 档期已提交记录数.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月15日    	  杜旭    新建
	 * </pre>
	 */
	public int countByScheduleSubmit(Map map);
	/**
	 * 
	 *
	 * TODO 根据档期编号集查询数据.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月25日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebScheduleAppInfo> selectByScheduleCode(Map map);
	/**
	 * 
	 *
	 * TODO 查询档期待签收信息.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月25日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebScheduleAppInfo> selectByScheduleRev(Map map);
	/**
	 * 
	 *
	 * TODO 档期待签收记录数.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月25日    	  杜旭    新建
	 * </pre>
	 */
	public int countByScheduleRev(Map map);
	/**
	 * 
	 *
	 * TODO 查询最近5个档期
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月19日    	   谷立羊  新建
	 * </pre>
	 */
	public List<WebScheduleAppInfo> selectPreSchedule();
}