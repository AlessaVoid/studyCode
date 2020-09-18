package com.boco.RE.controller;

import com.boco.PM.service.IFdOrganService;
import com.boco.RE.service.TbKeyReportOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbKeyReportOrgan;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.FdOperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * �����ص���
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbKeyReportOrgan/")
public class TbKeyReportOrganController extends BaseController {
    @Autowired
    private TbKeyReportOrganService tbKeyReportOrganService;
    @Autowired
    private IFdOrganService fdOrganService;

    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/RE/reportParamManage/keyCity/list";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/RE/reportParamManage/keyCity/add";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbKeyReportOrgan tbKeyReportOrgan) throws Exception {
        setAttribute("TbKeyReportOrgan", tbKeyReportOrganService.selectByPK(tbKeyReportOrgan.getOrgancode()));
        return basePath + "/RE/reportParamManage/keyCity/edit";
    }

    /**
     * TODO ��ѯtb_key_report_organ��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbKeyReportOrgan tbKeyReportOrgan) throws Exception {
        List<TbKeyReportOrgan> list = tbKeyReportOrganService.selectByAttr(tbKeyReportOrgan);
        return pageData(list);
    }

    /**
     * TODO ����tb_key_report_organ.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(TbKeyReportOrgan tbKeyReportOrgan, HttpSession session) throws Exception {

        if ("11005293".equals(tbKeyReportOrgan.getOrgancode())) {
            return this.json.returnMsg("false", "����ʧ�ܣ������������!").toJson();
        }
        List<TbKeyReportOrgan> fdReportOrgans = tbKeyReportOrganService.selectByAttr(tbKeyReportOrgan);
        if (fdReportOrgans != null && fdReportOrgans.size() > 0) {
            return this.json.returnMsg("false", "����ʧ�ܣ����������ظ�������Ϣ!").toJson();
        }

        FdOrgan fdOrgan = fdOrganService.selectByPK(tbKeyReportOrgan.getOrgancode());
        if (fdOrgan != null) {
            if (Integer.parseInt(fdOrgan.getOrganlevel()) > 2) {
                return this.json.returnMsg("false", "����ʧ�ܣ���ѡ�������!").toJson();
            }
            tbKeyReportOrgan.setOrganlevel(fdOrgan.getOrganlevel());
            tbKeyReportOrgan.setUporgan(fdOrgan.getUporgan());
            FdOrgan fdOrganTemp = fdOrganService.selectByPK(tbKeyReportOrgan.getUporgan());
            tbKeyReportOrgan.setUporganname(fdOrganTemp.getOrganname());
        }

        tbKeyReportOrganService.insertEntity(tbKeyReportOrgan);
        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }

    /**
     * TODO �޸�tb_key_report_organ.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(TbKeyReportOrgan tbKeyReportOrgan, HttpSession session) throws Exception {
        tbKeyReportOrganService.updateByPK(tbKeyReportOrgan);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

    /**
     * TODO ɾ��tb_key_report_organ
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "��������", funCode = "RE", funName = "ɾ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbKeyReportOrgan tbKeyReportOrgan, HttpSession session) throws Exception {
        tbKeyReportOrganService.deleteByPK(tbKeyReportOrgan.getOrgancode());
        return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
    }
}