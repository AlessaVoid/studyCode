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
 * Action���Ʋ� ���Ų���ά��
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/msgParam/")
public class MsgParamController extends BaseController{
	@Autowired
	private MsgParamService msgParamService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="���Ų���ά��",funCode="SYS-13-1",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/SYS/shortMessage/msgParamList";
	}

	@RequestMapping("updateUI")
	@SystemLog(tradeName="���Ų���ά��",funCode="SYS-13-1", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(MsgParam msgParam) throws Exception {
		setAttribute("msg", msgParamService.selectByPK(msgParam.getId()));
		return basePath + "/SYS/shortMessage/msgParamEdit";
	}

	/**
	 * �б�ҳ
	 * @param msgParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="���Ų���ά��",funCode="SYS-13-1",funName="�����б�����",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(MsgParam msgParam) throws Exception {
		setPageParam();
		List<MsgParam> list = msgParamService.selectByAttr(msgParam);
		return pageData(list);
	}

	/**
	 * �޸�
	 * @param msgParam
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="���Ų���ά��",funCode="SYS-13-1",funName="�޸�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(MsgParam msgParam, HttpSession session) throws Exception{
		msgParamService.updateByPK(msgParam);
		return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
	}


}