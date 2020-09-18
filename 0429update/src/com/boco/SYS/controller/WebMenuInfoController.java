package com.boco.SYS.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebMenuInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IWebMenuInfoService;
import com.boco.SYS.util.TreeNode;
import com.boco.SYS.util.TreeNodeGrid;
import com.boco.util.JsonUtils;
/**
 * 
 * 
 * 菜单表Action控制层
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/webMenuInfo/")
public class WebMenuInfoController extends BaseController{
	@Autowired
	private IWebMenuInfoService webMenuInfoService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="菜单管理",funCode="PM-14",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/webMenuInfo/webMenuInfoList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="菜单管理",funCode="PM-14-04",funName="加载详细页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(WebMenuInfo webMenuInfo) throws Exception {
		List<WebMenuInfo> Info = webMenuInfoService.selectByAttr(webMenuInfo);
		setAttribute("webMenuInfoDTO", Info.get(0) );
		return basePath + "/PM/webMenuInfo/webMenuInfoInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="菜单管理",funCode="PM-14-01", funName="加载新增页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/webMenuInfo/webMenuInfoEdit";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="菜单管理",funCode="PM-14-02", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(WebMenuInfo webMenuInfo) throws Exception {
 		WebMenuInfo webMenuInfoDTO = webMenuInfoService.selectByPK(webMenuInfo.getId());
		if("2".equals(webMenuInfoDTO.getMenuType())){
			String url = webMenuInfoDTO.getMenuUrl();
			String method = url.substring(url.indexOf("click : ")+8, url.indexOf(", iconClass"));
			webMenuInfoDTO.setMenuUrl(method);
		}
		setAttribute("webMenuInfoDTO", webMenuInfoDTO);
		return basePath + "/PM/webMenuInfo/webMenuInfoEdit";
	}
	
	/**
	 * 
	 *
	 * TODO 查询WEB_MENU_INFO分页数据
	 * 树形grid的查询数据
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="菜单管理",funCode="PM-14",funName="加载列表数据",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(WebMenuInfo webMenuInfo) throws Exception {
		Map<String, Object> results = new Hashtable<String, Object>();
		List<TreeNodeGrid> treeNode = new ArrayList<TreeNodeGrid>();
		//获取树形菜单信息
		treeNode = getTreeNode(webMenuInfo);
		results.put("rows", treeNode);
		return JsonUtils.toJson(results);
	}
	/**
	 * 
	 *
	 * TODO 获取菜单树.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月29日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public List<TreeNodeGrid> getTreeNode(WebMenuInfo webMenuInfo) throws Exception{
		//临时map
		Map<String,TreeNodeGrid> tempMap = new HashMap<String,TreeNodeGrid>();
		Map<String,TreeNodeGrid> map = new HashMap<String,TreeNodeGrid>();
		List<TreeNodeGrid> treeNode = new ArrayList<TreeNodeGrid>();
		Map selectAttr = new HashMap();
		//若是否禁用为空
		if(webMenuInfo.getMenuStatus() == null || "".equals(webMenuInfo.getMenuStatus())){
			webMenuInfo.setMenuStatus("1");
		}
		List<WebMenuInfo> webMenulist = webMenuInfoService.selectByAttr(webMenuInfo);
		if(webMenulist.size()!=0){
			List<WebMenuInfo> menuList = null;
			String upMenu = "";
			String menuCountFlag = "";
			if(StringUtils.isNotEmpty(webMenuInfo.getMenuNo()) || StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
				if(StringUtils.isNotEmpty(webMenuInfo.getMenuNo()) && !StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
					upMenu = webMenuInfo.getMenuNo();
					menuCountFlag = "single";
				}else if(!StringUtils.isNotEmpty(webMenuInfo.getMenuNo()) && StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
					upMenu = webMenuInfo.getUpMenuNo();
					menuCountFlag = "single";
				}else if(StringUtils.isNotEmpty(webMenuInfo.getMenuNo()) && StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
					upMenu = webMenuInfo.getMenuNo();
					menuCountFlag = "single";
				}
			}
			if("single".equals(menuCountFlag)){
				//获取菜单信息
				selectAttr.put("upMenuNo", upMenu);
				selectAttr.put("menuStatus", webMenuInfo.getMenuStatus());
				menuList = webMenuInfoService.selectByNo(selectAttr);
			}else{
				menuList = webMenulist;
			}
			
			for(WebMenuInfo menu : menuList){
				String menuNo = menu.getMenuNo();
				if("1".equals(menu.getIsParent())){//若为父菜单
					TreeNodeGrid node = new TreeNodeGrid();
					node = getChildNode(node,menu,"0");
					map.put(menuNo, node);
				}else if("0".equals(menu.getIsParent())){//若为子菜单
					if("1".equals(menu.getMenuType())){//菜单
						tempMap = getChildMenu(tempMap,menu);
					}else if("2".equals(menu.getMenuType())){//按钮
						tempMap = getButtonMenu(tempMap,menu);
					}
				}
			}
			//获取完整的菜单信息
			map = getNodeMap(map,tempMap);
			//将菜单信息保存在list集合中
			for(String menu : map.keySet()){
				treeNode.add(map.get(menu));
			}
		}
		return treeNode;
	}
	/**
	 * 
	 *
	 * TODO 处理子菜单信息.
	 *
	 * @param tempMap
	 * @param menu
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月3日    	  杜旭    新建
	 * </pre>
	 */
	public Map<String,TreeNodeGrid> getChildMenu(Map<String,TreeNodeGrid> tempMap,WebMenuInfo menu){
		if(tempMap.containsKey(menu.getMenuNo())){//存在
			TreeNodeGrid node = tempMap.get(menu.getMenuNo());
			node = getChildNode(node,menu,"1");
			tempMap.put(menu.getMenuNo(), node);
		}else{//不存在
			TreeNodeGrid node = new TreeNodeGrid();
			node = getChildNode(node,menu,"0");
			tempMap.put(menu.getMenuNo(), node);
			
			if(tempMap.containsKey(menu.getUpMenuNo())){//父节点存在
				TreeNodeGrid upNode = tempMap.get(menu.getUpMenuNo());
				upNode = getNode(upNode,node,"1");
				tempMap.put(menu.getUpMenuNo(), upNode);
			}else{
				TreeNodeGrid upNode = new TreeNodeGrid();//创建父节点
				upNode = getNode(upNode,node,"0");//父节点不存在
				tempMap.put(menu.getUpMenuNo(),upNode);
			}
		}
		return tempMap;
	}
	/**
	 * 
	 *
	 * TODO 处理按钮信息.
	 *
	 * @param tempMap
	 * @param menu
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月3日    	  杜旭    新建
	 * </pre>
	 */
	public Map<String,TreeNodeGrid> getButtonMenu(Map<String,TreeNodeGrid> tempMap,WebMenuInfo menu){
		if(tempMap.containsKey(menu.getUpMenuNo())){//存在
			TreeNodeGrid upNode = tempMap.get(menu.getUpMenuNo());
			TreeNodeGrid node = new TreeNodeGrid();
			node = getChildNode(node,menu,"0");
			upNode = getNode(upNode,node,"1");//父节点存在
			tempMap.put(menu.getUpMenuNo(),upNode);
		}else{//不存在
			TreeNodeGrid upNode = new TreeNodeGrid();//创建父节点
			TreeNodeGrid node = new TreeNodeGrid();//创建子节点
			//封装子节点的信息
			node = getChildNode(node,menu,"0");
			upNode = getNode(upNode,node,"0");//父节点存在
			tempMap.put(menu.getUpMenuNo(),upNode);
		}
		return tempMap;
	}
	/**
	 * 
	 *
	 * TODO 若子节点不存在时，封装子节点信息，并返回.
	 *
	 * @param menu
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月3日    	  杜旭    新建
	 * </pre>
	 */
	public TreeNodeGrid getChildNode(TreeNodeGrid node,WebMenuInfo menu,String childType){
		node.setId(menu.getMenuNo());//id
		node.setParentId(menu.getUpMenuNo());//parentId
		node.setName(menu.getMenuName());//name
		node.setVersion(menu.getId());//version
		if("0".equals(childType)){
			node.setChildren(new ArrayList<TreeNodeGrid>());
		}
		return node;
	}
	/**
	 * 
	 *
	 * TODO 封装单个菜单节点信息.
	 *
	 * @param upNode
	 * @param node
	 * @param type
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月3日    	  杜旭    新建
	 * </pre>
	 */
	public TreeNodeGrid getNode(TreeNodeGrid upNode,TreeNodeGrid node,String type){
		List<TreeNodeGrid> treeNodeChild = null;
		if("1".equals(type)){//父节点存在
			treeNodeChild = upNode.getChildren();
		}else{//父节点不存在
			treeNodeChild = new ArrayList<TreeNodeGrid>();
		}
		treeNodeChild.add(node);
		upNode.setChildren(treeNodeChild);
		return upNode;
	}
	/**
	 * 
	 *
	 * TODO 获取存储菜单信息的map.
	 *
	 * @param map
	 * @param tempMap
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月3日    	  杜旭    新建
	 * </pre>
	 */
	public Map<String,TreeNodeGrid> getNodeMap(Map<String,TreeNodeGrid> map,Map<String,TreeNodeGrid> tempMap){
		for(String key : tempMap.keySet()){
			TreeNodeGrid node = tempMap.get(key);
			if(node != null){
				String upMenuNo = node.getParentId();
				if(map.containsKey(upMenuNo)){
					TreeNodeGrid upNode = map.get(upMenuNo);
					List<TreeNodeGrid> treeNodeChild = upNode.getChildren();
					treeNodeChild.add(node);
					upNode.setChildren(treeNodeChild);
					map.put(upMenuNo, upNode);
				}
			}
		}
		return map;
	}
	
	/**
	 * 
	 *
	 * TODO 新增WEB_MENU_INFO.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="菜单管理",funCode="PM-14-01",funName="新增菜单表",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(WebMenuInfo webMenuInfo, HttpServletRequest request) throws Exception{
		FdOper fdOper = getSessionUser();
		this.json = webMenuInfoService.insertWebMenuInfo(webMenuInfo, fdOper);
		return this.json.toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 修改WEB_MENU_INFO.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="菜单管理",funCode="PM-14-02",funName="修改菜单表",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(WebMenuInfo webMenuInfo) throws Exception{
		FdOper fdOper = getSessionUser();
		this.json = webMenuInfoService.updateWebMenuInfo(webMenuInfo, fdOper);
		return this.json.toJson();
	}

	/**
	 * 
	 *
	 * TODO 删除WEB_MENU_INFO
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "deleteWebMenuInfo")
	@SystemLog(tradeName="菜单管理",funCode="PM-14-03",funName="删除菜单表",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(WebMenuInfo webMenuInfo) throws Exception {
		this.json = webMenuInfoService.deleteWebMenuInfo(webMenuInfo);
		return this.json.toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 菜单新增/修改中的上级菜单数据
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "selectUpMenuNo")
	@SystemLog(tradeName="菜单管理",funCode="PM-14",funName="查询上级菜单",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String selectUpMenuNo(HttpServletRequest request) throws Exception {
		WebMenuInfo webMenuInfo = new WebMenuInfo();
		webMenuInfo.setMenuType("1");
		List<WebMenuInfo> menuList = webMenuInfoService.selectByAttr(webMenuInfo);
		Map<String, List<TreeNode>> resultMap = new HashMap<String, List<TreeNode>>();
		List<TreeNode> list = new ArrayList<TreeNode>();
		for (WebMenuInfo menu: menuList) {
			TreeNode node = new TreeNode();
			node.setId(menu.getMenuNo());
			node.setName(menu.getMenuName());
			node.setIcon(menu.getMenuIcon());
			node.setOpen(true);
			node.setParentId(menu.getUpMenuNo());
			list.add(node);
		}
		resultMap.put("treeNodes", list);
		return JsonUtils.toJson(resultMap);
	}
}