package com.boco.PM.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IFdOrganService;
import com.boco.PM.service.IWebAreaOrganInfoService;
import com.boco.PM.service.impl.FdOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.GfDict;
import com.boco.SYS.entity.WebAreaOrganInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IGfDictService;
import com.boco.SYS.util.MapUtil;
import com.boco.SYS.util.TreeNodeGrid;
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
@RequestMapping(value = "/webAreaOrganInfo/")
public class WebAreaOrganInfoController extends BaseController{
	@Autowired
	private IWebAreaOrganInfoService webAreaOrganInfoService;
	@Autowired
	private IGfDictService gfDictService;
	@Autowired
	private IFdOrganService fdOrganService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="��������",funCode="PM-18",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/webAreaOrganInfo/webAreaOrganInfoList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="��������",funCode="PM-18-04",funName="������ϸҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(WebAreaOrganInfo webAreaOrganInfo) throws Exception {
		WebAreaOrganInfo webAreaOrganInfoDTO = getWebAreaOrganInfo(webAreaOrganInfo,"info");
		setAttribute("webAreaOrganInfoDTO", webAreaOrganInfoDTO);
		return basePath + "/PM/webAreaOrganInfo/webAreaOrganInfoInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="��������",funCode="PM-18-01", funName="��������ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		WebAreaOrganInfo webAreaOrganInfo = new WebAreaOrganInfo();
		String provCode = getOrganList(webAreaOrganInfo,"insert");
		setAttribute("provCode", provCode);
		return basePath + "/PM/webAreaOrganInfo/webAreaOrganInfoAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="��������",funCode="PM-18-02", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(WebAreaOrganInfo webAreaOrganInfo) throws Exception {
		String provCode = getOrganList(webAreaOrganInfo,"update");
		setAttribute("provCode", provCode);
		WebAreaOrganInfo webAreaOrganInfoDTO = getWebAreaOrganInfo(webAreaOrganInfo,"update");
		setAttribute("webAreaOrganInfoDTO", webAreaOrganInfoDTO);
		return basePath + "/PM/webAreaOrganInfo/webAreaOrganInfoEdit";
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯWEB_AREA_ORGAN_INFO��ҳ����
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="��������",funCode="PM-18",funName="�����б�����",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(GfDict gfDict) throws Exception {
		if(!StringUtils.isNotEmpty(gfDict.getDictNo())){
			gfDict.setDictNo("D_AREA_NAME");
		}
		gfDict.setSortColumn("DICT_NO_ORDER");
		gfDict.setStatus("1");
		setPageParam();
		List<GfDict> list = gfDictService.selectByAttr(gfDict);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO ����WEB_AREA_ORGAN_INFO.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="��������",funCode="PM-18-01",funName="����",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(WebAreaOrganInfo webAreaOrganInfo,HttpServletRequest request) throws Exception{
		FdOper fdOper = getSessionUser();
		String organs = request.getParameter("organs");
		json = webAreaOrganInfoService.insertWebAreaOrgan(webAreaOrganInfo,organs,fdOper);
		return this.json.toJson();
	}
	
	/**
	 * 
	 *
	 * TODO �޸�WEB_AREA_ORGAN_INFO.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="��������",funCode="PM-18-02",funName="�޸�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(WebAreaOrganInfo webAreaOrganInfo,HttpServletRequest request) throws Exception{
		FdOper fdOper = getSessionUser();
		String organs = request.getParameter("organs");
		json = webAreaOrganInfoService.updateWebAreaOrgan(webAreaOrganInfo,organs,fdOper);
		return this.json.toJson();
	}

	/**
	 * 
	 *
	 * TODO ɾ��WEB_AREA_ORGAN_INFO
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "deleteWebAreaOrgan")
	@SystemLog(tradeName="��������",funCode="PM-18-03",funName="ɾ��",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String deleteWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo) throws Exception {
		webAreaOrganInfoService.deleteWebAreaOrgan(webAreaOrganInfo);
		return this.json.returnMsg("true", "ɾ���ɹ�").toJson();
	}
	/**
	 * 
	 *
	 * TODO ���������е�һ�ֻ�������.
	 *
	 * @param webAreaOrganInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "getOrganList")
	@SystemLog(tradeName="��������",funCode="PM-18",funName="��ȡ���������е�һ�ֻ���",accessType=AccessType.READ,level=Debug.DEBUG)
	public String getOrganList(WebAreaOrganInfo webAreaOrganInfo,String type) throws Exception {
		String areaHasOrgan = "",otherHasOrgan="";
		List<WebAreaOrganInfo> organList = new ArrayList<WebAreaOrganInfo>();
		//��������ʱû�е������룬��˻�ȡ���ݿ����ѱ�ѡȡ��һ�ֻ���
		if("insert".equals(type)){
			organList = webAreaOrganInfoService.selectByAttr(webAreaOrganInfo);
		}else if("update".equals(type)){
			//�޸ģ���ǰ�������е������룬ͨ�����������ȡ�õ�����ѡ���һ�ֻ���
			List<WebAreaOrganInfo> areaOrganList = webAreaOrganInfoService.selectByAttr(webAreaOrganInfo);
			//��ȡ����ǰ����֮����������ѡȡ��һ�ֻ���
			organList = webAreaOrganInfoService.selectNotEqualOrgan(webAreaOrganInfo.getAreaCode());
			//������ӵ�е�һ�ֻ���ת��Ϊ�ַ�����ʽ
			areaHasOrgan = getHasOrgan(areaOrganList);
		}
		//�������е�һ�ֻ���ת��Ϊ�ַ�����ʽ
		otherHasOrgan = getHasOrgan(organList);
		//��ѯ�������г�'99'��ͷ����������Ϊ1��һ�ֻ���
		List<FdOrgan> fdOrganList = fdOrganService.selectProvOrgan();
		StringBuffer sbuff = new StringBuffer();
		String br = "";
		//��ʽѭ������
		int  count = 0;
		for(int i = 0;i < fdOrganList.size();i++){
			String checked="";
			FdOrgan fdOrgan = fdOrganList.get(i);
			if(otherHasOrgan.indexOf(fdOrgan.getThiscode()) == -1){
				count++;
				if(count%3==0){
					br = "\"<br/>\"+";
				}else{
					br = "";
				}
				if(areaHasOrgan.indexOf(fdOrgan.getThiscode()) != -1){
					checked = "checked='checked'";
				}
				sbuff.append("\"<input type='checkbox' "+ checked+" id='organ-"+ i + "' name='provCode' value='" + fdOrgan.getThiscode() + "'/>\"+\n");
				sbuff.append("\"<label class='hand' for='organ-"+ i + "'>"+ fdOrgan.getOrganname() + "</label>\"+\"&nbsp;&nbsp;&nbsp;\"+"+br+"\n");
			}
		}
		String provCode = sbuff.toString();
		if(StringUtils.isNotEmpty(provCode)){
			provCode = provCode.substring(0,provCode.length() - 2);
		}else{
			provCode = "'��'";
		}
		return provCode;
	}
	/**
	 * 
	 *
	 * TODO �������еĻ�����Ϣƴ���ַ���.
	 *
	 * @param organList
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��5��    	  ����    �½�
	 * </pre>
	 */
	public String getHasOrgan(List<WebAreaOrganInfo> organList){
		String hasOrgan = "";
		if(organList.size() != 0){
			for(WebAreaOrganInfo areaOrgan:organList){
				hasOrgan += areaOrgan.getProvCode() + ",";
			}
			if(!"".equals(hasOrgan)){
				hasOrgan = hasOrgan.substring(0, hasOrgan.length() - 1);
			}
		}
		return hasOrgan;
	}
	/**
	 * 
	 *
	 * TODO ��װ����ʵ��.
	 *
	 * @param webAreaOrganInfo
	 * @param type
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��5��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public WebAreaOrganInfo getWebAreaOrganInfo(WebAreaOrganInfo webAreaOrganInfo,String type) throws Exception{
		WebAreaOrganInfo webAreaOrganInfoDTO = new WebAreaOrganInfo();
		webAreaOrganInfoDTO.setAreaCode(webAreaOrganInfo.getAreaCode());
		webAreaOrganInfoDTO.setAreaName(webAreaOrganInfo.getAreaName());
		String provCode = "",organName="";
		List<WebAreaOrganInfo> list = webAreaOrganInfoService.selectByAttr(webAreaOrganInfo);
		if(list.size() != 0){
			for(WebAreaOrganInfo webAreaOrgan : list){
				provCode += webAreaOrgan.getProvCode() + ",";
//				if("update".equals(type)){
//					provCode += webAreaOrgan.getProvCode() + ",";
//				}
//				else{
//					provCode += webAreaOrgan.getProvCode() + "\n";
//				}
			}
			provCode = provCode.substring(0,provCode.length() - 1);
		}
		//�޸ĵ�ʱ����ʾһ�ֻ������뼯
		if("update".equals(type)){
			webAreaOrganInfoDTO.setProvCode(provCode);
		}else{//�鿴��ϸ��ʱ����ʾ��������
			List<Map<String,String>> organMap = fdOrganService.selectProvName(provCode.split(","));
			if(organMap.size() > 0){
				for(Map<String,String> map : organMap){
					organName += ","+map.get("organname");
				}
			}
			if(organName.length() > 0){
				organName = organName.substring(1);
			}else{
				organName="��";
			}
			webAreaOrganInfoDTO.setProvCode(organName);
		}
		return webAreaOrganInfoDTO;
	}
}