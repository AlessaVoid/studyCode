package com.boco.PM.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.boco.PM.service.IWebImpTemplateService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.WebImpTemplate;
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
@RequestMapping(value = "/webImpTemplate/")
public class WebImpTemplateController extends BaseController{
	@Autowired
	private IWebImpTemplateService webImpTemplateService;
	
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="ģ�����",funCode="PM-21",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/webImpTemplate/webImpTemplateList";
	}
	@RequestMapping("uploadUI")
	@SystemLog(tradeName="ģ�����",funCode="PM-21-01", funName="�����ϴ�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/PM/webImpTemplate/webImpTemplateEdit";
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯWEB_IMP_TEMPLATE��ҳ����
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="ģ�����",funCode="PM-21",funName="�����б�����",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(WebImpTemplate webImpTemplate) throws Exception {
		setPageParam();
		List<WebImpTemplate> list = webImpTemplateService.selectByLikeAttr(webImpTemplate);
		return pageData(list);
	}
	/**
	 * 
	 *
	 * TODO �ϴ�ģ��.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��03��10��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "upLoad")
	@SystemLog(tradeName="ģ�����",funCode="PM-21-01",funName="�ϴ�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String upLoad(@RequestParam("paramXsl") CommonsMultipartFile paramXsl,WebImpTemplate webImpTemplate,HttpServletRequest request,HttpServletResponse response) throws Exception{
		json = webImpTemplateService.upLoad(paramXsl,webImpTemplate,request,response);
		return json.toJson();
	}
	/**
	 * 
	 *
	 * TODO ����ģ��.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��03��10��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "download")
	@SystemLog(tradeName="ģ�����",funCode="PM-21-02",funName="����",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody void download(WebImpTemplate webImpTemplate,HttpServletRequest request,HttpServletResponse response) throws Exception{
		webImpTemplateService.download(webImpTemplate,request,response);
	}

	/**
	 * 
	 *
	 * TODO ɾ��ģ����Ϣ
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "deleteImpTemplate")
	@SystemLog(tradeName="ģ�����",funCode="PM-21-03",funName="ɾ��",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String deleteImpTemplate(WebImpTemplate webImpTemplate,HttpServletRequest request,HttpServletResponse response) throws Exception {
		json = webImpTemplateService.deleteImpTemplate(webImpTemplate,request,response);
		return  json.toJson();
	}
}