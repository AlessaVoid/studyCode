package com.boco.PM.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IFdOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.WebAreaOrganInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.util.JsonUtils;
/**
 * 
 * 
 * FdOrganAction���Ʋ�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/fdOrgan/")
public class FdOrganController extends BaseController{
	@Autowired
	private IFdOrganService fdOrganService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="��������",funCode="system",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/fdOrgan/fdOrganList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="��������",funCode="system",funName="������ϸҳ��",accessType=AccessType.READ,level=Debug.INFO)
	public String infoUI(FdOrgan fdOrgan) throws Exception {
		setAttribute("fdOrgan", fdOrganService.selectByPK(fdOrgan.getThiscode()));
		return basePath + "/fdOrgan/fdOrganInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="��������",funCode="system", funName="��������ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/fdOrgan/fdOrganEdit";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="��������",funCode="system", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.INFO)
	public String updateUI(FdOrgan fdOrgan) throws Exception {
		setAttribute("fdOrgan", fdOrganService.selectByPK(fdOrgan.getThiscode()));
		return basePath + "/fdOrgan/fdOrganEdit";
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯFD_ORGAN��ҳ����
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="��������",funCode="system",funName="�����б�����",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(FdOrgan fdOrgan) throws Exception {
		setPageParam();
		List<FdOrgan> list = fdOrganService.selectByAttr(fdOrgan);
		return pageData(list);
	}

	/**
	 * 
	 *
	 * TODO ����FD_ORGAN-��¼��־.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="��������",funCode="system",funName="����",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(FdOrgan fdOrgan) throws Exception{
		fdOrganService.insertEntity(fdOrgan);
		return this.json.returnMsg("true", "�����ɹ�!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO �޸�FD_ORGAN-��¼��־.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="��������",funCode="system",funName="�޸�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(FdOrgan fdOrgan) throws Exception{
		fdOrganService.updateByPK(fdOrgan);
		return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
	}

	/**
	 * 
	 *
	 * TODO ɾ��FD_ORGAN-��¼��־
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="��������",funCode="system",funName="ɾ��",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(FdOrgan fdOrgan) throws Exception {
		fdOrganService.deleteByPK(fdOrgan.getThiscode());
		return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
	}
	/**
	 * 
	 *
	 * TODO ��ѯһ�����У��齨��ѡ������.
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	    ������   �½�
	 * </pre>
	 */
	@RequestMapping(value = "getProvince")
	@SystemLog(tradeName="��ȡһ������",funCode="system",funName="��ȡһ������",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String getProvince() throws Exception {
		List<FdOrgan> list=fdOrganService.selectProvOrgan();
		
		String type=getParameter("type");
		/*Dzqd������������*/
		String Dzqd=getParameter("Dzqd");
		Map<String, Object> results = new Hashtable<String, Object>();
		List<Map<String,String>> treelist=new ArrayList<Map<String,String>>();
		Map<String,String> treeMap=new HashMap<String,String>();
		treeMap.put("id", "c");
		treeMap.put("parentId", "p");
		treeMap.put("name", "ȫѡ");
		treelist.add(treeMap);
		if (null!=Dzqd) {
			treeMap=new HashMap<String,String>();
			treeMap.put("id", "11005293");
			treeMap.put("parentId", "c");
			treeMap.put("name", "ȫ������");
			treelist.add(treeMap);
			treeMap=new HashMap<String,String>();
			treeMap.put("id", "88888888");
			treeMap.put("parentId", "c");
			treeMap.put("name", "��������");
			treelist.add(treeMap);
		}
		for(FdOrgan info:list){
			treeMap=new HashMap<String,String>();
			treeMap.put("id", info.getThiscode());
			treeMap.put("parentId", "c");
			treeMap.put("name", info.getOrganname());
			if (null!=type) {
				treeMap.put("chkDisabled", "true");
			}
			treelist.add(treeMap);
		}
		
		results.put("treeNodes", treelist);
		return JsonUtils.toJson(results);	
	}
	
}