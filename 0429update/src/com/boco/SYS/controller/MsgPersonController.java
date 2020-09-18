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
 * Action���Ʋ�  ������Աά��
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/msgPerson/")
public class MsgPersonController extends BaseController{
	@Autowired
	private MsgPersonService msgPersonService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="������Աά��",funCode="SYS-13-2",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/SYS/shortMessage/msgPersonList";
	}

	@RequestMapping("insertUI")
	@SystemLog(tradeName="������Աά��",funCode="SYS-13-2", funName="��������ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/SYS/shortMessage/msgPersonAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="������Աά��",funCode="SYS-13-2", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(MsgPerson msgPerson) throws Exception {
		setAttribute("msg", msgPersonService.selectByPK(msgPerson.getId()));
		return basePath + "/SYS/shortMessage/msgPersonEdit";
	}

	/**
	 * �б�ҳ
	 * @param msgPerson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="������Աά��",funCode="SYS-13-2",funName="�����б�����",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(MsgPerson msgPerson) throws Exception {
		msgPerson.setSortColumn("name");
		setPageParam();
		List<MsgPerson> list = msgPersonService.selectByAttr(msgPerson);
		return pageData(list);
	}

	/**
	 * ����
	 * @param msgPerson
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="������Աά��",funCode="SYS-13-2",funName="����",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(MsgPerson msgPerson, HttpSession session) throws Exception{

	    //У��
        MsgPerson msgParam = new MsgPerson();
        msgParam.setName(msgPerson.getName());
        msgParam.setPhoneNumber(msgPerson.getPhoneNumber());
        List<MsgPerson> msgPersonList = msgPersonService.selectByAttr(msgParam);
        if (msgPersonList != null && msgPersonList.size() > 0) {
        	return this.json.returnMsg("false", "����ʧ�ܣ�����Ա�Ѵ��ڣ�").toJson();
        }

        msgPerson.setId(IDGeneratorUtils.getSequence());
		msgPersonService.insertEntity(msgPerson);
		return this.json.returnMsg("true", "�����ɹ�!").toJson();
	}

	/**
	 * �޸�
	 * @param msgPerson
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="������Աά��",funCode="SYS-13-2",funName="�޸�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(MsgPerson msgPerson, HttpSession session) throws Exception{

		//У��
		MsgPerson msgParam = new MsgPerson();
        msgParam.setName(msgPerson.getName());
        msgParam.setPhoneNumber(msgPerson.getPhoneNumber());
        List<MsgPerson> msgPersonList = msgPersonService.selectByAttr(msgParam);
        if (msgPersonList != null && msgPersonList.size() > 0) {
			for (MsgPerson person : msgPersonList) {
				if (!person.getId().equals(msgPerson.getId())) {
					return this.json.returnMsg("false", "�޸�ʧ�ܣ�����Ա�Ѵ��ڣ�").toJson();
				}
			}
        }

		msgPersonService.updateByPK(msgPerson);
		return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
	}

	/**
	 * ɾ��
	 * @param msgPerson
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="������Աά��",funCode="SYS-13-2",funName="ɾ��",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(MsgPerson msgPerson, HttpSession session) throws Exception {
		msgPersonService.deleteByPK(msgPerson.getId());
		return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
	}
}