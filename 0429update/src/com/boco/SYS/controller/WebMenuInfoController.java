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
 * �˵���Action���Ʋ�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/webMenuInfo/")
public class WebMenuInfoController extends BaseController{
	@Autowired
	private IWebMenuInfoService webMenuInfoService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="�˵�����",funCode="PM-14",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/webMenuInfo/webMenuInfoList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="�˵�����",funCode="PM-14-04",funName="������ϸҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(WebMenuInfo webMenuInfo) throws Exception {
		List<WebMenuInfo> Info = webMenuInfoService.selectByAttr(webMenuInfo);
		setAttribute("webMenuInfoDTO", Info.get(0) );
		return basePath + "/PM/webMenuInfo/webMenuInfoInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="�˵�����",funCode="PM-14-01", funName="��������ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/webMenuInfo/webMenuInfoEdit";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="�˵�����",funCode="PM-14-02", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
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
	 * TODO ��ѯWEB_MENU_INFO��ҳ����
	 * ����grid�Ĳ�ѯ����
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="�˵�����",funCode="PM-14",funName="�����б�����",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(WebMenuInfo webMenuInfo) throws Exception {
		Map<String, Object> results = new Hashtable<String, Object>();
		List<TreeNodeGrid> treeNode = new ArrayList<TreeNodeGrid>();
		//��ȡ���β˵���Ϣ
		treeNode = getTreeNode(webMenuInfo);
		results.put("rows", treeNode);
		return JsonUtils.toJson(results);
	}
	/**
	 * 
	 *
	 * TODO ��ȡ�˵���.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��29��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public List<TreeNodeGrid> getTreeNode(WebMenuInfo webMenuInfo) throws Exception{
		//��ʱmap
		Map<String,TreeNodeGrid> tempMap = new HashMap<String,TreeNodeGrid>();
		Map<String,TreeNodeGrid> map = new HashMap<String,TreeNodeGrid>();
		List<TreeNodeGrid> treeNode = new ArrayList<TreeNodeGrid>();
		Map selectAttr = new HashMap();
		//���Ƿ����Ϊ��
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
				//��ȡ�˵���Ϣ
				selectAttr.put("upMenuNo", upMenu);
				selectAttr.put("menuStatus", webMenuInfo.getMenuStatus());
				menuList = webMenuInfoService.selectByNo(selectAttr);
			}else{
				menuList = webMenulist;
			}
			
			for(WebMenuInfo menu : menuList){
				String menuNo = menu.getMenuNo();
				if("1".equals(menu.getIsParent())){//��Ϊ���˵�
					TreeNodeGrid node = new TreeNodeGrid();
					node = getChildNode(node,menu,"0");
					map.put(menuNo, node);
				}else if("0".equals(menu.getIsParent())){//��Ϊ�Ӳ˵�
					if("1".equals(menu.getMenuType())){//�˵�
						tempMap = getChildMenu(tempMap,menu);
					}else if("2".equals(menu.getMenuType())){//��ť
						tempMap = getButtonMenu(tempMap,menu);
					}
				}
			}
			//��ȡ�����Ĳ˵���Ϣ
			map = getNodeMap(map,tempMap);
			//���˵���Ϣ������list������
			for(String menu : map.keySet()){
				treeNode.add(map.get(menu));
			}
		}
		return treeNode;
	}
	/**
	 * 
	 *
	 * TODO �����Ӳ˵���Ϣ.
	 *
	 * @param tempMap
	 * @param menu
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��3��    	  ����    �½�
	 * </pre>
	 */
	public Map<String,TreeNodeGrid> getChildMenu(Map<String,TreeNodeGrid> tempMap,WebMenuInfo menu){
		if(tempMap.containsKey(menu.getMenuNo())){//����
			TreeNodeGrid node = tempMap.get(menu.getMenuNo());
			node = getChildNode(node,menu,"1");
			tempMap.put(menu.getMenuNo(), node);
		}else{//������
			TreeNodeGrid node = new TreeNodeGrid();
			node = getChildNode(node,menu,"0");
			tempMap.put(menu.getMenuNo(), node);
			
			if(tempMap.containsKey(menu.getUpMenuNo())){//���ڵ����
				TreeNodeGrid upNode = tempMap.get(menu.getUpMenuNo());
				upNode = getNode(upNode,node,"1");
				tempMap.put(menu.getUpMenuNo(), upNode);
			}else{
				TreeNodeGrid upNode = new TreeNodeGrid();//�������ڵ�
				upNode = getNode(upNode,node,"0");//���ڵ㲻����
				tempMap.put(menu.getUpMenuNo(),upNode);
			}
		}
		return tempMap;
	}
	/**
	 * 
	 *
	 * TODO ����ť��Ϣ.
	 *
	 * @param tempMap
	 * @param menu
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��3��    	  ����    �½�
	 * </pre>
	 */
	public Map<String,TreeNodeGrid> getButtonMenu(Map<String,TreeNodeGrid> tempMap,WebMenuInfo menu){
		if(tempMap.containsKey(menu.getUpMenuNo())){//����
			TreeNodeGrid upNode = tempMap.get(menu.getUpMenuNo());
			TreeNodeGrid node = new TreeNodeGrid();
			node = getChildNode(node,menu,"0");
			upNode = getNode(upNode,node,"1");//���ڵ����
			tempMap.put(menu.getUpMenuNo(),upNode);
		}else{//������
			TreeNodeGrid upNode = new TreeNodeGrid();//�������ڵ�
			TreeNodeGrid node = new TreeNodeGrid();//�����ӽڵ�
			//��װ�ӽڵ����Ϣ
			node = getChildNode(node,menu,"0");
			upNode = getNode(upNode,node,"0");//���ڵ����
			tempMap.put(menu.getUpMenuNo(),upNode);
		}
		return tempMap;
	}
	/**
	 * 
	 *
	 * TODO ���ӽڵ㲻����ʱ����װ�ӽڵ���Ϣ��������.
	 *
	 * @param menu
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��3��    	  ����    �½�
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
	 * TODO ��װ�����˵��ڵ���Ϣ.
	 *
	 * @param upNode
	 * @param node
	 * @param type
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��3��    	  ����    �½�
	 * </pre>
	 */
	public TreeNodeGrid getNode(TreeNodeGrid upNode,TreeNodeGrid node,String type){
		List<TreeNodeGrid> treeNodeChild = null;
		if("1".equals(type)){//���ڵ����
			treeNodeChild = upNode.getChildren();
		}else{//���ڵ㲻����
			treeNodeChild = new ArrayList<TreeNodeGrid>();
		}
		treeNodeChild.add(node);
		upNode.setChildren(treeNodeChild);
		return upNode;
	}
	/**
	 * 
	 *
	 * TODO ��ȡ�洢�˵���Ϣ��map.
	 *
	 * @param map
	 * @param tempMap
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��3��    	  ����    �½�
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
	 * TODO ����WEB_MENU_INFO.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="�˵�����",funCode="PM-14-01",funName="�����˵���",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(WebMenuInfo webMenuInfo, HttpServletRequest request) throws Exception{
		FdOper fdOper = getSessionUser();
		this.json = webMenuInfoService.insertWebMenuInfo(webMenuInfo, fdOper);
		return this.json.toJson();
	}
	
	/**
	 * 
	 *
	 * TODO �޸�WEB_MENU_INFO.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="�˵�����",funCode="PM-14-02",funName="�޸Ĳ˵���",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(WebMenuInfo webMenuInfo) throws Exception{
		FdOper fdOper = getSessionUser();
		this.json = webMenuInfoService.updateWebMenuInfo(webMenuInfo, fdOper);
		return this.json.toJson();
	}

	/**
	 * 
	 *
	 * TODO ɾ��WEB_MENU_INFO
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "deleteWebMenuInfo")
	@SystemLog(tradeName="�˵�����",funCode="PM-14-03",funName="ɾ���˵���",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(WebMenuInfo webMenuInfo) throws Exception {
		this.json = webMenuInfoService.deleteWebMenuInfo(webMenuInfo);
		return this.json.toJson();
	}
	
	/**
	 * 
	 *
	 * TODO �˵�����/�޸��е��ϼ��˵�����
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "selectUpMenuNo")
	@SystemLog(tradeName="�˵�����",funCode="PM-14",funName="��ѯ�ϼ��˵�",accessType=AccessType.READ,level=Debug.DEBUG)
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