package com.boco.PM.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IWebMsgService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.cache.WebMsgCache;
import com.boco.SYS.entity.WebMsg;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IGfDictService;
import com.boco.util.JsonUtils;

/**
 * 
 * 
 * Action控制层
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29    宁智杰     批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/webMsg/")
public class WebMsgController extends BaseController{
	@Autowired
	private IWebMsgService webMsgService;
	@Autowired
	private IGfDictService gfDictService;
	@Autowired
	private WebMsgCache webMsgCache;
	
	@RequestMapping("listUI")
	@SystemLog(tradeName="交易名称",funCode="PM-25",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/webMsg/webMsgInfoList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="交易名称",funCode="PM-25",funName="加载详细页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(WebMsg webMsg) throws Exception {
		setAttribute("entity", webMsgService.selectByPK(webMsg.getMsgNo()));
		return basePath + "/webMsg/webMsgInfo";
	}
	/**
	 * 
	 *
	 * TODO 查询WEB_MSG分页数据
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="交易名称",funCode="PM-25",funName="加载列表数据",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(WebMsg webMsg) throws Exception {
		setPageParam();
		webMsg.setRepUserCode(getSessionUser().getOpercode());
		List<WebMsg> list = webMsgService.selectByAttr(webMsg);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO 获取首页待办消息列表
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2016-04-23       宁智杰      批量新建
	 * </pre>
	 */
	@RequestMapping("getHomePageWebMsg")
	@SystemLog(tradeName="首页面待办信息列表",funCode="system",funName="首页面加载待办信息列表",accessType=AccessType.READ,level=Debug.DEBUG)
	public  String getHomePageWebMsg() throws Exception {
		String opercode = getSessionUser().getOpercode();
		Map<String, List<WebMsg>> mapList = webMsgService.getHomePageWebMsg(opercode);
		setAttribute("mapList",mapList);
		return basePath + "/layout/ind-iframe";
	}
	/**
	 * 
	 *
	 * TODO 待办消息页面
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2016-04-23       宁智杰      批量新建
	 * </pre>
	 */
	@RequestMapping("getWebMsgList")
	@SystemLog(tradeName="待办消息",funCode="system",funName="加载待办消息页面",accessType=AccessType.READ,level=Debug.INFO)
	public String getWebMsgList() throws Exception {
		String opercode = getSessionUser().getOpercode();
		List<Map<String, String>> list = webMsgService.getWebMsgList(opercode);
		setAttribute("list", list);
		return basePath + "/PM/webMsg/webMsgList";
	}
	/**
	 * 
	 *
	 * TODO 待办消息列表
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2016-04-23       宁智杰      批量新建
	 * </pre>
	 */
	@RequestMapping("getWebMsgInfo")
	@SystemLog(tradeName="待办消息",funCode="system", funName="加载待办列表",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String getWebMsgInfo() throws Exception {
		String msgTypeCode = getParameter("msgTypeCode");
		String opercode = getSessionUser().getOpercode();
		WebMsg webMsg = new WebMsg();
		webMsg.setWebMsgStatus("1");//复核状态||复核状态1-待审批 2-撤销 3-审批通过 4-审批驳回 5-关闭申请
		webMsg.setRepUserCode(opercode);//复核人员
		webMsg.setMsgType(msgTypeCode);//消息类型
		setPageParam();
		List<WebMsg> list = webMsgService.selectByAttr(webMsg);

		//处理机构名称
		for (WebMsg msg : list) {
			if ("11005293".equals(msg.getAppOrganCode())) {
				msg.setAppOrganName("总行");
			}
		}
		return pageData(list);
	}
	
	
	/**
	 * 
	 *
	 * TODO 查询	待办消息记录数
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2016-04-23       宁智杰      批量新建
	 * </pre>
	 */
	@RequestMapping("countByAttr")
	@SystemLog(tradeName="待办消息",funCode="system",funName="获取待办消息数",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String countByAttr() throws Exception {
		//获取在线柜员号
		String opercode = getSessionUser().getOpercode();
		//查询待办信息数
		WebMsg webMsg = new WebMsg();
		webMsg.setWebMsgStatus("1");//复核状态||复核状态1-待审批 2-撤销 3-审批通过 4-审批驳回 5-关闭申请
		webMsg.setRepUserCode(opercode);
		Integer result = webMsgService.countByAttr(webMsg);
		return JsonUtils.toJson(result);
	}
	/**
	 * 
	 *
	 * TODO 关闭申请
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月24日    	   谷立羊  新建
	 * </pre>
	 */
	@RequestMapping("onCloseMsg")
	@SystemLog(tradeName="待办消息",funCode="system",funName="关闭消息",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String onCloseMsg() throws Exception {
		String msgNo=getParameter("msgNo");
		WebMsg webMsg=webMsgService.selectByPK(msgNo);
		webMsg.setWebMsgStatus("5");//复核状态||复核状态1-待审批 2-撤销 3-审批通过 4-审批驳回 5-关闭申请
		webMsgService.updateByPK(webMsg);
		webMsgService.refreshNow();
		return json.returnMsg("true", "关闭成功！").toJson();
	}

	@RequestMapping("onUpdateState")
	@SystemLog(tradeName="待办消息",funCode="system",funName="更改消息状态",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String onUpdateState() throws Exception {
		String msgNo=getParameter("msgNo");
		WebMsg webMsg=webMsgService.selectByPK(msgNo);
		webMsg.setWebMsgStatus("6");//复核状态||复核状态1-待审批 2-撤销 3-审批通过 4-审批驳回 5-关闭申请 6-已读
		webMsgService.updateByPK(webMsg);
		webMsgService.refreshNow();
		return json.returnMsg("true", "消息已关闭！").toJson();
	}
	/**
	 * 
	 *
	 * TODO 从字典表获去刷新时间
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2016-04-23       宁智杰      批量新建
	 * </pre>
	 */
	@RequestMapping("refreshTime")
	@SystemLog(tradeName="待办消息",funCode="system",funName="查询待办消息更新频率",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String refreshTime() throws Exception {
		//去字典表获取时间
		String interval = DicCache.getKeyByName_("后台查询", "D_REFRESH_INTERVAL");
		Double dinterval = Double.valueOf(interval);
		String intervalNew = String.valueOf(dinterval*1000);//将秒转换成毫秒
		return intervalNew;
	}
	
	/**
	 * 
	 *
	 * TODO 查询待办记录数.
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月27日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping("getWebMsgCount")
	@SystemLog(tradeName="待办消息",funCode="system",funName="查询待办记录数",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String getWebMsgCount() throws Exception {
		String repUser = getSessionUser().getOpercode();
		List<Map<String,String>> list = webMsgCache.getRepMap();
		//登录用户的待办消息数
		String count = "0";
		for(Map<String, String> map : list){
			if(StringUtils.equals(repUser, map.get("repuser"))){
				count = String.valueOf(map.get("count"));
				break;
			}
		}
		return count;
	}

}