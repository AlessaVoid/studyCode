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
    @SystemLog(tradeName = "���ɲ���ģʽһ���", funCode = "PUB-31-02", funName = "���ɲ���ģʽһ���", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public @ResponseBody
    String restartParam(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        tbCalculateOneResultService.addCalculateOneResult();
        json.returnMsg("true", "������ɳɹ���");//�����ֵ����óɹ�
        return json.toJson();
    }

    @RequestMapping("/enterUI")
    @SystemLog(tradeName = "���㵼��ҳ��", funCode = "PUB-31-02", funName = "���㵼��ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String enterUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbCalculateOne/tbCalculateOneResult/tbCalculateOneResultEnter";
    }


    //�������ģʽһ������Ҫ������
    @ResponseBody
    @RequestMapping(value = "/enterReport", method = RequestMethod.POST)
    @SystemLog(tradeName = "�����������", funCode = "PUB-31-02", funName = "�����������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String enterReportPlanByMonth(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        Map<String, String> resultMap = new HashMap<>();
        try {
            //��ǰ��¼��
            String operCode = getSessionOperInfo().getOperCode();

            resultMap = tbCalculateOneImportDataService.enterDataByMonth(file, operCode, request);
        } catch (Exception e) {
            e.printStackTrace();
            return this.json.returnMsg("false", "¼��ʧ��,����!").toJson();
        }
        return this.json.returnMsg(resultMap.get("code"), resultMap.get("msg")).toJson();
    }


}
