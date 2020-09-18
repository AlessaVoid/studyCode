package com.boco.TONY.biz.loancomb.controller;

import com.alibaba.fastjson.JSON;
import com.boco.PUB.service.ITbReqListService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombDetailMapper;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.service.IWebMenuInfoService;
import com.boco.SYS.util.TreeNode;
import com.boco.SYS.util.TreeNodeGrid;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDTO;
import com.boco.TONY.biz.loancomb.POJO.DTO.combtaken.CombTakenDetailDTO;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombDetailService;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.TONY.biz.loancomb.service.LoanCombTakenService;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 贷种组合业务控制层
 *
 * @author tony
 * @describe WebLoanCombController
 * @date 2019-09-17
 */
@RequestMapping("/webLoan")
@Controller
public class WebLoanCombController extends BaseController {
    @Autowired
    IWebLoanCombService webLoanService;
    @Autowired
    IWebLoanCombDetailService webLoanDetailService;
    @Autowired
    LoanCombTakenService combTakenService;
    @Autowired
    LoanCombTakenService loanCombTakenService;
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    LoanCombDetailMapper loanCombDetailMapper;
    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    private IWebMenuInfoService webMenuInfoService;


    @RequestMapping(value = "/listUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "展示贷种组合UI界面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombIndexUI() throws Exception {
        authButtons();
        return basePath + "/PM/webLoanInfo/webLoanInfoList";
    }

    @RequestMapping(value = "/insertUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27-01", funName = "新增贷种组合UI界面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombInsertUI() throws Exception {
        authButtons();
        return basePath + "/PM/webLoanInfo/webLoanInfoAdd";
    }

    @RequestMapping(value = "/updateUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27-02", funName = "更新贷种组合UI界面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombUpdateUI(@RequestParam String combCode) throws Exception {
        authButtons();
        PlainResult<LoanCombDTO> result = webLoanService.getLoanCombInfoByCombCode(combCode);
        setAttribute("webLoanComb", result.getData());
        return basePath + "/PM/webLoanInfo/webLoanInfoEdit";
    }

    @RequestMapping(value = "/infoUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27-02", funName = "显示贷种组合详情UI界面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombInfoUI(@RequestParam String combCode) throws Exception {
        PlainResult<LoanCombDTO> result = webLoanService.getLoanCombInfoByCombCode(combCode);
        setAttribute("webLoanComb", result.getData());
        return basePath + "/PM/webLoanInfo/webLoanInfoInfo";
    }

    @RequestMapping(value = "/showLoanCombInnerTakenPage")
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "展示贷种组合内关系", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String showLoanCombInnerTakenPage(@RequestParam String combCode) throws Exception {
        PlainResult<LoanCombDTO> result = webLoanService.getLoanCombInfoByCombCode(combCode);
        setAttribute("webLoanComb", result.getData());
        Integer takenType = loanCombTakenService.getTakenTypeByCombParent(combCode);
        setAttribute("InnerTakenType", takenType);
        return basePath + "/PM/webLoanInfo/webLoanInnerCombEdit";
    }

    @RequestMapping(value = "/showLoanCombInterTakenPage")
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "展示贷种组合间关系", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String showLoanCombInterTakenPage() throws Exception {
        Integer takenType = loanCombTakenService.selectInterTakentype();
        setAttribute("betweenTakenType", takenType);
        return basePath + "/PM/webLoanInfo/webLoanInterCombEdit";
    }

    @ResponseBody
    @RequestMapping(value = "/listChildrenComposeInfo", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27-02", funName = "查询子节点", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listChildrenLoanComb(String combCode, int combLevel) throws Exception {
        ListResult<TreeNode> result = webLoanService.getLoanCombDetailInfoByCombCode(combCode, combLevel);
        List<TreeNode> data = result.getData();
        setAttribute("treeNodes", JSON.toJSONString(data));
        return JSON.toJSONString(data);
    }

    @ResponseBody
    @RequestMapping("/listAllLoanComb")
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "列出所有贷种级别产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
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
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "列出所有贷种级别产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllLoanCombNoCondition1(String method, String reqId) throws Exception {
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        TbCombDetail tbCombDetail = new TbCombDetail();
        tbCombDetail.setStatus(1);
        List<TbCombDetail> TbCombDetailS = loanCombDetailMapper.selectByAttr(tbCombDetail);
        TbReqList tbReqList = new TbReqList();
        String combNameStr = "";
        Map<String, Object> results = new HashMap<>();
        List<Map<String, Object>> treeList = new ArrayList();
        for (LoanCombDO loanCombDO : loanCombDOS) {
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
            for (TbCombDetail TbCombDetail : TbCombDetailS) {
                if (TbCombDetail.getProdCode().equals(combCode)) {
                    treeMap.put("parentId", TbCombDetail.getCombCode());
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

//    public List<TreeNodeGrid> getTreeNode(LoanCombDO loanCombDO) throws Exception{
//        //临时map
//        Map<String,TreeNodeGrid> tempMap = new HashMap<String,TreeNodeGrid>();
//        Map<String,TreeNodeGrid> map = new HashMap<String,TreeNodeGrid>();
//        List<TreeNodeGrid> treeNode = new ArrayList<TreeNodeGrid>();
//        Map selectAttr = new HashMap();
//
//        List<LoanCombDO> loanComblist = loanCombMapper.getAllLoanCombInfoList(loanCombDO);
//        if(loanComblist.size()!=0){
//            List<WebMenuInfo> menuList = null;
//            String upMenu = "";
//            String menuCountFlag = "";
//            if(StringUtils.isNotEmpty(webMenuInfo.getMenuNo()) || StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
//                if(StringUtils.isNotEmpty(webMenuInfo.getMenuNo()) && !StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
//                    upMenu = webMenuInfo.getMenuNo();
//                    menuCountFlag = "single";
//                }else if(!StringUtils.isNotEmpty(webMenuInfo.getMenuNo()) && StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
//                    upMenu = webMenuInfo.getUpMenuNo();
//                    menuCountFlag = "single";
//                }else if(StringUtils.isNotEmpty(webMenuInfo.getMenuNo()) && StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
//                    upMenu = webMenuInfo.getMenuNo();
//                    menuCountFlag = "single";
//                }
//            }
//            if("single".equals(menuCountFlag)){
//                //获取菜单信息
//                selectAttr.put("upMenuNo", upMenu);
//                selectAttr.put("menuStatus", webMenuInfo.getMenuStatus());
//                menuList = webMenuInfoService.selectByNo(selectAttr);
//            }else{
//                menuList = webMenulist;
//            }
//
//            for(WebMenuInfo menu : menuList){
//                String menuNo = menu.getMenuNo();
//                if("1".equals(menu.getIsParent())){//若为父菜单
//                    TreeNodeGrid node = new TreeNodeGrid();
//                    node = getChildNode(node,menu,"0");
//                    map.put(menuNo, node);
//                }else if("0".equals(menu.getIsParent())){//若为子菜单
//                    if("1".equals(menu.getMenuType())){//菜单
//                        tempMap = getChildMenu(tempMap,menu);
//                    }else if("2".equals(menu.getMenuType())){//按钮
//                        tempMap = getButtonMenu(tempMap,menu);
//                    }
//                }
//            }
//            //获取完整的菜单信息
//            map = getNodeMap(map,tempMap);
//            //将菜单信息保存在list集合中
//            for(String menu : map.keySet()){
//                treeNode.add(map.get(menu));
//            }
//        }
//        return treeNode;
//    }

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
     * TODO 处理按钮信息.
     *
     * @param tempMap
     * @param menu    <pre>
     *                修改日期        修改人    修改原因
     *                2016年3月3日    	  杜旭    新建
     *                </pre>
     */
    public Map<String, TreeNodeGrid> getButtonMenu(Map<String, TreeNodeGrid> tempMap, WebMenuInfo menu) {
        if (tempMap.containsKey(menu.getUpMenuNo())) {//存在
            TreeNodeGrid upNode = tempMap.get(menu.getUpMenuNo());
            TreeNodeGrid node = new TreeNodeGrid();
            node = getChildNode(node, menu, "0");
            upNode = getNode(upNode, node, "1");//父节点存在
            tempMap.put(menu.getUpMenuNo(), upNode);
        } else {//不存在
            TreeNodeGrid upNode = new TreeNodeGrid();//创建父节点
            TreeNodeGrid node = new TreeNodeGrid();//创建子节点
            //封装子节点的信息
            node = getChildNode(node, menu, "0");
            upNode = getNode(upNode, node, "0");//父节点存在
            tempMap.put(menu.getUpMenuNo(), upNode);
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

    /**
     * TODO 获取存储菜单信息的map.
     *
     * @param map
     * @param tempMap
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月3日    	  杜旭    新建
     * </pre>
     */
    public Map<String, TreeNodeGrid> getNodeMap(Map<String, TreeNodeGrid> map, Map<String, TreeNodeGrid> tempMap) {
        for (String key : tempMap.keySet()) {
            TreeNodeGrid node = tempMap.get(key);
            if (node != null) {
                String upMenuNo = node.getParentId();
                if (map.containsKey(upMenuNo)) {
                    TreeNodeGrid upNode = map.get(upMenuNo);
                    List<TreeNodeGrid> treeNodeChild = upNode.getChildren();
                    treeNodeChild.add(node);
                    upNode.setChildren(treeNodeChild);
                    map.put(upMenuNo, upNode);
                }
            }
        }
        return map;
    }

//    @ResponseBody
//    @RequestMapping("/listAllLoanComb2")
//    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "列出所有贷种级别产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
//    public String listAllLoanCombNoCondition2(LoanCombDO loanCombDO) throws Exception {
//        Map<String, Object> results = new Hashtable<String, Object>();
//        List<TreeNodeGrid> treeNode = new ArrayList<TreeNodeGrid>();
//        //获取树形菜单信息
//        treeNode = getTreeNode(loanCombDO);
//        results.put("rows", treeNode);
//        return JsonUtils.toJson(results);
//    }

    @ResponseBody
    @RequestMapping(value = "/listLoanCombByLevel", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "列出当前贷种级别产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombByCombLevel(@RequestParam Integer combLevel, @RequestParam boolean isLine) throws Exception {
        authButtons();
        if (combLevel==null){
            return this.json.returnMsg("true", "请选择贷种组合级别!").toJson();
        }
        ListResult<TreeNode> result = webLoanService.getLoanCombInfoByLevel(combLevel, isLine);
        return JSON.toJSONString(result);
    }


    @ResponseBody
    @RequestMapping(value = "/listAllLoanCombByLevel", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "列出当前贷种级别产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllLoanCombByLevel(@RequestParam Integer combLevel) throws Exception {
        authButtons();
        ListResult<TreeNode> result = webLoanService.getLoanCombInfoByLevel(combLevel, false);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/listAllLoanCombByLevelV2", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "列出当前贷种级别产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllLoanCombByLevelV2(@RequestParam Integer combLevel) throws Exception {
        authButtons();
        ListResult<LoanCombDTO> result = webLoanService.getLoanCombInfoByLevel2(combLevel);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/listAllSelectedLoanCombByLevelAndCode", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "列出当前贷种级别产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllSelectedLoanCombByLevelAndCode() throws Exception {
        authButtons();
        String combLevel = getParameter("combLevel");
        String combCode = getParameter("combCode");
        ListResult<TreeNode> result = webLoanService.getLoanCombDetailInfoByCombCode(combCode, Integer.parseInt(combLevel));
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/listLoanCombByCombCode", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "根据贷种编码查询贷种组合信息", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombInfoByCombCode(@RequestParam String combCode) throws Exception {
        authButtons();
        PlainResult<LoanCombDTO> result = webLoanService.getLoanCombInfoByCombCode(combCode);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/listLoanCombByCombName", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "根据贷种名称查询贷种组合信息", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listLoanCombInfoByCombName(@RequestParam String combName) throws Exception {
        authButtons();
        ListResult<LoanCombDTO> result = webLoanService.getLoanCombInfoByName(combName);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/insertLoanCombInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "创建贷种组合", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String insertLoanCombInfo(@RequestParam String combCode, @RequestParam String combName
            , @RequestParam int combLevel, @RequestParam String productIds) throws Exception {
        authButtons();
        WebOperInfo operInfo = getSessionOperInfo();
        PlainResult<String> result = webLoanService.insertLoanCombInfo(combCode, combName, combLevel, operInfo.getOperCode(), productIds);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/updateLoanCombInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "更新贷种组合", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String updateLoanCombInfo(@RequestParam String combCode, @RequestParam String combName, @RequestParam int combLevel
            , @RequestParam String productIds) throws Exception {
        authButtons();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        PlainResult<String> result = webLoanService.updateLoanCombInfo(combCode, combLevel, combName, productIds, sessionOperInfo.getOperCode());
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteLoanCombInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "删除贷种组合信息", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String deleteLoanCombInfo(@RequestParam String combCode, @RequestParam int combLevel) throws Exception {
        authButtons();
        PlainResult<String> result = webLoanService.deleteLoanCombInfoByCombCode(combCode, combLevel);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/takeLoanCombInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "删除贷种组合信息", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String takeLoanCombInfo() throws Exception {
        authButtons();
        String gridData = getParameter("gridData");
        String parentCombCode = getParameter("parentCombCode");
        String takenType = getParameter("takenType");
        PlainResult<String> result = combTakenService.takeLoanCombInfo(gridData, parentCombCode, Integer.parseInt(takenType));
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/selectLoanCombByParentId", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "通过贷种级别获取贷种信息", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String selectLoanCombByParentId() throws Exception {
        String combParentCode = getParameter("combParentCode");
        ListResult<CombTakenDetailDTO> combTakenDetailDTOList = combTakenService.selectLoanCombTakenByParentId(combParentCode);
        return JSON.toJSONString(combTakenDetailDTOList);
    }


    @ResponseBody
    @RequestMapping(value = "/getLoanCombInfoByLevelAndOrganCode", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合", funCode = "PM-27", funName = "通过贷种级别获取贷种信息", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getLoanCombInfoByLevelAndOrganCode() throws Exception {
        String combLevelStr = getParameter("combLevel");
        FdOrgan sessionOrgan = getSessionOrgan();
        ListResult<TreeNode> result = webLoanService.getLoanCombInfoByLevelAndOrganCode(Integer.parseInt(combLevelStr), sessionOrgan.getThiscode());
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "selectCombName")
    @SystemLog(tradeName = "联想产品名称", funCode = "PM-07", funName = "联想输入", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String selectCombName(HttpServletRequest request) throws Exception {
        String combName = request.getParameter("combName").replace("'","");
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
    @SystemLog(tradeName = "联想产品名称", funCode = "PM-07", funName = "联想输入", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String selectCombCode(HttpServletRequest request) throws Exception {
        String combCode = request.getParameter("combCode").replace("'","");
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
    @SystemLog(tradeName = "联想产品名称", funCode = "PM-07", funName = "检查贷种组合信息", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    @ResponseBody
    public String checkCombInfo(@RequestParam String combCode, @RequestParam String combName) throws Exception {
        PlainResult<String> result = webLoanService.checkCombInfo(combCode, combName);
        return JSON.toJSONString(result);
    }
}
