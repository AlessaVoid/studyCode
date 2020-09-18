package com.boco.SYS.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.GfDict;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.service.IGfDictService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.MapUtil;
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
@RequestMapping(value = "/gfDict/")
public class GfDictController extends BaseController{
	@Autowired
	private IGfDictService gfDictService;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="字典信息列表",funCode="PM-08",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/gfDict/gfDictList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="字典详细信息",funCode="PM-08-04",funName="加载详细页面",accessType=AccessType.READ,level=Debug.INFO)
	public String infoUI(GfDict gfDict) throws Exception {
		gfDict.setDictName(BocoUtils.UTF8String(gfDict.getDictName(), "UTF-8"));
		gfDict.setDictNo(BocoUtils.UTF8String(gfDict.getDictNo(), "UTF-8"));
		gfDict.setDictKeyIn(BocoUtils.UTF8String(gfDict.getDictKeyIn(), "UTF-8"));
		setAttribute("dict", gfDictService.selectByPK(MapUtil.beanToMap(gfDict)));
		return basePath + "/PM/gfDict/gfDictInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="新增字典信息",funCode="PM-08-01", funName="加载新增页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/PM/gfDict/gfDictAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="修改字典信息",funCode="PM-08-02", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(GfDict gfDict) throws Exception {
		gfDict.setDictName(BocoUtils.UTF8String(gfDict.getDictName(), "UTF-8"));
		setAttribute("dict", gfDictService.selectByPK(MapUtil.beanToMap(gfDict)));
		return basePath + "/PM/gfDict/gfDictEdit";
	}
	@RequestMapping("coypUI")
	@SystemLog(tradeName="修改字典信息",funCode="PM-08-05", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String coypUI(GfDict gfDict) throws Exception {
		gfDict.setDictName(BocoUtils.UTF8String(gfDict.getDictName(), "UTF-8"));
		setAttribute("dict", gfDictService.selectByPK(MapUtil.beanToMap(gfDict)));
		return basePath + "/PM/gfDict/gfDictEdit_copy";
	}
	
	/**
	 * 
	 *
	 * TODO 查询GF_DICT分页数据
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="字典列表信息",funCode="PM-08",funName="加载列表数据",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(GfDict gfDict) throws Exception {
		setPageParam();
		List<GfDict> list = gfDictService.selectByAttr(gfDict);
		return pageData(list);
	}
	/**
	 * 
	 *
	 * TODO 新增GF_DICT.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="新增字典信息",funCode="PM-08-01",funName="新增",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(GfDict gfDict) throws Exception{
		 
		//当前日期、当前时间
		gfDict.setUpdateDate(fdBusinessDateService.getCommonDateDetails());
		gfDict.setUpdateTime(BocoUtils.getNowTime());
		this.json = gfDictService.insert(gfDict);
		return this.json.toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 修改GF_DICT.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="修改字典信息",funCode="PM-08-02",funName="修改",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(GfDict gfDict) throws Exception{
		
		//当前日期、当前时间
		gfDict.setUpdateDate(fdBusinessDateService.getCommonDateDetails());
		gfDict.setUpdateTime(BocoUtils.getNowTime());
		
		this.json = gfDictService.update(gfDict);
		return this.json.toJson();
	}

	/**
	 * 
	 *
	 * TODO 删除GF_DICT
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="删除字典信息",funCode="PM-08-03",funName="删除",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(GfDict gfDict) throws Exception {
		gfDictService.deleteByPK(MapUtil.beanToMap(gfDict));
		return this.json.returnMsg("true", "删除成功!").toJson();
	}
	/**
	 * 
	 *
	 * TODO 获取字典表中类型为dicType的组并组装成页面多选下拉框数据格式.
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	    谷立羊   新建
	 * </pre>
	 */
	@RequestMapping(value = "getTreeDic")
	@SystemLog(tradeName="获取下拉多选框",funCode="SYSTEM",funName="查询",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String getTreeDic(HttpServletRequest request) throws Exception {
		String dicType=(String) request.getParameter("dicType");
		String  type=request.getParameter("type");
		GfDict gfDict=new GfDict();
		gfDict.setDictNo(dicType);
		List<GfDict> list=gfDictService.selectByAttr(gfDict);
		Map<String, Object> results = new Hashtable<String, Object>();
		List<Map<String,Object>> treelist=new ArrayList<Map<String,Object>>();
		Map<String,Object> treeMap=new HashMap<String,Object>();
		treeMap.put("id", "c");
		treeMap.put("parentId", "p");
		treeMap.put("name", "全选");
		treelist.add(treeMap);
		for(GfDict info:list){
			treeMap=new HashMap<String,Object>();
			treeMap.put("id", info.getDictKeyIn());
			treeMap.put("parentId", "c");
			treeMap.put("name", info.getDictValueIn());
			if (null!=type) {
				treeMap.put("chkDisabled", "true");
			}
			treelist.add(treeMap);
		}
		results.put("treeNodes", treelist);
		return JsonUtils.toJson(results);
	}
	/**
	 * 
	 *
	 * TODO 联想输入框
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      秦海洲      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "selectDictNo")
	@SystemLog(tradeName="分组英文名联想输入",funCode="PM-08",funName="联想输入",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String selectCode(GfDict gfDict,HttpServletRequest request) throws Exception {
		String dictNo = request.getParameter("dictNo").replace("'","");
		if(dictNo!=null){
			gfDict.setDictNo(dictNo);
		}
		Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Set<String> set = new TreeSet<String>();
		List<Map<String, String>> dict = gfDictService.selectDictNo(gfDict);
		for (Map<String, String> dictInfo : dict) {
			String data = dictInfo.get("dict_no");
			set.add(data);
		}
		
		for (String data : set) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("key", data);
			map.put("value", data);
			list.add(map);
		}

		resultMap.put("list", list);
		String json = JsonUtils.toJson(resultMap);
		return json;
	}
	/**
	 * 
	 *
	 * TODO 联想输入框
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      秦海洲      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "selectDictName")
	@SystemLog(tradeName="分组中文名联想输入",funCode="PM-08",funName="联想输入",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String selectDictName(GfDict gfDict,HttpServletRequest request) throws Exception {
		String dictName = request.getParameter("dictName").replace("'","");
		if(dictName!=null){
			gfDict.setDictName(dictName);
		}
		Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Set<String> set = new TreeSet<String>();
		List<Map<String, String>> dict = gfDictService.selectDictName(gfDict);
		for (Map<String, String> dictInfo : dict) {
			String data = dictInfo.get("dict_name");
			set.add(data);
		}
		
		for (String data : set) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("key", data);
			map.put("value", data);
			list.add(map);
		}

		resultMap.put("list", list);
		String json = JsonUtils.toJson(resultMap);
		return json;
	}
	/**
	 * 
	 *
	 * TODO 联想输入框
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      秦海洲      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "selectCreateOper")
	@SystemLog(tradeName="创建人员联想输入",funCode="PM-08",funName="联想输入",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String selectCreateOper(GfDict gfDict,HttpServletRequest request) throws Exception {
		String createOper = request.getParameter("createOper").replace("'","");
		if(createOper!=null){
			gfDict.setCreateOper(createOper);
		}
		Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Set<String> set = new TreeSet<String>();
		List<Map<String, String>> dict = gfDictService.selectCreateOper(gfDict);
		for (Map<String, String> dictInfo : dict) {
			String data = dictInfo.get("create_oper");
			set.add(data);
		}
		
		for (String data : set) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("key", data);
			map.put("value", data);
			list.add(map);
		}

		resultMap.put("list", list);
		String json = JsonUtils.toJson(resultMap);
		return json;
	}
	/**
	 * 
	 *
	 * TODO 获取查询产品状态范围
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月18日    	   谷立羊  新建
	 * </pre>
	 */
	@RequestMapping(value = "getPordStatusTreeDic")
	@SystemLog(tradeName="获取下拉多选框",funCode="SYSTEM",funName="查询",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String getPordStatusTreeDic(HttpServletRequest request) throws Exception {
		String dicType=(String) request.getParameter("dicType");
		String  type=request.getParameter("type");
		String  prodStatus=request.getParameter("prodStatus");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("dicNo", dicType);
		map.put("valKeys", prodStatus.split(","));
		List<GfDict> list=gfDictService.findByValINKeys(map);
		Map<String, Object> results = new Hashtable<String, Object>();
		List<Map<String,Object>> treelist=new ArrayList<Map<String,Object>>();
		Map<String,Object> treeMap=new HashMap<String,Object>();
		treeMap.put("id", "c");
		treeMap.put("parentId", "p");
		treeMap.put("name", "全选");
		treelist.add(treeMap);
		for(GfDict info:list){
			treeMap=new HashMap<String,Object>();
			treeMap.put("id", info.getDictKeyIn());
			treeMap.put("parentId", "c");
			treeMap.put("name", info.getDictValueIn());
			if (null!=type) {
				treeMap.put("chkDisabled", "true");
			}
			treelist.add(treeMap);
		}
		results.put("treeNodes", treelist);
		return JsonUtils.toJson(results);
	}
}