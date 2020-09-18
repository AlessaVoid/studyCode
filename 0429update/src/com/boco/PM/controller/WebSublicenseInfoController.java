package com.boco.PM.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IWebRoleInfoService;
import com.boco.PM.service.IWebSublicenseInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebRoleInfo;
import com.boco.SYS.entity.WebSublicenseInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.util.JsonUtils;
/**
 * 
 * 
 * WebSublicenseInfoAction���Ʋ�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/webSublicenseInfo/")
public class WebSublicenseInfoController extends BaseController{
	@Autowired
	private IWebSublicenseInfoService webSublicenseInfoService;
	@Autowired
	private IFdOperService fdOperService;
	@Autowired
	private IWebRoleInfoService webRoleInfoService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="ת��Ȩ����",funCode="PM-09",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/webSublicenseInfo/webSublicenseInfoList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="ת��Ȩ����",funCode="PM-09-03",funName="������ϸҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(WebSublicenseInfo webSublicenseInfo) throws Exception {
		setAttribute("webSublicenseInfo", webSublicenseInfoService.selectByPK(webSublicenseInfo.getId()));
		return basePath + "/webSublicenseInfo/webSublicenseInfoInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="��������",funCode="δ��д", funName="��������ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/webSublicenseInfo/webSublicenseInfoEdit";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="��������",funCode="δ��д", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(WebSublicenseInfo webSublicenseInfo) throws Exception {
		setAttribute("webSublicenseInfo", webSublicenseInfoService.selectByPK(webSublicenseInfo.getId()));
		return basePath + "/webSublicenseInfo/webSublicenseInfoEdit";
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯWEB_SUBLICENSE_INFO��ҳ����
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="ת��Ȩ����",funCode="PM-09",funName="�����б�����",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(WebSublicenseInfo webSublicenseInfo) throws Exception {
		setPageParam();
		List<WebSublicenseInfo> list = webSublicenseInfoService.selectByAttr(webSublicenseInfo);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯ˫��ѡ������ת����Աӵ�еĽ�ɫ.
	 *
	 * @param webSublicenseInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��2��    	  ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "findWebSub")
	@SystemLog(tradeName="ת��Ȩ����",funCode="PM-09",funName="����ת��Ȩ����",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findWebSub(WebSublicenseInfo webSublicenseInfo) throws Exception {
		Map<String,List<Map<String,String>>> result = new HashMap<String,List<Map<String,String>>>();
		List<Map<String,String>> fromList = new ArrayList<Map<String,String>>();
		List<Map<String,String>> toList = new ArrayList<Map<String,String>>();
		FdOper fdOper = getSessionUser();
		HashMap keyMap = new HashMap();
		keyMap.put("organcode",fdOper.getOrgancode());
		keyMap.put("opercode",fdOper.getOpercode());
		FdOper outOper = fdOperService.selectByPK(keyMap);
		
		if(outOper != null){
			String roles = outOper.getOperdegree();
			String outRoles = "";
			//��ȡ�Ƿ���ת����ɫ,����еĻ�������Щ��ɫ��ӵ�еĽ�ɫ��ȥ��
			WebSublicenseInfo subInfo  = new WebSublicenseInfo();
			subInfo.setOutOper(outOper.getOpercode());
			subInfo.setIsBack("2");
			//��ת����ɫ
			List<WebSublicenseInfo> subList = webSublicenseInfoService.selectByAttr(subInfo);
			if(subList.size() != 0){
				for(WebSublicenseInfo info : subList){
					//��ת����ɫ��
					roles = roles.replace(info.getRoleCode(), "");
					//���ջؽ�ɫ
					outRoles += info.getRoleCode();
				}
			}
			//��ת����ɫ��
			fromList = this.getSubList(roles);
			//���ջؽ�ɫ��
			if(outRoles.length() > 0){
				toList = this.getSubList(outRoles);
			}
		}
		result.put("fromList", fromList);
		result.put("toList", toList);
		return JsonUtils.toJson(result);
	}
	/**
	 * 
	 *
	 * TODO ��ȡת��Ȩת�룬ת������Ҫ�ļ���.
	 *
	 * @param roles
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��11��    	  ����    �½�
	 * </pre>
	 */
	public List getSubList(String roles) throws Exception{
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(int i = 0; i < roles.length() ; i=i+3){
			Map<String,String> map = new HashMap<String,String>();
			String roleCode = roles.substring(i,i+3);
			WebRoleInfo webRoleInfo = webRoleInfoService.selectByPK(roleCode);
			if(webRoleInfo != null){
				map.put("key", webRoleInfo.getRoleName());
				map.put("value",roleCode);
				list.add(map);
			}
		}
		return list;
	}
	/**
	 * 
	 *
	 * TODO ����ת����Ա��ת���Ա��ȡ��ת��δ�ջ�Ȩ�޵Ľ�ɫ��Ϣ.
	 *
	 * @param webSublicenseInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��2��    	  ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "selectWebSub")
	@SystemLog(tradeName="ת��Ȩ����",funCode="PM-09",funName="����ת��Ȩ����",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String selectWebSub(WebSublicenseInfo webSublicenseInfo,HttpServletRequest request) throws Exception {
		String webSubSelect = "";
		//��֤����Ĺ�Ա�Ƿ����
		if(StringUtils.isNotEmpty(webSublicenseInfo.getInOper())){
			FdOper fdOper = new FdOper();
			fdOper.setOpercode(webSublicenseInfo.getInOper());
			List<FdOper> list = fdOperService.selectByAttr(fdOper);
			if(list.size() == 0){
				return json.returnMsg("false", "ת���Ա�����ڣ����������룡").toJson();
			}
			//��ѯת���ԱΪ�����Ա���ջ�״̬Ϊδ�ջصĽ�ɫ��
			webSublicenseInfo.setIsBack("2");
			List<WebSublicenseInfo> webList = webSublicenseInfoService.selectByAttr(webSublicenseInfo);
			webSubSelect = this.findOutRoles(webList);
		}else{
			WebSublicenseInfo subInfo  = new WebSublicenseInfo();
			subInfo.setOutOper(webSublicenseInfo.getOutOper());
			subInfo.setIsBack("2");
			//��ת����ɫ
			List<WebSublicenseInfo> webList = webSublicenseInfoService.selectByAttr(subInfo);
			webSubSelect = this.findOutRoles(webList);
		}
		return json.returnMsg("true", webSubSelect).toJson();
	}
	
	/**
	 * 
	 *
	 * TODO ת��
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "outWebSub")
	@SystemLog(tradeName="ת��Ȩ����",funCode="PM-09",funName="����WebSublicenseInfo",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String outWebSub(WebSublicenseInfo webSublicenseInfo,HttpServletRequest request) throws Exception{
		String roles = request.getParameter("lister");
		json = webSublicenseInfoService.outWebSub(webSublicenseInfo, roles);
		return this.json.toJson();
	}
	/**
	 * 
	 *
	 * TODO ����Աû�б�ϵͳ��ɫ��ֻ��ת��Ȩ�ı�ϵͳ��ɫʱ�����ת��Ȩ�ᱨ��.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��7��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	@RequestMapping(value = "checkRole")
	@SystemLog(tradeName="ת��Ȩ����-ת��ȨУ�鷽��",funCode="PM-09",funName="ת��ȨУ�鷽��",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String checkRole() throws Exception{
		//��õ�ǰ��Ա�Ľ�ɫ��Ϣ
		FdOper fdOper = getSessionUser();
		FdOper fdOperDTO = new FdOper();
		HashMap map = new HashMap();
		map.put("opercode", fdOper.getOpercode());
		map.put("organcode", fdOper.getOrgancode());
		fdOperDTO = fdOperService.selectByPK(map);
		String role = "";
		if(fdOperDTO != null){
			if(!StringUtils.isNotEmpty(fdOperDTO.getOperdegree())){
				return json.returnMsg("false", "��û�п���ת���Ľ�ɫ�������Խ���ת��Ȩ����").toJson();
			}
		}
		return  json.returnMsg("true", "��֤ͨ��").toJson();
	}
	/**
	 * 
	 *
	 * TODO ��ȡ��ת����ɫ���뼯.
	 *
	 * @param webList
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��6��22��    	    ����    �½�
	 * </pre>
	 */
	public String findOutRoles(List<WebSublicenseInfo> webList){
		String webSubSelect = "";
		StringBuffer sbuff = new StringBuffer();
		if(webList.size() != 0){
			for(WebSublicenseInfo webSub : webList){
				sbuff.append(webSub.getRoleCode() + ",");
			}
		}
		if(sbuff.length() != 0){
			String webSub = sbuff.toString();
			webSubSelect = webSub.substring(0, webSub.length()-1);
		}
		return webSubSelect;
	}
}