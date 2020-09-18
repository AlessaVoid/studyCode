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
 * 系统首页信息
 * @Author zhuhongjiang
 * @Date 2019/12/18 下午5:25
 **/
@Controller
@RequestMapping(value = "/indexData")
public class IndexDataController extends BaseController {

    @Autowired
    private IReportService reportService;

    /**
     * ajax判断是否显示首页信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "/isShowIndexData")
    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "首页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String isShowIndexData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 所有机构都可以查看首页图
        Boolean isShow = true;
        // try {
        //     //总行、一级分行，可查看首页信息
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
     * 首页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toIndex")
    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "首页", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("organlevel", getSessionOrgan().getOrganlevel());
        return "/system/index/indexData";
    }

    /**
     * 获取机构本月净增量信息（柱形图）
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getIndexOrganBarInfo")
    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "机构本月净增量", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getIndexOrganBarInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<List<Map<String, Object>>> result = new PlainResult<>();
        try {
            //当前登录机构信息
            FdOrgan fdOrgan = getSessionOrgan();
            //当前登录机构信息 - 级别
            String organlevel = fdOrgan.getOrganlevel();
            //当前登录机构信息 - 机构编码
            String organCode = fdOrgan.getThiscode();
            //当前登录机构信息 - 机构名称
            String organName = fdOrgan.getOrganname();
            //当前登录柜员信息 - 人员编码
            String operCode = getSessionUser().getOpercode();

            List<Map<String, Object>> resultList = reportService.getIndexOrganBarInfo(organlevel, organCode, organName, operCode);
            result.success(resultList, "getIndexOrganBarInfo success.");
        } catch (Exception e) {
            result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "getIndexOrganBarInfo.");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 二级贷种本月净增量（柱形图）
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getIndexCombBarInfo")
    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "机构本月净增量", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getIndexCombBarInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<List<Map<String, Object>>> result = new PlainResult<>();
        try {
            //当前登录机构信息
            FdOrgan fdOrgan = getSessionOrgan();
            //当前登录机构信息 - 级别
            String organlevel = fdOrgan.getOrganlevel();
            //当前登录机构信息 - 机构编码
            String organCode = fdOrgan.getThiscode();
            //当前登录柜员信息 - 人员编码
            String operCode = getSessionUser().getOpercode();

            List<Map<String, Object>> resultList = reportService.getIndexCombBarInfo(organlevel, organCode, operCode);
            result.success(resultList, "getIndexCombBarInfo success.");
        } catch (Exception e) {
            result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "getIndexCombBarInfo.");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 二级贷种本月计划完成率（折线图）
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getIndexCombLineInfo")
    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "机构本月净增量", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getIndexCombLineInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<List<Map<String, Object>>> result = new PlainResult<>();
        try {
            //当前登录机构信息
            FdOrgan fdOrgan = getSessionOrgan();
            //当前登录机构信息 - 级别
            String organlevel = fdOrgan.getOrganlevel();
            //当前登录机构信息 - 机构编码
            String organCode = fdOrgan.getThiscode();
            //当前登录柜员信息 - 人员编码
            String operCode = getSessionUser().getOpercode();

            List<Map<String, Object>> resultList = reportService.getIndexCombLineInfo(organlevel, organCode, operCode);
            result.success(resultList, "getIndexCombLineInfo success.");
        } catch (Exception e) {
            result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "getIndexCombLineInfo.");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 首页预警线（完成率）
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getIndexWarnCompleteInfo")
    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "预警线完成率", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getIndexWarnCompleteInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<List<Map<String, Object>>> result = new PlainResult<>();
        try {
            //当前登录机构信息
            FdOrgan fdOrgan = getSessionOrgan();
            //当前登录机构信息 - 级别
            String organlevel = fdOrgan.getOrganlevel();
            //当前登录机构信息 - 机构编码
            String organCode = fdOrgan.getThiscode();
            //当前登录柜员信息 - 人员编码
            String operCode = getSessionUser().getOpercode();

            List<Map<String, Object>> resultList = reportService.getIndexWarnCompleteInfo(organlevel, organCode, operCode);
            result.success(resultList, "getIndexWarnCompleteInfo success.");
        } catch (Exception e) {
            result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "getIndexWarnCompleteInfo.");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 首页预警线（绝对值）
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getIndexWarnAbsInfo")
    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "预警线绝对值", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getIndexWarnAbsInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<List<Map<String, Object>>> result = new PlainResult<>();
        try {
            //当前登录机构信息
            FdOrgan fdOrgan = getSessionOrgan();
            //当前登录机构信息 - 级别
            String organlevel = fdOrgan.getOrganlevel();
            //当前登录机构信息 - 机构编码
            String organCode = fdOrgan.getThiscode();
            //当前登录柜员信息 - 人员编码
            String operCode = getSessionUser().getOpercode();

            List<Map<String, Object>> resultList = reportService.getIndexWarnAbsInfo(organlevel, organCode, operCode);
            result.success(resultList, "getIndexWarnAbsInfo success.");
        } catch (Exception e) {
            result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "getIndexWarnAbsInfo.");
        }
        return JSON.toJSONString(result);
    }
}
