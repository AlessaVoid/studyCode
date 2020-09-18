package com.boco.RE.controller;

import com.boco.RE.service.ITbBankRmbBusiService;
import com.boco.RE.service.ITbPsbcRmbBusiService;
import com.boco.RE.service.ITbPsbcRmbLoanDayService;
import com.boco.RE.service.ITbPsbcRmbLoanSumService;
import com.boco.RE.util.Constant;
import com.boco.RE.util.FileUtil;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbBankRmbBusi;
import com.boco.SYS.entity.TbPsbcRmbLoanSum;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.TbBankInfoMapper;
import com.boco.SYS.mapper.TbHisImportTypeMapper;
import com.boco.util.JsonUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ��ʷ���ݱ�����
 * @Author zhuhongjiang
 * @Date 2020/1/8 ����9:32
 **/
@Controller
@RequestMapping("/reportHisImport")
public class ReportHisImportController extends BaseController {

    @Value("${upload.reportImport.path}")
    private String reportImportPath;
    @Autowired
    private ITbPsbcRmbBusiService tbPsbcRmbBusiService;
    @Autowired
    private ITbBankRmbBusiService tbBankRmbBusiService;
    @Autowired
    private ITbPsbcRmbLoanSumService tbPsbcRmbLoanSumService;
    @Autowired
    private ITbPsbcRmbLoanDayService tbPsbcRmbLoanDayService;
    @Autowired
    private TbBankInfoMapper tbBankInfoMapper;
    @Autowired
    private TbHisImportTypeMapper tbHisImportTypeMapper;


    public static SimpleDateFormat sdf_ym = new SimpleDateFormat("yyyyMM");
    public static SimpleDateFormat sdf_ymdhmss = new SimpleDateFormat("yyyyMMddhhmmssSSS");


