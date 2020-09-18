package com.boco.PM.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IWebRoleFunService;
import com.boco.PM.service.IWebShortMenuInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.controller.WebRoleInfoController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebMenuInfo;
import com.boco.SYS.entity.WebRoleFun;
import com.boco.SYS.entity.WebShortMenuInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IWebMenuInfoService;
import com.boco.SYS.util.MapUtil;
import com.boco.SYS.util.TreeNode;
import com.boco.util.JsonUtils;
/**
 * 
 * 
 * Action控制层
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/webShortMenuInfo/")
public class WebShortMenuInfoController extends BaseController{
	@Autowired
	private IWebShortMenuInfoService webShortMenuInfoService;
	@Autowired
	private IFdOperService fdOperService;
	@Autowired
	private IWebRoleFunService webRoleFunService;
	@Autowired
	private IWebMenuInfoService webMenuInfoService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("updateUI")
	@SystemLog(tradeName="快捷菜单",funCode="system", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(WebShortMenuInfo webShortMenuInfo) throws Exception {
		getOperMenu();
		return basePath + "/PM/webShortMenuInfo/webShortMenuInfoEdit";
	}
	
	/**
	 * 
	 *
	 * TODO 修改WEB_SHORT_MENU_INFO.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="快捷菜单",funCode="system",funName="修改",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(WebShortMenuInfo webShortMenuInfo,HttpServletRequest request) throws Exception{
		String funCode = request.getParameter("funCode");
		json = webShortMenuInfoService.updateWebShortMenuInfo(webShortMenuInfo,funCode);
		return this.json.toJson();
	}
	/**
	 * 
	 *
	 * TODO 获得当前柜员拥有的菜单信息.
	 *
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public void getOperMenu() throws Exception{
		//功能集合
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		FdOper fdOper = getSessionUser();
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("opercode",fdOper.getOpercode());
		map.put("organcode",fdOper.getOrgancode());
		FdOper fdOperDTO = fdOperService.selectByPK(map);
		if(StringUtils.isNotEmpty(fdOperDTO.getOperdegree())){
			List<String> list = new ArrayList<String>();
			if(fdOperDTO != null){
				String roles = fdOperDTO.getOperdegree();
				for(int i=0;i<roles.length();i=i+3){
					String role = roles.substring(i,i+3);
					list.add(role);
				}
			}
			//当前柜员拥有的功能集合
			List<WebMenuInfo> funList = webMenuInfoService.selectMenuByRole(list);
			//当前柜员选择的快捷快单信息
			WebShortMenuInfo webShortMenuInfo = new WebShortMenuInfo();
			webShortMenuInfo.setOperCode(fdOper.getOpercode());
			List<String> shortMenuList=webShortMenuInfoService.selectMenuNoByAttr(webShortMenuInfo);
			List<String> parentMenu=webShortMenuInfoService.selectUpMenuNo();
			
			for(WebMenuInfo menu:funList){
				TreeNode node=new TreeNode();
				node.setChecked(false);
				node.setChkDisabled(false);
				node.setClickExpand(true);
				node.setId(menu.getMenuNo());
				node.setTarget("frmright");
				node.setName(menu.getMenuName());
				node.setParentId(menu.getUpMenuNo());
				node.setOpen(false);
				node.setExisted(true);
				if(parentMenu.contains(menu.getMenuNo())){
					node.setNocheck(true);
					node.setDrag(false);
					node.setIconClose(getRequest().getContextPath() + "/libs/icons/folderclosed.gif");
					node.setIconOpen(getRequest().getContextPath() + "/libs/icons/folderopen.gif");
				}else{
					node.setIcon(getRequest().getContextPath() + "/libs/icons/" +menu.getMenuIcon());
				}
				if(shortMenuList.contains(menu.getMenuNo())){
					node.setChecked(true);
				}
				treeNodes.add(node);
			}
		}
		setAttribute("treeNodes", JsonUtils.toJson(treeNodes));
	}
	
	
}