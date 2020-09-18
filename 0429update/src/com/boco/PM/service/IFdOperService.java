package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;

/**
 *
 *
 * FdOper业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法).
 *
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年2月24日    		杜旭    新建
 * </pre>
 */
public interface IFdOperService extends IGenericService<FdOper,HashMap<String,Object>>{
	/**
	 *
	 *
	 * TODO 柜员签到.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月24日    	  杜旭    新建
	 * </pre>
	 */
	public boolean operSignIn(FdOper fdOper);
	/**
	 *
	 *
	 * TODO 柜员签退.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月24日    	  杜旭    新建
	 * </pre>
	 */
	public boolean operSignOut(FdOper fdOper);
	/**
	 *
	 *
	 * TODO 柜员强制签退.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月24日    	  杜旭    新建
	 * </pre>
	 */
	public Map operQzSignout(FdOper fdOper, String doOperCode);
	/**
	 *
	 *
	 * TODO 柜员密码修改
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月26日    	   谷立羊  新建
	 * </pre>
	 */
	public Map operUpdatePwd(FdOper fdOper, String pwd);

	/**
	 * 柜员机构修改
	 *
	 */
	Map operUpdateOrgan(FdOper fdOper, String newOrganCode);

	/**
	 * 柜员转岗
	 * @param fdOper
	 * @return
	 */
	Map insertNewOper(FdOper fdOper);

	/**
	 *
	 *
	 * TODO 柜员密码重置
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月26日    	   谷立羊  新建
	 * </pre>
	 */
	public Map operRePwd(FdOper sessionUser, FdOper fdOper);
	/**
	 *
	 *
	 * TODO 与角色功能表关联查询授权用户下拉列表数据.
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月7日    	  杜旭    新建
	 * </pre>
	 */
	public List<Map<String,String>> getPowerList(Map<String, String> query) throws Exception;
	/**
	 *
	 *
	 *  * TODO 根据请求参数查询操作员信息，返回用于操作员生产下拉框的json串.
	 *
	 * @param request
	 *            查询串格式为 funno=**** 例如：funno=FD-01
	 * @return
	 *         json串格式为{"list":[{"value":"操作员A的编号","key":"操作员A"},{"value":"操作员B的编号"
	 *         ,"key":"操作员B"}]}
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月14日    	  杜旭    新建
	 * </pre>
	 */
	public Map<String,List<Map<String, String>>> getAppUserList(String funCode, HttpSession session) throws Exception;
	/**
	 * 获取总行复核人员
	 * @param funCode
	 * @param session
	 * @return
	 */
	public Map<String, List<Map<String, String>>> getHeadOfficeAppUserList(String funCode, HttpSession session) throws Exception;
	/**
	 *
	 *
	 * TODO 根据人员编号查询人员信息，机构信息，角色信息.
	 *
	 * @param userNo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月14日    	  杜旭    新建
	 * </pre>
	 */
	public Map getUserInfo(String userNo) throws Exception;
	/**
	 *
	 *
	 * TODO 复杂流程： 与角色功能表关联查询授权下拉列表数据.
	 *
	 * @param funCode
	 * @param session
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月5日    	  杜旭    新建
	 * </pre>
	 */
	public Map<String,List<Map<String, String>>> getAppUserListByRole(String funCode, String roleCode, String organcode) throws Exception;
}