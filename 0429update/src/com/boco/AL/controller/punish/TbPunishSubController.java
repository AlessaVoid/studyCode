package com.boco.AL.controller.punish;

import com.boco.AL.service.ITbPunishListService;
import com.boco.PM.service.IFdOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbPunishList;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LineProductDetailMapper;
import com.boco.SYS.mapper.LineProductMapper;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TbOverSubController
 * @Description 罚息结果申请已提交记录
 * @Author tangxn
 * @Date 20191117 下午2:20
 * @Version 2.0
 **/
@Controller
@RequestMapping("/punishApplySub")
public class TbPunishSubController extends BaseController {

    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private ITbPunishListService tbPunishListService;

    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    LineProductMapper lineProductMapper;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    LineProductDetailMapper lineProductDetailsMapper;
    @Autowired
    private IFdOrganService organService;

    @RequestMapping("/punishSubmitRecordUI")
    @SystemLog(tradeName = "已提交罚息结果申请", funCode = "AL", funName = "已提交罚息结果申请列表", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String loanSubmitAuditHistoryRecordUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbPunishManage/tbPunishList/submitted/tbQuotaApplySubmitIndexList";
    }

    @ResponseBody
    @RequestMapping("/getSubmitAuditHistoryRecord")
    @SystemLog(tradeName = "已提交罚息结果申请", funCode = "AL", funName = "已提交罚息结果申请", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getSubmitAuditHistoryRecord(String auditStatus, String month) throws Exception {
        setPageParam();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        setPageParam();
        List<Map<String, Object>> list = tbPunishListService.getAuditRecordHistRecord(sessionOperInfo.getOperCode(), auditStatus,
                month);
        if (!"".equals(auditStatus) && auditStatus != null && auditStatus.trim().length() > 0) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if (auditStatus.equals(String.valueOf(map.get("state")))) {
                    tempList.add(map);
                }
            }
            return pageData(tempList);
        }
        return pageData(list);
    }

    @RequestMapping("/listReqSubmitDetailAuditUI")
    @SystemLog(tradeName = "查看审批详情", funCode = "AL", funName = "查看审批详情", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listReqSubmitDetailAuditUI(int id, String processInstanceId) throws Exception {
        authButtons();

        TbPunishList tbPunishList = tbPunishListService.selectByPK(id);

        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);
        setAttribute("id", id);
        setAttribute("comments",  BocoUtils.translateComments(comments,""));
        setAttribute("TbPunishList", tbPunishList);
        setAttr(tbPunishList);
        return basePath + "/AL/tbPunishManage/tbPunishList/submitted/tbQuotaApplySubmitDetailPage";
    }


    /**
     * @param tbPunishList 罚息列表
     * @throws Exception
     */
    public void setAttr(TbPunishList tbPunishList) throws Exception {

        FdOrgan thisOrgan = getSessionOrgan();
        List<Map<String, Object>> organList = organService.selectByUporgan(thisOrgan.getThiscode());
        setAttribute("organList", organList);
        List<Map<String, String>> combAmountNameList = new ArrayList<>();

        Map<String, String> map0 = new HashMap<>(2);
        map0.put("name", "状态");
        map0.put("code", "state");
        combAmountNameList.add(map0);
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "当月总计划(亿元)");
        map1.put("code", "planMount");
        combAmountNameList.add(map1);
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "月末闲置额度(亿元)");
        map2.put("code", "monthVacancyAmt");
        combAmountNameList.add(map2);
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "月末闲置率(%)");
        map3.put("code", "monthVacancyRate");
        combAmountNameList.add(map3);
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "总闲置费(万元)");
        map4.put("code", "monthFiveVacancy");
        combAmountNameList.add(map4);
        Map<String, String> map5 = new HashMap<>(2);
        map5.put("name", "实体考核计划(亿元)");
        map5.put("code", "monthShitiPlanMount");
        combAmountNameList.add(map5);
        Map<String, String> map6 = new HashMap<>(2);
        map6.put("name", "实体月末超计划额度(亿元)");
        map6.put("code", "monthShitiOverAmt");
        combAmountNameList.add(map6);
        Map<String, String> map7 = new HashMap<>(2);
        map7.put("name", "实体月末超计划幅度(%)");
        map7.put("code", "monthShitiOverRate");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "实体超计划费(万元)");
        map7.put("code", "monthFiveShitiOver");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "票据考核计划(亿元)");
        map7.put("code", "monthPiapjuPlanMount");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "票据月末超计划额度(亿元)");
        map7.put("code", "monthPiaojuOverAmt");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "票据月末超计划幅度(%)");
        map7.put("code", "monthPiaojuOverRate");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "票据超计划费(万元)");
        map7.put("code", "monthFivePiaojuOver");
        combAmountNameList.add(map7);
        Map<String, String> map8 = new HashMap<>(2);
        map8.put("name", "罚息总计(万元)");
        map8.put("code", "monthTotalPunish");
        combAmountNameList.add(map8);
        map7 = new HashMap<>(2);
        map7.put("name", "反馈意见");
        map7.put("code", "note");
        combAmountNameList.add(map7);
        setAttribute("combAmountNameList", combAmountNameList);

    }

}
