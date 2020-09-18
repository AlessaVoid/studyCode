package com.boco.SYS.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebDeptInfo;
import com.boco.SYS.entity.WebLogInfo;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.TreeNode;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * 业务日志表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebLogInfoService extends IGenericService<WebLogInfo,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 动态根据部门编号查询系统日志信息.
	 *
	 * @param roleCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	  杜旭    新建
	 * </pre>
	 */

	/**
	 * 
	 *
	 * TODO 封装查询条件（部门领导）.
	 * 选择一个部门，可以查询出这个部门及下级下的操作员的系统日志信息
	 *
	 * @param webLogInfo
	 * @param webOperInfoDTO
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月9日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public HashMap getSelectMap(WebLogInfo webLogInfo,WebOperInfo webOperInfoDTO) throws Exception;
	/**
	 * 
	 *
	 * TODO 校验操作员部门.
	 *
	 * @param webLogInfo
	 * @param fdOper
	 * @param operDept
	 * @param upOperDept
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月9日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkOperDept(WebLogInfo webLogInfo,FdOper fdOper,String operDept,String upOperDept) throws Exception;
	/**
	 * 
	 *
	 * TODO 校验操作员代码.
	 *
	 * @param webLogInfo
	 * @param fdOper
	 * @param selectDeptCode
	 * @param selectUpDeptCode
	 * @param operDept
	 * @param upOperDept
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月9日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkOperCode(WebLogInfo webLogInfo,FdOper fdOper,String operDept,String upOperDept) throws Exception;
	/**
	 * 
	 *
	 * TODO 查询是否是当前部门的下级部门.
	 *
	 * @param deptInfo
	 * @param deptCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkDept(WebDeptInfo deptInfo,String deptCode) throws Exception;
	/**
	 * 
	 *
	 * TODO 系统日志查询获取机构部门信息.
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	  杜旭    新建
	 * </pre>
	 */
	public List<TreeNode> getWebDeptInfo() throws Exception;
}