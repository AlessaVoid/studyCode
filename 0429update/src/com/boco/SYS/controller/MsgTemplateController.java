package com.boco.SYS.controller;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.MsgPerson;
import com.boco.SYS.entity.MsgTemplate;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.MsgTemplateService;
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
 * Action控制层
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/msgTemplate/")
public class MsgTemplateController extends BaseController{
	@Autowired
	private MsgTemplateService msgTemplateService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="短信模板",funCode="SYS-13",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/SYS/shortMessage/msgTemplateList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="短信模板",funCode="SYS-13",funName="加载详细页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(MsgTemplate msgTemplate) throws Exception {
		setAttribute("msg", msgTemplateService.selectByPK(msgTemplate.getId()));
		return basePath + "/SYS/shortMessage/msgTemplateInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="短信模板",funCode="SYS-13", funName="加载新增页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/SYS/shortMessage/msgTemplateAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="短信模板",funCode="SYS-13", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(MsgTemplate msgTemplate) throws Exception {
		setAttribute("msg", msgTemplateService.selectByPK(msgTemplate.getId()));
		return basePath + "/SYS/shortMessage/msgTemplateEdit";
	}

	/**
	 * 加载列表数据
	 * @param msgTemplate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="短信模板",funCode="SYS-13",funName="加载列表数据",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(MsgTemplate msgTemplate) throws Exception {
		setPageParam();
		List<MsgTemplate> list = msgTemplateService.selectByAttr(msgTemplate);
		return pageData(list);
	}

	/**
	 * 新增
	 * @param msgTemplate
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="短信模板",funCode="SYS-13",funName="新增",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(MsgTemplate msgTemplate, HttpSession session) throws Exception{
		//校验
		MsgTemplate template = msgTemplateService.selectByPK(msgTemplate.getId());
		if (template != null) {
			return this.json.returnMsg("false", "新增失败，模板ID已存在！").toJson();
		}
		msgTemplateService.insertEntity(msgTemplate);
		return this.json.returnMsg("true", "新增成功!").toJson();
	}

	/**
	 * 修改
	 * @param msgTemplate
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="短信模板",funCode="SYS-13",funName="修改",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(MsgTemplate msgTemplate, HttpSession session) throws Exception{
		msgTemplateService.updateByPK(msgTemplate);
		return this.json.returnMsg("true", "修改成功!").toJson();
	}

	/**
	 * 删除
	 * @param msgTemplate
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="短信模板",funCode="SYS-13",funName="删除",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(MsgTemplate msgTemplate, HttpSession session) throws Exception {
		msgTemplateService.deleteByPK(msgTemplate.getId());
		return this.json.returnMsg("true", "删除成功!").toJson();
	}
}