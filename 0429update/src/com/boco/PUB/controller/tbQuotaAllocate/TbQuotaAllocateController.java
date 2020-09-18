package com.boco.PUB.controller.tbQuotaAllocate;

import com.alibaba.fastjson.JSON;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.tbQuotaAllocate.TbQuotaAllocateService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbQuotaAllocate;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
@RequestMapping(value = "/tbQuotaAllocate/")
public class TbQuotaAllocateController extends BaseController {
    @Autowired
    private TbQuotaAllocateService tbQuotaAllocateService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IWebLoanCombService loanCombService;


    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "��������", funCode = "SYS-06", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbQuotaAllocate/tbQuotaAllocateIndex";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "��������", funCode = "SYS-06", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbQuotaAllocate tbQuotaAllocate) throws Exception {
        setAttribute("entity", tbQuotaAllocateService.selectByPK(tbQuotaAllocate.getPaId()));
        return basePath + "/PUB/tbQuotaAllocate/tbQuotaAllocateInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��������", funCode = "SYS-06", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PUB/tbQuotaAllocate/tbQuotaAllocateEdit";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "SYS-06", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbQuotaAllocate tbQuotaAllocate) throws Exception {
        TbQuotaAllocate tbQuotaAllocateResult = tbQuotaAllocateService.selectByPK(tbQuotaAllocate.getPaId());
        //��ѯ��������
        HashMap<String, String> combMap = new HashMap<>();
        List<Map<String, Object>> combList = loanCombService.selectComb(new HashMap<>());
        for (Map<String, Object> map : combList) {
            combMap.put(map.get("combcode").toString(), map.get("combname").toString());
        }

        //��������
        FdOrgan fdOrganQuery = new FdOrgan();
        fdOrganQuery.setThiscode(tbQuotaAllocateResult.getPaOrgan());
        FdOrgan fdOrgan = fdOrganService.selectByAttr(fdOrganQuery).get(0);

        setAttribute("tbQuotaAllcate", tbQuotaAllocateResult);
        setAttribute("combName", combMap.get(tbQuotaAllocateResult.getPaProdCode()));
        setAttribute("organName", fdOrgan.getOrganname());
        return basePath + "/PUB/tbQuotaAllocate/tbQuotaAllocateUpdate";
    }

    /*�б�ҳ*/
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(HttpServletRequest request, TbQuotaAllocate tbQuotaAllocate) throws Exception {

        String pageNo = getParameter("pageNo");
        String pageSize = getParameter("pageSize");

        Map<String, Object> jsonMap = tbQuotaAllocateService.getQuotaAllocate(Integer.parseInt(pageNo), Integer.parseInt(pageSize), request, tbQuotaAllocate);

        return JSON.toJSONString(jsonMap);
    }

    /*����*/
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(TbQuotaAllocate tbQuotaAllocate) throws Exception {
        tbQuotaAllocateService.insertEntity(tbQuotaAllocate);
        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }

    /*�޸�*/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(TbQuotaAllocate tbQuotaAllocate) throws Exception {
        tbQuotaAllocateService.updateByPK(tbQuotaAllocate);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

    /*ɾ��*/
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "ɾ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbQuotaAllocate tbQuotaAllocate) throws Exception {
        tbQuotaAllocateService.deleteByPK(tbQuotaAllocate.getPaId());
        return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
    }


    @RequestMapping(value = "selectName")
    @SystemLog(tradeName = "����������������", funCode = "PUB3", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectName() throws Exception {
        String organName = getParameter("organname").replace("'", "");
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setOrganname(organName);
        fdOrgan.setThiscode(organName);
        List<FdOrgan> fdOrganList = fdOrganService.selectByLikeAttr(fdOrgan);

        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        for (FdOrgan organ : fdOrganList) {
            Map<String, String> map = new HashMap<>();
            map.put("key", organ.getOrganname());
            map.put("value", organ.getThiscode());
            list.add(map);
        }
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }
}