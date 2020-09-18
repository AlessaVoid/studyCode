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
 * ��ɫ���ܶ��ձ�Action���Ʋ�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/webRoleFun/")
public class WebRoleFunController extends BaseController{
	@Autowired
	private IWebRoleFunService webRoleFunService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="��������",funCode="δ��д",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/webRoleFun/webRoleFunList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="��������",funCode="δ��д",funName="������ϸҳ��",accessType=AccessType.READ,level=Debug.INFO)
	public String infoUI(WebRoleFun webRoleFun) throws Exception {
		setAttribute("webRoleFun", webRoleFunService.selectByPK(MapUtil.beanToMap(webRoleFun)));
		return basePath + "/webRoleFun/webRoleFunInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="��������",funCode="δ��д", funName="��������ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/webRoleFun/webRoleFunEdit";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="��������",funCode="δ��д", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(WebRoleFun webRoleFun) throws Exception {
		setAttribute("webRoleFun", webRoleFunService.selectByPK(MapUtil.beanToMap(webRoleFun)));
		return basePath + "/webRoleFun/webRoleFunEdit";
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯWEB_ROLE_FUN��ҳ����
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="��������",funCode="δ��д",funName="�����б�����",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(WebRoleFun webRoleFun) throws Exception {
		setPageParam();
		List<WebRoleFun> list = webRoleFunService.selectByAttr(webRoleFun);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO ����WEB_ROLE_FUN.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="��������",funCode="δ��д",funName="������ɫ���ܶ��ձ�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(WebRoleFun webRoleFun) throws Exception{
		webRoleFunService.insertEntity(webRoleFun);
		return this.json.returnMsg("true", "�����ɹ�!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO �޸�WEB_ROLE_FUN.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="��������",funCode="δ��д",funName="�޸Ľ�ɫ���ܶ��ձ�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(WebRoleFun webRoleFun) throws Exception{
		webRoleFunService.updateByPK(webRoleFun);
		return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
	}

	/**
	 * 
	 *
	 * TODO ɾ��WEB_ROLE_FUN
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="��������",funCode="δ��д",funName="ɾ����ɫ���ܶ��ձ�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(WebRoleFun webRoleFun) throws Exception {
		webRoleFunService.deleteByPK(MapUtil.beanToMap(webRoleFun));
		return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
	}
}