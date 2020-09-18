package com.boco.PM.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IWebMsgService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.cache.WebMsgCache;
import com.boco.SYS.entity.WebMsg;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IGfDictService;
import com.boco.util.JsonUtils;

/**
 * 
 * 
 * Action���Ʋ�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29    ���ǽ�     �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/webMsg/")
public class WebMsgController extends BaseController{
	@Autowired
	private IWebMsgService webMsgService;
	@Autowired
	private IGfDictService gfDictService;
	@Autowired
	private WebMsgCache webMsgCache;
	
	@RequestMapping("listUI")
	@SystemLog(tradeName="��������",funCode="PM-25",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/webMsg/webMsgInfoList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="��������",funCode="PM-25",funName="������ϸҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(WebMsg webMsg) throws Exception {
		setAttribute("entity", webMsgService.selectByPK(webMsg.getMsgNo()));
		return basePath + "/webMsg/webMsgInfo";
	}
	/**
	 * 
	 *
	 * TODO ��ѯWEB_MSG��ҳ����
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="��������",funCode="PM-25",funName="�����б�����",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(WebMsg webMsg) throws Exception {
		setPageParam();
		webMsg.setRepUserCode(getSessionUser().getOpercode());
		List<WebMsg> list = webMsgService.selectByAttr(webMsg);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO ��ȡ��ҳ������Ϣ�б�
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2016-04-23       ���ǽ�      �����½�
	 * </pre>
	 */
	@RequestMapping("getHomePageWebMsg")
	@SystemLog(tradeName="��ҳ�������Ϣ�б�",funCode="system",funName="��ҳ����ش�����Ϣ�б�",accessType=AccessType.READ,level=Debug.DEBUG)
	public  String getHomePageWebMsg() throws Exception {
		String opercode = getSessionUser().getOpercode();
		Map<String, List<WebMsg>> mapList = webMsgService.getHomePageWebMsg(opercode);
		setAttribute("mapList",mapList);
		return basePath + "/layout/ind-iframe";
	}
	/**
	 * 
	 *
	 * TODO ������Ϣҳ��
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2016-04-23       ���ǽ�      �����½�
	 * </pre>
	 */
	@RequestMapping("getWebMsgList")
	@SystemLog(tradeName="������Ϣ",funCode="system",funName="���ش�����Ϣҳ��",accessType=AccessType.READ,level=Debug.INFO)
	public String getWebMsgList() throws Exception {
		String opercode = getSessionUser().getOpercode();
		List<Map<String, String>> list = webMsgService.getWebMsgList(opercode);
		setAttribute("list", list);
		return basePath + "/PM/webMsg/webMsgList";
	}
	/**
	 * 
	 *
	 * TODO ������Ϣ�б�
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2016-04-23       ���ǽ�      �����½�
	 * </pre>
	 */
	@RequestMapping("getWebMsgInfo")
	@SystemLog(tradeName="������Ϣ",funCode="system", funName="���ش����б�",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String getWebMsgInfo() throws Exception {
		String msgTypeCode = getParameter("msgTypeCode");
		String opercode = getSessionUser().getOpercode();
		WebMsg webMsg = new WebMsg();
		webMsg.setWebMsgStatus("1");//����״̬||����״̬1-������ 2-���� 3-����ͨ�� 4-�������� 5-�ر�����
		webMsg.setRepUserCode(opercode);//������Ա
		webMsg.setMsgType(msgTypeCode);//��Ϣ����
		setPageParam();
		List<WebMsg> list = webMsgService.selectByAttr(webMsg);

		//�����������
		for (WebMsg msg : list) {
			if ("11005293".equals(msg.getAppOrganCode())) {
				msg.setAppOrganName("����");
			}
		}
		return pageData(list);
	}
	
	
	/**
	 * 
	 *
	 * TODO ��ѯ	������Ϣ��¼��
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2016-04-23       ���ǽ�      �����½�
	 * </pre>
	 */
	@RequestMapping("countByAttr")
	@SystemLog(tradeName="������Ϣ",funCode="system",funName="��ȡ������Ϣ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String countByAttr() throws Exception {
		//��ȡ���߹�Ա��
		String opercode = getSessionUser().getOpercode();
		//��ѯ������Ϣ��
		WebMsg webMsg = new WebMsg();
		webMsg.setWebMsgStatus("1");//����״̬||����״̬1-������ 2-���� 3-����ͨ�� 4-�������� 5-�ر�����
		webMsg.setRepUserCode(opercode);
		Integer result = webMsgService.countByAttr(webMsg);
		return JsonUtils.toJson(result);
	}
	/**
	 * 
	 *
	 * TODO �ر�����
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��24��    	   ������  �½�
	 * </pre>
	 */
	@RequestMapping("onCloseMsg")
	@SystemLog(tradeName="������Ϣ",funCode="system",funName="�ر���Ϣ",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String onCloseMsg() throws Exception {
		String msgNo=getParameter("msgNo");
		WebMsg webMsg=webMsgService.selectByPK(msgNo);
		webMsg.setWebMsgStatus("5");//����״̬||����״̬1-������ 2-���� 3-����ͨ�� 4-�������� 5-�ر�����
		webMsgService.updateByPK(webMsg);
		webMsgService.refreshNow();
		return json.returnMsg("true", "�رճɹ���").toJson();
	}

	@RequestMapping("onUpdateState")
	@SystemLog(tradeName="������Ϣ",funCode="system",funName="������Ϣ״̬",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String onUpdateState() throws Exception {
		String msgNo=getParameter("msgNo");
		WebMsg webMsg=webMsgService.selectByPK(msgNo);
		webMsg.setWebMsgStatus("6");//����״̬||����״̬1-������ 2-���� 3-����ͨ�� 4-�������� 5-�ر����� 6-�Ѷ�
		webMsgService.updateByPK(webMsg);
		webMsgService.refreshNow();
		return json.returnMsg("true", "��Ϣ�ѹرգ�").toJson();
	}
	/**
	 * 
	 *
	 * TODO ���ֵ���ȥˢ��ʱ��
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2016-04-23       ���ǽ�      �����½�
	 * </pre>
	 */
	@RequestMapping("refreshTime")
	@SystemLog(tradeName="������Ϣ",funCode="system",funName="��ѯ������Ϣ����Ƶ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String refreshTime() throws Exception {
		//ȥ�ֵ���ȡʱ��
		String interval = DicCache.getKeyByName_("��̨��ѯ", "D_REFRESH_INTERVAL");
		Double dinterval = Double.valueOf(interval);
		String intervalNew = String.valueOf(dinterval*1000);//����ת���ɺ���
		return intervalNew;
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯ�����¼��.
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��27��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping("getWebMsgCount")
	@SystemLog(tradeName="������Ϣ",funCode="system",funName="��ѯ�����¼��",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String getWebMsgCount() throws Exception {
		String repUser = getSessionUser().getOpercode();
		List<Map<String,String>> list = webMsgCache.getRepMap();
		//��¼�û��Ĵ�����Ϣ��
		String count = "0";
		for(Map<String, String> map : list){
			if(StringUtils.equals(repUser, map.get("repuser"))){
				count = String.valueOf(map.get("count"));
				break;
			}
		}
		return count;
	}

}