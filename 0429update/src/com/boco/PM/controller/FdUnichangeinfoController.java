package com.boco.PM.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IFdUnichangeinfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdUnichangeinfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.MapUtil;
/**
 * 
 * 
 * FdUnichangeinfoAction控制层
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/fdUnichangeinfo/")
public class FdUnichangeinfoController extends BaseController{
	@Autowired
	private IFdUnichangeinfoService fdUnichangeinfoService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="业务模块-交易名称",funCode="未填写",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI(Model model) throws Exception {
		authButtons();
		return basePath + "/fdUnichangeinfo/fdUnichangeinfoList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="业务模块-交易名称",funCode="未填写",funName="加载详细页面",accessType=AccessType.READ,level=Debug.INFO)
	public String infoUI(Model model,FdUnichangeinfo fdUnichangeinfo) throws Exception {
		setAttribute("fdUnichangeinfo", fdUnichangeinfoService.selectByPK(MapUtil.beanToMap(fdUnichangeinfo)));
		return basePath + "/fdUnichangeinfo/fdUnichangeinfoInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="业务模块-交易名称",funCode="未填写", funName="加载新增页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI(Model model) throws Exception {
		return basePath + "/fdUnichangeinfo/fdUnichangeinfoEdit";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="业务模块-交易名称",funCode="未填写", funName="加载修改页面",accessType=AccessType.READ,level=Debug.INFO)
	public String updateUI(Model model,FdUnichangeinfo fdUnichangeinfo) throws Exception {
		setAttribute("fdUnichangeinfo", fdUnichangeinfoService.selectByPK(MapUtil.beanToMap(fdUnichangeinfo)));
		return basePath + "/fdUnichangeinfo/fdUnichangeinfoEdit";
	}
	
	/**
	 * 
	 *
	 * TODO 查询FD_UNICHANGEINFO分页数据
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="业务模块-交易名称",funCode="未填写",funName="加载列表数据",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(FdUnichangeinfo fdUnichangeinfo) throws Exception {
		setPageParam();
		List<FdUnichangeinfo> list = fdUnichangeinfoService.selectByAttr(fdUnichangeinfo);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO 新增FD_UNICHANGEINFO-记录日志.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="业务模块-交易名称",funCode="未填写",funName="新增FdUnichangeinfo",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(FdUnichangeinfo fdUnichangeinfo) throws Exception{
		fdUnichangeinfoService.insertEntity(fdUnichangeinfo);
		return this.json.returnMsg("true", "新增成功!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 修改FD_UNICHANGEINFO-记录日志.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="业务模块-交易名称",funCode="未填写",funName="修改FdUnichangeinfo",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(FdUnichangeinfo fdUnichangeinfo) throws Exception{
		fdUnichangeinfoService.updateByPK(fdUnichangeinfo);
		return this.json.returnMsg("true", "修改成功!").toJson();
	}

	/**
	 * 
	 *
	 * TODO 删除FD_UNICHANGEINFO-记录日志
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="业务模块-交易名称",funCode="未填写",funName="删除FdUnichangeinfo",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(FdUnichangeinfo fdUnichangeinfo) throws Exception {
		fdUnichangeinfoService.deleteByPK(MapUtil.beanToMap(fdUnichangeinfo));
		return this.json.returnMsg("true", "删除成功!").toJson();
	}
}