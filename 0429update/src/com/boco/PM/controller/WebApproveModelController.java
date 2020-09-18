package com.boco.PM.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IWebApproveModelService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebApproveModel;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
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
@RequestMapping(value = "/webApproveModel/")
public class WebApproveModelController extends BaseController{
	@Autowired
	private IWebApproveModelService webApproveModelService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="常用审批意见管理",funCode="PM-19",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/webApproveModel/webApproveModelList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="常用审批意见管理",funCode="PM-19-04",funName="加载详细页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(WebApproveModel webApproveModel) throws Exception {
		setAttribute("webApproveModelDTO", webApproveModelService.selectByPK(webApproveModel.getAppCode()));
		return basePath + "/PM/webApproveModel/webApproveModelInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="常用审批意见管理",funCode="PM-19-01", funName="加载新增页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/PM/webApproveModel/webApproveModelAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="常用审批意见管理",funCode="PM-19-02", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(WebApproveModel webApproveModel) throws Exception {
		setAttribute("webApproveModelDTO", webApproveModelService.selectByPK(webApproveModel.getAppCode()));
		return basePath + "/PM/webApproveModel/webApproveModelEdit";
	}
	
	/**
	 * 
	 *
	 * TODO 查询WEB_APPROVE_MODEL分页数据
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="常用审批意见管理",funCode="PM-19",funName="加载列表数据",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(WebApproveModel webApproveModel) throws Exception {
		setPageParam();
		List<WebApproveModel> list = webApproveModelService.selectByLikeAttr(webApproveModel);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO 新增WEB_APPROVE_MODEL.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="常用审批意见管理",funCode="PM-19-01",funName="新增常用审批意见",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(WebApproveModel webApproveModel) throws Exception{
		FdOper fdOper = getSessionUser();
		json = webApproveModelService.insertWebApproveMode(webApproveModel,fdOper);
		return this.json.toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 修改WEB_APPROVE_MODEL.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="常用审批意见管理",funCode="PM-19-02",funName="修改常用审批意见",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(WebApproveModel webApproveModel) throws Exception{
		FdOper fdOper = getSessionUser();
		json = webApproveModelService.updateWebApproveMode(webApproveModel,fdOper);
		return this.json.toJson();
	}

	/**
	 * 
	 *
	 * TODO 删除WEB_APPROVE_MODEL
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "deleteApproveModel")
	@SystemLog(tradeName="常用审批意见管理",funCode="PM-19-03",funName="删除常用审批意见",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String deleteApproveModel(WebApproveModel webApproveModel) throws Exception {
		webApproveModelService.deleteByPK(webApproveModel.getAppCode());
		return this.json.returnMsg("true", "删除成功!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 产品预研、产品设计获取模板信息.
	 *
	 * @param webApproveModel
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月31日    	  杜旭    新建
	 * </pre>
	 */
	@RequestMapping(value = "getWebApproveModel")
	@SystemLog(tradeName="常用审批意见管理",funCode="SYSTEM",funName="加载审批意见数据",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String getWebApproveModel(WebApproveModel webApproveModel) throws Exception {
		List<WebApproveModel> list = webApproveModelService.selectByAttr(webApproveModel);
		Map<String,List<Map<String,String>>> result = new HashMap<String,List<Map<String,String>>>();
		List<Map<String,String>> modelList = new ArrayList<Map<String,String>>();
		for(WebApproveModel model : list){
			Map<String,String> map = new HashMap<String,String>();
			map.put("key", model.getAppAdvice());
			map.put("value", model.getAppCode());
			modelList.add(map);
		}
		result.put("list", modelList);
		return JsonUtils.toJson(result);
	}
}