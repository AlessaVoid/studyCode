package com.boco.PM.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IFdUnichangeinfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdUnichangeinfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.MapUtil;
/**
 * 
 * 
 * FdUnichangeinfoAction���Ʋ�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/fdUnichangeinfo/")
public class FdUnichangeinfoController extends BaseController{
	@Autowired
	private IFdUnichangeinfoService fdUnichangeinfoService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="ҵ��ģ��-��������",funCode="δ��д",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI(Model model) throws Exception {
		authButtons();
		return basePath + "/fdUnichangeinfo/fdUnichangeinfoList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="ҵ��ģ��-��������",funCode="δ��д",funName="������ϸҳ��",accessType=AccessType.READ,level=Debug.INFO)
	public String infoUI(Model model,FdUnichangeinfo fdUnichangeinfo) throws Exception {
		setAttribute("fdUnichangeinfo", fdUnichangeinfoService.selectByPK(MapUtil.beanToMap(fdUnichangeinfo)));
		return basePath + "/fdUnichangeinfo/fdUnichangeinfoInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="ҵ��ģ��-��������",funCode="δ��д", funName="��������ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI(Model model) throws Exception {
		return basePath + "/fdUnichangeinfo/fdUnichangeinfoEdit";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="ҵ��ģ��-��������",funCode="δ��д", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.INFO)
	public String updateUI(Model model,FdUnichangeinfo fdUnichangeinfo) throws Exception {
		setAttribute("fdUnichangeinfo", fdUnichangeinfoService.selectByPK(MapUtil.beanToMap(fdUnichangeinfo)));
		return basePath + "/fdUnichangeinfo/fdUnichangeinfoEdit";
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯFD_UNICHANGEINFO��ҳ����
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="ҵ��ģ��-��������",funCode="δ��д",funName="�����б�����",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(FdUnichangeinfo fdUnichangeinfo) throws Exception {
		setPageParam();
		List<FdUnichangeinfo> list = fdUnichangeinfoService.selectByAttr(fdUnichangeinfo);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO ����FD_UNICHANGEINFO-��¼��־.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="ҵ��ģ��-��������",funCode="δ��д",funName="����FdUnichangeinfo",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(FdUnichangeinfo fdUnichangeinfo) throws Exception{
		fdUnichangeinfoService.insertEntity(fdUnichangeinfo);
		return this.json.returnMsg("true", "�����ɹ�!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO �޸�FD_UNICHANGEINFO-��¼��־.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="ҵ��ģ��-��������",funCode="δ��д",funName="�޸�FdUnichangeinfo",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(FdUnichangeinfo fdUnichangeinfo) throws Exception{
		fdUnichangeinfoService.updateByPK(fdUnichangeinfo);
		return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
	}

	/**
	 * 
	 *
	 * TODO ɾ��FD_UNICHANGEINFO-��¼��־
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="ҵ��ģ��-��������",funCode="δ��д",funName="ɾ��FdUnichangeinfo",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(FdUnichangeinfo fdUnichangeinfo) throws Exception {
		fdUnichangeinfoService.deleteByPK(MapUtil.beanToMap(fdUnichangeinfo));
		return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
	}
}