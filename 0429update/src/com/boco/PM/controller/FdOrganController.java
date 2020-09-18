package com.boco.PM.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IFdOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.WebAreaOrganInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.util.JsonUtils;
/**
 * 
 * 
 * FdOrganAction控制层
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/fdOrgan/")
public class FdOrganController extends BaseController{
	@Autowired
	private IFdOrganService fdOrganService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="机构管理",funCode="system",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/fdOrgan/fdOrganList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="机构管理",funCode="system",funName="加载详细页面",accessType=AccessType.READ,level=Debug.INFO)
	public String infoUI(FdOrgan fdOrgan) throws Exception {
		setAttribute("fdOrgan", fdOrganService.selectByPK(fdOrgan.getThiscode()));
		return basePath + "/fdOrgan/fdOrganInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="机构管理",funCode="system", funName="加载新增页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/fdOrgan/fdOrganEdit";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="机构管理",funCode="system", funName="加载修改页面",accessType=AccessType.READ,level=Debug.INFO)
	public String updateUI(FdOrgan fdOrgan) throws Exception {
		setAttribute("fdOrgan", fdOrganService.selectByPK(fdOrgan.getThiscode()));
		return basePath + "/fdOrgan/fdOrganEdit";
	}
	
	/**
	 * 
	 *
	 * TODO 查询FD_ORGAN分页数据
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="机构管理",funCode="system",funName="加载列表数据",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(FdOrgan fdOrgan) throws Exception {
		setPageParam();
		List<FdOrgan> list = fdOrganService.selectByAttr(fdOrgan);
		return pageData(list);
	}

	/**
	 * 
	 *
	 * TODO 新增FD_ORGAN-记录日志.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="机构管理",funCode="system",funName="新增",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(FdOrgan fdOrgan) throws Exception{
		fdOrganService.insertEntity(fdOrgan);
		return this.json.returnMsg("true", "新增成功!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 修改FD_ORGAN-记录日志.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="机构管理",funCode="system",funName="修改",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(FdOrgan fdOrgan) throws Exception{
		fdOrganService.updateByPK(fdOrgan);
		return this.json.returnMsg("true", "修改成功!").toJson();
	}

	/**
	 * 
	 *
	 * TODO 删除FD_ORGAN-记录日志
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="机构管理",funCode="system",funName="删除",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(FdOrgan fdOrgan) throws Exception {
		fdOrganService.deleteByPK(fdOrgan.getThiscode());
		return this.json.returnMsg("true", "删除成功!").toJson();
	}
	/**
	 * 
	 *
	 * TODO 查询一级分行，组建多选下拉框.
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	    谷立羊   新建
	 * </pre>
	 */
	@RequestMapping(value = "getProvince")
	@SystemLog(tradeName="获取一级分行",funCode="system",funName="获取一级分行",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String getProvince() throws Exception {
		List<FdOrgan> list=fdOrganService.selectProvOrgan();
		
		String type=getParameter("type");
		/*Dzqd电子虚拟渠道*/
		String Dzqd=getParameter("Dzqd");
		Map<String, Object> results = new Hashtable<String, Object>();
		List<Map<String,String>> treelist=new ArrayList<Map<String,String>>();
		Map<String,String> treeMap=new HashMap<String,String>();
		treeMap.put("id", "c");
		treeMap.put("parentId", "p");
		treeMap.put("name", "全选");
		treelist.add(treeMap);
		if (null!=Dzqd) {
			treeMap=new HashMap<String,String>();
			treeMap.put("id", "11005293");
			treeMap.put("parentId", "c");
			treeMap.put("name", "全国中心");
			treelist.add(treeMap);
			treeMap=new HashMap<String,String>();
			treeMap.put("id", "88888888");
			treeMap.put("parentId", "c");
			treeMap.put("name", "电子渠道");
			treelist.add(treeMap);
		}
		for(FdOrgan info:list){
			treeMap=new HashMap<String,String>();
			treeMap.put("id", info.getThiscode());
			treeMap.put("parentId", "c");
			treeMap.put("name", info.getOrganname());
			if (null!=type) {
				treeMap.put("chkDisabled", "true");
			}
			treelist.add(treeMap);
		}
		
		results.put("treeNodes", treelist);
		return JsonUtils.toJson(results);	
	}
	
}