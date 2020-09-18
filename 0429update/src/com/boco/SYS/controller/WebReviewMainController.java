package com.boco.SYS.controller;

import com.boco.PM.service.IWebReviewMainService;
import com.boco.PM.service.IWebReviewSublistService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.WebReviewMain;
import com.boco.SYS.entity.WebReviewSublist;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Action���Ʋ�
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/webReviewMain/")
public class WebReviewMainController extends BaseController {
    @Autowired
    private IWebReviewMainService webReviewMainService;
    @Autowired
    private IWebReviewSublistService webReviewSublistService;


    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "�����б�", funCode = "PM-17", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PM/webReviewMain/webReviewMainList";
    }

    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("historyListUI")
    @SystemLog(tradeName = "��ʷ��¼", funCode = "PM-16", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String historyListUI() throws Exception {
        authButtons();
        return basePath + "/PM/webReviewMain/webReviewMainHistoryList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "��ʷ��¼", funCode = "PM-16-02", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.INFO)
    public String infoUI(WebReviewMain webReviewMain) throws Exception {
        setAttribute("webReviewMainDTO", webReviewMainService.selectByPK(webReviewMain.getAppNo()));
        return basePath + "/PM/webReviewMain/webReviewMainInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��������", funCode = "system", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/webReviewMain/webReviewMainEdit";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "system", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(WebReviewMain webReviewMain) throws Exception {
        setAttribute("webReviewMain", webReviewMainService.selectByPK(webReviewMain.getAppNo()));
        return basePath + "/webReviewMain/webReviewMainEdit";
    }

    /**
     * TODO ��ѯWEB_REVIEW_MAIN��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "�����б�", funCode = "PM-17", funName = "�����б�����", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findPage(WebReviewMain webReviewMain, HttpServletRequest request) throws Exception {
        String opercode = getSessionUser().getOpercode();//����Ա����
        webReviewMain.setRepUserCode(opercode);//ȡ�Լ����˵��б�
        webReviewMain.setRepStatus("1");//ȡ������
        webReviewMain.setRevocationFlag("2");//ȡ�ǳ���״̬�������¼
        webReviewMain.setSortColumn("APP_DATE DESC,APP_TIME DESC");
        setPageParam();
        List<WebReviewMain> list = webReviewMainService.selectByAttr(webReviewMain);
        return pageData(list);
    }

    /**
     * TODO ��ѯ������ʷ��Ϣ�б�.
     *
     * @param
     * @param
     * @param request
     * @return
     * @throws Exception <pre>
     *                   �޸�����        �޸���    �޸�ԭ��
     *                   2016��2��25��    	  ����    �½�
     *                   </pre>
     */
    @RequestMapping(value = "approvalHistoryList", method = RequestMethod.POST)
    @SystemLog(tradeName = "��ʷ��¼", funCode = "PM-16", funName = "�����б�����", accessType = AccessType.READ, level = Debug.INFO)
    @ResponseBody
    public String approvalHistoryList(WebReviewMain webReviewMain, HttpServletRequest request) throws Exception {
        String opercode = getSessionUser().getOpercode();//����Ա����
        String queryType = request.getParameter("queryType") == null ? "" : request.getParameter("queryType");
        if (queryType.equals("0")) {
            webReviewMain.setAppUser(opercode);//ȡ�Լ�������б�
        } else if (queryType.equals("1")) {
            webReviewMain.setRepUserCode(opercode);//ȡ�Լ����˵��б�
        } else {
            webReviewMain.setDefaultList(opercode);//Ĭ�ϲ�ѯ���û�����͸��˵��б�
        }
        webReviewMain.setSortColumn("APP_DATE DESC,APP_TIME DESC");
        setPageParam();
        List<WebReviewMain> list = webReviewMainService.selectByAttr(webReviewMain);
        return pageData(list);
    }

    /**
     * TODO ��ֵά�������б�.
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                   �޸�����        	�޸���    �޸�ԭ��
     *                   2017��1��3��    ����˧    �½�
     *                   </pre>
     */
    @RequestMapping(value = "NetvalueInfoList")
    @SystemLog(tradeName = "�����б�", funCode = "system", funName = "�����б�����", accessType = AccessType.READ, level = Debug.INFO)
    public String NetvalueInfoList(HttpServletRequest request) throws Exception {
        return basePath + "/PM//webReviewMain/NetvalueInfoList";
    }

    /**
     * TODO ����Id��ѯ������Ϣ��.
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                   �޸�����        �޸���    �޸�ԭ��
     *                   2016��2��25��    	  ����    �½�
     *                   </pre>
     */
    @RequestMapping(value = "seachWebReviewMain")
    @SystemLog(tradeName = "�����б�", funCode = "system", funName = "�����б�����", accessType = AccessType.READ, level = Debug.INFO)
    public String seachApproval(HttpServletRequest request) throws Exception {
        String urlType = request.getParameter("urlType");
        WebReviewMain webReviewMain = new WebReviewMain();
        String appNo = request.getParameter("appNo");//��ȡ���˱��
        webReviewMain = webReviewMainService.selectByPK(appNo);//���ݱ�Ų��Ҹ�����Ϣ

        StringBuffer appData = new StringBuffer();
        //pmjnl_approval_sub����app_data�ֶζ�����¼�洢һ����ŵĸ������ݣ������ݰ�ordernoƴ��
        List<WebReviewSublist> list = webReviewSublistService.selectAppNo(webReviewMain.getAppNo());
        for (WebReviewSublist sub : list) {
            appData.append(sub.getAppData());
        }
        if ("[".equals(appData.substring(0, 1))) {
            Map<String, String> appDataMap = new HashMap<String, String>();
            appDataMap.put("gridData", appData.toString());
            request.setAttribute("appDataMap", appDataMap);//��ӻ�ɾ�������ݣ����޸ĺ������
        } else {
            Map<String, String> appDataMap = JsonUtils.ToMap(appData.toString());//����ɾ������ת��Map��ʽ
            request.setAttribute("appDataMap", appDataMap);//��ӻ�ɾ�������ݣ����޸ĺ������

        }
        Map<String, String> oldDataMap = JsonUtils.ToMap(webReviewMain.getOldData());//������޸Ĳ������޸�ǰ������ת��Map��ʽ

        request.setAttribute("oldDataMap", oldDataMap);//�޸�ǰ������
        request.setAttribute("webReviewMain", webReviewMain);//�����˱�Ŵ���ҳ�棬�����ڴ�����ҳ����ø��˴���������
        if ("check".equals(urlType)) {
            return webReviewMain.getAppUrl();//����ҳ��
        } else {
            //�ر༭ʱ��0��1ת����add,update���������޸�ҳ��ֵһ�£���֤��Action�����ж������޸ĵķ�ʽ����
            if ("0".equals(webReviewMain.getAppType())) {
                request.setAttribute("type", "insert");
            } else if ("1".equals(webReviewMain.getAppType())) {
                request.setAttribute("type", "update");
            }
            return webReviewMain.getReeditUrl();//���±༭ҳ��
        }
    }

    /**
     * TODO ����Id��ѯ������Ϣ��.
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                   �޸�����        �޸���    �޸�ԭ��
     *                   2016��2��25��    	  ����    �½�
     *                   </pre>
     */
    @RequestMapping(value = "seachWebReviewMainInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "��ʷ��¼", funCode = "PM-16", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public String approvalInfo(HttpServletRequest request) throws Exception {
        WebReviewMain webReviewMain = new WebReviewMain();
        String appNo = request.getParameter("appNo");//��ȡ���˱��
        webReviewMain = webReviewMainService.selectByPK(appNo);//���ݱ�Ų��Ҹ���
        request.setAttribute("webReviewMain", webReviewMain);//�����˱�Ŵ���ҳ�棬�����ڴ�����ҳ����ø��˴���������
        return "../system/PM/webReviewMain/webReviewMainInfo";
    }

    /**
     * TODO �޸ĸ���״̬,������ر�.
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                   �޸�����        �޸���    �޸�ԭ��
     *                   2016��2��25��    	  ����    �½�
     *                   </pre>
     */
    @RequestMapping(value = "updateWebReviewMainInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "��ʷ��¼", funCode = "PM-16", funName = "�����б�����", accessType = AccessType.WRITE, level = Debug.INFO)
    @ResponseBody
    public String updateWebReviewMainInfo(HttpServletRequest request) throws Exception {
        String appNo = request.getParameter("appNo");//�������
        String type = request.getParameter("type");//�޸����ͣ�revocation:����;close:�ر�
        json = webReviewMainService.updateWebReviewMain(appNo, type);
        return json.toJson();
    }

    /**
     * TODO ����WEB_REVIEW_MAIN.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "��������", funCode = "system", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(WebReviewMain webReviewMain) throws Exception {
        webReviewMainService.insertEntity(webReviewMain);
        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }

    /**
     * TODO �޸�WEB_REVIEW_MAIN.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "system", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(WebReviewMain webReviewMain) throws Exception {
        webReviewMainService.updateByPK(webReviewMain);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

    /**
     * TODO ɾ��WEB_REVIEW_MAIN
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "��������", funCode = "system", funName = "ɾ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(WebReviewMain webReviewMain) throws Exception {
        webReviewMainService.deleteByPK(webReviewMain.getAppNo());
        return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
    }

    /**
     * TODO ��ȡ������Ϣ��ʾ
     *
     * @return
     * @throws Exception <pre>
     *                   �޸�����        �޸���    �޸�ԭ��
     *                   2016��12��9��    	   ������  �½�
     *                   </pre>
     */
    @RequestMapping(value = "getReviewMsgs")
    @SystemLog(tradeName = "��������", funCode = "system", funName = "������Ϣ��ʾ", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getReviewMsgs() throws Exception {
        List<WebReviewMain> list = webReviewMainService.getReviewMsgs();
        return this.json.returnMsg("true", JsonUtils.toJson(list)).toJson();
    }

    /**
     * TODO ��ȡ��ֵά����ʾ��Ϣ
     *
     * @return
     * @throws Exception <pre>
     *                   �޸�����        �޸���   	 �޸�ԭ��
     *                   2016��12��29��    ����˧  �½�
     *                   </pre>
     */
    @RequestMapping(value = "getLeftReviewMsgs")
    @SystemLog(tradeName = "��������", funCode = "system", funName = "��ֵά����Ϣ��ʾ", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getLeftReviewMsgs() throws Exception {
//		List<GfProdNetvalueInfo> list = gfProdNetvalueInfoService.getTips();
        return this.json.returnMsg("true", JsonUtils.toJson("")).toJson();
    }

//    /**
//     * TODO ��ȡ����ά����ʾ��Ϣ
//     *
//     * @return
//     * @throws Exception <pre>
//     *                   �޸�����        �޸���   	 �޸�ԭ��
//     *                   2016��12��29��    ����˧  �½�
//     *                   </pre>
//     */
//    @RequestMapping(value = "getExchangeRate")
//    @SystemLog(tradeName = "��������", funCode = "system", funName = "����ά����Ϣ��ʾ", accessType = AccessType.READ, level = Debug.DEBUG)
//    public @ResponseBody
//    String getExchangeRate() throws Exception {
//        List<GfProdBaseInfo> list = webReviewMainService.getExchangeRate();
//        return this.json.returnMsg("true", JsonUtils.toJson(list)).toJson();
//    }
}