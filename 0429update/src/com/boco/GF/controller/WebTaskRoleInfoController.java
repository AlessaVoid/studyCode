package com.boco.GF.controller;

import com.boco.GF.service.IWebTaskRoleInfoNewService;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.WebTaskRoleInfo;
import com.boco.SYS.entity.WebTaskRoleInfoNew;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.MapUtil;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping(value = "/webTaskRoleInfo/")
public class WebTaskRoleInfoController extends BaseController {
    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IWebTaskRoleInfoNewService webTaskRoleInfoNewService;

    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/webTaskRoleInfo/webTaskRoleInfoList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        setAttribute("entity", webTaskRoleInfoService.selectByPK(MapUtil.beanToMap(webTaskRoleInfo)));
        return basePath + "/webTaskRoleInfo/webTaskRoleInfoInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/webTaskRoleInfo/webTaskRoleInfoEdit";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        setAttribute("entity", webTaskRoleInfoService.selectByPK(MapUtil.beanToMap(webTaskRoleInfo)));
        return basePath + "/webTaskRoleInfo/webTaskRoleInfoEdit";
    }

    /**
     * TODO ��ѯWEB_TASK_ROLE_INFO��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "�����б�����", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findPage(WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        setPageParam();
        List<WebTaskRoleInfo> list = webTaskRoleInfoService.selectByAttr(webTaskRoleInfo);
        return pageData(list);
    }

    /**
     * TODO ����WEB_TASK_ROLE_INFO.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        webTaskRoleInfoService.insertEntity(webTaskRoleInfo);
        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }

    /**
     * TODO �޸�WEB_TASK_ROLE_INFO.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        webTaskRoleInfoService.updateByPK(webTaskRoleInfo);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

    /**
     * TODO ������������ڵ��ɫ������Ϣ
     *
     * @return
     * @throws Exception <pre>
     *                                                                                           �޸�����        �޸���    �޸�ԭ��
     *                                                                                           2016��6��13��    	   ������  �½�
     *                                                                                           </pre>
     */
    @RequestMapping(value = "updateTaskRoleInfo")
    @SystemLog(tradeName = "����ڵ��ɫ����", funCode = "ACT-01-05", funName = "����ڵ��ɫ����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String updateTaskRoleInfo() throws Exception {
        String gridData = getParameter("gridData");
        String procDefId = getParameter("procDefId");
        List<Map<String, Object>> list = JsonUtils.toList(gridData);
        List<WebTaskRoleInfo> webTaskRoleInfoList = MapUtil.listMapToListBean(list, WebTaskRoleInfo.class);
        webTaskRoleInfoService.updateRoleInfoByBatch(webTaskRoleInfoList, procDefId);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

    /**
     * TODO ά�����̷�����ת
     *
     * @return
     * @throws Exception <pre>
     *                                                                                           �޸�����        �޸���    �޸�ԭ��
     *                                                                                           2016��8��8��    	   ������  �½�
     *                                                                                           </pre>
     */
    @RequestMapping(value = "updateTaskRoleInfoNew")
    @SystemLog(tradeName = "����ڵ��ɫ����", funCode = "ACT-01-06", funName = "����ڵ��ɫ����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String updateTaskRoleInfoNew() throws Exception {
        String gridData = getParameter("gridData");
        String procDefId = getParameter("procDefId");
        List<Map<String, Object>> list = JsonUtils.toList(gridData);
        List<WebTaskRoleInfoNew> webTaskRoleInfoList = MapUtil.listMapToListBean(list, WebTaskRoleInfoNew.class);
        webTaskRoleInfoNewService.updateRoleInfoByBatch(webTaskRoleInfoList, procDefId);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

    /**
     * TODO ɾ��WEB_TASK_ROLE_INFO
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "ɾ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        webTaskRoleInfoService.deleteByPK(MapUtil.beanToMap(webTaskRoleInfo));
        return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
    }
}