package com.boco.PM.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IFdOrganService;
import com.boco.PM.service.IWebAreaOrganInfoService;
import com.boco.PM.service.impl.FdOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.GfDict;
import com.boco.SYS.entity.WebAreaOrganInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IGfDictService;
import com.boco.SYS.util.MapUtil;
import com.boco.SYS.util.TreeNodeGrid;
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
@RequestMapping(value = "/webAreaOrganInfo/")
public class WebAreaOrganInfoController extends BaseController{
	@Autowired
	private IWebAreaOrganInfoService webAreaOrganInfoService;
	@Autowired
	private IGfDictService gfDictService;
	@Autowired
	private IFdOrganService fdOrganService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="地区管理",funCode="PM-18",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/webAreaOrganInfo/webAreaOrganInfoList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="地区管理",funCode="PM-18-04",funName="加载详细页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(WebAreaOrganInfo webAreaOrganInfo) throws Exception {
		WebAreaOrganInfo webAreaOrganInfoDTO = getWebAreaOrganInfo(webAreaOrganInfo,"info");
		setAttribute("webAreaOrganInfoDTO", webAreaOrganInfoDTO);
		return basePath + "/PM/webAreaOrganInfo/webAreaOrganInfoInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="地区管理",funCode="PM-18-01", funName="加载新增页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		WebAreaOrganInfo webAreaOrganInfo = new WebAreaOrganInfo();
		String provCode = getOrganList(webAreaOrganInfo,"insert");
		setAttribute("provCode", provCode);
		return basePath + "/PM/webAreaOrganInfo/webAreaOrganInfoAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="地区管理",funCode="PM-18-02", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(WebAreaOrganInfo webAreaOrganInfo) throws Exception {
		String provCode = getOrganList(webAreaOrganInfo,"update");
		setAttribute("provCode", provCode);
		WebAreaOrganInfo webAreaOrganInfoDTO = getWebAreaOrganInfo(webAreaOrganInfo,"update");
		setAttribute("webAreaOrganInfoDTO", webAreaOrganInfoDTO);
		return basePath + "/PM/webAreaOrganInfo/webAreaOrganInfoEdit";
	}
	
	/**
	 * 
	 *
	 * TODO 查询WEB_AREA_ORGAN_INFO分页数据
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="地区管理",funCode="PM-18",funName="加载列表数据",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(GfDict gfDict) throws Exception {
		if(!StringUtils.isNotEmpty(gfDict.getDictNo())){
			gfDict.setDictNo("D_AREA_NAME");
		}
		gfDict.setSortColumn("DICT_NO_ORDER");
		gfDict.setStatus("1");
		setPageParam();
		List<GfDict> list = gfDictService.selectByAttr(gfDict);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO 新增WEB_AREA_ORGAN_INFO.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="地区管理",funCode="PM-18-01",funName="新增",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(WebAreaOrganInfo webAreaOrganInfo,HttpServletRequest request) throws Exception{
		FdOper fdOper = getSessionUser();
		String organs = request.getParameter("organs");
		json = webAreaOrganInfoService.insertWebAreaOrgan(webAreaOrganInfo,organs,fdOper);
		return this.json.toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 修改WEB_AREA_ORGAN_INFO.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="地区管理",funCode="PM-18-02",funName="修改",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(WebAreaOrganInfo webAreaOrganInfo,HttpServletRequest request) throws Exception{
		FdOper fdOper = getSessionUser();
		String organs = request.getParameter("organs");
		json = webAreaOrganInfoService.updateWebAreaOrgan(webAreaOrganInfo,organs,fdOper);
		return this.json.toJson();
	}

	/**
	 * 
	 *
	 * TODO 删除WEB_AREA_ORGAN_INFO
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "deleteWebAreaOrgan")
	@SystemLog(tradeName="地区管理",funCode="PM-18-03",funName="删除",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String deleteWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo) throws Exception {
		webAreaOrganInfoService.deleteWebAreaOrgan(webAreaOrganInfo);
		return this.json.returnMsg("true", "删除成功").toJson();
	}
	/**
	 * 
	 *
	 * TODO 地区管理中的一分机构集合.
	 *
	 * @param webAreaOrganInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	@RequestMapping(value = "getOrganList")
	@SystemLog(tradeName="地区管理",funCode="PM-18",funName="获取地区管理中的一分机构",accessType=AccessType.READ,level=Debug.DEBUG)
	public String getOrganList(WebAreaOrganInfo webAreaOrganInfo,String type) throws Exception {
		String areaHasOrgan = "",otherHasOrgan="";
		List<WebAreaOrganInfo> organList = new ArrayList<WebAreaOrganInfo>();
		//新增，这时没有地区编码，因此获取数据库中已被选取的一分机构
		if("insert".equals(type)){
			organList = webAreaOrganInfoService.selectByAttr(webAreaOrganInfo);
		}else if("update".equals(type)){
			//修改，当前地区已有地区编码，通过地区编码获取该地区已选择的一分机构
			List<WebAreaOrganInfo> areaOrganList = webAreaOrganInfoService.selectByAttr(webAreaOrganInfo);
			//获取除当前地区之外其他地区选取的一分机构
			organList = webAreaOrganInfoService.selectNotEqualOrgan(webAreaOrganInfo.getAreaCode());
			//将地区拥有的一分机构转化为字符串格式
			areaHasOrgan = getHasOrgan(areaOrganList);
		}
		//将集合中的一分机构转化为字符串格式
		otherHasOrgan = getHasOrgan(organList);
		//查询机构表中除'99'开头，机构级别为1的一分机构
		List<FdOrgan> fdOrganList = fdOrganService.selectProvOrgan();
		StringBuffer sbuff = new StringBuffer();
		String br = "";
		//格式循环变量
		int  count = 0;
		for(int i = 0;i < fdOrganList.size();i++){
			String checked="";
			FdOrgan fdOrgan = fdOrganList.get(i);
			if(otherHasOrgan.indexOf(fdOrgan.getThiscode()) == -1){
				count++;
				if(count%3==0){
					br = "\"<br/>\"+";
				}else{
					br = "";
				}
				if(areaHasOrgan.indexOf(fdOrgan.getThiscode()) != -1){
					checked = "checked='checked'";
				}
				sbuff.append("\"<input type='checkbox' "+ checked+" id='organ-"+ i + "' name='provCode' value='" + fdOrgan.getThiscode() + "'/>\"+\n");
				sbuff.append("\"<label class='hand' for='organ-"+ i + "'>"+ fdOrgan.getOrganname() + "</label>\"+\"&nbsp;&nbsp;&nbsp;\"+"+br+"\n");
			}
		}
		String provCode = sbuff.toString();
		if(StringUtils.isNotEmpty(provCode)){
			provCode = provCode.substring(0,provCode.length() - 2);
		}else{
			provCode = "'无'";
		}
		return provCode;
	}
	/**
	 * 
	 *
	 * TODO 将集合中的机构信息拼组字符串.
	 *
	 * @param organList
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月5日    	  杜旭    新建
	 * </pre>
	 */
	public String getHasOrgan(List<WebAreaOrganInfo> organList){
		String hasOrgan = "";
		if(organList.size() != 0){
			for(WebAreaOrganInfo areaOrgan:organList){
				hasOrgan += areaOrgan.getProvCode() + ",";
			}
			if(!"".equals(hasOrgan)){
				hasOrgan = hasOrgan.substring(0, hasOrgan.length() - 1);
			}
		}
		return hasOrgan;
	}
	/**
	 * 
	 *
	 * TODO 封装地区实体.
	 *
	 * @param webAreaOrganInfo
	 * @param type
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月5日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public WebAreaOrganInfo getWebAreaOrganInfo(WebAreaOrganInfo webAreaOrganInfo,String type) throws Exception{
		WebAreaOrganInfo webAreaOrganInfoDTO = new WebAreaOrganInfo();
		webAreaOrganInfoDTO.setAreaCode(webAreaOrganInfo.getAreaCode());
		webAreaOrganInfoDTO.setAreaName(webAreaOrganInfo.getAreaName());
		String provCode = "",organName="";
		List<WebAreaOrganInfo> list = webAreaOrganInfoService.selectByAttr(webAreaOrganInfo);
		if(list.size() != 0){
			for(WebAreaOrganInfo webAreaOrgan : list){
				provCode += webAreaOrgan.getProvCode() + ",";
//				if("update".equals(type)){
//					provCode += webAreaOrgan.getProvCode() + ",";
//				}
//				else{
//					provCode += webAreaOrgan.getProvCode() + "\n";
//				}
			}
			provCode = provCode.substring(0,provCode.length() - 1);
		}
		//修改的时候，显示一分机构代码集
		if("update".equals(type)){
			webAreaOrganInfoDTO.setProvCode(provCode);
		}else{//查看详细的时候，显示机构名称
			List<Map<String,String>> organMap = fdOrganService.selectProvName(provCode.split(","));
			if(organMap.size() > 0){
				for(Map<String,String> map : organMap){
					organName += ","+map.get("organname");
				}
			}
			if(organName.length() > 0){
				organName = organName.substring(1);
			}else{
				organName="无";
			}
			webAreaOrganInfoDTO.setProvCode(organName);
		}
		return webAreaOrganInfoDTO;
	}
}