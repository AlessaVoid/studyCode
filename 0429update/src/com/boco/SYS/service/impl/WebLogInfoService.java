package com.boco.SYS.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IWebDeptInfoService;
import com.boco.PM.service.IWebOperInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebDeptInfo;
import com.boco.SYS.entity.WebLogInfo;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.WebLogInfoMapper;
import com.boco.SYS.service.IWebLogInfoService;
import com.boco.SYS.util.TreeNode;

/**
 * 
 * 
 * 业务日志表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class WebLogInfoService extends GenericService<WebLogInfo,java.lang.String> implements IWebLogInfoService{
	@Autowired
	private WebLogInfoMapper webLogInfoMapper;
	@Autowired
	private IWebDeptInfoService webDeptInfoService;
	@Autowired
	private IFdOperService fdOperService;
	@Autowired
	private IWebOperInfoService webOperInfoService;
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
	public HashMap getSelectMap(WebLogInfo webLogInfo,WebOperInfo webOperInfoDTO) throws Exception{
		List<String> operDepts = new ArrayList<String>();
		WebDeptInfo webDeptInfo = new WebDeptInfo();
		List<WebDeptInfo> deptList = webDeptInfoService.selectByAttr(webDeptInfo);
		if(deptList.size() != 0){
			for(WebDeptInfo dept : deptList){
				operDepts.add(dept.getDeptCode());
			}
		}
		HashMap map = new HashMap();
		if(operDepts.size() != 0){
			map.put("deptList", operDepts);
		}
		if(StringUtils.isNotEmpty(webLogInfo.getOperCode())){
			map.put("operCode", webLogInfo.getOperCode());
		}
		if(StringUtils.isNotEmpty(webLogInfo.getOperName())){
			map.put("operName", webLogInfo.getOperName());
		}
		if(StringUtils.isNotEmpty(webLogInfo.getTradeDate())){
			map.put("tradeDate", webLogInfo.getTradeDate());
		}
		return map;
	}
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
	public boolean checkOperDept(WebLogInfo webLogInfo,FdOper fdOper,String operDept,String upOperDept) throws Exception{
		return true;
	}
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
	public boolean checkOperCode(WebLogInfo webLogInfo,FdOper fdOper,String operDept,String upOperDept) throws Exception{
		String selectDeptCode="",selectUpDeptCode = "";
		//校验操作员代码
		if(StringUtils.isNotEmpty(webLogInfo.getOperCode())){
			boolean check = false;
			//校验要查询的操作员是否存在，是否与当前操作员是同一个机构的
			FdOper oper = new FdOper();
			oper.setOpercode(webLogInfo.getOperCode());
			List<FdOper> operList = fdOperService.selectByAttr(oper);
			if(operList.size() != 0){
				oper = operList.get(0);
				if(!fdOper.getOrgancode().equals(oper.getOrgancode())){
					json.returnMsg("false", "只能查询本机构柜员的系统日志信息");
					return false;
				}
			}else{
				json.returnMsg("false", "查询的柜员不存在，请重新输入");
				return false;
			}
			//要查询的操作员
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("organCode", fdOper.getOrgancode());
			map.put("operCode", fdOper.getOpercode());
			WebOperInfo selectWebOperInfo = webOperInfoService.selectByPK(map);
			if(selectWebOperInfo == null){
				throw new SystemException("您查询的操作员没有维护人员信息，无法查询该操作员的系统日志信息");
			}else{
				//要查询的操作员的部门代码
				HashMap queryMap = new HashMap();
				queryMap.put("deptCode",selectDeptCode);
				queryMap.put("organcode",fdOper.getOrgancode());
				WebDeptInfo selectDeptInfo = webDeptInfoService.selectByPK(queryMap);
				if(StringUtils.isNotEmpty(selectDeptInfo.getUpDeptCode())){
					selectUpDeptCode = selectDeptInfo.getUpDeptCode();
				}
				//当前操作员是一级部门的
				if(!StringUtils.isNotEmpty(upOperDept)){
					//若要查询的操作员是一级部门
					if(!StringUtils.isNotEmpty(selectUpDeptCode)){
						if(operDept.equals(selectDeptCode)){
							check = true;
						}
					}else{//若要查询的操作员是二级部门
						WebDeptInfo webDeptInfo = new WebDeptInfo();
						webDeptInfo.setUpDeptCode(operDept);
						List<WebDeptInfo> childDept = webDeptInfoService.selectByAttr(webDeptInfo);
						if(childDept.size() != 0){
							for(WebDeptInfo info : childDept){
								if(selectDeptCode.equals(info.getDeptCode())){
									check = true;
								}
							}
						}
					}
				}else{//当前操作员是二级部门的
					if(selectDeptCode.equals(operDept)){
						check = true;
					}
				}
				
				if(check == false){
					json.returnMsg("false", "您只能查询本级及下级操作员的系统日志信息");
					return false;
				}
			}
		}
		return true;
	}
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
	public boolean checkDept(WebDeptInfo deptInfo,String deptCode) throws Exception{
		WebDeptInfo childDept = new WebDeptInfo();
		childDept.setUpDeptCode(deptInfo.getDeptCode());
		List<WebDeptInfo> childList = webDeptInfoService.selectByAttr(childDept);
		if(childList.size() != 0){
			for(WebDeptInfo info : childList){
				if(deptCode.equals(info.getDeptCode())){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 
	 *
	 * TODO 系统日志查询获取机构部门信息.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	  杜旭    新建
	 * </pre>
	 */
	public List<TreeNode> getWebDeptInfo() throws Exception {
		WebDeptInfo webDeptInfo = new WebDeptInfo();
		List<WebDeptInfo> deptList = webDeptInfoService.selectByAttr(webDeptInfo);
		List<TreeNode> list = new ArrayList<TreeNode>();
		if(deptList.size() != 0){
			for(WebDeptInfo dept : deptList){
				TreeNode treeNode = new TreeNode();
				treeNode.setId(dept.getDeptCode());
				if(!StringUtils.isNotEmpty(dept.getUpDeptCode())){
					dept.setUpDeptCode("0");
				}
				treeNode.setParentId(dept.getUpDeptCode());
				treeNode.setName(dept.getDeptName());
				treeNode.setOpen(true);
				list.add(treeNode);
			}
		}
		return list;
	}
}