    /**
     * ����ҵ�ֵ�������-ҳ��
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/toPsbcRmbBusiIndex")
    @SystemLog(tradeName = "��ʷ���ݵ������", funCode = "RE-03-01", funName = "����ҵ�ֵ�������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toPsbcRmbBusiIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //��������Map
        Map<String, String> loanTypeMap = new LinkedHashMap<>();
        List<Map<String, Object>> loanTypeList = tbHisImportTypeMapper.selectByType(Constant.UPLOAD_TYPE_1);
        for(Map<String, Object> map : loanTypeList){
            loanTypeMap.put(String.valueOf(map.get("code")), String.valueOf(map.get("alias")));
        }

        request.setAttribute("loanTypeMap", JsonUtils.toJson(loanTypeMap));
        return basePath + "/RE/reportImport/importPsbcRmbBusiIndex";
    }

    /**
     * ����ҵ�ֵ�������-��������
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getPsbcRmbBusiData")
    @SystemLog(tradeName = "��ʷ���ݵ������", funCode = "RE-03-01", funName = "��������ҵ�ֵ��������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getPsbcRmbBusiData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> data = null;
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("statisticsDate", request.getParameter("statisticsDate"));
            paramMap.put("loanType", request.getParameter("loanType"));
            setPageParam();
            data = tbPsbcRmbBusiService.selectListByPage(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData(data);
    }

    /**
     * ����ҵ�ֻ�������
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/toBankRmbBusiIndex")
    @SystemLog(tradeName = "��ʷ���ݵ������", funCode = "RE-03-02", funName = "����ҵ�ֻ�������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toBankRmbBusiIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //��������Map
        Map<String, String> loanTypeMap = new LinkedHashMap<>();
        List<Map<String, Object>> loanTypeList = tbHisImportTypeMapper.selectByType(Constant.UPLOAD_TYPE_2);
        for(Map<String, Object> map : loanTypeList){
            loanTypeMap.put(String.valueOf(map.get("code")), String.valueOf(map.get("alias")));
        }

        //���м��Map
        Map<String, String> bankNameMap = new HashMap<>();
        List<Map<String, Object>> bankNameList = tbBankInfoMapper.selectBankList();
        for(Map<String, Object> map : bankNameList){
            bankNameMap.put(String.valueOf(map.get("code")), String.valueOf(map.get("name")));
        }

        request.setAttribute("loanTypeMap", JsonUtils.toJson(loanTypeMap));
        request.setAttribute("bankNameMap", JsonUtils.toJson(bankNameMap));
        return basePath + "/RE/reportImport/importBankRmbBusiIndex";
    }

    /**
     * ����ҵ�ֻ�������-��������
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getBankRmbBusiData")
    @SystemLog(tradeName = "��ʷ���ݵ������", funCode = "RE-03-02", funName = "��������ҵ�ֻ��������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getBankRmbBusiData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TbBankRmbBusi> data = null;
        try {
            TbBankRmbBusi record = new TbBankRmbBusi();
            record.setStatisticsDay(request.getParameter("statisticsDate"));
            record.setLoanType(request.getParameter("loanType"));
            record.setSortColumn("statistics_day desc, loan_type asc, bankname asc");
            setPageParam();
            data = tbBankRmbBusiService.selectByAttr(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData(data);
    }

    /**
     * ����Ҵ����ձ����ܵ��루�ʴ��ֲ�Ʒ��
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/toPsbcRmbLoanSumIndex")
    @SystemLog(tradeName = "��ʷ���ݵ������", funCode = "RE-03-03", funName = "����Ҵ����ձ����ܵ���", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toPsbcRmbLoanSumIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //��������Map
        Map<String, String> loanTypeMap = new LinkedHashMap<>();
        List<Map<String, Object>> loanTypeList = tbHisImportTypeMapper.selectByType(Constant.UPLOAD_TYPE_3);
        for(Map<String, Object> map : loanTypeList){
            loanTypeMap.put(String.valueOf(map.get("code")), String.valueOf(map.get("name")));
        }

        request.setAttribute("loanTypeMap", JsonUtils.toJson(loanTypeMap));
        return basePath + "/RE/reportImport/importPsbcRmbLoanSumIndex";
    }

    /**
     * ����Ҵ����ձ����ܵ���-��������
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getPsbcRmbLoanSumData")
    @SystemLog(tradeName = "��ʷ���ݵ������", funCode = "RE-03-03", funName = "��������Ҵ����ձ����ܵ����б�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getPsbcRmbLoanSumData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TbPsbcRmbLoanSum> data = null;
        try {
            TbPsbcRmbLoanSum record = new TbPsbcRmbLoanSum();
            record.setStatisticsDay(request.getParameter("statisticsDate"));
            record.setLoanType(request.getParameter("loanType"));
            record.setSortColumn("statistics_day desc, loan_type asc");
            setPageParam();
            data = tbPsbcRmbLoanSumService.selectByAttr(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData(data);
    }

    /**
     * ����Ҵ����ձ����루�ʴ��ܷ��У�
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/toPsbcRmbLoanDayIndex")
    @SystemLog(tradeName = "��ʷ���ݵ������", funCode = "RE-03-04", funName = "����Ҵ����ձ�����", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toPsbcRmbLoanDayIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authButtons();
        //��������Map
        Map<String, String> loanTypeMap = new LinkedHashMap<>();
        List<String> typeList = new ArrayList<>();
        typeList.add(Constant.UPLOAD_TYPE_4);
        typeList.add(Constant.UPLOAD_TYPE_5);
        typeList.add(Constant.UPLOAD_TYPE_6);
        List<Map<String, Object>> loanTypeList = tbHisImportTypeMapper.selectByTypeList(typeList);
        for(Map<String, Object> map : loanTypeList){
            loanTypeMap.put(String.valueOf(map.get("code")), String.valueOf(map.get("alias")));
        }

        request.setAttribute("loanTypeMap", JsonUtils.toJson(loanTypeMap));
        return basePath + "/RE/reportImport/importPsbcRmbLoanDayIndex";
    }

    /**
     * ����Ҵ����ձ�����-��������
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getPsbcRmbLoanDayData")
    @SystemLog(tradeName = "��ʷ���ݵ������", funCode = "RE-03-04", funName = "��������Ҵ����ձ������б�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String getPsbcRmbLoanDayData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> data = null;
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("statisticsDate", request.getParameter("statisticsDate"));
            paramMap.put("loanType", request.getParameter("loanType"));
            setPageParam();
            data = tbPsbcRmbLoanDayService.selectListByPage(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData(data);
    }

    /**
     * �ϴ�
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/upload")
    @SystemLog(tradeName = "��ʷ���ݵ������", funCode = "RE-03", funName = "�ϴ��ļ�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException, InvalidFormatException {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", "0001");
        resultMap.put("message", "�ϴ�ʧ�ܣ�");
        try {
            String type = request.getParameter("type");
            String statisticsDay = request.getParameter("statisticsDay");
            String projectPath = request.getSession().getServletContext().getRealPath("/");
            String operCode = getSessionOperInfo().getOperCode();
            List<Map<String, Object>> loanTypeList = tbHisImportTypeMapper.selectByType(type);

            if(Constant.UPLOAD_TYPE_1.equals(type)){
                //����ҵ�ֵ���
                resultMap = tbPsbcRmbBusiService.upload(loanTypeList, statisticsDay, file, projectPath, operCode, resultMap);

            }else if(Constant.UPLOAD_TYPE_2.equals(type)){
                //����ҵ�ֻ���
                resultMap = tbBankRmbBusiService.upload(loanTypeList, statisticsDay, file, projectPath, operCode, resultMap);

            }else if(Constant.UPLOAD_TYPE_3.equals(type)){
                resultMap = tbPsbcRmbLoanSumService.upload(loanTypeList, statisticsDay, file, projectPath, operCode, resultMap);

            }else if(Constant.UPLOAD_TYPE_4.equals(type) || Constant.UPLOAD_TYPE_5.equals(type) || Constant.UPLOAD_TYPE_6.equals(type)){
                resultMap = tbPsbcRmbLoanDayService.upload(loanTypeList, statisticsDay, type, file, projectPath, operCode, resultMap);

            }else{
                resultMap.put("status", "0001");
                resultMap.put("message", "�ϴ�ʧ�ܣ�����δƥ�䣡");
            }

            //�����ļ�
            if("0000".equals(resultMap.get("status"))){
                Date date = new Date();
                //�ļ��ϴ�·��  /app/xegl/upload/reportImport/{type}/{yyyyMM}
                String fileDir = reportImportPath + type + File.separator + sdf_ym.format(date);
                //�ļ��ϴ�����
                String fileName = sdf_ymdhmss.format(date) + "_" + statisticsDay + "_" + file.getOriginalFilename();
                FileUtil.saveFile(file, fileDir, fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return JsonUtils.toJson(resultMap);
        }
    }

    /**
     * У��ͳ������
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/checkStatisticsDay")
    @SystemLog(tradeName = "��ʷ���ݵ������", funCode = "RE-03", funName = "�ϴ��ļ�", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String checkStatisticsDay(HttpServletRequest request, HttpServletResponse response) throws IOException, InvalidFormatException {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", "0001");
        resultMap.put("message", "�ϴ�ʧ�ܣ�");
        try {
            String statisticsDay = request.getParameter("statisticsDay");
            String type = request.getParameter("type");

            if(Constant.UPLOAD_TYPE_1.equals(type)){
                resultMap = tbPsbcRmbBusiService.checkStatisticsDay(statisticsDay, resultMap);

            }else if(Constant.UPLOAD_TYPE_2.equals(type)){
                resultMap = tbBankRmbBusiService.checkStatisticsDay(statisticsDay, resultMap);

            }else if(Constant.UPLOAD_TYPE_3.equals(type)){
                resultMap = tbPsbcRmbLoanSumService.checkStatisticsDay(statisticsDay, resultMap);

            }else if(Constant.UPLOAD_TYPE_4.equals(type) || Constant.UPLOAD_TYPE_5.equals(type) || Constant.UPLOAD_TYPE_6.equals(type)){
                resultMap = tbPsbcRmbLoanDayService.checkStatisticsDay(type, statisticsDay, resultMap);

            }else{
                resultMap.put("status", "0001");
                resultMap.put("message", "�ϴ�ʧ�ܣ�����δƥ�䣡");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return JsonUtils.toJson(resultMap);
        }
    }

}
