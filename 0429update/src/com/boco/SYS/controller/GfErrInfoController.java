package com.boco.SYS.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.GfErrInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.service.IGfErrInfoService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.MapUtil;
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
@RequestMapping(value = "/gfErrInfo/")
public class GfErrInfoController extends BaseController{
	@Autowired
	private IGfErrInfoService gfErrInfoService;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
		@RequestMapping("listUI")
		@SystemLog(tradeName="������Ϣ��",funCode="SYS-01",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
		public String listUI() throws Exception {
			authButtons();
			return basePath + "/PM/gfErrInfo/gfErrInfoList";
		}
		@RequestMapping("infoUI")
		@SystemLog(tradeName="������Ϣ��",funCode="SYS-01-04",funName="������ϸҳ��",accessType=AccessType.READ,level=Debug.INFO)
		public String infoUI(GfErrInfo gfErrInfo) throws Exception {
			setAttribute("entity", gfErrInfoService.selectByPK(MapUtil.beanToMap(gfErrInfo)));
			return basePath + "/PM/gfErrInfo/gfErrInfoInfo";
		}
		@RequestMapping("insertUI")
		@SystemLog(tradeName="������Ϣ��",funCode="SYS-01-01", funName="��������ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
		public String insertUI() throws Exception {
			return basePath + "/PM/gfErrInfo/gfErrInfoAdd";
		}
		@RequestMapping("updateUI")
		@SystemLog(tradeName="������Ϣ��",funCode="SYS-01-02", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
		public String updateUI(GfErrInfo gfErrInfo) throws Exception {
			setAttribute("entity", gfErrInfoService.selectByPK(MapUtil.beanToMap(gfErrInfo)));
			return basePath + "/PM/gfErrInfo/gfErrInfoEdit";
		}
	
	/**
	 * 
	 *
	 * TODO ��ѯGF_ERR_INFO��ҳ����
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="������Ϣ��",funCode="SYS-01",funName="�����б�����",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(GfErrInfo gfErrInfo) throws Exception {
		setPageParam();
		List<GfErrInfo> list = gfErrInfoService.selectByLikeAttr(gfErrInfo);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO ����GF_ERR_INFO.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="����������",funCode="SYS-01-01",funName="����",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(GfErrInfo gfErrInfo) throws Exception{
		gfErrInfo.setGfSysCode("99370000000");
		gfErrInfo.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		gfErrInfo.setLatestModifyTime(BocoUtils.getNowTime());
		if(gfErrInfo.getOtherSysCode()==null||"".equals(gfErrInfo.getOtherSysCode())){
			gfErrInfo.setOtherSysCode(gfErrInfo.getGfSysCode());
		}
		if(gfErrInfo.getOtherRetCode()==null||"".equals(gfErrInfo.getOtherRetCode())){
			gfErrInfo.setOtherRetCode(gfErrInfo.getGfRetCode());
		}
		if(gfErrInfo.getOtherRetInfo()==null||"".equals(gfErrInfo.getOtherRetInfo())){
			gfErrInfo.setOtherRetInfo(gfErrInfo.getGfRetInfo());
		}
		gfErrInfoService.insertEntity(gfErrInfo);
		return this.json.returnMsg("true", "�����ɹ�!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO �޸�GF_ERR_INFO.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="�޸ô�����",funCode="SYS-01-02",funName="�޸�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(GfErrInfo gfErrInfo) throws Exception{
		gfErrInfo.setGfSysCode("99370000000");
		gfErrInfo.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		gfErrInfo.setLatestModifyTime(BocoUtils.getNowTime());
		if(gfErrInfo.getOtherSysCode()==null||"".equals(gfErrInfo.getOtherSysCode())){
			gfErrInfo.setOtherSysCode(gfErrInfo.getGfSysCode());
		}
		if(gfErrInfo.getOtherRetCode()==null||"".equals(gfErrInfo.getOtherRetCode())||"99370000000".equals(gfErrInfo.getOtherSysCode())){
			gfErrInfo.setOtherRetCode(gfErrInfo.getGfRetCode());
		}
		if(gfErrInfo.getOtherRetInfo()==null||"".equals(gfErrInfo.getOtherRetInfo())||"99370000000".equals(gfErrInfo.getOtherSysCode())){
			gfErrInfo.setOtherRetInfo(gfErrInfo.getGfRetInfo());
		}
		gfErrInfoService.updateByPK(gfErrInfo);
		return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
	}

	/**
	 * 
	 *
	 * TODO ɾ��GF_ERR_INFO
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="ɾ��������",funCode="SYS-01-03",funName="ɾ��",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(GfErrInfo gfErrInfo) throws Exception {
		gfErrInfoService.deleteByPK(MapUtil.beanToMap(gfErrInfo));
		return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
	}
}