package com.boco.SYS.controller;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.MsgPerson;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.MsgPersonService;
import com.boco.SYS.util.MapUtil;
import com.boco.TONY.utils.IDGeneratorUtils;
import com.sun.deploy.util.GeneralUtil;
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
 * Action控制层  短信人员维护
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/msgPerson/")
public class MsgPersonController extends BaseController{
	@Autowired
	private MsgPersonService msgPersonService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="短信人员维护",funCode="SYS-13-2",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/SYS/shortMessage/msgPersonList";
	}

	@RequestMapping("insertUI")
	@SystemLog(tradeName="短信人员维护",funCode="SYS-13-2", funName="加载新增页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/SYS/shortMessage/msgPersonAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="短信人员维护",funCode="SYS-13-2", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(MsgPerson msgPerson) throws Exception {
		setAttribute("msg", msgPersonService.selectByPK(msgPerson.getId()));
		return basePath + "/SYS/shortMessage/msgPersonEdit";
	}

	/**
	 * 列表页
	 * @param msgPerson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="短信人员维护",funCode="SYS-13-2",funName="加载列表数据",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(MsgPerson msgPerson) throws Exception {
		msgPerson.setSortColumn("name");
		setPageParam();
		List<MsgPerson> list = msgPersonService.selectByAttr(msgPerson);
		return pageData(list);
	}

	/**
	 * 新增
	 * @param msgPerson
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="短信人员维护",funCode="SYS-13-2",funName="新增",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(MsgPerson msgPerson, HttpSession session) throws Exception{

	    //校验
        MsgPerson msgParam = new MsgPerson();
        msgParam.setName(msgPerson.getName());
        msgParam.setPhoneNumber(msgPerson.getPhoneNumber());
        List<MsgPerson> msgPersonList = msgPersonService.selectByAttr(msgParam);
        if (msgPersonList != null && msgPersonList.size() > 0) {
        	return this.json.returnMsg("false", "新增失败，该人员已存在！").toJson();
        }

        msgPerson.setId(IDGeneratorUtils.getSequence());
		msgPersonService.insertEntity(msgPerson);
		return this.json.returnMsg("true", "新增成功!").toJson();
	}

	/**
	 * 修改
	 * @param msgPerson
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="短信人员维护",funCode="SYS-13-2",funName="修改",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(MsgPerson msgPerson, HttpSession session) throws Exception{

		//校验
		MsgPerson msgParam = new MsgPerson();
        msgParam.setName(msgPerson.getName());
        msgParam.setPhoneNumber(msgPerson.getPhoneNumber());
        List<MsgPerson> msgPersonList = msgPersonService.selectByAttr(msgParam);
        if (msgPersonList != null && msgPersonList.size() > 0) {
			for (MsgPerson person : msgPersonList) {
				if (!person.getId().equals(msgPerson.getId())) {
					return this.json.returnMsg("false", "修改失败，该人员已存在！").toJson();
				}
			}
        }

		msgPersonService.updateByPK(msgPerson);
		return this.json.returnMsg("true", "修改成功!").toJson();
	}

	/**
	 * 删除
	 * @param msgPerson
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="短信人员维护",funCode="SYS-13-2",funName="删除",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(MsgPerson msgPerson, HttpSession session) throws Exception {
		msgPersonService.deleteByPK(msgPerson.getId());
		return this.json.returnMsg("true", "删除成功!").toJson();
	}
}