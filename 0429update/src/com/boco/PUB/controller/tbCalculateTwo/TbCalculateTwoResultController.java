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
    @SystemLog(tradeName = "生成测算模式一结果", funCode = "PUB-31-05", funName = "生成测算模式一结果", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String restartParam(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        try {
            tbCalculateTwoResultService.addCalculateOneResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.returnMsg("true", "结果生成成功！");//数据字典重置成功
        return json.toJson();
    }

    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31-05", funName = "加载列表页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbCalculateTwo/tbCalculateTwoResult/tbCalculateTwoResultList";
    }

    /**
     * 查询tb_req_list分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "PUB-01-02", funName = "加载列表数据", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String findPage(TbCalculateTwoResult tbCalculateTwoResult) throws Exception {
        setPageParam();
        List<TbCalculateTwoResult> list = tbCalculateTwoResultService.selectMonth();
        return pageData(list);
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31-05", funName = "加载详细页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String infoUI(TbCalculateTwoResult tbCalculateTwoResult) throws Exception {
        setAttribute("month", tbCalculateTwoResult.getMonth());
        setAttr();
        return basePath + "/PUB/tbCalculateTwo/tbCalculateTwoResult/tbCalculateTwoResultInfo";
    }


    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31-05", funName = "加载修改页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String updateUI(TbCalculateTwoResult tbCalculateTwoResult) throws Exception {
        setAttribute("month", tbCalculateTwoResult.getMonth());
        setAttr();
        return basePath + "/PUB/tbCalculateTwo/tbCalculateTwoResult/tbCalculateTwoResultEdit";
    }

    /**
     * 详情页数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getDetailList")
    @SystemLog(tradeName = "信贷需求", funCode = "PUB-31", funName = "测算二详情", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
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
    @SystemLog(tradeName = "测算导入页面", funCode = "PUB-31-05", funName = "测算导入页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String enterUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbCalculateTwo/tbCalculateTwoResult/tbCalculateTwoResultEnter";
    }


    //导入测算模式一计算需要的数据
    @ResponseBody
    @RequestMapping(value = "/enterReport", method = RequestMethod.POST)
    @SystemLog(tradeName = "导入测算数据", funCode = "PUB-31-05", funName = "导入测算数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String enterReportPlanByMonth(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        Map<String, String> resultMap ;
        try {
            //当前登录人
            String operCode = getSessionOperInfo().getOperCode();

            resultMap = tbCalculateTwoImportDataService.enterDataByMonth(file, operCode, request);
        } catch (Exception e) {
            e.printStackTrace();
            return this.json.returnMsg("false", "录入失败,请检查!").toJson();
        }
        return this.json.returnMsg(resultMap.get("code"), resultMap.get("msg")).toJson();
    }

    /**
     * 通用方法
     *
     * @throws Exception
     */
    private void setAttr() throws Exception {

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //到期量、净增量必填项，利率、余额非必填项
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "年度信贷计划");
        map1.put("code", "code1");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "分行间比例");
        map1.put("code", "code2");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "统一切分额度");
        map1.put("code", "code3");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "上月末EVA");
        map1.put("code", "code4");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "年初到上月末新发生利率");
        map1.put("code", "code5");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "上月末不良率");
        map1.put("code", "code6");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "上月末存款计划完成率");
        map1.put("code", "code7");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "上月末经济资本回报率");
        map1.put("code", "code8");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "原份额");
        map1.put("code", "code9");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "新发生利率调整");
        map1.put("code", "code10");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "不良率调整");
        map1.put("code", "code11");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "自营存款计划完成率调整");
        map1.put("code", "code12");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "经济资本汇报率调整");
        map1.put("code", "code13");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "调整后份额");
        map1.put("code", "code14");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "优化额度配置");
        map1.put("code", "code15");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "调整计划金额排名");
        map1.put("code", "code16");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "与原份额差异");
        map1.put("code", "code17");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "份额差异排名");
        map1.put("code", "code18");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "年初到上月平均超计划或闲置金额");
        map1.put("code", "code20");
        combAmountNameList.add(map1);
        map1 = new HashMap<>(2);
        map1.put("name", "手工调节前计划");
        map1.put("code", "code21");
        combAmountNameList.add(map1);
//        map1 = new HashMap<>(2);
//        map1.put("name", "调整值");
//        map1.put("code", "code22");
//        combAmountNameList.add(map1);
//        map1 = new HashMap<>(2);
//        map1.put("name", "最终月度计划额度");
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
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31", funName = "修改", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request) throws Exception {
        //最后操作员
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
