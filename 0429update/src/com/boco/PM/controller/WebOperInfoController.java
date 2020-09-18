package com.boco.PM.controller;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.impl.PunishInterestService;
import com.boco.PM.service.IWebOperInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.SYS.mapper.WebOperRoleMapper;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.TONY.biz.weboper.exception.OperLineException;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.biz.line.POJO.DTO.ProductLineInfoDTO;
import com.boco.TONY.biz.weboper.POJO.DTO.WebOperLineDTO;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.WebDeptInfo;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.FdOperMapper;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.TONY.biz.line.service.IWebLineProductService;
import com.boco.TONY.biz.weboper.service.IWebOperLineService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.MapUtil;
import com.boco.util.JsonUtils;
import com.boco.util.security.cert.OfflineResolver;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * WebOperInfoAction���Ʋ�
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/webOperInfo/")
public class WebOperInfoController extends BaseController {
    @Autowired
    private IWebOperInfoService webOperInfoService;
    @Autowired
    private IFdBusinessDateService fdBusinessDateService;
    @Autowired
    private FdOrganMapper fdOrganMapper;
    @Autowired
    private IWebOperLineService webOperLineService;
    @Autowired
    private IWebLineProductService webLineProductService;
    @Autowired
    private FdOperMapper fdOperMapper;
    @Autowired
    protected WebOperLineMapper webOperLineMapper;
    @Autowired
    private WebOperRoleMapper webOperRoleMapper;

    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "��Ա�����б�", funCode = "PM-07", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PM/webOperInfo/webOperInfoList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "��Ա������ϸ", funCode = "PM-07-04", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(HttpSession session) throws Exception {
        //��������
        FdOrgan fdOrgan = (FdOrgan) session.getAttribute("sessionOrgan");
        String operCode = getParameter("operCode");
        WebOperInfo webOperInfo = new WebOperInfo();
        webOperInfo.setOperCode(operCode);
        //������Ȩ��,�������й�Ա
        if (!"11005293".equals(fdOrgan.getThiscode())) {
            webOperInfo.setOrganCode(fdOrgan.getThiscode());
        }

        List<WebOperInfo> webOperInfoList = webOperInfoService.selectByAttr(webOperInfo);
        if (CollectionUtils.isNotEmpty(webOperInfoList)) {
            webOperInfo = webOperInfoList.get(0);
        }
        setAttribute("OperInfo", webOperInfo);
        ListResult<WebOperLineDTO> webOperLineDTOList = webOperLineService.getAllWebOperLineByOperCode(webOperInfo.getOperCode(), 1);
        String lineNameList = "";
        for (WebOperLineDTO webOperLineDTO : webOperLineDTOList.getData()) {
            ProductLineInfoDTO lineInfo = webLineProductService.getProductLineInfoByLineId(webOperLineDTO.getLineId());
            if (Objects.nonNull(lineInfo)) {
                lineNameList += lineInfo.getLineName() + ",";
            }
        }
        if (StringUtils.isNotBlank(lineNameList)) {
            lineNameList = lineNameList.substring(0, lineNameList.length() - 1);
        } else {
            lineNameList = "��";
        }
        setAttribute("lineNameList", lineNameList);
        return basePath + "/PM/webOperInfo/webOperInfoInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��Ա��������", funCode = "PM-07-01", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI(HttpSession session) throws Exception {
        FdOrgan fdOrgan = (FdOrgan) session.getAttribute("sessionOrgan");
        setAttribute("organCode", fdOrgan.getThiscode());
        return basePath + "/PM/webOperInfo/webOperInfoAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��Ա�����޸�", funCode = "PM-07-02", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(WebOperInfo webOperInfo, HttpSession session) throws Exception {
        //��������
//        FdOrgan fdOrgan = (FdOrgan) session.getAttribute("sessionOrgan");
//        webOperInfo.setOrganCode(fdOrgan.getThiscode());
        setAttribute("OperInfo", webOperInfoService.selectByPK(MapUtil.beanToMap(webOperInfo)));
        return basePath + "/PM/webOperInfo/webOperInfoEdit";
    }

    /**
     * ���µ�ǰ�û���Ϣ
     * �����û�������ת�������޸�ҳ��
     *
     * @param webOperInfo
     * @return
     * @throws Exception
     */
    @RequestMapping("insertOrUpdate")
    @SystemLog(tradeName = "��Ա�����޸�", funCode = "system", funName = "�����޸�ҳ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public String insertOrUpdate(WebOperInfo webOperInfo, HttpSession session) throws Exception {
        //��ǰ����Ա
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        webOperInfo.setOperCode(fdOper.getOpercode());
        //��������
        FdOrgan fdOrgan = (FdOrgan) session.getAttribute("sessionOrgan");
        webOperInfo.setOrganCode(fdOrgan.getThiscode());
        //���ݲ���Ա��Ų�ѯ�Ƿ��ɴ˲���Ա��Ϣ�Ӷ���ת��ͬ��ҳ��
        int count = webOperInfoService.countByAttr(webOperInfo);
        if (count == 1) {
            WebOperInfo webOperInfo2 = webOperInfoService.selectByPK(MapUtil.beanToMap(webOperInfo));
            setAttribute("OperInfo", webOperInfo2);
            ListResult<WebOperLineDTO> webOperLineDTOList = webOperLineService.getAllWebOperLineByOperCode(webOperInfo.getOperCode(), 1);
            String lineNameList = "";
            for (WebOperLineDTO webOperLineDTO : webOperLineDTOList.getData()) {
                ProductLineInfoDTO lineInfo = webLineProductService.getProductLineInfoByLineId(webOperLineDTO.getLineId());
                lineNameList += lineInfo.getLineName() + ",";
            }
            setAttribute("lineNameList", lineNameList);
            return basePath + "/PM/webOperInfo/webOperInfoInfo";
        } else {
            setAttribute("OperInfo", webOperInfo);
            return basePath + "/PM/webOperInfo/webOperInfoInfo";
        }
    }


    /**
     * ��̬��ѯ������������
     *
     *
     * <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      �غ���      ��ѯ
     * </pre>
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "operName", method = RequestMethod.POST)
    @SystemLog(tradeName = "������������", funCode = "PM-07", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String operName(WebDeptInfo webDeptInfo, HttpSession session) throws Exception {
        //��������
        FdOrgan fdOrgan = (FdOrgan) session.getAttribute("sessionOrgan");
        webDeptInfo.setOrgancode(fdOrgan.getThiscode());
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }


    /**
     * TODO ��ѯWEB_OPER_INFO��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "PM-07", funName = "�����б�����", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findPage(WebOperInfo webOperInfo, HttpSession session) throws Exception {
        //��ȡ��ǰ����Ա����
        FdOrgan fdOrgan = (FdOrgan) session.getAttribute("sessionOrgan");
        FdOrgan operFdOrgan = fdOrganMapper.selectByPK(fdOrgan.getThiscode());
        String operdegree = getSessionUser().getOperdegree();
        int roleNum = (operdegree.length() + 1) / 3;
        boolean contains = false;
        double i;
        if (roleNum > 1) {
            i = (double) operdegree.split("001")[0].length() / 3;
            contains = (i + "").contains(".");
        }
        if (!operdegree.contains("001") || (operdegree.contains("001") && contains)) {//�������г�������Ա�������Ķ��߽��������
            webOperInfo.setOrganCode(fdOrgan.getThiscode());
        }
        int pageNum = Integer.valueOf(getParameter("pageNo"));
        int pageSize = Integer.valueOf(getParameter("pageSize"));
        return JSON.toJSONString(webOperInfoService.select(webOperInfo, pageNum, pageSize));
    }


    /**
     * TODO ����WEB_OPER_INFO.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "��Ա��������", funCode = "PM-07-01", funName = "����WebOperInfo", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(WebOperInfo webOperInfo, HttpSession session) throws Exception {
        //������Ա
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        webOperInfo.setLatestOperCode(fdOper.getOpercode());
        //��ǰ���ڡ���ǰʱ��
        String latestModifyDate = fdBusinessDateService.getCommonDateDetails();
        webOperInfo.setLatestModifyDate(latestModifyDate);
        webOperInfo.setLatestModifyTime(BocoUtils.getNowTime());
        this.json = webOperInfoService.insert(webOperInfo);
        return this.json.toJson();
    }

    /**
     * TODO �޸�WEB_OPER_INFO.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��Ա�����޸�", funCode = "PM-07-02", funName = "�޸�WebOperInfo", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(WebOperInfo webOperInfo, HttpSession session) throws Exception {
        //������Ա
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        webOperInfo.setLatestOperCode(fdOper.getOpercode());
        //��������
        FdOrgan fdOrgan = (FdOrgan) session.getAttribute("sessionOrgan");
        webOperInfo.setOrganCode(fdOrgan.getThiscode());
        //��ǰ���ڡ���ǰʱ��
        String latestModifyDate = fdBusinessDateService.getCommonDateDetails();
        webOperInfo.setLatestModifyDate(latestModifyDate);
        webOperInfo.setLatestModifyTime(BocoUtils.getNowTime());
        try {
            this.json = webOperInfoService.update(webOperInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.json.toJson();
    }

    /**
     * TODO ɾ��WEB_OPER_INFO
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "��������", funCode = "δ��д", funName = "ɾ��WebOperInfo", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(WebOperInfo webOperInfo) throws Exception {
        WebOperLineDO webOperLineDO = new WebOperLineDO();
        webOperLineDO.setOperCode(webOperInfo.getOperCode());
        webOperLineDO.setStatus(1);
        List<WebOperLineDO> allWebOperLineByOperCode = webOperLineMapper.getAllWebOperLineByOperCode(webOperLineDO);
        List<String> operRole=webOperRoleMapper.selectOwnRoleByOperCode(webOperInfo.getOperCode());
        if (allWebOperLineByOperCode.size() == 0 && operRole.size() == 0) {
            webOperInfoService.deleteByPK(MapUtil.beanToMap(webOperInfo));
            return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
        } else {
            return this.json.returnMsg("false", "ɾ��ʧ�ܣ�����ɾ����Ա�Ľ�ɫ��������Ϣ!").toJson();

        }
    }

    @RequestMapping(value = "check")
    @SystemLog(tradeName = "��������", funCode = "PM-07", funName = "��֤���в�����ά��������Ա", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String check(HttpSession session) throws Exception {
        String opersOrganCode = getParameter("organcode");
        FdOrgan thisFdOrgan = (FdOrgan) session.getAttribute("sessionOrgan");
        String thiscode = thisFdOrgan.getThiscode();
        FdOrgan operFdOrgan = fdOrganMapper.selectByPK(opersOrganCode);
        String opersCountrycode = operFdOrgan.getCountrycode();
        String opersProvincecode = operFdOrgan.getProvincecode();
        String opersAreacode = operFdOrgan.getAreacode();
        String opersCitycode = operFdOrgan.getCitycode();
        String opersBranchcode = operFdOrgan.getBranchcode();
        //����
        if (!thiscode.equals("11005293")) {
            if (!opersOrganCode.equals(thiscode)) {
                return this.json.setSuccess("false").setMsg("���л���ֻ��ά���÷��е���Ա��Ϣ").toJson();
            } else {
                FdOrgan fdOrgan_ = fdOrganMapper.selectByPK(opersOrganCode);
                if (fdOrgan_ == null) {
                    return this.json.setSuccess("false").setMsg("��������ȷ�Ļ������룡").toJson();
//                return this.json.returnMsg("false", "��������ȷ�Ļ������룡").toJson();
                }
                this.json.setSuccess("true");
                return this.json.toJson();
            }
        }
        //����
        this.json.setSuccess("true");
        return this.json.toJson();
    }


    /**
     * TODO ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29     �غ���      �����½�
     * </pre>
     */
    @RequestMapping(value = "checkAndGetOperName", method = RequestMethod.POST)
    @SystemLog(tradeName = "��鲢����ʾ��Ա����", funCode = "PM-07", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String checkAndGetOperName(WebOperInfo webOperInfo, HttpServletRequest request) throws Exception {
        String operCode = request.getParameter("operCode");
        if (operCode != null && "".equals(operCode)) {
            webOperInfo.setOperCode(operCode);
        }
        FdOrgan sessionOrgan = getSessionOrgan();
        webOperInfo.setOrganCode(sessionOrgan.getThiscode());
        WebOperInfo operInfo = webOperInfoService.selectOperCode(webOperInfo);
        if (Objects.nonNull(operInfo)) {
            return this.json.setSuccess("false").setMsg("��ǰ��Ա�Ѵ���!").toJson();
        }
        FdOper fdOper = new FdOper();
        fdOper.setOpercode(operCode);
        List<FdOper> fdOperList = fdOperMapper.selectByAttr(fdOper);
        if (CollectionUtils.isEmpty(fdOperList)) {
            return this.json.setSuccess("false").setMsg("��Ա���в����ڸù�Ա!").toJson();
        }
        return this.json.setSuccess("true").setMsg(fdOperList.get(0).getOpername()).toJson();
    }


    /**
     * TODO ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29     �غ���      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectOperCode")
    @SystemLog(tradeName = "�����Ա��", funCode = "PM-07", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectOperCode(WebOperInfo webOperInfo, HttpServletRequest request) throws Exception {
        String operCode = request.getParameter("operCode").replace("'", "");
        if (operCode != null) {
            webOperInfo.setOperCode(operCode);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Set<String> set = new TreeSet<String>();
        if (operCode.length()>6) {
            List<Map<String, String>> webOperInfoList = webOperInfoService.selectOperCodeLike(webOperInfo);
            for (Map<String, String> deptInfo : webOperInfoList) {
                String data = deptInfo.get("oper_code");
                set.add(data);
            }
            for (String data : set) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", data);
                map.put("value", data);
                list.add(map);
            }
            resultMap.put("list", list);
        }
        return JsonUtils.toJson(resultMap);
    }

    /**
     * TODO ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29     �غ���      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectOperName")
    @SystemLog(tradeName = "��������", funCode = "PM-07", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectOperName(WebOperInfo webOperInfo, HttpServletRequest request) throws Exception {
        String operName = request.getParameter("operName").replace("'", "");
        if (operName != null) {
            webOperInfo.setOperName(operName);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Set<String> set = new TreeSet<String>();
        List<Map<String, String>> webOperInfoList = webOperInfoService.selectOperName(webOperInfo);
        for (Map<String, String> deptInfo : webOperInfoList) {
            String data = deptInfo.get("oper_name");
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        String json = JsonUtils.toJson(resultMap);
        return json;
    }
}