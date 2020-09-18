package com.boco.PM.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IWebApproveModelService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebApproveModel;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.util.JsonUtils;
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
@RequestMapping(value = "/webApproveModel/")
public class WebApproveModelController extends BaseController{
	@Autowired
	private IWebApproveModelService webApproveModelService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="���������������",funCode="PM-19",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/webApproveModel/webApproveModelList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="���������������",funCode="PM-19-04",funName="������ϸҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(WebApproveModel webApproveModel) throws Exception {
		setAttribute("webApproveModelDTO", webApproveModelService.selectByPK(webApproveModel.getAppCode()));
		return basePath + "/PM/webApproveModel/webApproveModelInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="���������������",funCode="PM-19-01", funName="��������ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/PM/webApproveModel/webApproveModelAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="���������������",funCode="PM-19-02", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(WebApproveModel webApproveModel) throws Exception {
		setAttribute("webApproveModelDTO", webApproveModelService.selectByPK(webApproveModel.getAppCode()));
		return basePath + "/PM/webApproveModel/webApproveModelEdit";
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯWEB_APPROVE_MODEL��ҳ����
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="���������������",funCode="PM-19",funName="�����б�����",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(WebApproveModel webApproveModel) throws Exception {
		setPageParam();
		List<WebApproveModel> list = webApproveModelService.selectByLikeAttr(webApproveModel);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO ����WEB_APPROVE_MODEL.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="���������������",funCode="PM-19-01",funName="���������������",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(WebApproveModel webApproveModel) throws Exception{
		FdOper fdOper = getSessionUser();
		json = webApproveModelService.insertWebApproveMode(webApproveModel,fdOper);
		return this.json.toJson();
	}
	
	/**
	 * 
	 *
	 * TODO �޸�WEB_APPROVE_MODEL.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="���������������",funCode="PM-19-02",funName="�޸ĳ����������",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(WebApproveModel webApproveModel) throws Exception{
		FdOper fdOper = getSessionUser();
		json = webApproveModelService.updateWebApproveMode(webApproveModel,fdOper);
		return this.json.toJson();
	}

	/**
	 * 
	 *
	 * TODO ɾ��WEB_APPROVE_MODEL
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "deleteApproveModel")
	@SystemLog(tradeName="���������������",funCode="PM-19-03",funName="ɾ�������������",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String deleteApproveModel(WebApproveModel webApproveModel) throws Exception {
		webApproveModelService.deleteByPK(webApproveModel.getAppCode());
		return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO ��ƷԤ�С���Ʒ��ƻ�ȡģ����Ϣ.
	 *
	 * @param webApproveModel
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��31��    	  ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "getWebApproveModel")
	@SystemLog(tradeName="���������������",funCode="SYSTEM",funName="���������������",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String getWebApproveModel(WebApproveModel webApproveModel) throws Exception {
		List<WebApproveModel> list = webApproveModelService.selectByAttr(webApproveModel);
		Map<String,List<Map<String,String>>> result = new HashMap<String,List<Map<String,String>>>();
		List<Map<String,String>> modelList = new ArrayList<Map<String,String>>();
		for(WebApproveModel model : list){
			Map<String,String> map = new HashMap<String,String>();
			map.put("key", model.getAppAdvice());
			map.put("value", model.getAppCode());
			modelList.add(map);
		}
		result.put("list", modelList);
		return JsonUtils.toJson(result);
	}
}