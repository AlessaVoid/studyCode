package com.boco.SYS.controller;

import com.boco.PM.service.IWebDeptInfoService;
import com.boco.PM.service.IWebOperInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebDeptInfo;
import com.boco.SYS.entity.WebLogInfo;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IWebLogInfoService;
import com.boco.SYS.util.TreeNode;
import com.boco.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ҵ����־��Action���Ʋ�
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/webLogInfo/")
public class WebLogInfoController extends BaseController {
    @Autowired
    private IWebLogInfoService webLogInfoService;
    @Autowired
    private IWebOperInfoService webOperInfoService;
    @Autowired
    private IWebDeptInfoService webDeptInfoService;

    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "ϵͳ��־��ѯ", funCode = "PM-20", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/SYS/webLogInfo/webLogInfoList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "ϵͳ��־��ѯ", funCode = "PM-20-01", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.INFO)
    public String infoUI(WebLogInfo webLogInfo) throws Exception {
        setAttribute("webLogInfoDTO", webLogInfoService.selectByPK(webLogInfo.getId()));
        return basePath + "/SYS/webLogInfo/webLogInfoInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/webLogInfo/webLogInfoEdit";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(WebLogInfo webLogInfo) throws Exception {
        setAttribute("webLogInfo", webLogInfoService.selectByPK(webLogInfo.getId()));
        return basePath + "/webLogInfo/webLogInfoEdit";
    }

    /**
     * TODO ��֤��Ա�Ƿ����쵼.
     *
     * @param webLogInfo
     * @return
     * @throws Exception <pre>
     *                                                                                                                               �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                               2016��3��8��    	  ����    �½�
     *                                                                                                                               </pre>
     */
    @RequestMapping("checkIsLeader")
    @SystemLog(tradeName = "ϵͳ��־��ѯ", funCode = "PM-20", funName = "��֤��Ա�Ƿ����쵼", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String checkIsLeader(WebLogInfo webLogInfo, HttpServletRequest request) throws Exception {
        FdOper fdOper = getSessionUser();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("organCode", fdOper.getOrgancode());
        map.put("operCode", fdOper.getOpercode());
        WebOperInfo webOperInfo = webOperInfoService.selectByPK(map);
        if (webOperInfo == null) {
            json.returnMsg("false", "��û��ά����Ա��Ϣ���޷�����ϵͳ��־��ѯ����");
        }
        return json.toJson();
    }

    /**
     * TODO ��ѯWEB_LOG_INFO��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "ϵͳ��־��ѯ", funCode = "PM-20", funName = "�����б�����", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findPage(WebLogInfo webLogInfo, WebOperInfo webOperInfo, HttpSession session) throws Exception {
        FdOper fdOper = getSessionUser();
        setPageParam();
        webLogInfo.setOrganCode(fdOper.getOrgancode());
        webLogInfo.setOperCode(fdOper.getOpercode());
        List<WebLogInfo> list = webLogInfoService.selectByAttr(webLogInfo);
        return pageData(list);
    }

    /**
     * TODO �жϲ�ѯ����.
     *
     * @param webLogInfo
     * @param fdOper
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��3��8��    	  ����    �½�
     * </pre>
     * @throws Exception
     */
    @RequestMapping(value = "checkSelectData", method = RequestMethod.POST)
    @SystemLog(tradeName = "ϵͳ��־��ѯ", funCode = "PM-20", funName = "У���ѯ����", accessType = AccessType.READ, level = Debug.DEBUG)
    @ResponseBody
    public String checkSelectData(WebLogInfo webLogInfo, FdOper fdOper, HttpSession session) throws Exception {
        fdOper = getSessionUser();
        WebOperInfo webOperInfo = new WebOperInfo();
        HashMap<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("organCode", fdOper.getOrgancode());
        queryMap.put("operCode", fdOper.getOpercode());
        webOperInfo = webOperInfoService.selectByPK(queryMap);
        String operDept = "", upOperDept = "";
        if (webOperInfo != null) {
            HashMap map = new HashMap();
            map.put("organcode", fdOper.getOrgancode());
            WebDeptInfo webDeptInfo = webDeptInfoService.selectByPK(map);
            if (webDeptInfo != null) {
                if (StringUtils.isNotEmpty(webDeptInfo.getUpDeptCode())) {
                    upOperDept = webDeptInfo.getUpDeptCode();
                }
            }
        }

        boolean checkOperDept = webLogInfoService.checkOperDept(webLogInfo, fdOper, operDept, upOperDept);
        if (checkOperDept == false) {
            return json.toJson();
        }

        boolean checkOperCode = webLogInfoService.checkOperCode(webLogInfo, fdOper, operDept, upOperDept);
        if (checkOperCode == false) {
            return json.returnMsg("false", "1").toJson();
        }
        return json.returnMsg("true", "У��ɹ�").toString();
    }


    /**
     * TODO ϵͳ��־��ѯ��ȡ����������Ϣ.
     *
     * @param webDeptInfo
     * @return
     * @throws Exception <pre>
     *                                                                                                                               �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                               2016��3��8��    	  ����    �½�
     *                                                                                                                               </pre>
     */
    @RequestMapping(value = "getWebDeptInfo")
    @SystemLog(tradeName = "ϵͳ��־��ѯ", funCode = "PM-20", funName = "ϵͳ��־��ѯ��ȡ����������Ϣ", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getWebDeptInfo(WebLogInfo webLogInfo) throws Exception {
        Map<String, List<TreeNode>> result = new HashMap<String, List<TreeNode>>();
        List<TreeNode> list = webLogInfoService.getWebDeptInfo();
        result.put("treeNodes", list);
        return JsonUtils.toJson(result);
    }

}