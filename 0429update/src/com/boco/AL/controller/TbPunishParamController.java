package com.boco.AL.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.AL.service.ITbPunishParamService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbPunishParam;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * ��Ϣ������Ʋ�
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/punishManger/punishParam/")
public class TbPunishParamController extends BaseController {
    @Autowired
    private ITbPunishParamService tbPunishParamService;

    @RequestMapping("listUI")
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "AL-01-01", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbPunishManage/punishParam/tbPunishParamList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "AL-01-01-04", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbPunishParam tbPunishParam) throws Exception {
        setAttribute("TbPunishParamDTOList", tbPunishParamService.selectByAttr(tbPunishParam));
        return basePath + "/AL/tbPunishManage/punishParam/tbPunishParamInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "AL-01-01-01", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/AL/tbPunishManage/punishParam/tbPunishParamAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "AL-01-01-02", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbPunishParam tbPunishParam) throws Exception {
        setAttribute("TbPunishParamDTOList", tbPunishParamService.selectByAttr(tbPunishParam));
        return basePath + "/AL/tbPunishManage/punishParam/tbPunishParamEdit";
    }


    /**
     * ��ѯtb_punish_param��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "AL-01-01", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbPunishParam tbPunishParam) throws Exception {
//        setPageParam();
        List<TbPunishParam> result = tbPunishParamService.selectByAttr(tbPunishParam);
        List<TbPunishParam> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (TbPunishParam tbParam : result) {
            Integer type = tbParam.getType();
            if (!set.contains(type)) {
                list.add(tbParam);
            }
            set.add(type);
        }
        return pageData(list);
    }

    /**
     * ����tb_punish_param.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "AL-01-01-01", funName = "����TbPunishParam", accessType = AccessType.WRITE, level = Debug.INFO)
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
        List<TbPunishParam> tbPunishParamList = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            TbPunishParam tbPunishParam = new TbPunishParam();
            tbPunishParam.setType(jsonObject.getInteger("type"));
            tbPunishParam.setPpType(jsonObject.getInteger("ppType"));
            tbPunishParam.setCollecttype(jsonObject.getInteger("collecttype"));
            tbPunishParam.setState(jsonObject.getInteger("state"));
            tbPunishParam.setMinnum(new BigDecimal(jsonObject.getLong("minnum")));
            tbPunishParam.setMaxnum(new BigDecimal(jsonObject.getLong("maxnum")));
            tbPunishParam.setInterest(new BigDecimal(jsonObject.getLong("interest")));
            tbPunishParam.setPpOrgan("0");
            tbPunishParam.setCreatetime(BocoUtils.getTime());
            tbPunishParam.setCreateuserid(fdOper.getOpercode());
            tbPunishParam.setUpdatetime(BocoUtils.getTime());
            tbPunishParam.setUpdateuserid(fdOper.getOpercode());
            tbPunishParamList.add(tbPunishParam);
        }
        try {
            tbPunishParamService.deleteByWhere("type=" + tbPunishParamList.get(0).getType());
            tbPunishParamService.insertBatch(tbPunishParamList);
        } catch (Exception e) {
            e.printStackTrace();
            return json.returnMsg("false", getErrorInfo("w447")).toJson();
        }
        return json.returnMsg("true", getErrorInfo("w456")).toJson();
    }

    /**
     * �޸�tb_punish_param.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "AL-01-01-02", funName = "�޸�TbPunishParam", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {
        return deleteAndInsertOrUpdate(request, session);
    }

    /**
     * ɾ��tb_punish_param
     * ֱ��ɾ��һ������м�¼
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "deleteTbPunishParam")
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "AL-01-01-03", funName = "ɾ��TbPunishParam", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbPunishParam tbPunishParam) throws Exception {
        tbPunishParamService.deleteByWhere("type = " + tbPunishParam.getType());
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
    String deploy(TbPunishParam tbPunishParam) throws Exception {
        Json json;
        json = tbPunishParamService.deploy();//TODO ���𷽷�������
        return json.toJson();
    }


    /**
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectPpId")
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "AL-01-01", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectPpId(TbPunishParam tbPunishParam, HttpServletRequest request) throws Exception {
        String ppId = request.getParameter("ppId").replace("'","");
        if (ppId != null) {
            tbPunishParam.setPpId(Integer.valueOf(ppId));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>(4);
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, Integer>> tbPunishParamList = tbPunishParamService.selectPpId(tbPunishParam);
        for (Map<String, Integer> deptInfo : tbPunishParamList) {
            String data = String.valueOf(deptInfo.get("pp_id"));
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
    @RequestMapping(value = "selectPpName")
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "AL-01-01", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectPpName(TbPunishParam tbPunishParam, HttpServletRequest request) throws Exception {
        String ppName = request.getParameter("ppName").replace("'","");
        if (ppName != null) {
            tbPunishParam.setPpName(ppName);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> tbPunishParamList = tbPunishParamService.selectPpName(tbPunishParam);
        for (Map<String, String> deptInfo : tbPunishParamList) {
            String data = deptInfo.get("pp_name");
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
    @RequestMapping(value = "selectPpOrgan")
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "AL-01-01", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectPpOrgan(TbPunishParam tbPunishParam, HttpServletRequest request) throws Exception {
        String ppOrgan = request.getParameter("ppOrgan").replace("'","");
        if (ppOrgan != null) {
            tbPunishParam.setPpOrgan(ppOrgan);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>(4);
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> tbPunishParamList = tbPunishParamService.selectPpOrgan(tbPunishParam);
        for (Map<String, String> deptInfo : tbPunishParamList) {
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