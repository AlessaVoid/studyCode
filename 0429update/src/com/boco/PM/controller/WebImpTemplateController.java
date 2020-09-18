package com.boco.PM.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.boco.PM.service.IWebImpTemplateService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.WebImpTemplate;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
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
@RequestMapping(value = "/webImpTemplate/")
public class WebImpTemplateController extends BaseController{
	@Autowired
	private IWebImpTemplateService webImpTemplateService;
	
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="模板管理",funCode="PM-21",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/webImpTemplate/webImpTemplateList";
	}
	@RequestMapping("uploadUI")
	@SystemLog(tradeName="模板管理",funCode="PM-21-01", funName="加载上传页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/PM/webImpTemplate/webImpTemplateEdit";
	}
	
	/**
	 * 
	 *
	 * TODO 查询WEB_IMP_TEMPLATE分页数据
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="模板管理",funCode="PM-21",funName="加载列表数据",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(WebImpTemplate webImpTemplate) throws Exception {
		setPageParam();
		List<WebImpTemplate> list = webImpTemplateService.selectByLikeAttr(webImpTemplate);
		return pageData(list);
	}
	/**
	 * 
	 *
	 * TODO 上传模板.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年03月10日    	    杜旭    新建
	 * </pre>
	 */
	@RequestMapping(value = "upLoad")
	@SystemLog(tradeName="模板管理",funCode="PM-21-01",funName="上传",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String upLoad(@RequestParam("paramXsl") CommonsMultipartFile paramXsl,WebImpTemplate webImpTemplate,HttpServletRequest request,HttpServletResponse response) throws Exception{
		json = webImpTemplateService.upLoad(paramXsl,webImpTemplate,request,response);
		return json.toJson();
	}
	/**
	 * 
	 *
	 * TODO 下载模板.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年03月10日    	    杜旭    新建
	 * </pre>
	 */
	@RequestMapping(value = "download")
	@SystemLog(tradeName="模板管理",funCode="PM-21-02",funName="下载",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody void download(WebImpTemplate webImpTemplate,HttpServletRequest request,HttpServletResponse response) throws Exception{
		webImpTemplateService.download(webImpTemplate,request,response);
	}

	/**
	 * 
	 *
	 * TODO 删除模板信息
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "deleteImpTemplate")
	@SystemLog(tradeName="模板管理",funCode="PM-21-03",funName="删除",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String deleteImpTemplate(WebImpTemplate webImpTemplate,HttpServletRequest request,HttpServletResponse response) throws Exception {
		json = webImpTemplateService.deleteImpTemplate(webImpTemplate,request,response);
		return  json.toJson();
	}
}