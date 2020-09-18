package com.boco.PM.controller;

//import com.boco.AL.service.impl.PunishInterestService;
import com.boco.AL.service.impl.PunishInterestService;
import com.boco.PM.service.IFdOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WebDeptInfoAction���Ʋ�
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/WebOrganInfo/")
public class WebOrganInfoController extends BaseController {
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IFdBusinessDateService fdBusinessDateService;
    @Autowired
    private PunishInterestService punishInterestService;
    @Autowired
    private FdOrganMapper fdOrganMapper;

    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "���������б�", funCode = "PM-06", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PM/webOrganInfo/webOrganInfoList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "����������ϸ��Ϣ", funCode = "PM-06-04", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI() throws Exception {
        String organCode = getParameter("organCode");
        FdOrgan fdOrgan = fdOrganService.selectByPK(organCode);
        setAttribute("fdOrgan", fdOrgan);
        return basePath + "/PM/webOrganInfo/webOrganInfoInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "������������", funCode = "PM-06-01", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PM/webOrganInfo/webOrganInfoAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "���������޸�", funCode = "PM-06-02", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(FdOrgan fdOrgan) throws Exception {
        List<FdOrgan> fdOrganList = fdOrganService.selectByAttr(fdOrgan);
        setAttribute("organInfo", fdOrganList.get(0));
        return basePath + "/PM/webOrganInfo/webOrganInfoEdit";
    }

    /**
     * TODO ��ѯWEB_DEPT_INFO��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "���������б�", funCode = "PM-06", funName = "�����б�����", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findPage(FdOrgan fdOrgan) throws Exception {
        setPageParam();
        String organCode = getParameter("thiscode");
        String organName = getParameter("organname");
        fdOrgan.setUporgan(getSessionOrgan().getThiscode());

        fdOrgan.setOrganname(organName);
        fdOrgan.setThiscode(organCode);
        List<FdOrgan> fdOrganList = fdOrganService.selectByLikeAttr(fdOrgan);
        return pageData(fdOrganList);
    }

    @RequestMapping(value = "findList", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "system", funName = "�����б�����", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String find(HttpServletRequest request, FdOrgan fdOrgan) throws Exception {
        setPageParam();
//        punishInterestService.punishCalculate(1, 1, 1, 1, new BigDecimal("1"), new BigDecimal("1"), new BigDecimal("1"));
        String sort = request.getParameter("sort");
        String direction = request.getParameter("direction");
        if (sort != null) {
            sort = sort + " " + direction;
        }
        String organCode = getParameter("thiscode");
        String organName = getParameter("organname");
        String organlevel = getParameter("organlevel");
        String uporgan =getParameter("uporgan");
        List<String> thiscodeList = null;

        if(uporgan != null && !"".equals(uporgan)){
            thiscodeList = fdOrganMapper.selectthiscodeByLikeOrganname(uporgan);
        }
        if (organlevel == null || "".equals(organlevel)) {
            organlevel = getSessionOrgan().getOrganlevel();
            Map<String, Object> map = new HashMap<>();
            map.put("organcode", organCode);
            map.put("organname", organName);
            map.put("organlevel", organlevel);
            map.put("session_organcode", getSessionOrgan().getThiscode());
            map.put("sort", sort);
            map.put("uporgan",thiscodeList);
            List<FdOrgan> list = fdOrganService.selectList(map);
            for (FdOrgan organ : list) {
                String cityName = fdOrganMapper.selectOrganName(organ.getCitycode());
                organ.setCitycode(cityName);
                String areaName = fdOrganMapper.selectOrganName(organ.getAreacode());
                organ.setAreacode(areaName);
                String upOrganName = fdOrganMapper.selectOrganName(organ.getUporgan());
                organ.setUporgan(upOrganName);
            }
            return pageData(list);
        } else {
            organlevel = getParameter("organlevel");
            String thisorganlevel = getSessionOrgan().getOrganlevel();

            Map<String, Object> map = new HashMap<>();
            map.put("thisorganlevel", thisorganlevel);
            map.put("organcode", organCode);
            map.put("organname", organName);
            map.put("organlevel", organlevel);
            map.put("session_organcode", getSessionOrgan().getThiscode());
            map.put("sort", sort);
            map.put("uporgan",thiscodeList);

            List<FdOrgan> list = fdOrganService.selectByLevel(map);
            for (FdOrgan organ : list) {
                String cityName = fdOrganMapper.selectOrganName(organ.getCitycode());
                organ.setCitycode(cityName);
                String areaName = fdOrganMapper.selectOrganName(organ.getAreacode());
                organ.setAreacode(areaName);
                String upOrganName = fdOrganMapper.selectOrganName(organ.getUporgan());
                organ.setUporgan(upOrganName);
            }
            return pageData(list);
        }
    }

    /**
     * TODO ����WEB_DEPT_INFO.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "������������", funCode = "PM-06-01", funName = "����WebDeptInfo", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(FdOrgan fdOrgan, HttpSession session) throws Exception {
        //������Ա
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");

        String latestModifyDate = fdBusinessDateService.getCommonDateDetails();
        fdOrgan.setModifyoper(fdOper.getOpercode());
        fdOrgan.setModifyorgan(fdOper.getOrgancode());
        //��ǰ���ڡ���ǰʱ��
        fdOrgan.setModifydate(latestModifyDate);
        fdOrganService.updateByPK(fdOrgan);
        fdOrganService.insertEntity(fdOrgan);
        return this.json.toJson();


    }

    /**
     * TODO �޸�WEB_DEPT_INFO.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "���������޸�", funCode = "PM-06-02", funName = "�޸�WebDeptInfo", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(FdOrgan fdOrgan, HttpSession session) throws Exception {
        //��ǰ����Ա
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        fdOrgan.setModifyoper(fdOper.getOpercode());
        fdOrgan.setModifyorgan(fdOper.getOrgancode());
        //��ǰ���ڡ���ǰʱ��
        String latestModifyDate = fdBusinessDateService.getCommonDateDetails();
        fdOrgan.setModifydate(latestModifyDate);
        fdOrganService.updateByPK(fdOrgan);
        return this.json.toJson();
    }

    /**
     * TODO ɾ��WEB_DEPT_INFO
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "��������ɾ��", funCode = "PM-06-03", funName = "ɾ��WebDeptInfo", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete() throws Exception {
        String organCode = getParameter("organCode");
        fdOrganService.deleteByPK(organCode);
        json.setSuccess("true").setMsg("����ɾ���ɹ�");
        return this.json.toJson();
    }

    /**
     * TODO ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectCode")
    @SystemLog(tradeName = "����������������", funCode = "PM-06", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectCode(HttpServletRequest request) throws Exception {
        String thisCode = getParameter("thiscode").replace("'", "");
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setThiscode(thisCode);
        fdOrgan.setUporgan(getSessionOrgan().getThiscode());
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        if (thisCode.length() > 4) {
            List<String> organCodeList = fdOrganService.selectByThisCode(fdOrgan);
            for (String data : organCodeList) {
                Map<String, String> map = new HashMap<>();
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
     * 2014-10-29      �غ���      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectName")
    @SystemLog(tradeName = "����������������", funCode = "PM-06", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectName() throws Exception {
        String organName = getParameter("organname").replace("'", "");
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setOrganname(organName);
        fdOrgan.setUporgan(getSessionOrgan().getThiscode());
        List<String> organNameList = fdOrganService.selectByName(fdOrgan);
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        for (String data : organNameList) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }

    }
