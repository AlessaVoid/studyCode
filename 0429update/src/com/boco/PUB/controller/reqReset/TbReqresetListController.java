package com.boco.PUB.controller.reqReset;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.PM.service.IFdOrganService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PUB.service.ITbLineReqresetDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.PUB.service.ITbReqresetDetailService;
import com.boco.PUB.service.ITbReqresetListService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.WebContextUtil;
import com.boco.TONY.biz.line.POJO.DO.ProductLineDetailDO;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 需求变更-控制层
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbTradeManger/tbReqresetList/")
public class TbReqresetListController extends BaseController {
    @Autowired
    private ITbLineReqresetDetailService tbLineReqresetDetailService;
    @Autowired
    private ITbReqresetListService tbReqresetListService;
    @Autowired
    private ITbReqresetDetailService tbReqresetDetailService;
    @Autowired
    private IFdOrganService organService;
    @Autowired
    private LineProductMapper lineProductMapper;
    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    private IWebMsgService webMsgService;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private LoanCombDetailMapper loanCombDetailMapper;
    @Autowired
    private TbPlanadjService tbPlanadjService;
    @Autowired
    private LineProductDetailMapper lineProductDetailsMapper;
    @Autowired
    private TbPlanService planService;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private FdOrganMapper fdOrganMapper;

    @Autowired
    private WebOperLineMapper webOperLineMapper;
    @Autowired
    private WebOperRoleMapper webOperRoleMapper;
    @Autowired
    private WebOperInfoMapper webOperInfoMapper;

    /*一分机构录入员角色编码*/
    @Value("${organ.oper.code.one}")
    private String organOperCodeOne;


    /*二分机构录入员角色编码*/
    @Value("${organ.oper.code.two}")
    private String organOperCodeTwo;

    /*总行条线录入员角色编码*/
    @Value("${line.oper.code.zero}")
    private String lineOperCodeZero;


    /*一分条线录入员角色编码*/
    @Value("${line.oper.code.one}")
    private String lineOperCodeOne;

    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-01", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbReqStatistics/tbReqresetList/tbReqresetListList";
    }

    @RequestMapping("showListUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-02", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String showListUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbReqStatistics/tbReqresetList/tbReqShowList";
    }


    @RequestMapping("infoUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-01-04", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbReqresetList tbReqresetList) throws Exception {
        tbReqresetList = tbReqresetListService.selectByPK(tbReqresetList.getReqresetId());
        tbReqresetList.setReqresetDateStart(tbReqresetList.getReqresetDateStart().substring(0, 4) + "-" + tbReqresetList.getReqresetDateStart().substring(4, 6) + "-" + tbReqresetList.getReqresetDateStart().substring(6, 8));
        tbReqresetList.setReqresetDateEnd(tbReqresetList.getReqresetDateEnd().substring(0, 4) + "-" + tbReqresetList.getReqresetDateEnd().substring(4, 6) + "-" + tbReqresetList.getReqresetDateEnd().substring(6, 8));
        tbReqresetList.setReqresetTimeStart(tbReqresetList.getReqresetTimeStart().substring(0, 4) + "-" + tbReqresetList.getReqresetTimeStart().substring(4, 6) + "-" + tbReqresetList.getReqresetTimeStart().substring(6, 8));
        tbReqresetList.setReqresetTimeEnd(tbReqresetList.getReqresetTimeEnd().substring(0, 4) + "-" + tbReqresetList.getReqresetTimeEnd().substring(4, 6) + "-" + tbReqresetList.getReqresetTimeEnd().substring(6, 8));
        setAttribute("TbreqresetListDTO", tbReqresetList);
        selectComb("info", String.valueOf(tbReqresetList.getReqresetId()));
        selectLine("info", String.valueOf(tbReqresetList.getReqresetId()));
        selectOrgan("info", String.valueOf(tbReqresetList.getReqresetId()));
        return basePath + "/PUB/tbReqStatistics/tbReqresetList/tbReqresetListInfo";
    }

    @RequestMapping("showChildUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-01-06", funName = "加载下级需求页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String showChildUI(String reqresetId) throws Exception {
        setAttribute("reqresetId", reqresetId);
        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(Integer.valueOf(reqresetId));
        WebOperInfo webOperInfo = getSessionOperInfo();
        String organCode = webOperInfo.getOrganCode();
        int reqresetType = tbReqresetList.getReqresetType();
        String combListStr = "";
        String organListStr = "";
        if (reqresetType == 0) {
            List<Map<String, String>> organList = new ArrayList<>();
            organListStr = tbReqresetList.getReqresetOrganList();
            String[] organArr = organListStr.split(",");
            FdOrgan thisOrgan = fdOrganMapper.selectByPK(organCode);
            FdOrgan searchOrgan = new FdOrgan();
            searchOrgan.setUporgan(thisOrgan.getThiscode());
            List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(searchOrgan);

            //一分查询2分机构就加本部
            if ("1".equals(thisOrgan.getOrganlevel())) {
                FdOrgan searchOrgan_1 = new FdOrgan();
                searchOrgan_1.setProvincecode(thisOrgan.getThiscode());
                searchOrgan_1.setOrganlevel("1");
                List<FdOrgan> organList_1 = fdOrganMapper.selectByAttr(searchOrgan_1);
                FdOrgan fdOrgan = organList_1.get(0);
                fdOrgan.setOrganname(fdOrgan.getOrganname() + "本部");
                fdOrganList.add(fdOrgan);
            }
            Map<String, String> organMap = new HashMap<>(16);
            for (int i = 0; i < organArr.length; i++) {
                for (FdOrgan fdOrgan : fdOrganList) {
                    if (organArr[i].equals(fdOrgan.getThiscode())) {
                        organMap = new HashMap<>(2);
                        organMap.put("organCode", organArr[i]);
                        organMap.put("organName", fdOrgan.getOrganname());
                        organList.add(organMap);
                        break;
                    }
                }
            }
            organMap = new HashMap<>();
            organMap.put("organCode", "TangLoveQianForever");
            organMap.put("organName", "总计");
            organList.add(organMap);
            setAttribute("tbReqresetList", tbReqresetList);
            setAttribute("organList", organList);
            List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
            List<Map<String, String>> combList = new ArrayList<>();
            List<Map<String, String>> combAmountNameList = new ArrayList<>();
            combListStr = tbReqresetList.getReqresetCombList();
            String[] combArr = combListStr.split(",");
            for (LoanCombDO loanCombDO : loanCombDOS) {
                for (int i = 0; i < combArr.length; i++) {
                    if (combArr[i].equals(loanCombDO.getCombCode())) {
                        Map<String, String> combMap = new HashMap<>(2);
                        Map<String, String> map1 = new HashMap<>(2);
                        map1.put("name", loanCombDO.getCombName());
                        map1.put("code", combArr[i]);
                        combAmountNameList.add(map1);
                        break;
                    }
                }
            }
            setAttribute("combAmountNameList", combAmountNameList);
            return basePath + "/PUB/tbReqStatistics/tbReqresetList/tbReqresetListShowChild";
        } else {
            TbLineReqresetDetail searchTbLineReqDetail = new TbLineReqresetDetail();
            searchTbLineReqDetail.setLineResetrefId(Integer.parseInt(reqresetId));
            List<TbLineReqresetDetail> list = tbLineReqresetDetailService.selectByAttr(searchTbLineReqDetail);
            if (list != null && list.size() > 0) {
                TbLineReqresetDetail tbLineReqresetDetail = list.get(0);
                String nameStr = "";
                for (TbLineReqresetDetail t : list) {
                    nameStr = nameStr + (t.getLineName() + "#");
                }
                nameStr = nameStr.substring(0, nameStr.length() - 1);
                tbLineReqresetDetail.setLineName(nameStr);
                setAttribute("tbLineReqResetDetail", tbLineReqresetDetail);
                setAttribute("tbReqresetList", tbReqresetList);

            }
            setAttr(list);
            setAttribute("lineResetrefId", reqresetId);
            return basePath + "/PUB/tbReqStatistics/tbReqresetList/tbLineReqresetListShowChild";
        }

    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-01-01", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        selectOrgan("", "");
        selectLine("", "0");
        return basePath + "/PUB/tbReqStatistics/tbReqresetList/tbReqresetListAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-01-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbReqresetList tbReqresetList) throws Exception {
        tbReqresetList = tbReqresetListService.selectByPK(tbReqresetList.getReqresetId());
        setAttribute("TbreqresetListDTO", tbReqresetList);
        selectComb("update", String.valueOf(tbReqresetList.getReqresetId()));
        selectLine("update", String.valueOf(tbReqresetList.getReqresetId()));
        selectOrgan("update", String.valueOf(tbReqresetList.getReqresetId()));
        return basePath + "/PUB/tbReqStatistics/tbReqresetList/tbReqresetListEdit";
    }


