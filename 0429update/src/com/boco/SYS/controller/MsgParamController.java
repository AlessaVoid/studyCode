package com.boco.SYS.controller;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.MsgParam;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.MsgParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 
 * 
 * Action控制层 短信参数维护
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/msgParam/")
public class MsgParamController extends BaseController{
	@Autowired
	private MsgParamService msgParamService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="短信参数维护",funCode="SYS-13-1",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/SYS/shortMessage/msgParamList";
	}

	@RequestMapping("updateUI")
	@SystemLog(tradeName="短信参数维护",funCode="SYS-13-1", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(MsgParam msgParam) throws Exception {
		setAttribute("msg", msgParamService.selectByPK(msgParam.getId()));
		return basePath + "/SYS/shortMessage/msgParamEdit";
	}

	/**
	 * 列表页
	 * @param msgParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="短信参数维护",funCode="SYS-13-1",funName="加载列表数据",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(MsgParam msgParam) throws Exception {
		setPageParam();
		List<MsgParam> list = msgParamService.selectByAttr(msgParam);
		return pageData(list);
	}

	/**
	 * 修改
	 * @param msgParam
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="短信参数维护",funCode="SYS-13-1",funName="修改",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(MsgParam msgParam, HttpSession session) throws Exception{
		msgParamService.updateByPK(msgParam);
		return this.json.returnMsg("true", "修改成功!").toJson();
	}


}