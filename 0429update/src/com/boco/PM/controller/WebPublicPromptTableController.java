package com.boco.PM.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IWebPublicPromptTableService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.WebPublicPromptTable;
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
@RequestMapping(value = "/webPublicPromptTable/")
public class WebPublicPromptTableController extends BaseController{
	@Autowired
	private IWebPublicPromptTableService webPublicPromptTableService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="交易名称",funCode="PM-05",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/webPublicPromptTable/webPublicPromptTableList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="交易名称",funCode="PM-05-04",funName="加载详细页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(WebPublicPromptTable webPublicPromptTable) throws Exception {
		setAttribute("entity", webPublicPromptTableService.selectByPK(webPublicPromptTable.getId()));
		return basePath + "/PM/webPublicPromptTable/webPublicPromptTableInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="交易名称",funCode="PM-05-01", funName="加载新增页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/PM/webPublicPromptTable/webPublicPromptTableAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="交易名称",funCode="PM-05-02", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(WebPublicPromptTable webPublicPromptTable) throws Exception {
		setAttribute("entity", webPublicPromptTableService.selectByPK(webPublicPromptTable.getId()));
		return basePath + "/PM/webPublicPromptTable/webPublicPromptTableEdit";
	}
	
	/**
	 * 
	 *
	 * TODO 查询WEB_PUBLIC_PROMPT_TABLE分页数据
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="交易名称",funCode="PM-05",funName="加载列表数据",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(WebPublicPromptTable webPublicPromptTable) throws Exception {
		setPageParam();
		List<WebPublicPromptTable> list = webPublicPromptTableService.selectByAttr(webPublicPromptTable);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO 新增WEB_PUBLIC_PROMPT_TABLE.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="交易名称",funCode="PM-05-01",funName="新增",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(WebPublicPromptTable webPublicPromptTable) throws Exception{
		webPublicPromptTableService.saveInfo(webPublicPromptTable);
		return this.json.returnMsg("true", "新增成功!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 修改WEB_PUBLIC_PROMPT_TABLE.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="交易名称",funCode="PM-05-02",funName="修改",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(WebPublicPromptTable webPublicPromptTable) throws Exception{
		webPublicPromptTableService.updateByPK(webPublicPromptTable);
		return this.json.returnMsg("true", "修改成功!").toJson();
	}

	/**
	 * 
	 *
	 * TODO 删除WEB_PUBLIC_PROMPT_TABLE
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="交易名称",funCode="PM-05-03",funName="删除",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(WebPublicPromptTable webPublicPromptTable) throws Exception {
		webPublicPromptTableService.deleteByPK(webPublicPromptTable.getId());
		webPublicPromptTableService.refresh(webPublicPromptTable);
		return this.json.returnMsg("true", "删除成功!").toJson();
	}
	/**
	 * 
	 *
	 * TODO 立即刷新
	 *
	 * @param webPublicPromptTable
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年11月21日    	   谷立羊  新建
	 * </pre>
	 */
	@RequestMapping(value = "refresh")
	@SystemLog(tradeName="交易名称",funCode="PM-05-05",funName="刷新",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String refresh(WebPublicPromptTable webPublicPromptTable) throws Exception {
		webPublicPromptTableService.refresh(webPublicPromptTable);
		return this.json.returnMsg("true", "操作成功,刷新页面后生效!").toJson();
	}
	
}