//    /**
//     * 获取所有贷种组合组合数据
//     *
//     * @return <pre>
//     * 修改日期            修改人      修改原因
//     * 2014-10-29      杨滔      批量新建
//     * param method add;update;info
//     * param reqId 需求批次号
//     * </pre>
//     */
//    @RequestMapping(value = "selectComb", method = RequestMethod.POST)
//    @SystemLog(tradeName = "获取下属机构信息", funCode = "PUB2", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
//    public @ResponseBody
//    String selectComb(String method, String reqresetId) throws Exception {
//        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
//        List<TbCombDetail> TbCombDetailS = loanCombDetailMapper.selectByAttr(new TbCombDetail());
//        TbReqresetList tbReqresetList = new TbReqresetList();
//        if (reqresetId != null && !"0".equals(reqresetId)) {
//            tbReqresetList = tbReqresetListService.selectByPK(Integer.parseInt(reqresetId));
//        }
//
//        Map<String, Object> results = new HashMap<>();
//        List<Map<String, Object>> treeList = new ArrayList();
//        for (LoanCombDO loanCombDO : loanCombDOS) {
//            Map<String, Object> treeMap = new HashMap<>(64);
//            int level = loanCombDO.getCombLevel();
//            String combCode = loanCombDO.getCombCode();
//            String combName = loanCombDO.getCombName();
//            treeMap.put("id", combCode);
//            treeMap.put("parentId", "0");
//            treeMap.put("name", combName);
//            if (level == 1) {
//                treeMap.put("icon", "/web/libs/icons/triangle.gif");
//            } else if (level == 2) {
//                treeMap.put("icon", "/web/libs/icons/rectangle.gif");
//            } else if (level == 3) {
//                treeMap.put("icon", "/web/libs/icons/polygon.gif");
//            }
//            for (TbCombDetail TbCombDetail : TbCombDetailS) {
//                if (TbCombDetail.getProdCode().equals(combCode)) {
//                    treeMap.put("parentId", TbCombDetail.getCombCode());
//                    break;
//                }
//            }
//            treeList.add(treeMap);
//        }
//        String combNameStr = "";
//        if ("update".equals(method) || "info".equals(method)) {
//            //0机构,1条线
//            int reqresetType = tbReqresetList.getReqresetType();
//            if (reqresetType == 0) {
//                String combListStr = tbReqresetList.getReqresetCombList();
//
//                String[] combList = combListStr.split(",");
//                Set<String> combSet = new HashSet<>(Arrays.asList(combList));
//                for (Map<String, Object> map : treeList) {
//                    Object combCode = map.get("id");
//                    if (combSet.contains(combCode)) {
//                        map.put("checked", "true");
//                        combNameStr += ("#" + map.get("name"));
//                        combSet.remove(combCode);
//                    }
//                }
//            }
//        }
//
//        if ("info".equals(method)) {
//            combNameStr.replaceAll("#全选", "");
//        }
//        setAttribute("combNameStr", combNameStr);
//        results.put("treeNodes", treeList);
//        setAttribute("combList", JsonUtils.toJson(results));
//        return JsonUtils.toJson(results);
//    }


    /**
     * 获取所有贷种组合组合数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * param method add;update;info
     * param reqId 需求批次号
     * </pre>
     */
    @RequestMapping(value = "selectComb", method = RequestMethod.POST)
    @SystemLog(tradeName = "获取下属机构信息", funCode = "PUB2", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectComb(String method, String reqId) throws Exception {
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        TbCombDetail tbCombDetail = new TbCombDetail();
        tbCombDetail.setStatus(1);
        List<TbCombDetail> TbCombDetailS = loanCombDetailMapper.selectByAttr(tbCombDetail);
        TbReqresetList tbReqresetList = new TbReqresetList();
        if (reqId != null && !"0".equals(reqId)) {
            tbReqresetList = tbReqresetListService.selectByPK(Integer.parseInt(reqId));
        }
        String combNameStr = "";
        Map<String, Object> results = new HashMap<>();
        List<Map<String, Object>> treeList = new ArrayList();
        Map<String, Object> treeMap = new HashMap<String, Object>();
        for (LoanCombDO loanCombDO : loanCombDOS) {
            treeMap = new HashMap<>(4);
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
        treeMap = new HashMap<>(4);
        treeMap.put("id", "totalOne");
        treeMap.put("parentId", "0");
        treeMap.put("name", "全选一级");
        treeList.add(treeMap);

        treeMap = new HashMap<>(4);
        treeMap.put("id", "totalTwo");
        treeMap.put("parentId", "0");
        treeMap.put("name", "全选二级");
        treeList.add(treeMap);

        treeMap = new HashMap<>(4);
        treeMap.put("id", "totalThree");
        treeMap.put("parentId", "0");
        treeMap.put("name", "全选三级");
        treeList.add(treeMap);

        if ("update".equals(method) || "info".equals(method)) {
            //0机构,1条线
            int reqType = tbReqresetList.getReqresetType();
            if (reqType == 0) {
                String combListStr = tbReqresetList.getReqresetCombList();

                String[] combList = combListStr.split(",");
                Set<String> combSet = new HashSet<>(Arrays.asList(combList));
                for (Map<String, Object> map : treeList) {
                    Object combCode = map.get("id");
                    if (combSet.contains(combCode)) {
                        map.put("checked", "true");
                        combSet.remove(combCode);
                        combNameStr += ("#" + map.get("name"));
                    } else {
                        map.put("clickExpand", "true");
                    }
                }
            }
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
     * 详情页数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/lineResetReqDetailList")
    @SystemLog(tradeName = "信贷需求", funCode = "PUB2", funName = "信贷计划详情数据", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String lineResetReqDetailList(String lineResetrefId, HttpSession session) throws Exception {
        TbLineReqresetDetail searchTb = new TbLineReqresetDetail();
        searchTb.setLineResetrefId(Integer.parseInt(lineResetrefId));
        searchTb.setLineState(TbLineReqresetDetail.STATE_APPROVED);
        List<TbLineReqresetDetail> list = tbLineReqresetDetailService.selectByAttr(searchTb);

        TbReqresetList searchTbList = tbReqresetListService.selectByPK(Integer.parseInt(lineResetrefId));
        String month = searchTbList.getReqresetMonth();
        List<Map<String, Object>> planList = null;
        boolean planIsOk = false;
        if (month == null || "".equals(month)) {
            //没有指定月份，所以没有计划
        } else {
            planList = tbPlanadjService.getPlanDetail(getSessionOrgan().getThiscode(), month, 2);
            if (planList != null && planList.size() > 0) {
                planIsOk = true;
            }
        }
        BigDecimal unit = new BigDecimal(1);
        int reqUnit = -1;

        if (list != null && list.size() > 0) {
            reqUnit = list.get(0).getLineUnit();
        }
        List<TbLineReqResetDetailDTO> tbLineReqResetDetailDTOS = new ArrayList<>();
        BigDecimal totalOldPlan = BigDecimal.ZERO;
        BigDecimal totalNum = BigDecimal.ZERO;
        BigDecimal totalNewPlan = BigDecimal.ZERO;
        for (TbLineReqresetDetail tbLineReqresetDetail : list) {
            String lineCombtr = tbLineReqresetDetail.getLineCombCode();
            String[] lineCombArr = lineCombtr.split(",");
            String numStr = tbLineReqresetDetail.getLineNum();
            String[] numArr = numStr.split(",");
            reqUnit = tbLineReqresetDetail.getLineUnit();
            if (reqUnit == 2) {
                unit = new BigDecimal(10000);
            } else if (reqUnit == 1) {
                unit = new BigDecimal(1);
            }
            for (int i = 0; i < lineCombArr.length; i++) {
                TbLineReqResetDetailDTO tb = new TbLineReqResetDetailDTO();
                String combCode = lineCombArr[i];
                tb.setLineCombCode(combCode);
                tb.setLineNum(new BigDecimal(numArr[i]).divide(unit));
                tb.setLineUpdateTime(tbLineReqresetDetail.getLineUpdateTime());
                tb.setOldPlan("无");
                tb.setNewPlan("无");
                if (planIsOk) {
                    for (Map<String, Object> map : planList) {
                        String planCombCode = map.get("loantype").toString();
                        if (planCombCode.equals(combCode)) {
                            BigDecimal oldPlan = new BigDecimal(map.get("amount").toString());
                            BigDecimal newPlan = oldPlan.add(new BigDecimal(numArr[i]));
                            tb.setOldPlan(oldPlan.divide(unit).toString());
                            tb.setNewPlan(newPlan.divide(unit).toString());
                            tb.setLineUpdateTime(tbLineReqresetDetail.getLineUpdateTime());
                            totalOldPlan = totalOldPlan.add(new BigDecimal(tb.getOldPlan()));
                            totalNewPlan = totalNewPlan.add(new BigDecimal(tb.getNewPlan()));
                            break;
                        }
                    }
                }
                totalNum = totalNum.add(tb.getLineNum());
                tbLineReqResetDetailDTOS.add(tb);
            }
        }
        TbLineReqResetDetailDTO tb = new TbLineReqResetDetailDTO();
        tb.setLineCombCode("total");
        tb.setLineNum(totalNum);
        tb.setOldPlan(totalOldPlan.toString());
        tb.setNewPlan(totalNewPlan.toString());
        tb.setLineUpdateTime("-");
        tbLineReqResetDetailDTOS.add(tb);

        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("tbLineReqResetDetailDTOS", tbLineReqResetDetailDTOS);
        return JSON.toJSONString(resultMap);
    }

    /**
     * 通用方法
     *
     * @param list
     * @throws Exception
     */
    private void setAttr(List<TbLineReqresetDetail> list) throws Exception {
        List<Map<String, String>> combList = new ArrayList<>();
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        Map<String, String> mapUpdateTime = new HashMap<>(2);
        mapUpdateTime.put("name", "上  报  时  间 ");
        mapUpdateTime.put("code", "updateTime");
        combAmountNameList.add(mapUpdateTime);
        Map<String, String> map0 = new HashMap<>(2);
        map0.put("name", "原计划");
        map0.put("code", "oldPlan");
        combAmountNameList.add(map0);
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "调整量");
        map1.put("code", "num");
        combAmountNameList.add(map1);
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "调整后计划");
        map2.put("code", "newPlan");
        combAmountNameList.add(map2);
        setAttribute("combAmountNameList", combAmountNameList);
        for (TbLineReqresetDetail tbLineReqresetDetail : list) {
            String combCodeStr = tbLineReqresetDetail.getLineCombCode();
            String[] combArr = combCodeStr.split(",");
            for (LoanCombDO loanCombDO : loanCombDOS) {
            for (int i = 0; i < combArr.length; i++) {
                    if (combArr[i].equals(loanCombDO.getCombCode())) {
                        Map<String, String> combMap = new HashMap<>(2);
                        combMap.put("combCode", combArr[i]);
                        combMap.put("combName", loanCombDO.getCombName());
                        combList.add(combMap);
                        break;
                    }
                }
            }
        }
        Map<String, String> combMap = new HashMap<>(2);
        combMap.put("combCode", "total");
        combMap.put("combName", "总 计");
        combList.add(combMap);
        setAttribute("combList", combList);
    }

    /**
     * 详情页数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getReqDetailList")
    @SystemLog(tradeName = "信贷需求", funCode = "PUB-05", funName = "信贷计划详情数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanDetailData(String reqresetId, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(2);
        try {
            TbReqresetDetail searchTbReqDetail = new TbReqresetDetail();
            searchTbReqDetail.setReqdresetRefId(Integer.valueOf(reqresetId));
            searchTbReqDetail.setReqdresetState(TbReqresetDetail.STATE_APPROVED);
            List<TbReqresetDetail> tbReqresetDetails = tbReqresetDetailService.selectByAttr(searchTbReqDetail);
            TbReqresetList searchTbList = tbReqresetListService.selectByPK(Integer.parseInt(reqresetId));
            String month = searchTbList.getReqresetMonth();
            List<Map<String, Object>> planList = null;
            boolean planIsOk = false;
            if (month == null || "".equals(month)) {
                //没有指定月份，所以没有计划
            } else {
                //机构的 计划 查询条件 需要修改为上级机构 code
                planList = tbPlanadjService.getPlanDetail(getSessionOrgan().getUporgan(), month, 1);
                if (planList != null && planList.size() > 0) {
                    planIsOk = true;
                }
            }
            int unit = 1;
            BigDecimal tbUnit = new BigDecimal(1);

            TbReqresetList tbReqresetList = new TbReqresetList();
            if (tbReqresetDetails != null && tbReqresetDetails.size() > 0) {
                int reqresetRefId = tbReqresetDetails.get(0).getReqdresetRefId();
                tbReqresetList = tbReqresetListService.selectByPK(reqresetRefId);
            }
            //添加 新旧计划属性
            //合计每个机构的 所有贷种组合的 oldPlan num newPlan
            Map<String, TbReqresetDetail> organTotalMap = new HashMap<>();
            for (TbReqresetDetail tb : tbReqresetDetails) {
                unit = tb.getReqdresetUnit();
                if (unit == 2) {
                    tbUnit = new BigDecimal(10000);
                } else if (unit == 1) {
                    tbUnit = new BigDecimal(1);
                }
                tb.setReqdresetNum(tb.getReqdresetNum().divide(tbUnit));
                tb.setReqdresetCreatetime(getPlanCount(tbReqresetList.getReqresetMonth(), tb.getReqdresetOrgan()).toString());
                tb.setOldPlan("无");
                tb.setNewPlan("无");
                if (planIsOk) {
                    for (Map<String, Object> map : planList) {
                        String planCombCode = map.get("loantype").toString();
                        String organcodeCodeStr = map.get("organcode").toString();
                        if (planCombCode.equals(tb.getReqdresetCombCode()) && organcodeCodeStr.equals(tb.getReqdresetOrgan())) {
                            BigDecimal oldPlan = new BigDecimal(map.get("amount").toString());
                            BigDecimal newPlan = oldPlan.add(tb.getReqdresetNum());
                            tb.setOldPlan(oldPlan.divide(tbUnit).toString());
                            tb.setNewPlan(newPlan.divide(tbUnit).toString());
                        } else {
                            if ((tb.getReqdresetNum().compareTo(BigDecimal.ZERO) == 1) && "无".equals(tb.getNewPlan()) && organcodeCodeStr.equals(tb.getReqdresetOrgan())) {
                                tb.setNewPlan(tb.getReqdresetNum().divide(tbUnit).toString());
                                tb.setOldPlan("无");
                            }
                        }
                    }
                    TbReqresetDetail organTotalTempTb = organTotalMap.get(tb.getReqdresetOrgan());
                    if (organTotalTempTb == null) {
                        organTotalTempTb = new TbReqresetDetail();
                        organTotalTempTb.setTotalOldPlan("0");
                        organTotalTempTb.setTotalNum("0");
                        organTotalTempTb.setTotalNewPlan("0");
                    }
                    if (!"无".equals(tb.getOldPlan())) {
                        organTotalTempTb.setTotalOldPlan(new BigDecimal(organTotalTempTb.getTotalOldPlan()).add(new BigDecimal(tb.getOldPlan())).toString());
                    }
                    organTotalTempTb.setTotalNum(new BigDecimal(organTotalTempTb.getTotalNum()).add(tb.getReqdresetNum()).toString());

                    if (!"无".equals(tb.getNewPlan())) {
                        organTotalTempTb.setTotalNewPlan(new BigDecimal(organTotalTempTb.getTotalNewPlan()).add(new BigDecimal(tb.getNewPlan())).toString());
                    }
                    organTotalMap.put(tb.getReqdresetOrgan(), organTotalTempTb);
                }
            }

            for (TbReqresetDetail tb : tbReqresetDetails) {
                TbReqresetDetail tempOrganTb = organTotalMap.get(tb.getReqdresetOrgan());
                if (tempOrganTb != null) {
                    tb.setTotalOldPlan(tempOrganTb.getTotalOldPlan());
                    tb.setTotalNum(tempOrganTb.getTotalNum());
                    tb.setTotalNewPlan(tempOrganTb.getTotalNewPlan());
                }
            }

            //合计每个贷种组合的 所有机构的 oldPlan num newPlan  totalNum totalOldPlan totalNewPlan
            Map<String, TbReqresetDetail> combTotalMap = new HashMap<>();
            for (TbReqresetDetail tb : tbReqresetDetails) {
                TbReqresetDetail tempCombTb = combTotalMap.get(tb.getReqdresetCombCode());
                if (tempCombTb == null) {
                    tempCombTb = new TbReqresetDetail();
                    tempCombTb.setReqdresetNum(BigDecimal.ZERO);
                    tempCombTb.setOldPlan("0");
                    tempCombTb.setNewPlan("0");
                    tempCombTb.setTotalOldPlan("0");
                    tempCombTb.setTotalNum("0");
                    tempCombTb.setTotalNewPlan("0");
                    tempCombTb.setReqdresetCombCode(tb.getReqdresetCombCode());
                }
                if (!"无".equals(tb.getOldPlan())) {
                    tempCombTb.setOldPlan(new BigDecimal(tempCombTb.getOldPlan()).add(new BigDecimal(tb.getOldPlan())).toString());
                }
                tempCombTb.setReqdresetNum(tempCombTb.getReqdresetNum().add(tb.getReqdresetNum()));

                if (!"无".equals(tb.getNewPlan())) {
                    tempCombTb.setNewPlan(new BigDecimal(tempCombTb.getNewPlan()).add(new BigDecimal(tb.getNewPlan())).toString());
                }
                combTotalMap.put(tb.getReqdresetCombCode(), tempCombTb);
            }
            Set<String> combKeySet = combTotalMap.keySet();

            BigDecimal totalCombOldPlan = BigDecimal.ZERO;
            BigDecimal totalCombNewPlan = BigDecimal.ZERO;
            BigDecimal totalCombNum = BigDecimal.ZERO;
            for (String combCodeStr : combKeySet) {
                TbReqresetDetail tb = combTotalMap.get(combCodeStr);
                tb.setReqdresetOrgan("TangLoveQianForever");
                tb.setReqdresetUpdatetime("--  --  --");
                tbReqresetDetails.add(tb);
                if (!"无".equals(tb.getOldPlan())) {
                    totalCombOldPlan = totalCombOldPlan.add(new BigDecimal(tb.getOldPlan()));
                }
                if (!"无".equals(tb.getNewPlan())) {
                    totalCombNewPlan = totalCombNewPlan.add(new BigDecimal(tb.getNewPlan()));
                }
                totalCombNum = totalCombNum.add(tb.getReqdresetNum());
            }
            for (TbReqresetDetail tb : tbReqresetDetails) {
                if ("TangLoveQianForever".equals(tb.getReqdresetOrgan())) {
                    tb.setTotalNewPlan(totalCombNewPlan.toString());
                    tb.setTotalOldPlan(totalCombOldPlan.toString());
                    tb.setTotalNum(totalCombNum.toString());
                }
            }
            resultMap.put("tbReqresetDetails", tbReqresetDetails);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return JSON.toJSONString(resultMap);
    }


    //月份  需要查询的机构号
    private BigDecimal getPlanCount(String month, String organ) throws Exception {

        //获取上级机构
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setThiscode(organ);
        List<FdOrgan> fdOrgans = fdOrganService.selectByAttr(fdOrgan);
        String upOrgan = "";
        if (fdOrgans != null && fdOrgans.size() != 0) {
            upOrgan = fdOrgans.get(0).getUporgan();
        }

        //获取计划
        TbPlan tbPlan = new TbPlan();
        tbPlan.setPlanMonth(month);
        tbPlan.setPlanType(TbPlan.PLAN);
        tbPlan.setPlanOrgan(upOrgan);
        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
        List<TbPlan> tbPlans = planService.selectByAttr(tbPlan);
        String planId = "";
        if (tbPlans != null && tbPlans.size() != 0) {
            planId = tbPlans.get(0).getPlanId();
        }

        //获取计划详情
        TbPlanDetail tbPlanDetail = new TbPlanDetail();
        tbPlanDetail.setPdRefId(planId);
        tbPlanDetail.setPdOrgan(organ);
        List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(tbPlanDetail);

        //计算总金额
        BigDecimal planCount = BigDecimal.ZERO;
        for (TbPlanDetail planDetail : tbPlanDetails) {
            planCount = planCount.add(planDetail.getPdAmount());
        }
        return planCount;

    }


    /**
     * 获取所有下级机构组合数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "selectOrgan", method = RequestMethod.POST)
    @SystemLog(tradeName = "获取下属机构信息", funCode = "PUB2", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public String selectOrgan(String method, String reqresetId) throws Exception {
        //本级机构
        FdOrgan thisOrgan = fdOrganMapper.selectByPK(getSessionOperInfo().getOrganCode());
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setUporgan(thisOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(searchOrgan);

        //一分查询2分机构就加本部
        if ("1".equals(thisOrgan.getOrganlevel())) {
            FdOrgan searchOrgan_1 = new FdOrgan();
            searchOrgan_1.setProvincecode(thisOrgan.getThiscode());
            searchOrgan_1.setOrganlevel("1");
            List<FdOrgan> organList_1 = fdOrganMapper.selectByAttr(searchOrgan_1);
            FdOrgan fdOrgan = organList_1.get(0);
            fdOrgan.setOrganname(fdOrgan.getOrganname() + "本部");
            fdOrganList.add(fdOrgan);
        }

        TbReqresetList tbReqresetList = new TbReqresetList();
        if (reqresetId != null && !"0".equals(reqresetId) && !"".equalsIgnoreCase(reqresetId)) {
            tbReqresetList = tbReqresetListService.selectByPK(Integer.parseInt(reqresetId));
        }


        Map<String, Object> results = new Hashtable<String, Object>();
        List<Map<String, Object>> treelist = new ArrayList<Map<String, Object>>();
        Map<String, Object> treeMap = new HashMap<String, Object>();
        String organNameStr = "";
        treeMap.put("id", thisOrgan.getThiscode());
        treeMap.put("name", thisOrgan.getOrganname());
        String selectedValue = "";
        String noSelectedValue = "";

        treeMap.put("id", "TangLoveQian");
        treeMap.put("name", "全选");
        treeMap.put("parentId", "0");
        selectedValue += "TangLoveQian";
        treelist.add(treeMap);

        for (FdOrgan fd : fdOrganList) {
            treeMap = new HashMap<>(64);
            treeMap.put("id", fd.getThiscode());
            treeMap.put("parentId", "TangLoveQian");
            treeMap.put("name", fd.getOrganname());
            selectedValue += ("," + fd.getThiscode());
            noSelectedValue += ("," + fd.getThiscode());
            treelist.add(treeMap);
        }
        if ("update".equals(method) || "info".equals(method)) {
            //0机构,1条线
            int reqresetType = tbReqresetList.getReqresetType();
            if (reqresetType == 0) {
                String organListStr = tbReqresetList.getReqresetOrganList();
                String[] organListNow = organListStr.split(",");
                Set<String> organSet = new HashSet<>(Arrays.asList(organListNow));
                for (Map<String, Object> map : treelist) {
                    Object organCode = map.get("id");
                    if (organSet.contains(organCode)) {
                        map.put("checked", "true");
                        organNameStr += ("#" + map.get("name"));
                        organSet.remove(organCode);
                    }
                }
            }
        } else {
            for (Map<String, Object> map : treelist) {
                map.put("checked", "true");
            }
        }
        if ("info".equals(method)) {
            organNameStr.replaceAll("#全选", "");
        }
        setAttribute("organNameStr", organNameStr);
        setAttribute("organSelectedValue", selectedValue);
        setAttribute("organOnSelectedValue", noSelectedValue);
        results.put("treeNodes", treelist);
        setAttribute("organList", JsonUtils.toJson(results));
        return JsonUtils.toJson(results);
    }

    /**
     * 获取所有贷种组合组合数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectLine", method = RequestMethod.POST)
    @SystemLog(tradeName = "获取下属机构信息", funCode = "PUB2", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectLine(String method, String reqresetId) throws Exception {
        ProductLineInfoDO productLineInfo = new ProductLineInfoDO();
        productLineInfo.setOrganCode(getSessionOperInfo().getOrganCode());
        List<ProductLineInfoDO> productLineInfoDOS = lineProductMapper.getAllAliveProductLineInfoByOrganCode(productLineInfo);
        TbReqresetList tbReqresetList = new TbReqresetList();
        if (reqresetId != null && !"0".equals(reqresetId)) {
            tbReqresetList = tbReqresetListService.selectByPK(Integer.parseInt(reqresetId));
        }
        Map<String, Object> results = new Hashtable<>();
        List<Map<String, Object>> treeList = new ArrayList();

        String lineNameStr = "";
        String selectedValue = "";
        Map<String, Object> treeMap = new HashMap<String, Object>();
        treeMap.put("id", "TangLoveQian");
        treeMap.put("parentId", "0");
        treeMap.put("name", "全选");
        selectedValue += "TangLoveQian";
        treeList.add(treeMap);
        for (ProductLineInfoDO productLineInfoDO : productLineInfoDOS) {
            treeMap = new HashMap<>(64);
            String lineId = productLineInfoDO.getLineId();
            String lineName = productLineInfoDO.getLineName();
            treeMap.put("id", lineId);
            treeMap.put("name", lineName);
            treeMap.put("parentId", "TangLoveQian");
            selectedValue += ("," + lineId);
            treeList.add(treeMap);
        }
        if ("update".equals(method) || "info".equals(method)) {
            //0机构,1条线
            int reqresetType = tbReqresetList.getReqresetType();
            if (reqresetType == 1) {
                String lineListStr = tbReqresetList.getReqresetProdLine();
                String[] lineListNow = lineListStr.split(",");
                Set<String> lineSet = new HashSet<>(Arrays.asList(lineListNow));
                for (Map<String, Object> map : treeList) {
                    Object organCode = map.get("id");
                    if (lineSet.contains(organCode)) {
                        map.put("checked", "true");
                        lineNameStr += ("#" + map.get("name"));
                        lineSet.remove(organCode);
                    }
                }
            }
        }
        if ("info".equals(method)) {
            lineNameStr.replaceAll("#全选", "");
        }
        setAttribute("lineSelectedValue", selectedValue);
        results.put("treeNodes", treeList);
        setAttribute("lineList", JsonUtils.toJson(results));
        setAttribute("lineNameStr", lineNameStr);
        return JsonUtils.toJson(results);
    }


    /**
     * 查询机构全量信息
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findOrgan", method = RequestMethod.POST)
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-05-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findOrgan(HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        List<Map<String, String>> fdOrganList = organService.findNextOrganCodeList(organCode);
        JSONObject listObj = new JSONObject();
        Map<String, String> map = new HashMap<>(50);
        for (Map<String, String> map1 : fdOrganList) {
            map.put(map1.get("value"), map1.get("key"));
        }
        listObj.put("organMap", map);
        return listObj.toString();
    }


    /**
     * 查询已下发的信贷需求
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findReqListMonth", method = RequestMethod.POST)
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-05-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findReqListMonth() throws Exception {
        List<TbReqList> TbReqListList = tbReqListService.getMonth();
        JSONObject listObj = new JSONObject();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        int dateNum = Integer.parseInt(sdf.format(date));
        for (TbReqList tb : TbReqListList) {
            Map<String, String> map = new HashMap<>();
            if (dateNum == Integer.parseInt((tb.getReqMonth()))) {
                map.put("value", tb.getReqMonth());
                map.put("key", tb.getReqMonth());
                list.add(map);
            }
        }
        listObj.put("list", list);
        return listObj.toString();
    }


    /**
     * 查询tb_req_list分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "showChildPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-01", funName = "加载下级需求列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showChildPage(TbReqresetList tbReqresetList, HttpSession session) throws Exception {
        setPageParam();
        TbReqresetDetail tbReqresetDetail = new TbReqresetDetail();
        tbReqresetDetail.setReqdresetRefId(tbReqresetList.getReqresetId());
        List<TbReqresetDetail> list = tbReqresetDetailService.selectByAttr(tbReqresetDetail);
        TbReqresetDetail totalDetail = new TbReqresetDetail();
        list.add(totalDetail);
        return pageData(list);
    }


    /**
     * 查询tb_req_list分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014.subtract(10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbReqresetList tbReqresetList, HttpSession session) throws Exception {
        setPageParam();
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        tbReqresetList.setReqresetOrgan(organCode);
        if (tbReqresetList.getReqresetState() != null && tbReqresetList.getReqresetState() == 0) {
            tbReqresetList.setReqresetState(100);
        } else if (tbReqresetList.getReqresetState() != null && tbReqresetList.getReqresetState() == 1) {
            tbReqresetList.setReqresetState(1);
        }
        List<TbReqresetList> list = tbReqresetListService.selectByAttr(tbReqresetList);
        return pageData(list);
    }


    /**
     * 查询贷种组合数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findComb", method = RequestMethod.POST)
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-01-04", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findComb() throws Exception {
        List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(3);
        JSONObject listObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (LoanCombDO tb : loanCombDOS) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", tb.getCombCode());
            jsonObject.put("key", tb.getCombName());
            jsonArray.add(jsonObject);
        }
        listObj.put("list", jsonArray);
        return listObj.toString();
    }

    /**
     * 查询贷种组合数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "showComb", method = RequestMethod.POST)
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-01-04", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showComb() throws Exception {
        List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(3);
        JSONObject listObj = new JSONObject();
        Map<String, String> map = new HashMap<>(32);
        for (LoanCombDO tb : loanCombDOS) {
            map.put(tb.getCombCode(), tb.getCombName());
        }
        listObj.put("combMap", map);
        return listObj.toString();
    }


    /**
     * 查询tb_req_list分页数据--查看上级机构的批次信息
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "showFindPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "PUB2", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showFindPage(HttpSession session, String reqresetMonth) throws Exception {

        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        FdOrgan fdOrgan = organService.selectByPK(organCode);
        String upOrganCode = fdOrgan.getUporgan();//获取到上级机构号，来查询上级下发的批次信息
        TbReqresetList searchTb = new TbReqresetList();
        searchTb.setReqresetTo(TbReqresetList.REQ_TO_CONSUMER);
        searchTb.setReqresetOrgan(upOrganCode);
        searchTb.setReqresetType(0);
        searchTb.setReqresetState(TbReqDetail.STATE_ISSUED);
        if (!"".equals(reqresetMonth) && reqresetMonth != null && reqresetMonth.trim().length() > 0) {
            searchTb.setReqresetMonth(reqresetMonth);
        }
        setPageParam();
        List<TbReqresetList> list = tbReqresetListService.selectByAttr(searchTb);

        TbReqresetDetail tbReqresetDetail = new TbReqresetDetail();
        tbReqresetDetail.setReqdresetOrgan(organCode);
        List<TbReqresetDetail> tbReqresetDetailList = tbReqresetDetailService.selectByAttr(tbReqresetDetail);
        List<TbReqresetList> tbReqresetListList = new ArrayList<>();
        for (TbReqresetList tbReqresetList : list) {
            //先统一默认刚下发
            tbReqresetList.setReqresetState(TbReqDetail.STATE_ISSUED);
            for (TbReqresetDetail tbReqDetailList : tbReqresetDetailList) {
                if (tbReqresetList.getReqresetId().equals(tbReqDetailList.getReqdresetRefId())) {
                    tbReqresetList.setReqresetState(tbReqDetailList.getReqdresetState());
                    break;
                }
            }
            if (tbReqresetList.getReqresetState() < TbReqDetail.STATE_SUBMITED) {
                tbReqresetListList.add(tbReqresetList);
            }
        }
        return pageData(tbReqresetListList);
    }


    /**
     * TODO 新增tb_req_list.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-01-01", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(HttpServletRequest request, HttpSession session) throws Exception {
        //最后操作员
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        TbReqresetList tbreqresetList = new TbReqresetList();
        String reqresetMonth = request.getParameter("reqresetMonth");
        String reqresetType = request.getParameter("reqresetType");
        String reqresetDateStart = request.getParameter("reqresetDateStart");
        String reqresetDateEnd = request.getParameter("reqresetDateEnd");
        String reqresetTimeStart = request.getParameter("reqresetTimeStart");
        String reqresetTimeEnd = request.getParameter("reqresetTimeEnd");
        String reqresetNote = request.getParameter("reqresetNote");
        String reqresetName = request.getParameter("reqresetName");
        String reqresetUnit = request.getParameter("reqresetUnit");
        String combListStr = request.getParameter("reqresetCombListStr");
        String organListStr = request.getParameter("reqresetOrganListStr");
        String lineListStr = request.getParameter("reqresetProdLineStr");

        if ("请选择".equals(reqresetMonth)) {
            reqresetMonth = "";
        }
        tbreqresetList.setReqresetMonth(reqresetMonth);
        tbreqresetList.setReqresetType(Integer.valueOf(reqresetType));
        tbreqresetList.setReqresetOrgan(organCode);

        if (!"".equals(reqresetMonth)) {
            List<TbReqresetList> checkTb = tbReqresetListService.checkApprovedList(tbreqresetList);
            if (checkTb != null && checkTb.size() > 0) {
                TbReqresetList tbReqresetList = checkTb.get(0);
                TbReqresetDetail serachTb = new TbReqresetDetail();
                serachTb.setReqdresetRefId(tbReqresetList.getReqresetId());
                List<TbReqresetDetail> tbReqresetDetailList = tbReqresetDetailService.selectByAttr(serachTb);
                for (TbReqresetDetail tb : tbReqresetDetailList) {
                    if (tb.getReqdresetState() != 16) {
                        return this.json.returnMsg("false", "新增失败,尚存在未完成机构类型的信贷需求调整!").toJson();
                    }
                }
            }
        }
        //前台传来的 字符串
        Set<String> combSet = new HashSet(Arrays.asList(combListStr.split(",")));

        TbCombDetail tbCombDetail = new TbCombDetail();
        tbCombDetail.setStatus(1);
        List<Map<String, Object>> combLists = loanCombMapper.selectComb(new HashMap<>());
        Set setOne = new HashSet();
        Set setTwo = new HashSet();
        Set setThree = new HashSet();
        for (Map<String, Object> tempMap : combLists) {
            if ("1".equals(tempMap.get("comblevel").toString())) {
                setOne.add(tempMap.get("combcode"));
            } else if ("2".equals(tempMap.get("comblevel").toString())) {
                setTwo.add(tempMap.get("combcode"));
            } else if ("3".equals(tempMap.get("comblevel").toString())) {
                setThree.add(tempMap.get("combcode"));
            }
        }


        if (combListStr.contains("totalOne")) {
            combSet.addAll(setOne);
        }
        if (combListStr.contains("totalTwo")) {
            combSet.addAll(setTwo);
        }
        if (combListStr.contains("totalThree")) {
            combSet.addAll(setThree);
        }
        combListStr = StringUtils.join(combSet.toArray(), ",");

        String[] tempCombStrArr = combListStr.split(",");
        String[] finalCombStrArr = new String[tempCombStrArr.length];
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());

        int num = 0;

        try {
            for (LoanCombDO loanCombDO : loanCombDOS) {
                for (int i = 0; i < tempCombStrArr.length; i++) {
                    if (tempCombStrArr[i].equals(loanCombDO.getCombCode())) {
                        Map<String, String> combMap = new HashMap<>(2);
                        finalCombStrArr[num] = tempCombStrArr[i];
                        num++;
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        combListStr = StringUtils.join(finalCombStrArr, ",");

        tbreqresetList.setReqresetUnit(Integer.valueOf(reqresetUnit));
        tbreqresetList.setReqresetOrganList(organListStr);
        tbreqresetList.setReqresetCombList(combListStr);
        tbreqresetList.setReqresetProdLine(lineListStr);
        tbreqresetList.setReqresetDateStart(reqresetDateStart);
        tbreqresetList.setReqresetDateEnd(reqresetDateEnd);
        tbreqresetList.setReqresetNote(reqresetNote);
        tbreqresetList.setReqresetName(reqresetName);
        tbreqresetList.setReqresetTimeStart(reqresetTimeStart);
        tbreqresetList.setReqresetTimeEnd(reqresetTimeEnd);
        tbreqresetList.setReqresetCreateOper(fdOper.getOpercode());
        tbreqresetList.setReqresetUpdateOper(fdOper.getOpercode());
        tbreqresetList.setReqresetCreatetime(BocoUtils.getTime());
        tbreqresetList.setReqresetUpdatetime(BocoUtils.getTime());
        //默认0： 未下发
        tbreqresetList.setReqresetState(TbReqDetail.STATE_NEW);
        tbreqresetList.setReqresetTo(TbReqList.REQ_TO_PRODUCER);

        tbReqresetListService.insertEntity(tbreqresetList);
        return this.json.returnMsg("true", "新增成功!").toJson();
    }

    /**
     * TODO 修改tb_req_list.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-01-02", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {

        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        TbReqresetList tbReqresetList = new TbReqresetList();
        String reqId = request.getParameter("reqresetId");
        String reqMonth = request.getParameter("reqresetMonth");
        String reqType = request.getParameter("reqresetType");
        String reqDateStart = request.getParameter("reqresetDateStart");
        String reqDateEnd = request.getParameter("reqresetDateEnd");
        String reqTimeStart = request.getParameter("reqresetTimeStart");
        String reqTimeEnd = request.getParameter("reqresetTimeEnd");
        String reqNote = request.getParameter("reqresetNote");
        String reqName = request.getParameter("reqresetName");
        String combListStr = request.getParameter("reqresetCombListStr");
        String organListStr = request.getParameter("reqresetOrganListStr");
        String lineListStr = request.getParameter("reqresetProdLineStr");
        String reqUnit = request.getParameter("reqresetUnit");
        if ("请选择".equals(reqMonth)) {
            reqMonth = "";
        }
        //前台传来的 字符串
        Set<String> combSet = new HashSet(Arrays.asList(combListStr.split(",")));

        TbCombDetail tbCombDetail = new TbCombDetail();
        tbCombDetail.setStatus(1);
        List<Map<String, Object>> combLists = loanCombMapper.selectComb(new HashMap<>());
        Set setOne = new HashSet();
        Set setTwo = new HashSet();
        Set setThree = new HashSet();
        for (Map<String, Object> tempMap : combLists) {
            if ("1".equals(tempMap.get("comblevel").toString())) {
                setOne.add(tempMap.get("combcode"));
            } else if ("2".equals(tempMap.get("comblevel").toString())) {
                setTwo.add(tempMap.get("combcode"));
            } else if ("3".equals(tempMap.get("comblevel").toString())) {
                setThree.add(tempMap.get("combcode"));
            }
        }


        if (combListStr.contains("totalOne")) {
            combSet.addAll(setOne);
        }
        if (combListStr.contains("totalTwo")) {
            combSet.addAll(setTwo);
        }
        if (combListStr.contains("totalThree")) {
            combSet.addAll(setThree);
        }
        combListStr = StringUtils.join(combSet.toArray(), ",");

        tbReqresetList.setReqresetMonth(reqMonth);
        tbReqresetList.setReqresetType(Integer.valueOf(reqType));
        tbReqresetList.setReqresetOrgan(organCode);
        tbReqresetList.setReqresetUnit(Integer.valueOf(reqUnit));
        tbReqresetList.setReqresetId(Integer.parseInt(reqId));
        tbReqresetList.setReqresetOrganList(organListStr);
        tbReqresetList.setReqresetCombList(combListStr);
        tbReqresetList.setReqresetProdLine(lineListStr);
        tbReqresetList.setReqresetDateStart(reqDateStart);
        tbReqresetList.setReqresetDateEnd(reqDateEnd);
        tbReqresetList.setReqresetNote(reqNote);
        tbReqresetList.setReqresetName(reqName);
        tbReqresetList.setReqresetTimeStart(reqTimeStart);
        tbReqresetList.setReqresetTimeEnd(reqTimeEnd);
        tbReqresetList.setReqresetCreateOper(fdOper.getOpercode());
        tbReqresetList.setReqresetUpdateOper(fdOper.getOpercode());
        tbReqresetList.setReqresetCreatetime(BocoUtils.getTime());
        tbReqresetList.setReqresetUpdatetime(BocoUtils.getTime());
        //默认0： 未下发
        tbReqresetList.setReqresetState(TbReqDetail.STATE_NEW);
        tbReqresetList.setReqresetTo(TbReqList.REQ_TO_PRODUCER);

        tbReqresetListService.updateByPK(tbReqresetList);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

    /**
     * TODO 删除tb_req_list
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-01-03", funName = "删除", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbReqresetList TbReqresetList) throws Exception {
        tbReqresetListService.deleteByPK(TbReqresetList.getReqresetId());
        return this.json.returnMsg("true", "删除成功!").toJson();
    }

    /**
     * 下发需求tb_req_list
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "issued")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-05-01-05", funName = "下发", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String issued(TbReqresetList tbReqresetList, HttpSession session) throws Exception {
        tbReqresetList = tbReqresetListService.selectByPK(tbReqresetList.getReqresetId());
        String reqName = tbReqresetList.getReqresetName();
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbReqresetList.setReqresetUpdateOper(fdOper.getOpercode());
        tbReqresetList.setReqresetUpdatetime(BocoUtils.getTime());
        tbReqresetList.setReqresetState(TbReqresetDetail.STATE_ISSUED);
        tbReqresetList.setReqresetTo(TbReqresetList.REQ_TO_CONSUMER);
        int type = tbReqresetList.getReqresetType();
        /*
         * 下发给条线比较特殊，几个条线将新建多条条线详情
         * */
        if (type == 1) {
            String lineStr = tbReqresetList.getReqresetProdLine();
            String[] lineArr = lineStr.replace("TangLoveQian,", "").split(",");
            List<TbLineReqresetDetail> tbLineReqresetDetails = new ArrayList<>();
            for (int i = 0; i < lineArr.length; i++) {
                TbLineReqresetDetail tbLineReqresetDetail = new TbLineReqresetDetail();
                tbLineReqresetDetail.setLineReqMonth(tbReqresetList.getReqresetMonth());
                tbLineReqresetDetail.setLineResetrefId(tbReqresetList.getReqresetId());
                tbLineReqresetDetail.setLineOrgan(tbReqresetList.getReqresetOrgan());
                tbLineReqresetDetail.setLineCode(lineArr[i]);
                ProductLineInfoDO productLineInfoDO = lineProductMapper.getProductLineInfoByLineId(lineArr[i]);
                tbLineReqresetDetail.setLineName(productLineInfoDO.getLineName());
                tbLineReqresetDetail.setLineState(TbLineReqDetail.STATE_ISSUED);
                tbLineReqresetDetail.setLineUnit(tbReqresetList.getReqresetUnit());
                tbLineReqresetDetail.setLineCreateTime(BocoUtils.getTime());
                tbLineReqresetDetail.setLineUpdateTime(BocoUtils.getTime());
                tbLineReqresetDetail.setLineReqName(tbReqresetList.getReqresetName());
                tbLineReqresetDetail.setLineReqNote(tbReqresetList.getReqresetNote());
                String combCodeStr = "";
                List<ProductLineDetailDO> productLineDetailDOList = lineProductDetailsMapper.getProductLineDetailById(lineArr[i]);
                for (ProductLineDetailDO productLineDetailDO : productLineDetailDOList) {
                    LoanCombDO loanComposeDO = loanCombMapper.getLoanCombInfoByCombCode(productLineDetailDO.getProductId());
                    if (Objects.nonNull(loanComposeDO)) {//
                        combCodeStr += (loanComposeDO.getCombCode() + ",");
                    }
                }
                tbLineReqresetDetail.setLineCombCode(combCodeStr);
                tbLineReqresetDetails.add(tbLineReqresetDetail);
            }
            //下发之后 批量
            tbLineReqresetDetailService.insertBatch(tbLineReqresetDetails);
        }
        tbReqresetListService.updateByPK(tbReqresetList);
        String operCode = getSessionOperInfo().getOperCode();//当前操作员
        String lineUrl = "tbTradeManger/tbLineReqResetDetail/insertUI.htm?lineReqresetId=";
        String url = "tbTradeManger/tbReqresetDetail/insertUI.htm?reqresetId=" + tbReqresetList.getReqresetId();

        TbLineReqresetDetail searchTbLineReqDetail = new TbLineReqresetDetail();
        searchTbLineReqDetail.setLineState(TbLineReqDetail.STATE_ISSUED);
        searchTbLineReqDetail.setLineReqMonth(tbReqresetList.getReqresetMonth());
        searchTbLineReqDetail.setLineResetrefId(tbReqresetList.getReqresetId());
        searchTbLineReqDetail.setLineOrgan(tbReqresetList.getReqresetOrgan());

        List<TbLineReqresetDetail> tbLineReqresetDetailList = tbLineReqresetDetailService.selectByAttr(searchTbLineReqDetail);
        int level = Integer.parseInt(getSessionOrgan().getOrganlevel());

        if (level == 0 && type == 0) {
            //总行机构下发给 36家一分
            String organListStr = tbReqresetList.getReqresetOrganList().replace("TangLoveQian,", "");
            String[] organArr = organListStr.split(",");
            List<String> operCodList = webOperRoleMapper.selectRoleCodeListByRoleId(organOperCodeOne);
            if (operCodList != null && operCodList.size() > 0) {
                for (String organCode : organArr) {
                    WebOperInfo searchOper = new WebOperInfo();
                    searchOper.setOrganCode(organCode);
                    List<WebOperInfo> operList = webOperInfoMapper.selectByAttr(searchOper);
                    for (WebOperInfo temp : operList) {
                        String operCodeTemp = temp.getOperCode();
                        String organCodeStr = temp.getOrganCode();
                        for (String roleOper : operCodList) {
                            if (roleOper.equals(operCodeTemp)) {
                                String msgNo = BocoUtils.getUUID();
                                saveMsg(msgNo, operCode, roleOper, url, tbReqresetList.getReqresetId().toString() + "_" + organCodeStr, reqName);
                            }
                        }
                    }
                }
            }

        } else if (level == 1 && type == 0) {
            //一分机构给二分下发
            String organListStr = tbReqresetList.getReqresetOrganList().replace("TangLoveQian,", "");
            String[] organArr = organListStr.split(",");
            List<String> operCodList = webOperRoleMapper.selectRoleCodeListByRoleId(organOperCodeTwo);
            if (operCodList != null && operCodList.size() > 0) {
                for (String organCode : organArr) {
                    WebOperInfo searchOper = new WebOperInfo();
                    searchOper.setOrganCode(organCode);
                    List<WebOperInfo> operList = webOperInfoMapper.selectByAttr(searchOper);
                    for (WebOperInfo temp : operList) {
                        String operCodeTemp = temp.getOperCode();
                        String organCodeStr = temp.getOrganCode();
                        for (String roleOper : operCodList) {
                            if (roleOper.equals(operCodeTemp)) {
                                String msgNo = BocoUtils.getUUID();
                                saveMsg(msgNo, operCode, roleOper, url, tbReqresetList.getReqresetId().toString() + "_" + organCodeStr, reqName);
                            }
                        }
                    }
                }
            }
        } else if (level == 0 && type == 1) {
            //总行条线 下发 给条线
            String lineListStr = tbReqresetList.getReqresetProdLine().replace("TangLoveQian,", "");
            String[] lineArr = lineListStr.split(",");
            List<String> operCodList = webOperRoleMapper.selectRoleCodeListByRoleId(lineOperCodeZero);
            //size==4
            for (String operCodeStr : operCodList) {
                WebOperLineDO searchTb = new WebOperLineDO().setStatus(1);
                searchTb.setOperCode(operCodeStr);
                List<WebOperLineDO> operLineList = webOperLineMapper.getAllWebOperLineByOperCode(searchTb);
                for (String lineCode : lineArr) {
                    for (WebOperLineDO operLineTemp : operLineList) {
                        if (lineCode.equals(operLineTemp.getLineId())) {
                            for (TbLineReqresetDetail tb : tbLineReqresetDetailList) {
                                String msgNo = BocoUtils.getUUID();
                                if (lineCode.equals(tb.getLineCode())) {
                                    saveMsg(msgNo, operCode, operLineTemp.getOperCode(), lineUrl + tb.getLineReqresetId(), tb.getLineReqresetId().toString(), reqName);
                                }
                            }
                        }
                    }
                }
            }

        } else if (level == 1 && type == 1) {
            //总行条线 下发 给条线
            String lineListStr = tbReqresetList.getReqresetProdLine().replace("TangLoveQian,", "");
            String[] lineArr = lineListStr.split(",");
            List<String> operCodList = webOperRoleMapper.selectRoleCodeListByRoleId(lineOperCodeOne);
            //size==4
            for (String operCodeStr : operCodList) {
                WebOperLineDO searchTb = new WebOperLineDO().setStatus(1);
                searchTb.setOperCode(operCodeStr);
                List<WebOperLineDO> operLineList = webOperLineMapper.getAllWebOperLineByOperCode(searchTb);
                for (String lineCode : lineArr) {
                    for (WebOperLineDO operLineTemp : operLineList) {
                        if (lineCode.equals(operLineTemp.getLineId())) {
                            for (TbLineReqresetDetail tb : tbLineReqresetDetailList) {
                                String msgNo = BocoUtils.getUUID();
                                if (lineCode.equals(tb.getLineCode())) {
                                    saveMsg(msgNo, operCode, operLineTemp.getOperCode(), lineUrl + tb.getLineReqresetId(), tb.getLineReqresetId().toString(), reqName);
                                }
                            }
                        }
                    }
                }
            }

        }


        return this.json.returnMsg("true", "下发成功!").toJson();
    }


    private void saveMsg(String msgNo, String operCode, String assignee, String msgUrl, String reqId, String reqName) throws Exception {
        WebMsg msg = new WebMsg();
        msg.setMsgNo(msgNo);
        msg.setAppDate(BocoUtils.getNowDate());
        msg.setAppUser(WebContextUtil.getSessionUser().getOpercode());
        msg.setAppOperName(WebContextUtil.getSessionUser().getOpername());
        msg.setAppRoleName("");
        msg.setAppOrganCode(WebContextUtil.getSessionOrgan().getThiscode());
        msg.setAppOrganName(WebContextUtil.getSessionOrgan().getOrganname());
        msg.setAppTime(BocoUtils.getNowTime());
        msg.setMsgType(DicCache.getKeyByName_("录入需求调整", "MSG_TYPE"));
        msg.setOperName(reqName);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("录入需求调整：" + reqName);
        webMsgService.insertEntity(msg);
    }

    /**
     * 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectReqresetId")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-05-01", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectReqId(TbReqresetList tbReqresetList, HttpServletRequest request, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbReqresetList.setReqresetOrgan(fdOper.getOrgancode());
        String reqId = request.getParameter("reqresetId");
        if (reqId != null && "".equals(reqId)) {
            tbReqresetList.setReqresetId(Integer.valueOf(reqId));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, Integer>> TbReqresetListList = tbReqresetListService.selectReqId(tbReqresetList);
        if (TbReqresetListList == null || TbReqresetListList.size() == 0) {
            resultMap.put("list", list);
            String json = JsonUtils.toJson(resultMap);
            return json;
        }
        for (Map<String, Integer> deptInfo : TbReqresetListList) {
            String data = String.valueOf(deptInfo.get("reqreset_id"));
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

    /**
     * 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectReqresetMonth")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-05-01", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectReqMonth(TbReqresetList tbReqresetList, HttpServletRequest request, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbReqresetList.setReqresetOrgan(fdOper.getOrgancode());
        String reqMonth = request.getParameter("reqresetMonth");
        if (reqMonth != null && "".equals(reqMonth)) {
            tbReqresetList.setReqresetMonth(reqMonth);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> TbReqresetListList = tbReqresetListService.selectReqMonth(tbReqresetList);
        if (TbReqresetListList == null || TbReqresetListList.size() == 0) {
            resultMap.put("list", list);
            String json = JsonUtils.toJson(resultMap);
            return json;
        }
        for (Map<String, String> deptInfo : TbReqresetListList) {
            String data = deptInfo.get("reqreset_month");
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


    /**
     * 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "showReqresetId")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-03", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showReqId(TbReqresetList tbReqresetList, HttpServletRequest request, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbReqresetList.setReqresetOrgan(fdOper.getOrgancode());
        FdOrgan fdOrgan = organService.selectByPK(fdOper.getOrgancode());
        String upOrganCode = "";
        if (fdOper.getOrgancode().equals("11005293")) {
            upOrganCode = "11005293";
        } else {
            upOrganCode = fdOrgan.getUporgan();
        }//获取到上级机构号，来查询上级下发的批次信息
        String reqId = request.getParameter("reqresetId");
        if (reqId != null && "".equals(reqId)) {
            tbReqresetList.setReqresetId(Integer.valueOf(reqId));
        }

        tbReqresetList.setReqresetTo(TbReqresetList.REQ_TO_CONSUMER);
        tbReqresetList.setReqresetOrgan(upOrganCode);
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, Integer>> TbReqresetListList = tbReqresetListService.showReqId(tbReqresetList);
        if (TbReqresetListList == null || TbReqresetListList.size() == 0) {
            resultMap.put("list", list);
            String json = JsonUtils.toJson(resultMap);
            return json;
        }
        for (Map<String, Integer> deptInfo : TbReqresetListList) {
            String data = String.valueOf(deptInfo.get("reqreset_id"));
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

    /**
     * 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "showReqresetMonth")
    @SystemLog(tradeName = "查询批次信息", funCode = "PUB-01-03", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showReqMonth(TbReqresetList tbReqresetList, HttpServletRequest request, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbReqresetList.setReqresetOrgan(fdOper.getOrgancode());
        FdOrgan fdOrgan = organService.selectByPK(fdOper.getOrgancode());
        String upOrganCode = "";
        if (fdOper.getOrgancode().equals("11005293")) {
            upOrganCode = "11005293";
        } else {
            upOrganCode = fdOrgan.getUporgan();
        }
        String reqMonth = request.getParameter("reqresetMonth");
        if (reqMonth != null && "".equals(reqMonth)) {
            tbReqresetList.setReqresetMonth(reqMonth);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        tbReqresetList.setReqresetTo(TbReqresetList.REQ_TO_CONSUMER);
        tbReqresetList.setReqresetOrgan(upOrganCode);
        List<Map<String, String>> TbReqresetListList = tbReqresetListService.showReqMonth(tbReqresetList);
        if (TbReqresetListList == null || TbReqresetListList.size() == 0) {
            resultMap.put("list", list);
            String json = JsonUtils.toJson(resultMap);
            return json;
        }
        for (Map<String, String> deptInfo : TbReqresetListList) {
            String data = deptInfo.get("reqreset_month");
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


    /**
     * 联想输入框-下发机构
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectReqresetOrgan")
    @SystemLog(tradeName = "需求报送要求下发", funCode = "PUB-05-01", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectReqresetOrgan(TbReqresetList tbReqresetList, HttpServletRequest request, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbReqresetList.setReqresetOrgan(fdOper.getOrgancode());
        String reqOrgan = request.getParameter("reqresetOrgan");
        if (reqOrgan != null && "".equals(reqOrgan)) {
            tbReqresetList.setReqresetOrgan(reqOrgan);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> tbReqresetListList = tbReqresetListService.selectReqresetOrgan(tbReqresetList);
        if (tbReqresetListList == null || tbReqresetListList.size() == 0) {
            resultMap.put("list", list);
            String json = JsonUtils.toJson(resultMap);
            return json;
        }
        for (Map<String, String> deptInfo : tbReqresetListList) {
            String data = deptInfo.get("reqreset_organ");
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


    //需求条线列表信息 导出excel
    @RequestMapping(value = "/exportresetLineExcel")
    @SystemLog(tradeName = "导出需求详情excel", funCode = "PUB2", funName = "导出需求调整条线详情excel", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void exportLineExcel(String lineResetrefId, HttpServletResponse response, HttpServletRequest request) throws Exception {
        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(Integer.valueOf(lineResetrefId));
        TbLineReqresetDetail searchTbReqDetail = new TbLineReqresetDetail();
        searchTbReqDetail.setLineResetrefId(Integer.valueOf(lineResetrefId));
        searchTbReqDetail.setLineState(TbReqDetail.STATE_APPROVED);
        List<TbLineReqresetDetail> tbLineReqresetDetails = tbLineReqresetDetailService.selectByAttr(searchTbReqDetail);
        //具体数据List
        List<TbLineReqresetDetail> tbReqLineDetailList = getDetailLineList(tbLineReqresetDetails, tbReqresetList.getReqresetMonth());
        //文件名
        String fileName = tbReqresetList.getReqresetName() + ".xls";
        TbLineReqresetDetail searchTb = new TbLineReqresetDetail();
        searchTb.setLineResetrefId(tbReqresetList.getReqresetId());
        List<TbLineReqresetDetail> list = tbLineReqresetDetailService.selectByAttr(searchTb);

        Map<String, List<Map<String, String>>> maps = getLineDetailName(list);
        List<Map<String, String>> combAmountNameList = maps.get("combAmountNameList");
        List<Map<String, String>> combList = maps.get("combList");
        DownResetLinePOIUtils.downPoi(request, response, fileName, combAmountNameList, tbReqLineDetailList, combList);
    }


    private Map<String, List<Map<String, String>>> getLineDetailName(List<TbLineReqresetDetail> list) {
        List<Map<String, String>> combList = new ArrayList<>();
        List<Map<String, String>> combListResult = new ArrayList<>();
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        Map<String, List<Map<String, String>>> maps = new HashMap<>();

        //表头name
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //到期量、净增量必填项，利率、余额非必填项
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "原计划");
        map1.put("code", "oldPlan");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "调整量");
        map2.put("code", "reqNum");
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "调整后计划");
        map3.put("code", "newPlan");
        combAmountNameList.add(map1);
        combAmountNameList.add(map2);
        combAmountNameList.add(map3);

        maps.put("combAmountNameList", combAmountNameList);

        for (TbLineReqresetDetail tbLineReqDetail : list) {
            String combCodeStr = tbLineReqDetail.getLineCombCode();
            String[] combArr = combCodeStr.split(",");
            for (LoanCombDO loanCombDO : loanCombDOS) {
                for (int i = 0; i < combArr.length; i++) {
                    if (combArr[i].equals(loanCombDO.getCombCode())) {
                        Map<String, String> combMap = new HashMap<>(2);
                        combMap.put("combCode", combArr[i]);
                        combMap.put("combName", loanCombDO.getCombName());
                        combList.add(combMap);
                        break;
                    }
                }
            }
        }
        //排序
        for (LoanCombDO loanCombDO : loanCombDOS) {
            for (Map<String, String> map : combList) {
                if (map.get("combCode").toString().equals(loanCombDO.getCombCode())) {
                    combListResult.add(map);
                    break;
                }
            }
        }
        Map<String, String> combMap = new HashMap<>(2);
        combMap.put("combCode", "total");
        combMap.put("combName", "总 计");
        combListResult.add(combMap);
        maps.put("combList", combListResult);
        return maps;
    }


    /**
     * 根据reqid 获取 条线 的数据列表
     *
     * @param list
     * @return
     */
    private List<TbLineReqresetDetail> getDetailLineList(List<TbLineReqresetDetail> list, String month) throws Exception {
        List<Map<String, Object>> planList = null;
        boolean planIsOk = false;
        if (month == null || "".equals(month)) {
            //没有指定月份，所以没有计划
        } else {
            planIsOk = true;
            planList = tbPlanadjService.getPlanDetail(getSessionOrgan().getThiscode(), month, 2);
        }
        BigDecimal unit = new BigDecimal(1);
        int reqUnit = -1;
        if (list != null && list.size() > 0) {
            reqUnit = list.get(0).getLineUnit();
        }
        if (reqUnit == 2) {
            unit = new BigDecimal(10000);
        }

        BigDecimal totalOldPlan = BigDecimal.ZERO;
        BigDecimal totalNum = BigDecimal.ZERO;
        BigDecimal totalNewPlan = BigDecimal.ZERO;

        List<TbLineReqresetDetail> tbLineReqDetailDTOS = new ArrayList<>();
        for (TbLineReqresetDetail tbLineReqDetail : list) {
            if (tbLineReqDetail != null) {
                String lineCombtr = tbLineReqDetail.getLineCombCode();
                if (lineCombtr.endsWith(",")) {
                    lineCombtr = lineCombtr.substring(0, lineCombtr.length() - 1);
                }
                String[] lineCombArr = lineCombtr.split(",");
                String lineNumStr = tbLineReqDetail.getLineNum();
                if (lineNumStr.endsWith(",")) {
                    lineNumStr = lineNumStr.substring(0, lineNumStr.length() - 1);
                }
                String[] lineNumArr = lineNumStr.split(",");
                for (int i = 0; i < lineCombArr.length; i++) {
                    TbLineReqresetDetail tb = new TbLineReqresetDetail();
                    String combCode = lineCombArr[i];
                    tb.setLineCombCode(combCode);
                    tb.setLineNum(new BigDecimal(lineNumArr[i]).divide(unit).toString());
                    tb.setOldPlan("无");
                    tb.setNewPlan("无");
                    if (planIsOk) {
                        for (Map<String, Object> map : planList) {
                            String planCombCode = map.get("loantype").toString();
                            if (planCombCode.equals(combCode)) {
                                BigDecimal oldPlan = new BigDecimal(map.get("amount").toString());
                                BigDecimal newPlan = oldPlan.add(new BigDecimal(lineNumArr[i]));
                                tb.setOldPlan(oldPlan.divide(unit).toString());
                                tb.setNewPlan(newPlan.divide(unit).toString());
                                totalOldPlan = totalOldPlan.add(new BigDecimal(tb.getOldPlan()));
                                totalNewPlan = totalNewPlan.add(new BigDecimal(tb.getNewPlan()));
                            }
                        }
                    }
                    totalNum = totalNum.add(new BigDecimal(tb.getLineNum()));
                    tbLineReqDetailDTOS.add(tb);
                }
            }
        }
        TbLineReqresetDetail tb = new TbLineReqresetDetail();
        tb.setLineCombCode("total");
        tb.setLineNum(totalNum.toString());
        tb.setOldPlan(totalOldPlan.toString());
        tb.setNewPlan(totalNewPlan.toString());
        tbLineReqDetailDTOS.add(tb);
        return tbLineReqDetailDTOS;
    }


    //需求机构列表信息 导出excel
    @RequestMapping(value = "/exportresetExcel")
    @SystemLog(tradeName = "导出需求详情excel", funCode = "PUB2", funName = "导出需求详情excel", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void exportresetExcel(String reqresetId, HttpServletResponse response, HttpServletRequest request) throws Exception {
        TbReqresetList tbReqList = tbReqresetListService.selectByPK(Integer.valueOf(reqresetId));
        //具体数据List
        List<TbReqresetDetail> tbReqDetailList = getDetailList(reqresetId);
        //文件名
        String fileName = tbReqList.getReqresetName() + ".xls";
        Map<String, List<Map<String, String>>> maps = getOrganDetailName(reqresetId);
        List<Map<String, String>> combAmountNameList = maps.get("combAmountNameList");
        List<Map<String, String>> organList = maps.get("organList");
        DownResetPOIUtils.downPoi(request, response, fileName, combAmountNameList, tbReqDetailList, organList);
    }


    /**
     * 处理tbReqDetailList
     *
     * @return
     */
    private List<TbReqresetDetail> getDetailList(String reqresetId) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(2);
        TbReqresetDetail searchTbReqDetail = new TbReqresetDetail();
        searchTbReqDetail.setReqdresetRefId(Integer.valueOf(reqresetId));
        searchTbReqDetail.setReqdresetState(TbReqresetDetail.STATE_APPROVED);
        List<TbReqresetDetail> tbReqresetDetails = tbReqresetDetailService.selectByAttr(searchTbReqDetail);
        TbReqresetList searchTbList = tbReqresetListService.selectByPK(Integer.parseInt(reqresetId));
        String month = searchTbList.getReqresetMonth();
        List<Map<String, Object>> planList = null;
        boolean planIsOk = false;
        if (month == null || "".equals(month)) {
            //没有指定月份，所以没有计划
        } else {
            //机构的 计划 查询条件 需要修改为上级机构 code
            planList = tbPlanadjService.getPlanDetail(getSessionOrgan().getUporgan(), month, 1);
            if (planList != null && planList.size() > 0) {
                planIsOk = true;
            }
        }
        int unit = 1;
        if (tbReqresetDetails != null && tbReqresetDetails.size() > 0) {
            unit = tbReqresetDetails.get(0).getReqdresetUnit();
        }
        BigDecimal tbUnit = new BigDecimal(1);
        if (unit == 2) {
            tbUnit = new BigDecimal(10000);
        }
        TbReqresetList tbReqresetList = new TbReqresetList();
        if (tbReqresetDetails != null && tbReqresetDetails.size() > 0) {
            int reqresetRefId = tbReqresetDetails.get(0).getReqdresetRefId();
            tbReqresetList = tbReqresetListService.selectByPK(reqresetRefId);
        }
        //添加 新旧计划属性
        //合计每个机构的 所有贷种组合的 oldPlan num newPlan
        Map<String, TbReqresetDetail> organTotalMap = new HashMap<>();
        for (TbReqresetDetail tb : tbReqresetDetails) {
            tb.setReqdresetNum(tb.getReqdresetNum().divide(tbUnit));
            tb.setReqdresetCreatetime(getPlanCount(tbReqresetList.getReqresetMonth(), tb.getReqdresetOrgan()).toString());
            tb.setOldPlan("无");
            tb.setNewPlan("无");
            if (planIsOk) {
                for (Map<String, Object> map : planList) {
                    String planCombCode = map.get("loantype").toString();
                    String organcodeCodeStr = map.get("organcode").toString();
                    if (planCombCode.equals(tb.getReqdresetCombCode()) && organcodeCodeStr.equals(tb.getReqdresetOrgan())) {
                        BigDecimal oldPlan = new BigDecimal(map.get("amount").toString());
                        BigDecimal newPlan = oldPlan.add(tb.getReqdresetNum());
                        tb.setOldPlan(oldPlan.divide(tbUnit).toString());
                        tb.setNewPlan(newPlan.divide(tbUnit).toString());
                    } else {
                        if ((tb.getReqdresetNum().compareTo(BigDecimal.ZERO) == 1) && "无".equals(tb.getNewPlan()) && organcodeCodeStr.equals(tb.getReqdresetOrgan())) {
                            tb.setNewPlan(tb.getReqdresetNum().divide(tbUnit).toString());
                            tb.setOldPlan("无");
                        }
                    }
                }
                TbReqresetDetail organTotalTempTb = organTotalMap.get(tb.getReqdresetOrgan());
                if (organTotalTempTb == null) {
                    organTotalTempTb = new TbReqresetDetail();
                    organTotalTempTb.setTotalOldPlan("0");
                    organTotalTempTb.setTotalNum("0");
                    organTotalTempTb.setTotalNewPlan("0");
                }
                if (!"无".equals(tb.getOldPlan())) {
                    organTotalTempTb.setTotalOldPlan(new BigDecimal(organTotalTempTb.getTotalOldPlan()).add(new BigDecimal(tb.getOldPlan())).toString());
                }
                organTotalTempTb.setTotalNum(new BigDecimal(organTotalTempTb.getTotalNum()).add(tb.getReqdresetNum()).toString());

                if (!"无".equals(tb.getNewPlan())) {
                    organTotalTempTb.setTotalNewPlan(new BigDecimal(organTotalTempTb.getTotalNewPlan()).add(new BigDecimal(tb.getNewPlan())).toString());
                }

                organTotalMap.put(tb.getReqdresetOrgan(), organTotalTempTb);
            }
        }

        for (TbReqresetDetail tb : tbReqresetDetails) {
            TbReqresetDetail tempOrganTb = organTotalMap.get(tb.getReqdresetOrgan());
            if (tempOrganTb != null) {
                tb.setTotalOldPlan(tempOrganTb.getTotalOldPlan());
                tb.setTotalNum(tempOrganTb.getTotalNum());
                tb.setTotalNewPlan(tempOrganTb.getTotalNewPlan());
            }
        }

        //合计每个贷种组合的 所有机构的 oldPlan num newPlan  totalNum totalOldPlan totalNewPlan
        Map<String, TbReqresetDetail> combTotalMap = new HashMap<>();
        for (TbReqresetDetail tb : tbReqresetDetails) {
            TbReqresetDetail tempCombTb = combTotalMap.get(tb.getReqdresetCombCode());
            if (tempCombTb == null) {
                tempCombTb = new TbReqresetDetail();
                tempCombTb.setReqdresetNum(BigDecimal.ZERO);
                tempCombTb.setOldPlan("0");
                tempCombTb.setNewPlan("0");
                tempCombTb.setTotalOldPlan("0");
                tempCombTb.setTotalNum("0");
                tempCombTb.setTotalNewPlan("0");
                tempCombTb.setReqdresetCombCode(tb.getReqdresetCombCode());
            }
            if (!"无".equals(tb.getOldPlan())) {
                tempCombTb.setOldPlan(new BigDecimal(tempCombTb.getOldPlan()).add(new BigDecimal(tb.getOldPlan())).toString());
            }
            tempCombTb.setReqdresetNum(tempCombTb.getReqdresetNum().add(tb.getReqdresetNum()));

            if (!"无".equals(tb.getNewPlan())) {
                tempCombTb.setNewPlan(new BigDecimal(tempCombTb.getNewPlan()).add(new BigDecimal(tb.getNewPlan())).toString());
            }
            combTotalMap.put(tb.getReqdresetCombCode(), tempCombTb);
        }
        Set<String> combKeySet = combTotalMap.keySet();

        BigDecimal totalCombOldPlan = BigDecimal.ZERO;
        BigDecimal totalCombNewPlan = BigDecimal.ZERO;
        BigDecimal totalCombNum = BigDecimal.ZERO;
        for (String combCodeStr : combKeySet) {
            TbReqresetDetail tb = combTotalMap.get(combCodeStr);
            tb.setReqdresetOrgan("TangLoveQianForever");
            tb.setReqdresetUpdatetime("--  --  --");
            tbReqresetDetails.add(tb);

            if (!"无".equals(tb.getOldPlan())) {
                totalCombOldPlan = totalCombOldPlan.add(new BigDecimal(tb.getOldPlan()));
            }
            if (!"无".equals(tb.getNewPlan())) {
                totalCombNewPlan = totalCombNewPlan.add(new BigDecimal(tb.getNewPlan()));
            }
            totalCombNum = totalCombNum.add(tb.getReqdresetNum());
        }

        for (TbReqresetDetail tb : tbReqresetDetails) {
            if ("TangLoveQianForever".equals(tb.getReqdresetOrgan())) {
                tb.setTotalNewPlan(totalCombNewPlan.toString());
                tb.setTotalOldPlan(totalCombOldPlan.toString());
                tb.setTotalNum(totalCombNum.toString());
            }
        }
        return tbReqresetDetails;
    }


    /**
     * 获取机构的excel表头name
     *
     * @param reqresetId
     * @return
     * @throws Exception
     */
    private Map<String, List<Map<String, String>>> getOrganDetailName(String reqresetId) throws Exception {
        TbReqresetList tbReqresetList = tbReqresetListService.selectByPK(Integer.valueOf(reqresetId));
        WebOperInfo webOperInfo = getSessionOperInfo();
        String organCode = webOperInfo.getOrganCode();
        String combListStr = "";
        String organListStr = "";
        List<Map<String, String>> organList = new ArrayList<>();
        organListStr = tbReqresetList.getReqresetOrganList();
        String[] organArr = organListStr.split(",");
        FdOrgan thisOrgan = fdOrganMapper.selectByPK(organCode);
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setUporgan(thisOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(searchOrgan);

        //一分查询2分机构就加本部
        if ("1".equals(thisOrgan.getOrganlevel())) {
            FdOrgan searchOrgan_1 = new FdOrgan();
            searchOrgan_1.setProvincecode(thisOrgan.getThiscode());
            searchOrgan_1.setOrganlevel("1");
            List<FdOrgan> organList_1 = fdOrganMapper.selectByAttr(searchOrgan_1);
            FdOrgan fdOrgan = organList_1.get(0);
            fdOrgan.setOrganname(fdOrgan.getOrganname() + "本部");
            fdOrganList.add(fdOrgan);
        }

        Map<String, String> organMap = new HashMap<>(16);
        for (FdOrgan fdOrgan : fdOrganList) {
            for (int i = 0; i < organArr.length; i++) {
                if (fdOrgan.getThiscode().equals(organArr[i])) {
                    if (!fdOrgan.getThiscode().equals("11000013")) {
                        organMap = new HashMap<>(2);
                        organMap.put("organCode", fdOrgan.getThiscode());
                        organMap.put("organName", fdOrgan.getOrganname());
                        organList.add(organMap);
                        break;
                    }
                }
            }
        }
        if ("0".equals(thisOrgan.getOrganlevel())) {
            organMap = new HashMap<>(2);
            organMap.put("organCode", "11000013");
            organMap.put("organName", "北京分行");
            organList.add(organMap);
        }
        organMap = new HashMap<>();
        organMap.put("organCode", "TangLoveQianForever");
        organMap.put("organName", "总计");
        organList.add(organMap);
        setAttribute("tbReqresetList", tbReqresetList);
        setAttribute("organList", organList);
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        combListStr = tbReqresetList.getReqresetCombList();
        String[] combArr = combListStr.split(",");
        for (LoanCombDO loanCombDO : loanCombDOS) {
        for (int i = 0; i < combArr.length; i++) {
                if (combArr[i].equals(loanCombDO.getCombCode())) {
                    Map<String, String> map1 = new HashMap<>(2);
                    map1.put("name", loanCombDO.getCombName());
                    map1.put("code", combArr[i]);
                    combAmountNameList.add(map1);
                    break;
                }
            }
        }
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "合计");
        map1.put("code", "total");
        combAmountNameList.add(map1);
        Map<String, List<Map<String, String>>> maps = new HashMap<>();
        maps.put("combAmountNameList", combAmountNameList);
        maps.put("organList", organList);
        return maps;
    }


}