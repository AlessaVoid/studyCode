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
 * @Description:   评分参数
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
     * 列表页
     *
     * @return
     * @throws Exception
     * @Author liujinbo
     */
    @RequestMapping("/tbOrganRateParamIndexPage")
    @SystemLog(tradeName = "评分参数", funCode = "AL-03-01", funName = "评分参数列表页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamIndex";
    }

    /**
     * 列表页数据
     *
     * @return
     * @throws Exception
     * @Author liujinbo
     */
    @RequestMapping("/tbOrganRateParamIndexDate")
    @SystemLog(tradeName = "评分参数", funCode = "AL-03-01", funName = "加载评分参数列表页数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
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
     * 修改页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateTbOrganRateParamPage")
    @SystemLog(tradeName = "评分参数", funCode = "AL-03-01-01", funName = "评分参数修改页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toUpdateCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String elementtype = request.getParameter("elementtype");

        //查询评分参数
        TbOrganRateParam tbOrganRateParam = new TbOrganRateParam();
        tbOrganRateParam.setElementType(elementtype);
        List<TbOrganRateParam> tbOrganRateParamList = tbOrganRateParamService.selectByAttr(tbOrganRateParam);
        // 排序
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
            //不良情况
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_bad_condition";
        }else if (OrganRateParamElementType.DEPOSIT_LOAN_RATIO.equals(elementtype)) {
            //自营新增存贷比
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_deposit_loan_ratio";
        }else if (OrganRateParamElementType.NEW_LOAN_RATIO.equals(elementtype)) {
            //新发生贷款利率
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_new_loan_ratio";
        }else if (OrganRateParamElementType.LOAN_STRUCT.equals(elementtype)) {
            //贷款结构
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_loan_struct";
        }else if (OrganRateParamElementType.SCALE_AMOUNT.equals(elementtype)) {
            //超规模占用费和规模闲置费
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_scale_amount";
        }else if (OrganRateParamElementType.PLAN_SUBMIT_DEVIATION.equals(elementtype)) {
            //计划报送偏离度
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_plan_submit_deviation";
        }else if (OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_MID.equals(elementtype)) {
            //计划执行偏离度-月末月中偏离度
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_plan_execute_deviation_month_mid";
        }else if (OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_INIT.equals(elementtype)) {
            //计划执行偏离度-月末月初偏离度
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_plan_execute_deviation_month_init";
        }else if (OrganRateParamElementType.ADJ_COUNT.equals(elementtype)) {
            //调整频次
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_adj_count";
        }else if (OrganRateParamElementType.SCALE_AMOUNT_AND_ADJ_COUNT.equals(elementtype)) {
            //未产生超规模占用费和规模闲置费和调整频次
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_scale_amount_and_adj_count";
        }else if (OrganRateParamElementType.QUARTER_GRADE.equals(elementtype)) {
            //季度评级
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_quarter_grade";
        }else if (OrganRateParamElementType.QUARTER_WEIGHT.equals(elementtype)) {
            //季度评分权重
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_quarter_weight";
        }


        return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamUpdate_plan_submit_deviation";
    }


    /**
     * 修改评分参数
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateTbOrganRateParam", method = RequestMethod.POST)
    @SystemLog(tradeName = "评分参数", funCode = "AL-03-01-01", funName = "修改评分参数", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanUpdate(HttpServletRequest request) throws Exception {
        //当前登录人
        String operCode = getSessionOperInfo().getOperCode();

        PlainResult<String> result = tbOrganRateParamService.updateOrganRateParam(request, operCode);

        return JSON.toJSONString(result);
    }

    /**
     * 详情页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/tbOrganRateParamDetailPage")
    @SystemLog(tradeName = "评分参数", funCode = "AL-03-01-02", funName = "评分参数详情页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toDetailCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String elementtype = request.getParameter("elementtype");

        //查询评分参数
        TbOrganRateParam tbOrganRateParam = new TbOrganRateParam();
        tbOrganRateParam.setElementType(elementtype);
        List<TbOrganRateParam> tbOrganRateParamList = tbOrganRateParamService.selectByAttr(tbOrganRateParam);
        // 排序
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
            //不良情况
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_bad_condition";
        }else if (OrganRateParamElementType.DEPOSIT_LOAN_RATIO.equals(elementtype)) {
            //自营新增存贷比
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_deposit_loan_ratio";
        }else if (OrganRateParamElementType.NEW_LOAN_RATIO.equals(elementtype)) {
            //新发生贷款利率
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_new_loan_ratio";
        }else if (OrganRateParamElementType.LOAN_STRUCT.equals(elementtype)) {
            //贷款结构
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_loan_struct";
        }else if (OrganRateParamElementType.SCALE_AMOUNT.equals(elementtype)) {
            //超规模占用费和规模闲置费
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_scale_amount";
        }else if (OrganRateParamElementType.PLAN_SUBMIT_DEVIATION.equals(elementtype)) {
            //计划报送偏离度
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_plan_submit_deviation";
        }else if (OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_MID.equals(elementtype)) {
            //计划执行偏离度-月末月中偏离度
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_plan_execute_deviation_month_mid";
        }else if (OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_INIT.equals(elementtype)) {
            //计划执行偏离度-月末月初偏离度
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_plan_execute_deviation_month_init";
        }else if (OrganRateParamElementType.ADJ_COUNT.equals(elementtype)) {
            //调整频次
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_adj_count";
        }else if (OrganRateParamElementType.SCALE_AMOUNT_AND_ADJ_COUNT.equals(elementtype)) {
            //未产生超规模占用费和规模闲置费和调整频次
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_scale_amount_and_adj_count";
        }else if (OrganRateParamElementType.QUARTER_GRADE.equals(elementtype)) {
            //季度评级
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_quarter_grade";
        }else if (OrganRateParamElementType.QUARTER_WEIGHT.equals(elementtype)) {
            //季度评分权重
            return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_quarter_weight";
        }


        return basePath +  "/AL/tbOrganRateParam/tbOrganRateParamList/tbOrganRateParamDetail_plan_submit_deviation";

    }
}
