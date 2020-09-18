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
 * Action���Ʋ�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/msgTemplate/")
public class MsgTemplateController extends BaseController{
	@Autowired
	private MsgTemplateService msgTemplateService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="����ģ��",funCode="SYS-13",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/SYS/shortMessage/msgTemplateList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="����ģ��",funCode="SYS-13",funName="������ϸҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(MsgTemplate msgTemplate) throws Exception {
		setAttribute("msg", msgTemplateService.selectByPK(msgTemplate.getId()));
		return basePath + "/SYS/shortMessage/msgTemplateInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="����ģ��",funCode="SYS-13", funName="��������ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/SYS/shortMessage/msgTemplateAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="����ģ��",funCode="SYS-13", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(MsgTemplate msgTemplate) throws Exception {
		setAttribute("msg", msgTemplateService.selectByPK(msgTemplate.getId()));
		return basePath + "/SYS/shortMessage/msgTemplateEdit";
	}

	/**
	 * �����б�����
	 * @param msgTemplate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="����ģ��",funCode="SYS-13",funName="�����б�����",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(MsgTemplate msgTemplate) throws Exception {
		setPageParam();
		List<MsgTemplate> list = msgTemplateService.selectByAttr(msgTemplate);
		return pageData(list);
	}

	/**
	 * ����
	 * @param msgTemplate
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="����ģ��",funCode="SYS-13",funName="����",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(MsgTemplate msgTemplate, HttpSession session) throws Exception{
		//У��
		MsgTemplate template = msgTemplateService.selectByPK(msgTemplate.getId());
		if (template != null) {
			return this.json.returnMsg("false", "����ʧ�ܣ�ģ��ID�Ѵ��ڣ�").toJson();
		}
		msgTemplateService.insertEntity(msgTemplate);
		return this.json.returnMsg("true", "�����ɹ�!").toJson();
	}

	/**
	 * �޸�
	 * @param msgTemplate
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="����ģ��",funCode="SYS-13",funName="�޸�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(MsgTemplate msgTemplate, HttpSession session) throws Exception{
		msgTemplateService.updateByPK(msgTemplate);
		return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
	}

	/**
	 * ɾ��
	 * @param msgTemplate
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="����ģ��",funCode="SYS-13",funName="ɾ��",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(MsgTemplate msgTemplate, HttpSession session) throws Exception {
		msgTemplateService.deleteByPK(msgTemplate.getId());
		return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
	}
}