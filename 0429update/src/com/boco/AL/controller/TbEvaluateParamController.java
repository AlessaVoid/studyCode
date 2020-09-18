package com.boco.AL.controller;

import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.ITbEvaluateParamService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbEvaluateParam;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import com.boco.util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.base.BaseController;

/**
 * Action���Ʋ�
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/evaluateManger/tbEvaluateParam/")
public class TbEvaluateParamController extends BaseController {
    @Autowired
    private ITbEvaluateParamService tbEvaluateParamService;

    @RequestMapping("listUI")
    @SystemLog(tradeName = "���۲���ά��", funCode = "AL-02-01", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbEvaluateManage/tbEvaluateParam/tbEvaluateParamList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "���۲���ά��", funCode = "AL-02-01-04", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbEvaluateParam tbEvaluateParam) throws Exception {
        setAttribute("tbEvaluateParamList", tbEvaluateParamService.selectByAttr(tbEvaluateParam));
        return basePath + "/AL/tbEvaluateManage/tbEvaluateParam/tbEvaluateParamInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "���۲���ά��", funCode = "AL-02-01-01", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/AL/tbEvaluateManage/tbEvaluateParam/tbEvaluateParamAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "���۲���ά��", funCode = "AL-02-01-02", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbEvaluateParam tbEvaluateParam) throws Exception {
        setAttribute("tbEvaluateParamList", tbEvaluateParamService.selectByAttr(tbEvaluateParam));
        return basePath + "/AL/tbEvaluateManage/tbEvaluateParam/tbEvaluateParamEdit";
    }

    /**
     * TODO ��ѯtb_evaluate_param��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "���۲���ά��", funCode = "AL-02-01", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbEvaluateParam tbEvaluateParam) throws Exception {
        setPageParam();
        List<TbEvaluateParam> result = tbEvaluateParamService.selectByAttr(tbEvaluateParam);
        List<TbEvaluateParam> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        //���� ͬһcomb������
        for (TbEvaluateParam tbParam : result) {
            String type = tbParam.getTpComb();
            if (!set.contains(type)) {
                list.add(tbParam);
            }
            set.add(type);
        }
        return pageData(list);
    }

    /**
     * TODO ����tb_evaluate_param.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "���۲���ά��", funCode = "AL-02-01", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(HttpServletRequest request, HttpSession session) throws Exception {
        return deleteAndInsertOrUpdate(request, session);
    }


    /**
     * �������¶�����ɾ���ٲ���
     *
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    private String deleteAndInsertOrUpdate(HttpServletRequest request, HttpSession session) throws Exception {
        //������Ա
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        Json json = new Json();
        String data = request.getParameter("data");
        JSONArray jsonArray = JSONArray.parseArray(data);
        List<TbEvaluateParam> tbEvaluateParamArrayList = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            TbEvaluateParam tbEvaluateParam = new TbEvaluateParam();
            tbEvaluateParam.setTpName(jsonObject.getString("tpName"));
            tbEvaluateParam.setTpComb(jsonObject.getString("tpComb"));
            tbEvaluateParam.setTpFullScore(jsonObject.getInteger("tpFullScore"));
            tbEvaluateParam.setTpMin(jsonObject.getDouble("tpMin"));
            tbEvaluateParam.setTpMax(jsonObject.getDouble("tpMax"));
            tbEvaluateParam.setTpDeduction(jsonObject.getInteger("tpDeduction"));
            tbEvaluateParam.setTpCreateTime(BocoUtils.getTime());
            tbEvaluateParam.setTpCreateOper(fdOper.getOpercode());
            tbEvaluateParam.setTpState(TbEvaluateParam.STATE_NEW);
            tbEvaluateParamArrayList.add(tbEvaluateParam);
        }
        try {
            tbEvaluateParamService.deleteByWhere("tp_comb='" + tbEvaluateParamArrayList.get(0).getTpComb() + "'");
            tbEvaluateParamService.insertBatch(tbEvaluateParamArrayList);
        } catch (Exception e) {
            e.printStackTrace();
            return json.returnMsg("false", getErrorInfo("w447")).toJson();
        }
        return json.returnMsg("true", getErrorInfo("w456")).toJson();
    }


    /**
     * TODO �޸�tb_evaluate_param.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "���۲���ά��", funCode = "AL-02-01", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest httpServletRequest, HttpSession session) throws Exception {
        return deleteAndInsertOrUpdate(httpServletRequest, session);
    }

    /**
     * TODO ɾ��tb_evaluate_param
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "���۲���ά��", funCode = "AL-02-01-04", funName = "ɾ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbEvaluateParam tbEvaluateParam, HttpSession session) throws Exception {
        tbEvaluateParamService.deleteByWhere("tp_comb='" + tbEvaluateParam.getTpComb() + "'");
        return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
    }

    /**
     * ����tb_punish_param
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "deploy")
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "AL-01-01-05", funName = "����TbPunishParam", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String deploy(TbEvaluateParam tbEvaluateParam) throws Exception {
        Json json;
        json = tbEvaluateParamService.deploy();//TODO ���𷽷�������
        return json.toJson();
    }


    /**
     * ���������
     * ����id��ѯ���۲���
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectTpId")
    @SystemLog(tradeName = "ά�����۲���", funCode = "AL-02-01", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectTpId(TbEvaluateParam tbEvaluateParam, HttpServletRequest request) throws Exception {
        String tpId = request.getParameter("tpId").replace("'","");
        if (tpId != null) {
            tbEvaluateParam.setTpId(Integer.valueOf(tpId));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, Integer>> tbEvaluateParamList = tbEvaluateParamService.selectTpId(tbEvaluateParam);
        for (Map<String, Integer> deptInfo : tbEvaluateParamList) {
            String data = String.valueOf(deptInfo.get("tpId"));
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<>(4);
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
    @RequestMapping(value = "selectTpComb")
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "AL-01-01", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectTpComb(TbEvaluateParam tbEvaluateParam, HttpServletRequest request) throws Exception {
        String tpComb = request.getParameter("tpComb").replace("'","");
        if (tpComb != null) {
            tbEvaluateParam.setTpComb(tpComb);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>(4);
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> tbEvaluateParamList = tbEvaluateParamService.selectTpComb(tbEvaluateParam);
        for (Map<String, String> deptInfo : tbEvaluateParamList) {
            String data = deptInfo.get("pp_organ");
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        String json = JsonUtils.toJson(resultMap);
        return json;
    }


}