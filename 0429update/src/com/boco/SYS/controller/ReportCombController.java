package com.boco.SYS.controller;

import com.alibaba.fastjson.JSON;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.ReportCombDetailMapper;
import com.boco.SYS.mapper.ReportCombMapper;
import com.boco.SYS.service.IWebReportCombDetailService;
import com.boco.SYS.service.IWebReportCombService;
import com.boco.SYS.util.TreeNode;
import com.boco.SYS.util.TreeNodeGrid;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷种组合业务控制层
 *
 * @author txn
 * @describe ReportCombController
 * @date 2020-05-13
 */
@RequestMapping("/webReportComb")
@Controller
public class ReportCombController extends BaseController {
    @Autowired
    IWebReportCombService webLoanService;
    @Autowired
    IWebReportCombDetailService webLoanDetailService;
    @Autowired
    ReportCombMapper loanCombMapper;
    @Autowired
    ReportCombDetailMapper loanCombDetailMapper;


    @RequestMapping(value = "/listUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12", funName = "展示贷种组合UI界面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombIndexUI() throws Exception {
        authButtons();
        return basePath + "/SYS/webReportComb/webReportCombInfoList";
    }

    @RequestMapping(value = "/insertUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12-01", funName = "新增贷种组合UI界面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombInsertUI() throws Exception {
        authButtons();
        return basePath + "/SYS/webReportComb/webReportCombInfoAdd";
    }

    @RequestMapping(value = "/updateUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12-02", funName = "更新贷种组合UI界面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombUpdateUI(@RequestParam String combCode) throws Exception {
        authButtons();
        PlainResult<TbReportComb> result = webLoanService.getLoanCombInfoByCombCode(combCode);
        setAttribute("webLoanComb", result.getData());
        return basePath + "/SYS/webReportComb/webReportCombInfoEdit";
    }

    @RequestMapping(value = "/infoUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12-02", funName = "显示贷种组合详情UI界面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombInfoUI(@RequestParam String combCode) throws Exception {
        PlainResult<TbReportComb> result = webLoanService.getLoanCombInfoByCombCode(combCode);
        setAttribute("webLoanComb", result.getData());
        return basePath + "/SYS/webReportComb/webReportCombInfoInfo";
    }


    @ResponseBody
    @RequestMapping(value = "/listChildrenComposeInfo", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12-02", funName = "查询子节点", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listChildrenLoanComb(String combCode, int combLevel) throws Exception {
        ListResult<TreeNode> result = webLoanService.getLoanCombDetailInfoByCombCode(combCode, combLevel);
        List<TreeNode> data = result.getData();
        setAttribute("treeNodes", JSON.toJSONString(data));
        return JSON.toJSONString(data);
    }

