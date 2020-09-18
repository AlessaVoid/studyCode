package com.boco.SYS.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.GfErrInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.service.IGfErrInfoService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.MapUtil;
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
@RequestMapping(value = "/gfErrInfo/")
public class GfErrInfoController extends BaseController{
	@Autowired
	private IGfErrInfoService gfErrInfoService;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	
	//访问列表、详细信息、新增、修改页面
		@RequestMapping("listUI")
		@SystemLog(tradeName="错误信息表",funCode="SYS-01",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
		public String listUI() throws Exception {
			authButtons();
			return basePath + "/PM/gfErrInfo/gfErrInfoList";
		}
		@RequestMapping("infoUI")
		@SystemLog(tradeName="错误信息表",funCode="SYS-01-04",funName="加载详细页面",accessType=AccessType.READ,level=Debug.INFO)
		public String infoUI(GfErrInfo gfErrInfo) throws Exception {
			setAttribute("entity", gfErrInfoService.selectByPK(MapUtil.beanToMap(gfErrInfo)));
			return basePath + "/PM/gfErrInfo/gfErrInfoInfo";
		}
		@RequestMapping("insertUI")
		@SystemLog(tradeName="错误信息表",funCode="SYS-01-01", funName="加载新增页面",accessType=AccessType.READ,level=Debug.DEBUG)
		public String insertUI() throws Exception {
			return basePath + "/PM/gfErrInfo/gfErrInfoAdd";
		}
		@RequestMapping("updateUI")
		@SystemLog(tradeName="错误信息表",funCode="SYS-01-02", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
		public String updateUI(GfErrInfo gfErrInfo) throws Exception {
			setAttribute("entity", gfErrInfoService.selectByPK(MapUtil.beanToMap(gfErrInfo)));
			return basePath + "/PM/gfErrInfo/gfErrInfoEdit";
		}
	
	/**
	 * 
	 *
	 * TODO 查询GF_ERR_INFO分页数据
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="错误信息表",funCode="SYS-01",funName="加载列表数据",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(GfErrInfo gfErrInfo) throws Exception {
		setPageParam();
		List<GfErrInfo> list = gfErrInfoService.selectByLikeAttr(gfErrInfo);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO 新增GF_ERR_INFO.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="新增错误码",funCode="SYS-01-01",funName="新增",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(GfErrInfo gfErrInfo) throws Exception{
		gfErrInfo.setGfSysCode("99370000000");
		gfErrInfo.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		gfErrInfo.setLatestModifyTime(BocoUtils.getNowTime());
		if(gfErrInfo.getOtherSysCode()==null||"".equals(gfErrInfo.getOtherSysCode())){
			gfErrInfo.setOtherSysCode(gfErrInfo.getGfSysCode());
		}
		if(gfErrInfo.getOtherRetCode()==null||"".equals(gfErrInfo.getOtherRetCode())){
			gfErrInfo.setOtherRetCode(gfErrInfo.getGfRetCode());
		}
		if(gfErrInfo.getOtherRetInfo()==null||"".equals(gfErrInfo.getOtherRetInfo())){
			gfErrInfo.setOtherRetInfo(gfErrInfo.getGfRetInfo());
		}
		gfErrInfoService.insertEntity(gfErrInfo);
		return this.json.returnMsg("true", "新增成功!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 修改GF_ERR_INFO.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="修该错误码",funCode="SYS-01-02",funName="修改",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(GfErrInfo gfErrInfo) throws Exception{
		gfErrInfo.setGfSysCode("99370000000");
		gfErrInfo.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		gfErrInfo.setLatestModifyTime(BocoUtils.getNowTime());
		if(gfErrInfo.getOtherSysCode()==null||"".equals(gfErrInfo.getOtherSysCode())){
			gfErrInfo.setOtherSysCode(gfErrInfo.getGfSysCode());
		}
		if(gfErrInfo.getOtherRetCode()==null||"".equals(gfErrInfo.getOtherRetCode())||"99370000000".equals(gfErrInfo.getOtherSysCode())){
			gfErrInfo.setOtherRetCode(gfErrInfo.getGfRetCode());
		}
		if(gfErrInfo.getOtherRetInfo()==null||"".equals(gfErrInfo.getOtherRetInfo())||"99370000000".equals(gfErrInfo.getOtherSysCode())){
			gfErrInfo.setOtherRetInfo(gfErrInfo.getGfRetInfo());
		}
		gfErrInfoService.updateByPK(gfErrInfo);
		return this.json.returnMsg("true", "修改成功!").toJson();
	}

	/**
	 * 
	 *
	 * TODO 删除GF_ERR_INFO
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="删除错误码",funCode="SYS-01-03",funName="删除",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(GfErrInfo gfErrInfo) throws Exception {
		gfErrInfoService.deleteByPK(MapUtil.beanToMap(gfErrInfo));
		return this.json.returnMsg("true", "删除成功!").toJson();
	}
}