package com.boco.PUB.controller.tbCalculateTwo;

import com.alibaba.fastjson.JSON;
import com.boco.PUB.service.tbCalculateTwo.impl.TbCalculateTwoImportDataService;
import com.boco.PUB.service.tbCalculateTwo.impl.TbCalculateTwoResultService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbCalculateTwoResult;
import com.boco.SYS.entity.TbReqList;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.FdOperMapper;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.common.PlainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Author: Liujinbo
 * @Date: 2020/3/2
 * @Description:
 */

@Controller
@RequestMapping(value = "/tbCalculateTwoResult")
public class TbCalculateTwoResultController extends BaseController {

    @Autowired
    private TbCalculateTwoResultService tbCalculateTwoResultService;
    @Autowired
    private TbCalculateTwoImportDataService tbCalculateTwoImportDataService;
    @Autowired
    private FdOrganMapper fdOrganMapper;


    @RequestMapping(value = "/addTbCalculateTwoResult")
    @SystemLog(tradeName = "���ɲ���ģʽһ���", funCode = "PUB-31-05", funName = "���ɲ���ģʽһ���", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String restartParam(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        try {
            tbCalculateTwoResultService.addCalculateOneResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.returnMsg("true", "������ɳɹ���");//�����ֵ����óɹ�
        return json.toJson();
    }

    @RequestMapping("listUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-31-05", funName = "�����б�ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbCalculateTwo/tbCalculateTwoResult/tbCalculateTwoResultList";
    }

    /**
     * ��ѯtb_req_list��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "PUB-01-02", funName = "�����б�����", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String findPage(TbCalculateTwoResult tbCalculateTwoResult) throws Exception {
        setPageParam();
        List<TbCalculateTwoResult> list = tbCalculateTwoResultService.selectMonth();
        return pageData(list);
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-31-05", funName = "������ϸҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String infoUI(TbCalculateTwoResult tbCalculateTwoResult) throws Exception {
        setAttribute("month", tbCalculateTwoResult.getMonth());
        setAttr();
        return basePath + "/PUB/tbCalculateTwo/tbCalculateTwoResult/tbCalculateTwoResultInfo";
    }


    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-31-05", funName = "�����޸�ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String updateUI(TbCalculateTwoResult tbCalculateTwoResult) throws Exception {
        setAttribute("month", tbCalculateTwoResult.getMonth());
        setAttr();
        return basePath + "/PUB/tbCalculateTwo/tbCalculateTwoResult/tbCalculateTwoResultEdit";
    }

    /**
     * ����ҳ����
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getDetailList")
    @SystemLog(tradeName = "�Ŵ�����", funCode = "PUB-31", funName = "���������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanDetailData(String month) throws Exception {
        TbCalculateTwoResult searchTb = new TbCalculateTwoResult();
        searchTb.setMonth(month);
        List<TbCalculateTwoResult> tbCalculateTwoResults = tbCalculateTwoResultService.selectByAttr(searchTb);
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("tbCalculateTwoResults", tbCalculateTwoResults);
        return JSON.toJSONString(resultMap);
    }

    @RequestMapping("/enterUI")
    @SystemLog(tradeName = "���㵼��ҳ��", funCode = "PUB-31-05", funName = "���㵼��ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String enterUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbCalculateTwo/tbCalculateTwoResult/tbCalculateTwoResultEnter";
    }


    //�������ģʽһ������Ҫ������
    @ResponseBody
    @RequestMapping(value = "/enterReport", method = RequestMethod.POST)
    @SystemLog(tradeName = "�����������", funCode = "PUB-31-05", funName = "�����������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String enterReportPlanByMonth(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        Map<String, String> resultMap ;
        try {
            //��ǰ��¼��
            String operCode = getSessionOperInfo().getOperCode();

            resultMap = tbCalculateTwoImportDataService.enterDataByMonth(file, operCode, request);
        } catch (Exception e) {
            e.printStackTrace();
            return this.json.returnMsg("false", "¼��ʧ��,����!").toJson();
        }
        return this.json.returnMsg(resultMap.get("code"), resultMap.get("msg")).toJson();
    }

    /**
     * ͨ�÷���
     *
     * @throws Exception
     */
    private void setAttr() throws Exception {

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //����������������������ʡ����Ǳ�����
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "����Ŵ��ƻ�");
        map1.put("code", "code1");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "���м����");
        map1.put("code", "code2");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "ͳһ�зֶ��");
        map1.put("code", "code3");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "����ĩEVA");
        map1.put("code", "code4");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "���������ĩ�·�������");
        map1.put("code", "code5");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "����ĩ������");
        map1.put("code", "code6");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "����ĩ���ƻ������");
        map1.put("code", "code7");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "����ĩ�����ʱ��ر���");
        map1.put("code", "code8");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "ԭ�ݶ�");
        map1.put("code", "code9");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "�·������ʵ���");
        map1.put("code", "code10");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "�����ʵ���");
        map1.put("code", "code11");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "��Ӫ���ƻ�����ʵ���");
        map1.put("code", "code12");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "�����ʱ��㱨�ʵ���");
        map1.put("code", "code13");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "������ݶ�");
        map1.put("code", "code14");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "�Ż��������");
        map1.put("code", "code15");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "�����ƻ��������");
        map1.put("code", "code16");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "��ԭ�ݶ����");
        map1.put("code", "code17");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "�ݶ��������");
        map1.put("code", "code18");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "���������ƽ�����ƻ������ý��");
        map1.put("code", "code20");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "�ֹ�����ǰ�ƻ�");
        map1.put("code", "code21");
        combAmountNameList.add(map1);
//        map1 = new HashMap<>(2);
//        map1.put("name", "����ֵ");
//        map1.put("code", "code22");
//        combAmountNameList.add(map1);
//        map1 = new HashMap<>(2);
//        map1.put("name", "�����¶ȼƻ����");
//        map1.put("code", "code23");
//        combAmountNameList.add(map1);

        setAttribute("combAmountNameList", combAmountNameList);
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setUporgan(getSessionOrgan().getThiscode());
        List<FdOrgan> organList = fdOrganMapper.selectByAttr2(searchOrgan);
        List<Map<String, String>> combList = new ArrayList<>();
        for (FdOrgan tempFd : organList) {
            Map<String, String> combMap = new HashMap<>(2);
            combMap.put("combCode", tempFd.getThiscode());
            combMap.put("combName", tempFd.getOrganname());
            combList.add(combMap);
        }
        setAttribute("combList", combList);
    }


    /**
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "PUB-31", funName = "�޸�", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request) throws Exception {
        //������Ա
        PlainResult<String> result = new PlainResult<>();
        try {
            tbCalculateTwoResultService.deleleAndInsert(request);
        } catch (Exception e) {
            e.printStackTrace();
            result = result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update  detail error");
        }
        result = result.success("update", "update req detail success.");
        return JSON.toJSONString(result);
    }


}
