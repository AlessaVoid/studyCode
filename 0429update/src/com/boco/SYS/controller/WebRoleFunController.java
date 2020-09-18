package com.boco.SYS.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IWebRoleFunService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.WebRoleFun;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.MapUtil;
/**
 * 
 * 
 * 角色功能对照表Action控制层
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/webRoleFun/")
public class WebRoleFunController extends BaseController{
	@Autowired
	private IWebRoleFunService webRoleFunService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="交易名称",funCode="未填写",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/webRoleFun/webRoleFunList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="交易名称",funCode="未填写",funName="加载详细页面",accessType=AccessType.READ,level=Debug.INFO)
	public String infoUI(WebRoleFun webRoleFun) throws Exception {
		setAttribute("webRoleFun", webRoleFunService.selectByPK(MapUtil.beanToMap(webRoleFun)));
		return basePath + "/webRoleFun/webRoleFunInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="交易名称",funCode="未填写", funName="加载新增页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/webRoleFun/webRoleFunEdit";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="交易名称",funCode="未填写", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(WebRoleFun webRoleFun) throws Exception {
		setAttribute("webRoleFun", webRoleFunService.selectByPK(MapUtil.beanToMap(webRoleFun)));
		return basePath + "/webRoleFun/webRoleFunEdit";
	}
	
	/**
	 * 
	 *
	 * TODO 查询WEB_ROLE_FUN分页数据
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="交易名称",funCode="未填写",funName="加载列表数据",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(WebRoleFun webRoleFun) throws Exception {
		setPageParam();
		List<WebRoleFun> list = webRoleFunService.selectByAttr(webRoleFun);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO 新增WEB_ROLE_FUN.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="交易名称",funCode="未填写",funName="新增角色功能对照表",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(WebRoleFun webRoleFun) throws Exception{
		webRoleFunService.insertEntity(webRoleFun);
		return this.json.returnMsg("true", "新增成功!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 修改WEB_ROLE_FUN.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="交易名称",funCode="未填写",funName="修改角色功能对照表",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(WebRoleFun webRoleFun) throws Exception{
		webRoleFunService.updateByPK(webRoleFun);
		return this.json.returnMsg("true", "修改成功!").toJson();
	}

	/**
	 * 
	 *
	 * TODO 删除WEB_ROLE_FUN
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="交易名称",funCode="未填写",funName="删除角色功能对照表",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(WebRoleFun webRoleFun) throws Exception {
		webRoleFunService.deleteByPK(MapUtil.beanToMap(webRoleFun));
		return this.json.returnMsg("true", "删除成功!").toJson();
	}
}