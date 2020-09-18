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
 * Action控制层
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/evaluateManger/tbEvaluateParam/")
public class TbEvaluateParamController extends BaseController {
    @Autowired
    private ITbEvaluateParamService tbEvaluateParamService;

    @RequestMapping("listUI")
    @SystemLog(tradeName = "评价参数维护", funCode = "AL-02-01", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbEvaluateManage/tbEvaluateParam/tbEvaluateParamList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "评价参数维护", funCode = "AL-02-01-04", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbEvaluateParam tbEvaluateParam) throws Exception {
        setAttribute("tbEvaluateParamList", tbEvaluateParamService.selectByAttr(tbEvaluateParam));
        return basePath + "/AL/tbEvaluateManage/tbEvaluateParam/tbEvaluateParamInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "评价参数维护", funCode = "AL-02-01-01", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/AL/tbEvaluateManage/tbEvaluateParam/tbEvaluateParamAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "评价参数维护", funCode = "AL-02-01-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbEvaluateParam tbEvaluateParam) throws Exception {
        setAttribute("tbEvaluateParamList", tbEvaluateParamService.selectByAttr(tbEvaluateParam));
        return basePath + "/AL/tbEvaluateManage/tbEvaluateParam/tbEvaluateParamEdit";
    }

    /**
     * TODO 查询tb_evaluate_param分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "评价参数维护", funCode = "AL-02-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbEvaluateParam tbEvaluateParam) throws Exception {
        setPageParam();
        List<TbEvaluateParam> result = tbEvaluateParamService.selectByAttr(tbEvaluateParam);
        List<TbEvaluateParam> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        //过滤 同一comb的评价
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
     * TODO 新增tb_evaluate_param.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "评价参数维护", funCode = "AL-02-01", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(HttpServletRequest request, HttpSession session) throws Exception {
        return deleteAndInsertOrUpdate(request, session);
    }


    /**
     * 插入或更新都是先删除再插入
     *
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    private String deleteAndInsertOrUpdate(HttpServletRequest request, HttpSession session) throws Exception {
        //最后操作员
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
     * TODO 修改tb_evaluate_param.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "评价参数维护", funCode = "AL-02-01", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest httpServletRequest, HttpSession session) throws Exception {
        return deleteAndInsertOrUpdate(httpServletRequest, session);
    }

    /**
     * TODO 删除tb_evaluate_param
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "评价参数维护", funCode = "AL-02-01-04", funName = "删除", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbEvaluateParam tbEvaluateParam, HttpSession session) throws Exception {
        tbEvaluateParamService.deleteByWhere("tp_comb='" + tbEvaluateParam.getTpComb() + "'");
        return this.json.returnMsg("true", "删除成功!").toJson();
    }

    /**
     * 部署tb_punish_param
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "deploy")
    @SystemLog(tradeName = "维护罚息参数", funCode = "AL-01-01-05", funName = "部署TbPunishParam", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String deploy(TbEvaluateParam tbEvaluateParam) throws Exception {
        Json json;
        json = tbEvaluateParamService.deploy();//TODO 部署方法待完善
        return json.toJson();
    }


    /**
     * 联想输入框
     * 根据id查询评价参数
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectTpId")
    @SystemLog(tradeName = "维护评价参数", funCode = "AL-02-01", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
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
     * 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectTpComb")
    @SystemLog(tradeName = "维护罚息参数", funCode = "AL-01-01", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
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