    @ResponseBody
    @RequestMapping("/listAllLoanComb")
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12", funName = "列出所有贷种级别产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllLoanCombNoCondition() throws Exception {
        authButtons();
        String combCode = getParameter("combCode");
        String combName = getParameter("combName");
        String combLevel = getParameter("combLevel");
        setPageParam();
        //返回页面的分页数据
        int pageNum = Integer.valueOf(getParameter("pageNo"));
        int pageSize = Integer.valueOf(getParameter("pageSize"));
        Map<String, Object> results = null;
        try {
            results = webLoanService.getAllLoanCombInfo3(combCode, combName, combLevel, pageNum, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(results);
    }

    @ResponseBody
    @RequestMapping("/listAllLoanComb1")
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12", funName = "列出所有贷种级别产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllLoanCombNoCondition1(String method, String reqId) throws Exception {
        List<TbReportComb> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new TbReportComb());
        TbReportCombDetail tbCombDetail = new TbReportCombDetail();
        tbCombDetail.setStatus(1);
        List<TbReportCombDetail> TbCombDetailS = loanCombDetailMapper.selectByAttr(tbCombDetail);
        String combNameStr = "";
        Map<String, Object> results = new HashMap<>();
        List<Map<String, Object>> treeList = new ArrayList();
        for (TbReportComb loanCombDO : loanCombDOS) {
            Map<String, Object> treeMap = new HashMap<>(64);
            int level = loanCombDO.getCombLevel();
            String combCode = loanCombDO.getCombCode();
            String combName = loanCombDO.getCombName();
            treeMap.put("id", combCode);
            treeMap.put("parentId", "0");
            treeMap.put("name", combName);
            if (level == 1) {
                treeMap.put("icon", "/web/libs/icons/triangle.gif");
            } else if (level == 2) {
                treeMap.put("icon", "/web/libs/icons/rectangle.gif");
            } else if (level == 3) {
                treeMap.put("icon", "/web/libs/icons/polygon.gif");
            }
            for (TbReportCombDetail TbReportCombDetail : TbCombDetailS) {
                if (TbReportCombDetail.getProdCode().equals(combCode)) {
                    treeMap.put("parentId", TbReportCombDetail.getCombCode());
                    break;
                }
            }
            treeList.add(treeMap);
        }
        results.put("treeNodes", treeList);
        setAttribute("combList", JsonUtils.toJson(results));
        if ("info".equals(method)) {
            combNameStr.replaceAll("#全选", "");
        }
        setAttribute("combNameStr", combNameStr);
        return JsonUtils.toJson(results);
    }


    /**
     * TODO 处理子菜单信息.
     *
     * @param tempMap
     * @param menu
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月3日    	  杜旭    新建
     * </pre>
     */
    public Map<String, TreeNodeGrid> getChildMenu(Map<String, TreeNodeGrid> tempMap, WebMenuInfo menu) {
        if (tempMap.containsKey(menu.getMenuNo())) {//存在
            TreeNodeGrid node = tempMap.get(menu.getMenuNo());
            node = getChildNode(node, menu, "1");
            tempMap.put(menu.getMenuNo(), node);
        } else {//不存在
            TreeNodeGrid node = new TreeNodeGrid();
            node = getChildNode(node, menu, "0");
            tempMap.put(menu.getMenuNo(), node);

            if (tempMap.containsKey(menu.getUpMenuNo())) {//父节点存在
                TreeNodeGrid upNode = tempMap.get(menu.getUpMenuNo());
                upNode = getNode(upNode, node, "1");
                tempMap.put(menu.getUpMenuNo(), upNode);
            } else {
                TreeNodeGrid upNode = new TreeNodeGrid();//创建父节点
                upNode = getNode(upNode, node, "0");//父节点不存在
                tempMap.put(menu.getUpMenuNo(), upNode);
            }
        }
        return tempMap;
    }


    /**
     * TODO 若子节点不存在时，封装子节点信息，并返回.
     *
     * @param menu
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月3日    	  杜旭    新建
     * </pre>
     */
    public TreeNodeGrid getChildNode(TreeNodeGrid node, WebMenuInfo menu, String childType) {
        node.setId(menu.getMenuNo());//id
        node.setParentId(menu.getUpMenuNo());//parentId
        node.setName(menu.getMenuName());//name
        node.setVersion(menu.getId());//version
        if ("0".equals(childType)) {
            node.setChildren(new ArrayList<TreeNodeGrid>());
        }
        return node;
    }

    /**
     * TODO 封装单个菜单节点信息.
     *
     * @param upNode
     * @param node
     * @param type
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月3日    	  杜旭    新建
     * </pre>
     */
    public TreeNodeGrid getNode(TreeNodeGrid upNode, TreeNodeGrid node, String type) {
        List<TreeNodeGrid> treeNodeChild = null;
        if ("1".equals(type)) {//父节点存在
            treeNodeChild = upNode.getChildren();
        } else {//父节点不存在
            treeNodeChild = new ArrayList<TreeNodeGrid>();
        }
        treeNodeChild.add(node);
        upNode.setChildren(treeNodeChild);
        return upNode;
    }


    @ResponseBody
    @RequestMapping(value = "/listLoanCombByLevel", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12", funName = "列出当前贷种级别产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombByCombLevel(@RequestParam Integer combLevel, @RequestParam boolean isLine) throws Exception {
        authButtons();
        if (combLevel == null) {
            return this.json.returnMsg("true", "请选择贷种组合级别!").toJson();
        }
        ListResult<TreeNode> result = webLoanService.getLoanCombInfoByLevel(combLevel, isLine);
        return JSON.toJSONString(result);
    }


    @ResponseBody
    @RequestMapping(value = "/listAllLoanCombByLevel", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12", funName = "列出当前贷种级别产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllLoanCombByLevel(@RequestParam Integer combLevel) throws Exception {
        authButtons();
        ListResult<TreeNode> result = webLoanService.getLoanCombInfoByLevel(combLevel, false);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/listAllLoanCombByLevelV2", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12", funName = "列出当前贷种级别产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllLoanCombByLevelV2(@RequestParam Integer combLevel) throws Exception {
        authButtons();
        ListResult<TbReportComb> result = webLoanService.getLoanCombInfoByLevel2(combLevel);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/listAllSelectedLoanCombByLevelAndCode", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12", funName = "列出当前贷种级别产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllSelectedLoanCombByLevelAndCode() throws Exception {
        authButtons();
        String combLevel = getParameter("combLevel");
        String combCode = getParameter("combCode");
        ListResult<TreeNode> result = webLoanService.getLoanCombDetailInfoByCombCode(combCode, Integer.parseInt(combLevel));
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/listLoanCombByCombCode", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12", funName = "根据贷种编码查询贷种组合信息", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombInfoByCombCode(@RequestParam String combCode) throws Exception {
        authButtons();
        PlainResult<TbReportComb> result = webLoanService.getLoanCombInfoByCombCode(combCode);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/listLoanCombByCombName", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12", funName = "根据贷种名称查询贷种组合信息", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombInfoByCombName(@RequestParam String combName) throws Exception {
        authButtons();
        ListResult<TbReportComb> result = webLoanService.getLoanCombInfoByName(combName);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/insertLoanCombInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12", funName = "创建贷种组合", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String insertLoanCombInfo(@RequestParam String combCode, @RequestParam String combName
            , @RequestParam int combLevel, @RequestParam String productIds) throws Exception {
        authButtons();
        WebOperInfo operInfo = getSessionOperInfo();
        PlainResult<String> result = webLoanService.insertLoanCombInfo(combCode, combName, combLevel, operInfo.getOperCode(), productIds);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/updateLoanCombInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12", funName = "更新贷种组合", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String updateLoanCombInfo(@RequestParam String combCode, @RequestParam String combName, @RequestParam int combLevel
            , @RequestParam String productIds) throws Exception {
        authButtons();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        PlainResult<String> result = webLoanService.updateLoanCombInfo(combCode, combLevel, combName, productIds, sessionOperInfo.getOperCode());
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteLoanCombInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12", funName = "删除贷种组合信息", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String deleteLoanCombInfo(@RequestParam String combCode, @RequestParam int combLevel) throws Exception {
        authButtons();
        PlainResult<String> result = webLoanService.deleteLoanCombInfoByCombCode(combCode, combLevel);
        return JSON.toJSONString(result);
    }


    @ResponseBody
    @RequestMapping(value = "/getLoanCombInfoByLevelAndOrganCode", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "SYS-12", funName = "通过贷种级别获取贷种信息", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getLoanCombInfoByLevelAndOrganCode() throws Exception {
        String combLevelStr = getParameter("combLevel");
        FdOrgan sessionOrgan = getSessionOrgan();
        ListResult<TreeNode> result = webLoanService.getLoanCombInfoByLevelAndOrganCode(Integer.parseInt(combLevelStr), sessionOrgan.getThiscode());
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "selectCombName")
    @SystemLog(tradeName = "联想产品名称", funCode = "SYS", funName = "联想输入", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String selectCombName(HttpServletRequest request) throws Exception {
        String combName = request.getParameter("combName").replace("'", "");
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<String> combNameList = webLoanService.selectCombName(combName);
        List<Map<String, String>> list = new ArrayList<>();
        for (String data : combNameList) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }

    @RequestMapping(value = "selectCombCode")
    @SystemLog(tradeName = "联想产品名称", funCode = "SYS", funName = "联想输入", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String selectCombCode(HttpServletRequest request) throws Exception {
        String combCode = request.getParameter("combCode").replace("'", "");
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<String> combCodeList = webLoanService.selectCombCode(combCode);
        List<Map<String, String>> list = new ArrayList<>();
        for (String data : combCodeList) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }

    @RequestMapping(value = "checkCombInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "联想产品名称", funCode = "SYS", funName = "检查贷种组合信息", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    @ResponseBody
    public String checkCombInfo(@RequestParam String combCode, @RequestParam String combName) throws Exception {
        PlainResult<String> result = webLoanService.checkCombInfo(combCode, combName);
        return JSON.toJSONString(result);
    }
}
