package com.boco.PUB.controller.tbOrganRateScoreMonth;

import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.POJO.DO.OrganRateParamElementType;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreMonthDetailService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbOrganRateScore;
import com.boco.SYS.entity.TbOrganRateScoreMonthDetail;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.global.Dic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分行查看月度评分
 *
 * @Author liujinbo
 * @Date 2020/02/11
 **/
@Controller
@RequestMapping("/tbOrganRateScoreMonthLowOrgan")
public class TbOrganRateScoreMonthLowOrgan extends BaseController {

    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbOrganRateScoreService tbOrganRateScoreService;
    @Autowired
    private TbOrganRateScoreMonthDetailService tbOrganRateScoreMonthDetailService;

    /**
     * 列表页
     *
     * @return
     * @throws Exception
     * @Author liujinbo
     */
    @RequestMapping("/MonthLowOrganListUI")
    @SystemLog(tradeName = "月度评分", funCode = "AL-03-06", funName = "月度评分列表页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String creditPlanIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        return basePath + "/AL/tbOrganRateScoreMonth/tbOrganRateScoreMonthLowOrgan/tbOrganRateScoreMonthIndex";
    }

    /**
     * 列表页数据
     *
     * @return
     * @throws Exception
     * @Author liujinbo
     */
    @RequestMapping("/tbOrganRateScoreMonthData")
    @SystemLog(tradeName = "月度评分", funCode = "AL-03-06", funName = "加载月度评分列表页数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanData(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<TbOrganRateScore> data = null;
        try {
            authButtons();
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
            tbOrganRateScore.setRateType(OrganRateParamElementType.RATE_MONTH);
            tbOrganRateScore.setRateMonth(rateMonth);
            tbOrganRateScore.setRateStatus(TbReqDetail.STATE_APPROVED);
            tbOrganRateScore.setSortColumn(sort);
            setPageParam();
            data = tbOrganRateScoreService.selectByAttr(tbOrganRateScore);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData(data);
    }

    /**
     * 详情页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/tbOrganRateScoreMonthDetailPage")
    @SystemLog(tradeName = "月度评分", funCode = "AL-03-06-01", funName = "月度评分详情页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toDetailCreditPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");

        //查询月度评分
        List<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetails = tbOrganRateScoreMonthDetailService.selectByWhere("ref_id = \'" + id + "\'");
        HashMap<String, Object> rateScoreMap = new HashMap<>();
        for (TbOrganRateScoreMonthDetail detail : tbOrganRateScoreMonthDetails) {
            rateScoreMap.put(detail.getRateOrgan() , detail);
        }

        //thiscode--organname
        String thiscode = getSessionOrgan().getThiscode();
        String organname = getSessionOrgan().getOrganname();

        //查询机构
        List<Map<String, Object>> organList = new ArrayList<>();
        HashMap<String, Object> organMap = new HashMap<>();
        organMap.put("thiscode", thiscode);
        organMap.put("organname", organname);
        organList.add(organMap);

        request.setAttribute("organList", organList);
        request.setAttribute("rateScoreMap", rateScoreMap);
        request.setAttribute("id", id);

        return basePath +  "/AL/tbOrganRateScoreMonth/tbOrganRateScoreMonthLowOrgan/tbOrganRateScoreMonthDetail";
    }



}
