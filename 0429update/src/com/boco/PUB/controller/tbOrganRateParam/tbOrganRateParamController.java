package com.boco.PUB.controller.tbOrganRateParam;

import com.alibaba.fastjson.JSON;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.POJO.DO.OrganRateParamElementType;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateParamService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreMonthDetailService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbOrganRateParam;
import com.boco.SYS.global.Dic;
import com.boco.TONY.common.PlainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @Author: Liujinbo
 * @Date: 2020/2/10
 * @Description:   ���ֲ���
 */
@Controller
@RequestMapping("/tbOrganRateParam")
public class tbOrganRateParamController extends BaseController {



    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbOrganRateScoreService tbOrganRateScoreService;
    @Autowired
    private TbOrganRateScoreMonthDetailService tbOrganRateScoreMonthDetailService;
    @Autowired
    private TbOrganRateParamService tbOrganRateParamService;


    /**
     * �б�ҳ
     *
     * @return
     * @throws Exception
     * @Author liujinbo
     */
    @RequestMapping("/tbOrganRateParamIndexPage")
    @SystemLog(tradeName = "���ֲ���", funCode = "AL-03-01", funName = "���ֲ����б�ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamIndex";
    }

    /**
     * �б�ҳ����
     *
     * @return
     * @throws Exception
     * @Author liujinbo
     */
    @RequestMapping("/tbOrganRateParamIndexDate")
    @SystemLog(tradeName = "���ֲ���", funCode = "AL-03-01", funName = "�������ֲ����б�ҳ����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanData(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Map<String, Object>> data = null;
        try {

            setPageParam();
            data = tbOrganRateParamService.selectByType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData(data);
    }


    /**
     * �޸�ҳ
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateTbOrganRateParamPage")
    @SystemLog(tradeName = "���ֲ���", funCode = "AL-03-01-01", funName = "���ֲ����޸�ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toUpdateCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String elementtype = request.getParameter("elementtype");

        //��ѯ���ֲ���
        TbOrganRateParam tbOrganRateParam = new TbOrganRateParam();
        tbOrganRateParam.setElementType(elementtype);
        List<TbOrganRateParam> tbOrganRateParamList = tbOrganRateParamService.selectByAttr(tbOrganRateParam);
        // ����
        Collections.sort(tbOrganRateParamList, new Comparator<TbOrganRateParam>() {
            @Override
            public int compare(TbOrganRateParam o1, TbOrganRateParam o2) {
                if (o1.getLow().compareTo(o2.getLow()) == -1) {
                    return -1;
                } else if (o1.getLow().compareTo(o2.getLow()) == 1) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });


        request.setAttribute("tbOrganRateParamList", tbOrganRateParamList);
        request.setAttribute("elementtype", elementtype);

        if (OrganRateParamElementType.BAD_CONDITION.equals(elementtype)) {
            //�������
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_bad_condition";
        }else if (OrganRateParamElementType.DEPOSIT_LOAN_RATIO.equals(elementtype)) {
            //��Ӫ���������
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_deposit_loan_ratio";
        }else if (OrganRateParamElementType.NEW_LOAN_RATIO.equals(elementtype)) {
            //�·�����������
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_new_loan_ratio";
        }else if (OrganRateParamElementType.LOAN_STRUCT.equals(elementtype)) {
            //����ṹ
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_loan_struct";
        }else if (OrganRateParamElementType.SCALE_AMOUNT.equals(elementtype)) {
            //����ģռ�÷Ѻ͹�ģ���÷�
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_scale_amount";
        }else if (OrganRateParamElementType.PLAN_SUBMIT_DEVIATION.equals(elementtype)) {
            //�ƻ�����ƫ���
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_plan_submit_deviation";
        }else if (OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_MID.equals(elementtype)) {
            //�ƻ�ִ��ƫ���-��ĩ����ƫ���
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_plan_execute_deviation_month_mid";
        }else if (OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_INIT.equals(elementtype)) {
            //�ƻ�ִ��ƫ���-��ĩ�³�ƫ���
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_plan_execute_deviation_month_init";
        }else if (OrganRateParamElementType.ADJ_COUNT.equals(elementtype)) {
            //����Ƶ��
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_adj_count";
        }else if (OrganRateParamElementType.SCALE_AMOUNT_AND_ADJ_COUNT.equals(elementtype)) {
            //δ��������ģռ�÷Ѻ͹�ģ���÷Ѻ͵���Ƶ��
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_scale_amount_and_adj_count";
        }else if (OrganRateParamElementType.QUARTER_GRADE.equals(elementtype)) {
            //��������
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_quarter_grade";
        }else if (OrganRateParamElementType.QUARTER_WEIGHT.equals(elementtype)) {
            //��������Ȩ��
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_quarter_weight";
        }


        return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_plan_submit_deviation";
    }


    /**
     * �޸����ֲ���
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateTbOrganRateParam", method = RequestMethod.POST)
    @SystemLog(tradeName = "���ֲ���", funCode = "AL-03-01-01", funName = "�޸����ֲ���", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanUpdate(HttpServletRequest request) throws Exception {
        //��ǰ��¼��
        String operCode = getSessionOperInfo().getOperCode();

        PlainResult<String> result = tbOrganRateParamService.updateOrganRateParam(request, operCode);

        return JSON.toJSONString(result);
    }

    /**
     * ����ҳ
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/tbOrganRateParamDetailPage")
    @SystemLog(tradeName = "���ֲ���", funCode = "AL-03-01-02", funName = "���ֲ�������ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toDetailCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String elementtype = request.getParameter("elementtype");

        //��ѯ���ֲ���
        TbOrganRateParam tbOrganRateParam = new TbOrganRateParam();
        tbOrganRateParam.setElementType(elementtype);
        List<TbOrganRateParam> tbOrganRateParamList = tbOrganRateParamService.selectByAttr(tbOrganRateParam);
        // ����
        Collections.sort(tbOrganRateParamList, new Comparator<TbOrganRateParam>() {
            @Override
            public int compare(TbOrganRateParam o1, TbOrganRateParam o2) {
                if (o1.getLow().compareTo(o2.getLow()) == -1) {
                    return -1;
                } else if (o1.getLow().compareTo(o2.getLow()) == 1) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });


        request.setAttribute("tbOrganRateParamList", tbOrganRateParamList);
        request.setAttribute("elementtype", elementtype);
        if (OrganRateParamElementType.BAD_CONDITION.equals(elementtype)) {
            //�������
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_bad_condition";
        }else if (OrganRateParamElementType.DEPOSIT_LOAN_RATIO.equals(elementtype)) {
            //��Ӫ���������
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_deposit_loan_ratio";
        }else if (OrganRateParamElementType.NEW_LOAN_RATIO.equals(elementtype)) {
            //�·�����������
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_new_loan_ratio";
        }else if (OrganRateParamElementType.LOAN_STRUCT.equals(elementtype)) {
            //����ṹ
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_loan_struct";
        }else if (OrganRateParamElementType.SCALE_AMOUNT.equals(elementtype)) {
            //����ģռ�÷Ѻ͹�ģ���÷�
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_scale_amount";
        }else if (OrganRateParamElementType.PLAN_SUBMIT_DEVIATION.equals(elementtype)) {
            //�ƻ�����ƫ���
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_plan_submit_deviation";
        }else if (OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_MID.equals(elementtype)) {
            //�ƻ�ִ��ƫ���-��ĩ����ƫ���
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_plan_execute_deviation_month_mid";
        }else if (OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_INIT.equals(elementtype)) {
            //�ƻ�ִ��ƫ���-��ĩ�³�ƫ���
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_plan_execute_deviation_month_init";
        }else if (OrganRateParamElementType.ADJ_COUNT.equals(elementtype)) {
            //����Ƶ��
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_adj_count";
        }else if (OrganRateParamElementType.SCALE_AMOUNT_AND_ADJ_COUNT.equals(elementtype)) {
            //δ��������ģռ�÷Ѻ͹�ģ���÷Ѻ͵���Ƶ��
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_scale_amount_and_adj_count";
        }else if (OrganRateParamElementType.QUARTER_GRADE.equals(elementtype)) {
            //��������
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_quarter_grade";
        }else if (OrganRateParamElementType.QUARTER_WEIGHT.equals(elementtype)) {
            //��������Ȩ��
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_quarter_weight";
        }


        return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_plan_submit_deviation";

    }
}
