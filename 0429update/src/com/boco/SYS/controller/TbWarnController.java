package com.boco.SYS.controller;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbWarn;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.service.ITbWarnService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Action���Ʋ�
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbWarn/")
public class TbWarnController extends BaseController {
    @Autowired
    private ITbWarnService tbWarnService;
    @Autowired
    private LoanCombMapper loanCombMapper;

    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "Ԥ���߹���", funCode = "SYS-02", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PM/warnManger/tbWarnList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "Ԥ���߹���", funCode = "SYS-02-04", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbWarn tbWarn) throws Exception {
        setAttribute("TbWarn", tbWarnService.selectByPK(tbWarn.getWarnId()));
        return basePath + "/PM/warnManger/tbWarnInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "Ԥ���߹���", funCode = "SYS-02-01", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PM/warnManger/tbWarnAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "Ԥ���߹���", funCode = "SYS-02-02", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbWarn tbWarn) throws Exception {
        setAttribute("TbWarn", tbWarnService.selectByPK(tbWarn.getWarnId()));
        return basePath + "/PM/warnManger/tbWarnEdit";
    }

    /**
     * TODO ��ѯtb_warn��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "Ԥ���߹���", funCode = "SYS-02", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbWarn tbWarn, HttpSession session, HttpServletRequest request) throws Exception {
        setPageParam();
        String warnId = null;
        if (request.getParameter("warnId1") != null) {
            try {
                if (request.getParameter("warnId1").replace("'", "").matches("\\d+")) {
                    warnId = getParameter("warnId1").replace("'", "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (warnId != null) {
                tbWarn.setWarnId(Integer.valueOf(warnId));
            }
//            else {
//                tbWarn.setWarnId(999999999);
//            }
        }
        if (request.getParameter("warnName") != null) {
            String warnName = getParameter("warnName").replace("'", "");
            if (warnName != null) {
                tbWarn.setWarnName(warnName);
            }
        }
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbWarn.setWarnOrgan(Integer.parseInt(fdOper.getOrgancode()));
//        List<TbWarn> list = tbWarnService.selectByAttr(tbWarn);
        List<TbWarn> list = null;
        try {
            list = tbWarnService.selectByLikeAttr(tbWarn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageData(list);
    }

    /**
     * TODO ����tb_warn.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "Ԥ���߹���", funCode = "SYS-02", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(TbWarn tbWarn, HttpSession session) throws Exception {

        //������Ա
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbWarn.setWarnOrgan(Integer.parseInt(fdOper.getOrgancode()));
        tbWarn.setWarnCreateOper(fdOper.getOpercode());
        tbWarn.setWarnUpdateOper(fdOper.getOpercode());
        tbWarn.setWarnCreateTime(BocoUtils.getTime());
        tbWarn.setWarnUpdateTime(BocoUtils.getTime());

        TbWarn tbWarn1 = new TbWarn();
        tbWarn1.setWarnOrgan(Integer.parseInt(fdOper.getOrgancode()));
        tbWarn1.setWarnType(tbWarn.getWarnType());
        String warnCombStr = tbWarn.getWarnComb();
        String[] warnCombArr = warnCombStr.split(",");
        //����������Ԥ��������Ч
        if (warnCombArr != null && warnCombArr.length == 1) {
            tbWarn1.setWarnComb(warnCombArr[0]);
            List<TbWarn> tbWarns = tbWarnService.selectByAttr(tbWarn1);
            if (tbWarns.size() == 0) {
                int count = tbWarnService.insertEntity(tbWarn);
                if (count == 1) {
                    //"�����ɹ�!"
                    return this.json.returnMsg("true", getErrorInfo("w456")).toJson();
                }
                //����ʧ��
                return this.json.returnMsg("false", getErrorInfo("w446")).toJson();
            } else {
                return this.json.returnMsg("false", "����ʧ�ܣ��û����¸�Ԥ������������ͬԤ������").toJson();
            }

        } else if (warnCombArr != null && warnCombArr.length > 1) {
            //��������
            List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());

            for (String combCode : warnCombArr) {
                tbWarn1.setWarnComb(combCode);
                List<TbWarn> tbWarns = tbWarnService.selectByAttr(tbWarn1);
                if (tbWarns.size() > 0) {
                    return this.json.returnMsg("false", "����ʧ�ܣ��û����¸�Ԥ������������ͬԤ������").toJson();
                }
            }
            List<TbWarn> insertList = new ArrayList<>();
            Map<String, TbWarn> tempMap = new HashMap<>();
            for (String combCode : warnCombArr) {
                for (LoanCombDO tempComb : loanCombDOS) {
                    if (combCode.equals(tempComb.getCombCode())) {
                        tbWarn.setWarnName(tempComb.getCombName());
                    }
                }
                tbWarn.setWarnComb(combCode);
                TbWarn tempTb = copyTbWarn(tbWarn);
                tempMap.put(combCode, tempTb);
            }
            for (String combCode : warnCombArr) {
                insertList.add(tempMap.get(combCode));
            }
            try {
                tbWarnService.insertBatch(insertList);
                return this.json.returnMsg("true", getErrorInfo("w456")).toJson();
            } catch (Exception e) {
                //����ʧ��
                logger.info(e.toString());
                return this.json.returnMsg("false", getErrorInfo("w446")).toJson();
            }
        }
        return this.json.returnMsg("false", "����ʧ�ܣ��û����¸�Ԥ������������ͬԤ������").toJson();
    }

    /**
     * copy class
     *
     * @param tbWarn
     * @return
     */
    private TbWarn copyTbWarn(TbWarn tbWarn) {
        TbWarn tempTbWarn = new TbWarn();
        tempTbWarn.setWarnName(tbWarn.getWarnName());
        tempTbWarn.setWarnComb(tbWarn.getWarnComb());
        tempTbWarn.setWarnOrgan(tbWarn.getWarnOrgan());
        tempTbWarn.setWarnType(tbWarn.getWarnType());
        tempTbWarn.setWarnUpdateOper(tbWarn.getWarnUpdateOper());
        tempTbWarn.setWarnUpdateTime(tbWarn.getWarnUpdateTime());
        tempTbWarn.setWarnCreateOper(tbWarn.getWarnCreateOper());
        tempTbWarn.setWarnCreateTime(tbWarn.getWarnCreateTime());
        tempTbWarn.setWarnOneLine(tbWarn.getWarnOneLine());
        tempTbWarn.setWarnTwoLine(tbWarn.getWarnTwoLine());
        tempTbWarn.setWarnThreeLine(tbWarn.getWarnThreeLine());
        tempTbWarn.setWarnFourLine(tbWarn.getWarnFourLine());
        tempTbWarn.setWarnFiveLine(tbWarn.getWarnFiveLine());
        return tempTbWarn;
    }

    /**
     * TODO �޸�tb_warn.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "Ԥ���߹���", funCode = "SYS-02", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(TbWarn tbWarn, HttpSession session) throws Exception {

        //������Ա
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        Json json = new Json();
        tbWarn.setWarnUpdateOper(fdOper.getOpercode());
        tbWarn.setWarnUpdateTime(BocoUtils.getTime());

        TbWarn tbWarn1 = new TbWarn();
        tbWarn1.setWarnOrgan(Integer.parseInt(fdOper.getOrgancode()));
        tbWarn1.setWarnComb(tbWarn.getWarnComb());
        tbWarn1.setWarnType(tbWarn.getWarnType());
        tbWarn1.setWarnId(tbWarn.getWarnId());

        List<TbWarn> tbWarns = tbWarnService.selectDistinctTbwarn(tbWarn1);

        if (tbWarns.size() == 0) {
            int count = tbWarnService.updateByPK(tbWarn);
            if (count == 1) {
                //"�޸ĳɹ�!"
                return this.json.returnMsg("true", getErrorInfo("w448")).toJson();
            }
            //�޸�ʧ��
            return this.json.returnMsg("false", getErrorInfo("w447")).toJson();
        }
        return this.json.returnMsg("false", "�޸�ʧ�ܣ��û����¸�Ԥ������������ͬԤ������").toJson();
    }

    /**
     * TODO ɾ��tb_warn
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "Ԥ���߹���", funCode = "SYS-02-03", funName = "ɾ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbWarn tbWarn, HttpSession session) throws Exception {

        int count = tbWarnService.deleteByPK(tbWarn.getWarnId());
        if (count == 1) {
            //"ɾ���ɹ�!"
            return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
        }
        return this.json.returnMsg("false", "ɾ��ʧ��!").toJson();

    }


    /**
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectWarnId")
    @SystemLog(tradeName = "Ԥ���߹���", funCode = "SYS-02", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectWarnId(TbWarn tbWarn, HttpServletRequest request) throws Exception {
        String warnId = null;
        if (request.getParameter("warnId1").replace("'", "").matches("\\d+")) {
            warnId = getParameter("warnId1").replace("'", "");
        }
        if (warnId != null) {
            tbWarn.setWarnId(Integer.valueOf(warnId));
        } else {
            tbWarn.setWarnId(999999999);
        }
        FdOper fdOper = (FdOper) request.getSession().getAttribute("sessionUser");
        tbWarn.setWarnOrgan(Integer.parseInt(fdOper.getOrgancode()));
        Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Set<String> set = new TreeSet<String>();
        List<Map<String, Integer>> tbWarnList = tbWarnService.selectWarnId(tbWarn);
        for (Map<String, Integer> deptInfo : tbWarnList) {
            String data = String.valueOf(deptInfo.get("warn_id"));
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

    /**
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectWarnName")
    @SystemLog(tradeName = "Ԥ���߹���", funCode = "SYS-02", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectWarnName(TbWarn tbWarn, HttpServletRequest request) throws Exception {
        String warnName = getParameter("warnName").replace("'", "");
        if (warnName != null) {
            tbWarn.setWarnName(warnName);
        }
        FdOper fdOper = (FdOper) request.getSession().getAttribute("sessionUser");
        tbWarn.setWarnOrgan(Integer.parseInt(fdOper.getOrgancode()));
        Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Set<String> set = new TreeSet<String>();
        List<Map<String, String>> tbWarnList = tbWarnService.selectWarnNameByOrgan(tbWarn);
        for (Map<String, String> deptInfo : tbWarnList) {
            String data = deptInfo.get("warn_name");
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


    /**
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectWarnOrgan")
    @SystemLog(tradeName = "Ԥ���߹���", funCode = "SYS-02", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectWarnOrgan(TbWarn tbWarn, HttpServletRequest request) throws Exception {
        String warnOrgan = request.getParameter("warnOrgan").replace("'", "");
        if (warnOrgan != null) {
            tbWarn.setWarnOrgan(Integer.valueOf(warnOrgan));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Set<String> set = new TreeSet<String>();
        List<Map<String, String>> tbWarnList = tbWarnService.selectWarnOrgan(tbWarn);
        for (Map<String, String> deptInfo : tbWarnList) {
            String data = deptInfo.get("warn_organ");
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