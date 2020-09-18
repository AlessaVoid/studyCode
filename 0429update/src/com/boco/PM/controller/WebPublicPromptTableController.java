package com.boco.PM.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IWebPublicPromptTableService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.WebPublicPromptTable;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
/**
 * 
 * 
 * Action���Ʋ�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/webPublicPromptTable/")
public class WebPublicPromptTableController extends BaseController{
	@Autowired
	private IWebPublicPromptTableService webPublicPromptTableService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="��������",funCode="PM-05",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/webPublicPromptTable/webPublicPromptTableList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="��������",funCode="PM-05-04",funName="������ϸҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(WebPublicPromptTable webPublicPromptTable) throws Exception {
		setAttribute("entity", webPublicPromptTableService.selectByPK(webPublicPromptTable.getId()));
		return basePath + "/PM/webPublicPromptTable/webPublicPromptTableInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="��������",funCode="PM-05-01", funName="��������ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/PM/webPublicPromptTable/webPublicPromptTableAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="��������",funCode="PM-05-02", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(WebPublicPromptTable webPublicPromptTable) throws Exception {
		setAttribute("entity", webPublicPromptTableService.selectByPK(webPublicPromptTable.getId()));
		return basePath + "/PM/webPublicPromptTable/webPublicPromptTableEdit";
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯWEB_PUBLIC_PROMPT_TABLE��ҳ����
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="��������",funCode="PM-05",funName="�����б�����",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(WebPublicPromptTable webPublicPromptTable) throws Exception {
		setPageParam();
		List<WebPublicPromptTable> list = webPublicPromptTableService.selectByAttr(webPublicPromptTable);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO ����WEB_PUBLIC_PROMPT_TABLE.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="��������",funCode="PM-05-01",funName="����",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(WebPublicPromptTable webPublicPromptTable) throws Exception{
		webPublicPromptTableService.saveInfo(webPublicPromptTable);
		return this.json.returnMsg("true", "�����ɹ�!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO �޸�WEB_PUBLIC_PROMPT_TABLE.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="��������",funCode="PM-05-02",funName="�޸�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(WebPublicPromptTable webPublicPromptTable) throws Exception{
		webPublicPromptTableService.updateByPK(webPublicPromptTable);
		return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
	}

	/**
	 * 
	 *
	 * TODO ɾ��WEB_PUBLIC_PROMPT_TABLE
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="��������",funCode="PM-05-03",funName="ɾ��",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(WebPublicPromptTable webPublicPromptTable) throws Exception {
		webPublicPromptTableService.deleteByPK(webPublicPromptTable.getId());
		webPublicPromptTableService.refresh(webPublicPromptTable);
		return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
	}
	/**
	 * 
	 *
	 * TODO ����ˢ��
	 *
	 * @param webPublicPromptTable
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��11��21��    	   ������  �½�
	 * </pre>
	 */
	@RequestMapping(value = "refresh")
	@SystemLog(tradeName="��������",funCode="PM-05-05",funName="ˢ��",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String refresh(WebPublicPromptTable webPublicPromptTable) throws Exception {
		webPublicPromptTableService.refresh(webPublicPromptTable);
		return this.json.returnMsg("true", "�����ɹ�,ˢ��ҳ�����Ч!").toJson();
	}
	
}