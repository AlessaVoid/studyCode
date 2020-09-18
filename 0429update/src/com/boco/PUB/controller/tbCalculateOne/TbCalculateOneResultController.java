package com.boco.PUB.controller.tbCalculateOne;

import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.tbCalculateOne.TbCalculateOneImportDataService;
import com.boco.PUB.service.tbCalculateOne.TbCalculateOneResultService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.global.Dic;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Liujinbo
 * @Date: 2020/3/2
 * @Description:
 */

@Controller
@RequestMapping(value = "/tbCalculateOneResult")
public class TbCalculateOneResultController extends BaseController  {

    @Autowired
    private TbCalculateOneResultService tbCalculateOneResultService;
    @Autowired
    private TbCalculateOneImportDataService tbCalculateOneImportDataService;
    @Autowired
    private TbPlanService tbPlanService;


    @RequestMapping(value = "/addTbCalculateOneResult")
    @SystemLog(tradeName = "生成测算模式一结果", funCode = "PUB-31-02", funName = "生成测算模式一结果", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String restartParam(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        tbCalculateOneResultService.addCalculateOneResult();
        json.returnMsg("true", "结果生成成功！");//数据字典重置成功
        return json.toJson();
    }

    @RequestMapping("/enterUI")
    @SystemLog(tradeName = "测算导入页面", funCode = "PUB-31-02", funName = "测算导入页面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String enterUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbCalculateOne/tbCalculateOneResult/tbCalculateOneResultEnter";
    }


    //导入测算模式一计算需要的数据
    @ResponseBody
    @RequestMapping(value = "/enterReport", method = RequestMethod.POST)
    @SystemLog(tradeName = "导入测算数据", funCode = "PUB-31-02", funName = "导入测算数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String enterReportPlanByMonth(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        Map<String, String> resultMap = new HashMap<>();
        try {
            //当前登录人
            String operCode = getSessionOperInfo().getOperCode();

            resultMap = tbCalculateOneImportDataService.enterDataByMonth(file, operCode, request);
        } catch (Exception e) {
            e.printStackTrace();
            return this.json.returnMsg("false", "录入失败,请检查!").toJson();
        }
        return this.json.returnMsg(resultMap.get("code"), resultMap.get("msg")).toJson();
    }


}
