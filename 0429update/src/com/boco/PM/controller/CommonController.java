package com.boco.PM.controller;

import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IWebRoleFunService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.WebMenuInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.service.IWebMenuInfoService;
import com.boco.SYS.util.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 *  公共控制器，用于加载系统公共信息.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年2月1日    	    杨滔    新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/")
public class CommonController extends BaseController{
	@Autowired
	private IWebMenuInfoService webMenuInfoService;
	@Autowired
	private IWebRoleFunService webRoleFunService;
	@Autowired
	private IFdOperService fdOperService;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	
	
	/**
	 * 
	 *
	 * TODO 数据字典重置.
	 *
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月17日    	     代策    新建
	 * </pre>
	 */
	@RequestMapping(value = "restartParam", method = RequestMethod.POST)
	@SystemLog(tradeName="数据字典重置",funCode="SYS-03", funName="加载菜单",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String restartParam(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		fdBusinessDateService.RestartParam();
		json.returnMsg("true", getErrorInfo("w644"));//数据字典重置成功
		return json.toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 加载菜单.
	 *
	 * @param
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月9日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "initMenu", method = RequestMethod.POST)
	@SystemLog(tradeName="系统功能",funCode="system", funName="加载菜单",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String initMenu() throws Exception {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List<WebMenuInfo> resList = webMenuInfoService.selectRolesMenus(getSessionUser().getOpercode());
		for(WebMenuInfo node : resList){
			TreeNode treeNode = convert(node);
			treeNodes.add(treeNode);
		}
		json.setBean(treeNodes);
		return json.toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 菜单对象转换为QUI树节点对象.
	 *
	 * @param menu
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月7日    	    杨滔    新建
	 * </pre>
	 * @throws Exception 
	 */
	private TreeNode convert(WebMenuInfo menu) throws Exception {
		TreeNode node = new TreeNode();
		node.setChecked(false);
		node.setChkDisabled(false);
		node.setClickExpand(true);
		if("1".equals(menu.getIsParent())){
			node.setIconClose(getRequest().getContextPath() + "/libs/icons/folderclosed.gif");
			node.setIconOpen(getRequest().getContextPath() + "/libs/icons/folderopen.gif");
		}else{
			String url = "";
			if(menu.getMenuUrl().contains("?")){
				url = getRequest().getContextPath() + menu.getMenuUrl() + "&menuNo=" + menu.getMenuNo();
			}else{
				url = getRequest().getContextPath() + menu.getMenuUrl() + "?menuNo=" + menu.getMenuNo();
			}
			node.setUrl(url);
			node.setIcon(getRequest().getContextPath() + menu.getMenuIcon());
		}
		node.setId(menu.getMenuNo());
		node.setTarget("frmright");
		node.setName(menu.getMenuName());
		node.setParentId(menu.getUpMenuNo());
		node.setOldParentId(menu.getUpMenuNo());
		node.setOpen(false);
		node.setExisted(true);
		return node;
	}
}
