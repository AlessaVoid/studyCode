package com.boco.RE.controller;

import com.boco.RE.service.FdReportOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdReportOrgan;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * ��������
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/fdReportOrgan/")
public class FdReportOrganController extends BaseController {
    @Autowired
    private FdReportOrganService fdReportOrganService;

    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/RE/reportParamManage/organRegion/list";

    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/RE/reportParamManage/organRegion/add";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(FdReportOrgan fdReportOrgan) throws Exception {
        setAttribute("FdReportOrgan", fdReportOrganService.selectByPK(fdReportOrgan.getOrgancode()));
        return basePath + "/RE/reportParamManage/organRegion/edit";
    }

    /**
     * TODO ��ѯfd_report_organ��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(FdReportOrgan fdReportOrgan) throws Exception {
        List<FdReportOrgan> list = fdReportOrganService.selectByAttr(fdReportOrgan);
        return pageData(list);
    }

    /**
     * TODO ����fd_report_organ.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(FdReportOrgan fdReportOrgan, HttpSession session) throws Exception {

        List<FdReportOrgan> fdReportOrgans = fdReportOrganService.selectByAttr(fdReportOrgan);
        if (fdReportOrgans != null && fdReportOrgans.size() > 0) {
            return this.json.returnMsg("false", "����ʧ�ܣ����������ظ�������Ϣ!").toJson();
        }

        fdReportOrganService.insertEntity(fdReportOrgan);
        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }

    /**
     * TODO �޸�fd_report_organ.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(FdReportOrgan fdReportOrgan, HttpSession session) throws Exception {
        fdReportOrganService.updateByPK(fdReportOrgan);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

    /**
     * TODO ɾ��fd_report_organ
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "ɾ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(FdReportOrgan fdReportOrgan, HttpSession session) throws Exception {
        fdReportOrganService.deleteByPK(fdReportOrgan.getOrgancode());
        return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
    }
}