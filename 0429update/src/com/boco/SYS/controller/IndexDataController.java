package com.boco.SYS.controller;

import com.alibaba.fastjson.JSON;
import com.boco.RE.service.IReportService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.global.Dic;
import com.boco.SYS.util.Constant;
import com.boco.TONY.common.PlainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * ϵͳ��ҳ��Ϣ
 * @Author zhuhongjiang
 * @Date 2019/12/18 ����5:25
 **/
@Controller
@RequestMapping(value = "/indexData")
public class IndexDataController extends BaseController {

    @Autowired
    private IReportService reportService;

    /**
     * ajax�ж��Ƿ���ʾ��ҳ��Ϣ
     * @param request
     * @param response
     */
    @RequestMapping(value = "/isShowIndexData")
    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "��ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String isShowIndexData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // ���л��������Բ鿴��ҳͼ
        Boolean isShow = true;
        // try {
        //     //���С�һ�����У��ɲ鿴��ҳ��Ϣ
        //     String organlevel = getSessionOrgan().getOrganlevel();
        //     if(Constant.ORGAN_LEVEL_0.equals(organlevel) || Constant.ORGAN_LEVEL_1.equals(organlevel)){
        //         isShow = true;
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        return JSON.toJSONString(isShow);
    }

    /**
     * ��ҳ
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toIndex")
    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "��ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("organlevel", getSessionOrgan().getOrganlevel());
        return "/system/index/indexData";
    }

    /**
     * ��ȡ�������¾�������Ϣ������ͼ��
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getIndexOrganBarInfo")
    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "�������¾�����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getIndexOrganBarInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<List<Map<String, Object>>> result = new PlainResult<>();
        try {
            //��ǰ��¼������Ϣ
            FdOrgan fdOrgan = getSessionOrgan();
            //��ǰ��¼������Ϣ - ����
            String organlevel = fdOrgan.getOrganlevel();
            //��ǰ��¼������Ϣ - ��������
            String organCode = fdOrgan.getThiscode();
            //��ǰ��¼������Ϣ - ��������
            String organName = fdOrgan.getOrganname();
            //��ǰ��¼��Ա��Ϣ - ��Ա����
            String operCode = getSessionUser().getOpercode();

            List<Map<String, Object>> resultList = reportService.getIndexOrganBarInfo(organlevel, organCode, organName, operCode);
            result.success(resultList, "getIndexOrganBarInfo success.");
        } catch (Exception e) {
            result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "getIndexOrganBarInfo.");
        }
        return JSON.toJSONString(result);
    }

    /**
     * �������ֱ��¾�����������ͼ��
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getIndexCombBarInfo")
    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "�������¾�����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getIndexCombBarInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<List<Map<String, Object>>> result = new PlainResult<>();
        try {
            //��ǰ��¼������Ϣ
            FdOrgan fdOrgan = getSessionOrgan();
            //��ǰ��¼������Ϣ - ����
            String organlevel = fdOrgan.getOrganlevel();
            //��ǰ��¼������Ϣ - ��������
            String organCode = fdOrgan.getThiscode();
            //��ǰ��¼��Ա��Ϣ - ��Ա����
            String operCode = getSessionUser().getOpercode();

            List<Map<String, Object>> resultList = reportService.getIndexCombBarInfo(organlevel, organCode, operCode);
            result.success(resultList, "getIndexCombBarInfo success.");
        } catch (Exception e) {
            result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "getIndexCombBarInfo.");
        }
        return JSON.toJSONString(result);
    }

    /**
     * �������ֱ��¼ƻ�����ʣ�����ͼ��
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getIndexCombLineInfo")
    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "�������¾�����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getIndexCombLineInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<List<Map<String, Object>>> result = new PlainResult<>();
        try {
            //��ǰ��¼������Ϣ
            FdOrgan fdOrgan = getSessionOrgan();
            //��ǰ��¼������Ϣ - ����
            String organlevel = fdOrgan.getOrganlevel();
            //��ǰ��¼������Ϣ - ��������
            String organCode = fdOrgan.getThiscode();
            //��ǰ��¼��Ա��Ϣ - ��Ա����
            String operCode = getSessionUser().getOpercode();

            List<Map<String, Object>> resultList = reportService.getIndexCombLineInfo(organlevel, organCode, operCode);
            result.success(resultList, "getIndexCombLineInfo success.");
        } catch (Exception e) {
            result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "getIndexCombLineInfo.");
        }
        return JSON.toJSONString(result);
    }

    /**
     * ��ҳԤ���ߣ�����ʣ�
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getIndexWarnCompleteInfo")
    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "Ԥ���������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getIndexWarnCompleteInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<List<Map<String, Object>>> result = new PlainResult<>();
        try {
            //��ǰ��¼������Ϣ
            FdOrgan fdOrgan = getSessionOrgan();
            //��ǰ��¼������Ϣ - ����
            String organlevel = fdOrgan.getOrganlevel();
            //��ǰ��¼������Ϣ - ��������
            String organCode = fdOrgan.getThiscode();
            //��ǰ��¼��Ա��Ϣ - ��Ա����
            String operCode = getSessionUser().getOpercode();

            List<Map<String, Object>> resultList = reportService.getIndexWarnCompleteInfo(organlevel, organCode, operCode);
            result.success(resultList, "getIndexWarnCompleteInfo success.");
        } catch (Exception e) {
            result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "getIndexWarnCompleteInfo.");
        }
        return JSON.toJSONString(result);
    }

    /**
     * ��ҳԤ���ߣ�����ֵ��
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getIndexWarnAbsInfo")
    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "Ԥ���߾���ֵ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getIndexWarnAbsInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<List<Map<String, Object>>> result = new PlainResult<>();
        try {
            //��ǰ��¼������Ϣ
            FdOrgan fdOrgan = getSessionOrgan();
            //��ǰ��¼������Ϣ - ����
            String organlevel = fdOrgan.getOrganlevel();
            //��ǰ��¼������Ϣ - ��������
            String organCode = fdOrgan.getThiscode();
            //��ǰ��¼��Ա��Ϣ - ��Ա����
            String operCode = getSessionUser().getOpercode();

            List<Map<String, Object>> resultList = reportService.getIndexWarnAbsInfo(organlevel, organCode, operCode);
            result.success(resultList, "getIndexWarnAbsInfo success.");
        } catch (Exception e) {
            result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "getIndexWarnAbsInfo.");
        }
        return JSON.toJSONString(result);
    }
}
