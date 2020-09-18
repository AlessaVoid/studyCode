package com.boco.PM.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IWebRoleInfoService;
import com.boco.PM.service.IWebSublicenseInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebRoleInfo;
import com.boco.SYS.entity.WebSublicenseInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.util.JsonUtils;
/**
 * 
 * 
 * WebSublicenseInfoAction控制层
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/webSublicenseInfo/")
public class WebSublicenseInfoController extends BaseController{
	@Autowired
	private IWebSublicenseInfoService webSublicenseInfoService;
	@Autowired
	private IFdOperService fdOperService;
	@Autowired
	private IWebRoleInfoService webRoleInfoService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="转授权管理",funCode="PM-09",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/webSublicenseInfo/webSublicenseInfoList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="转授权管理",funCode="PM-09-03",funName="加载详细页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(WebSublicenseInfo webSublicenseInfo) throws Exception {
		setAttribute("webSublicenseInfo", webSublicenseInfoService.selectByPK(webSublicenseInfo.getId()));
		return basePath + "/webSublicenseInfo/webSublicenseInfoInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="交易名称",funCode="未填写", funName="加载新增页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/webSublicenseInfo/webSublicenseInfoEdit";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="交易名称",funCode="未填写", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(WebSublicenseInfo webSublicenseInfo) throws Exception {
		setAttribute("webSublicenseInfo", webSublicenseInfoService.selectByPK(webSublicenseInfo.getId()));
		return basePath + "/webSublicenseInfo/webSublicenseInfoEdit";
	}
	
	/**
	 * 
	 *
	 * TODO 查询WEB_SUBLICENSE_INFO分页数据
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="转授权管理",funCode="PM-09",funName="加载列表数据",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(WebSublicenseInfo webSublicenseInfo) throws Exception {
		setPageParam();
		List<WebSublicenseInfo> list = webSublicenseInfoService.selectByAttr(webSublicenseInfo);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO 查询双向选择器中转出柜员拥有的角色.
	 *
	 * @param webSublicenseInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月2日    	  杜旭    新建
	 * </pre>
	 */
	@RequestMapping(value = "findWebSub")
	@SystemLog(tradeName="转授权管理",funCode="PM-09",funName="加载转授权数据",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findWebSub(WebSublicenseInfo webSublicenseInfo) throws Exception {
		Map<String,List<Map<String,String>>> result = new HashMap<String,List<Map<String,String>>>();
		List<Map<String,String>> fromList = new ArrayList<Map<String,String>>();
		List<Map<String,String>> toList = new ArrayList<Map<String,String>>();
		FdOper fdOper = getSessionUser();
		HashMap keyMap = new HashMap();
		keyMap.put("organcode",fdOper.getOrgancode());
		keyMap.put("opercode",fdOper.getOpercode());
		FdOper outOper = fdOperService.selectByPK(keyMap);
		
		if(outOper != null){
			String roles = outOper.getOperdegree();
			String outRoles = "";
			//获取是否有转出角色,如果有的话，将这些角色从拥有的角色中去除
			WebSublicenseInfo subInfo  = new WebSublicenseInfo();
			subInfo.setOutOper(outOper.getOpercode());
			subInfo.setIsBack("2");
			//已转出角色
			List<WebSublicenseInfo> subList = webSublicenseInfoService.selectByAttr(subInfo);
			if(subList.size() != 0){
				for(WebSublicenseInfo info : subList){
					//可转出角色集
					roles = roles.replace(info.getRoleCode(), "");
					//可收回角色
					outRoles += info.getRoleCode();
				}
			}
			//可转出角色集
			fromList = this.getSubList(roles);
			//可收回角色集
			if(outRoles.length() > 0){
				toList = this.getSubList(outRoles);
			}
		}
		result.put("fromList", fromList);
		result.put("toList", toList);
		return JsonUtils.toJson(result);
	}
	/**
	 * 
	 *
	 * TODO 获取转授权转入，转出所需要的集合.
	 *
	 * @param roles
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月11日    	  杜旭    新建
	 * </pre>
	 */
	public List getSubList(String roles) throws Exception{
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(int i = 0; i < roles.length() ; i=i+3){
			Map<String,String> map = new HashMap<String,String>();
			String roleCode = roles.substring(i,i+3);
			WebRoleInfo webRoleInfo = webRoleInfoService.selectByPK(roleCode);
			if(webRoleInfo != null){
				map.put("key", webRoleInfo.getRoleName());
				map.put("value",roleCode);
				list.add(map);
			}
		}
		return list;
	}
	/**
	 * 
	 *
	 * TODO 根据转出柜员和转入柜员获取已转出未收回权限的角色信息.
	 *
	 * @param webSublicenseInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月2日    	  杜旭    新建
	 * </pre>
	 */
	@RequestMapping(value = "selectWebSub")
	@SystemLog(tradeName="转授权管理",funCode="PM-09",funName="加载转授权数据",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String selectWebSub(WebSublicenseInfo webSublicenseInfo,HttpServletRequest request) throws Exception {
		String webSubSelect = "";
		//验证输入的柜员是否存在
		if(StringUtils.isNotEmpty(webSublicenseInfo.getInOper())){
			FdOper fdOper = new FdOper();
			fdOper.setOpercode(webSublicenseInfo.getInOper());
			List<FdOper> list = fdOperService.selectByAttr(fdOper);
			if(list.size() == 0){
				return json.returnMsg("false", "转入柜员不存在，请重新输入！").toJson();
			}
			//查询转入柜员为输入柜员，收回状态为未收回的角色集
			webSublicenseInfo.setIsBack("2");
			List<WebSublicenseInfo> webList = webSublicenseInfoService.selectByAttr(webSublicenseInfo);
			webSubSelect = this.findOutRoles(webList);
		}else{
			WebSublicenseInfo subInfo  = new WebSublicenseInfo();
			subInfo.setOutOper(webSublicenseInfo.getOutOper());
			subInfo.setIsBack("2");
			//已转出角色
			List<WebSublicenseInfo> webList = webSublicenseInfoService.selectByAttr(subInfo);
			webSubSelect = this.findOutRoles(webList);
		}
		return json.returnMsg("true", webSubSelect).toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 转出
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "outWebSub")
	@SystemLog(tradeName="转授权管理",funCode="PM-09",funName="新增WebSublicenseInfo",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String outWebSub(WebSublicenseInfo webSublicenseInfo,HttpServletRequest request) throws Exception{
		String roles = request.getParameter("lister");
		json = webSublicenseInfoService.outWebSub(webSublicenseInfo, roles);
		return this.json.toJson();
	}
	/**
	 * 
	 *
	 * TODO 当柜员没有本系统角色，只有转授权的本系统角色时，点击转授权会报错.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月7日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	@RequestMapping(value = "checkRole")
	@SystemLog(tradeName="转授权管理-转授权校验方法",funCode="PM-09",funName="转授权校验方法",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String checkRole() throws Exception{
		//获得当前柜员的角色信息
		FdOper fdOper = getSessionUser();
		FdOper fdOperDTO = new FdOper();
		HashMap map = new HashMap();
		map.put("opercode", fdOper.getOpercode());
		map.put("organcode", fdOper.getOrgancode());
		fdOperDTO = fdOperService.selectByPK(map);
		String role = "";
		if(fdOperDTO != null){
			if(!StringUtils.isNotEmpty(fdOperDTO.getOperdegree())){
				return json.returnMsg("false", "您没有可以转出的角色，不可以进行转授权交易").toJson();
			}
		}
		return  json.returnMsg("true", "验证通过").toJson();
	}
	/**
	 * 
	 *
	 * TODO 获取已转出角色代码集.
	 *
	 * @param webList
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年6月22日    	    杜旭    新建
	 * </pre>
	 */
	public String findOutRoles(List<WebSublicenseInfo> webList){
		String webSubSelect = "";
		StringBuffer sbuff = new StringBuffer();
		if(webList.size() != 0){
			for(WebSublicenseInfo webSub : webList){
				sbuff.append(webSub.getRoleCode() + ",");
			}
		}
		if(sbuff.length() != 0){
			String webSub = sbuff.toString();
			webSubSelect = webSub.substring(0, webSub.length()-1);
		}
		return webSubSelect;
	}
}