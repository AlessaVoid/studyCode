package com.boco.PUB.controller.tbOrganRateScoreQuarter;

import com.alibaba.fastjson.JSON;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.POJO.DO.OrganRateParamElementType;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreMonthDetailService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreQuarterDetailService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbOrganRateScore;
import com.boco.SYS.entity.TbOrganRateScoreQuarterDetail;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.global.Dic;
import com.boco.TONY.common.PlainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ¼�뼾������
 *
 * @Author liujinbo
 * @Date 2020/02/05
 **/
@Controller
@RequestMapping("/tbOrganRateScoreQuarter")
public class TbOrganRateScoreQuarterController extends BaseController {

    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbOrganRateScoreService tbOrganRateScoreService;
    @Autowired
    private TbOrganRateScoreMonthDetailService tbOrganRateScoreMonthDetailService;
    @Autowired
    private TbOrganRateScoreQuarterDetailService tbOrganRateScoreQuarterDetailService;

    /**
     * �б�ҳ
     *
     * @return
     * @throws Exception
     * @Author liujinbo
     */
    @RequestMapping("/tbOrganRateScoreMonthIndex")
    @SystemLog(tradeName = "��������", funCode = "AL-03-01", funName = "���������б�ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/AL/tbOrganRateScoreQuarter/tbOrganRateScoreQuarterList/tbOrganRateScoreQuarterIndex";
    }

    /**
     * �б�ҳ����
     *
     * @return
     * @throws Exception
     * @Author liujinbo
     */
    @RequestMapping("/tbOrganRateScoreMonthData")
    @SystemLog(tradeName = "��������", funCode = "AL-04-01", funName = "���ؼ��������б�ҳ����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanData(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<TbOrganRateScore> data = null;
        try {
            String sort = request.getParameter("sort");
            String direction = request.getParameter("direction");

            if ("rateMonth".equals(sort)) {
                sort = "rate_month";

            } else if ("rateType".equals(sort)) {
                sort = "rate_type";

            } else if ("rateStatus".equals(sort)) {
                sort = "rate_status";

            } else if ("createTime".equals(sort)) {
                sort = "create_time";

            } else if ("updateTime".equals(sort)) {
                sort = "update_time";

            }
            if (sort != null) {
                sort = sort + " " + direction;
            } else {
                sort = "rate_month desc";
            }

            String rateMonth = request.getParameter("rateMonth");
            TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
            tbOrganRateScore.setRateType(OrganRateParamElementType.RATE_QUARTER);
            tbOrganRateScore.setRateMonth(rateMonth);
            tbOrganRateScore.setRateStatus(TbReqDetail.STATE_NEW);
            tbOrganRateScore.setSortColumn(sort);
            setPageParam();
            data = tbOrganRateScoreService.selectByAttr(tbOrganRateScore);
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
    @RequestMapping("/updateTbOrganRateScoreMonthPage")
    @SystemLog(tradeName = "��������", funCode = "AL-04-01-01", funName = "���������޸�ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toUpdateCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");

        //��ѯ��������
        List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetails = tbOrganRateScoreQuarterDetailService.selectByWhere("ref_id = \'" + id + "\'");
        HashMap<String, Object> rateScoreMap = new HashMap<>();
        for (TbOrganRateScoreQuarterDetail detail : tbOrganRateScoreQuarterDetails) {
            rateScoreMap.put(detail.getRateOrgan() , detail);
        }

        //��ѯ����
        List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        // List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        request.setAttribute("organList", organList);
        request.setAttribute("rateScoreMap", rateScoreMap);
        request.setAttribute("id", id);

        return basePath + "/AL/tbOrganRateScoreQuarter/tbOrganRateScoreQuarterList/tbOrganRateScoreQuarterUpdate";
    }


    /**
     * �޸ļ�������
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateTbOrganRateScoreMonth", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "AL-04-01-01", funName = "�޸ļ�������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanUpdate(HttpServletRequest request) throws Exception {
        //��ǰ��¼��
        String operCode = getSessionOperInfo().getOperCode();
        //��ǰ��¼����
        String organCode = getSessionOrgan().getThiscode();
        //��ǰ��¼��������
        String organlevel = getSessionOrgan().getOrganlevel();

        PlainResult<String> result = tbOrganRateScoreService.updateTbOrganRateScoreQuarter(request, operCode, organCode);
        return JSON.toJSONString(result);
    }

    /**
     * ����ҳ
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/tbOrganRateScoreMonthDetailPage")
    @SystemLog(tradeName = "��������", funCode = "AL-04-01-02", funName = "������������ҳ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toDetailCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");


        //��ѯ��������
        List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetails = tbOrganRateScoreQuarterDetailService.selectByWhere("ref_id = \'" + id + "\'");
        HashMap<String, Object> rateScoreMap = new HashMap<>();
        for (TbOrganRateScoreQuarterDetail detail : tbOrganRateScoreQuarterDetails) {
            rateScoreMap.put(detail.getRateOrgan() , detail);
        }

        //��ѯ����
        List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());
        // List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        request.setAttribute("organList", organList);
        request.setAttribute("rateScoreMap", rateScoreMap);
        request.setAttribute("id", id);

        return basePath + "/AL/tbOrganRateScoreQuarter/tbOrganRateScoreQuarterList/tbOrganRateScoreQuarterDetail";
    }


    /**
     * ��������
     *
     * @return
     * @throws Exception
     * @Author liujinbo
     */
    @RequestMapping("/addTbOrganRateScoreMonth")
    @SystemLog(tradeName = "������������", funCode = "AL-04-01", funName = "������������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String addTbOrganRateScoreMonth(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<String> result = new PlainResult<>();
        try {
            tbOrganRateScoreService.addQuarterOrganRateScore();
            result.success("add", "���ɳɹ���");
        } catch (Exception e) {
            e.printStackTrace();
            result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "����ʧ�ܣ�");
        }
        return JSON.toJSONString(result);
    }



}
