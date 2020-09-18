package com.boco.PUB.controller.tbreqList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boco.PM.service.IWebMsgService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.PM.service.IFdOrganService;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.PUB.service.ITbReqDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.common.PlainResult;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * 需求明细表
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbTradeManger/tbReqDetail/")
public class TbReqDetailController extends BaseController {
    @Autowired
    private ITbReqDetailService tbReqDetailService;
    @Autowired
    private IWebMsgService webMsgService;
    @Autowired
    IWebLoanCombService webLoanService;
    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    LoanCombMapper loanCombMapper;

    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-01-04", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbReqStatistics/tbReqDetail/tbReqDetailList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-01-04-04", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(String reqId, HttpSession session) throws Exception {
        TbReqList tbReqList = tbReqListService.selectByPK(Integer.valueOf(reqId));
        if (tbReqList.getReqDateStart() != null && tbReqList.getReqDateStart().trim().length() > 0)
            tbReqList.setReqDateStart(tbReqList.getReqDateStart().substring(0, 4) + "-" + tbReqList.getReqDateStart().substring(4, 6) + "-" + tbReqList.getReqDateStart().substring(6, 8));
        if (tbReqList.getReqDateEnd() != null && tbReqList.getReqDateEnd().trim().length() > 0)
            tbReqList.setReqDateEnd(tbReqList.getReqDateEnd().substring(0, 4) + "-" + tbReqList.getReqDateEnd().substring(4, 6) + "-" + tbReqList.getReqDateEnd().substring(6, 8));
        if (tbReqList.getReqTimeStart() != null && tbReqList.getReqTimeStart().trim().length() > 0)
            tbReqList.setReqTimeStart(tbReqList.getReqTimeStart().substring(0, 4) + "-" + tbReqList.getReqTimeStart().substring(4, 6) + "-" + tbReqList.getReqTimeStart().substring(6, 8));
        if (tbReqList.getReqTimeEnd() != null && tbReqList.getReqTimeEnd().trim().length() > 0)
            tbReqList.setReqTimeEnd(tbReqList.getReqTimeEnd().substring(0, 4) + "-" + tbReqList.getReqTimeEnd().substring(4, 6) + "-" + tbReqList.getReqTimeEnd().substring(6, 8));
        if (tbReqList.getExpTimeStart() != null && tbReqList.getExpTimeStart().trim().length() > 0)
            tbReqList.setExpTimeStart(tbReqList.getExpTimeStart().substring(0, 4) + "-" + tbReqList.getExpTimeStart().substring(4, 6) + "-" + tbReqList.getExpTimeStart().substring(6, 8));
        if (tbReqList.getExpTimeEnd() != null && tbReqList.getExpTimeEnd().trim().length() > 0)
            tbReqList.setExpTimeEnd(tbReqList.getExpTimeEnd().substring(0, 4) + "-" + tbReqList.getExpTimeEnd().substring(4, 6) + "-" + tbReqList.getExpTimeEnd().substring(6, 8));
        if (tbReqList.getRateTimeStart() != null && tbReqList.getRateTimeStart().trim().length() > 0)
            tbReqList.setRateTimeStart(tbReqList.getRateTimeStart().substring(0, 4) + "-" + tbReqList.getRateTimeStart().substring(4, 6) + "-" + tbReqList.getRateTimeStart().substring(6, 8));
        if (tbReqList.getRateTimeEnd() != null && tbReqList.getRateTimeEnd().trim().length() > 0)
            tbReqList.setRateTimeEnd(tbReqList.getRateTimeEnd().substring(0, 4) + "-" + tbReqList.getRateTimeEnd().substring(4, 6) + "-" + tbReqList.getRateTimeEnd().substring(6, 8));
        if (tbReqList.getBalanceTimeStart() != null && tbReqList.getBalanceTimeStart().trim().length() > 0)
            tbReqList.setBalanceTimeStart(tbReqList.getBalanceTimeStart().substring(0, 4) + "-" + tbReqList.getBalanceTimeStart().substring(4, 6) + "-" + tbReqList.getBalanceTimeStart().substring(6, 8));
        if (tbReqList.getBalanceTimeEnd() != null && tbReqList.getBalanceTimeEnd().trim().length() > 0)
            tbReqList.setBalanceTimeEnd(tbReqList.getBalanceTimeEnd().substring(0, 4) + "-" + tbReqList.getBalanceTimeEnd().substring(4, 6) + "-" + tbReqList.getBalanceTimeEnd().substring(6, 8));

        setAttribute("TbReqListDTO", tbReqList);
        setAttribute("reqId", reqId);
        setAttr(tbReqList);
        return basePath + "/PUB/tbReqStatistics/tbReqDetail/tbReqDetailInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-01-04-01", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI(TbReqList tbReqList, HttpSession session) throws Exception {
        tbReqList = tbReqListService.selectByPK(tbReqList.getReqId());
        if (tbReqList.getReqDateStart() != null && tbReqList.getReqDateStart().trim().length() > 0)
            tbReqList.setReqDateStart(tbReqList.getReqDateStart().substring(0, 4) + "-" + tbReqList.getReqDateStart().substring(4, 6) + "-" + tbReqList.getReqDateStart().substring(6, 8));
        if (tbReqList.getReqDateEnd() != null && tbReqList.getReqDateEnd().trim().length() > 0)
            tbReqList.setReqDateEnd(tbReqList.getReqDateEnd().substring(0, 4) + "-" + tbReqList.getReqDateEnd().substring(4, 6) + "-" + tbReqList.getReqDateEnd().substring(6, 8));
        if (tbReqList.getReqTimeStart() != null && tbReqList.getReqTimeStart().trim().length() > 0)
            tbReqList.setReqTimeStart(tbReqList.getReqTimeStart().substring(0, 4) + "-" + tbReqList.getReqTimeStart().substring(4, 6) + "-" + tbReqList.getReqTimeStart().substring(6, 8));
        if (tbReqList.getReqTimeEnd() != null && tbReqList.getReqTimeEnd().trim().length() > 0)
            tbReqList.setReqTimeEnd(tbReqList.getReqTimeEnd().substring(0, 4) + "-" + tbReqList.getReqTimeEnd().substring(4, 6) + "-" + tbReqList.getReqTimeEnd().substring(6, 8));
        if (tbReqList.getExpTimeStart() != null && tbReqList.getExpTimeStart().trim().length() > 0)
            tbReqList.setExpTimeStart(tbReqList.getExpTimeStart().substring(0, 4) + "-" + tbReqList.getExpTimeStart().substring(4, 6) + "-" + tbReqList.getExpTimeStart().substring(6, 8));
        if (tbReqList.getExpTimeEnd() != null && tbReqList.getExpTimeEnd().trim().length() > 0)
            tbReqList.setExpTimeEnd(tbReqList.getExpTimeEnd().substring(0, 4) + "-" + tbReqList.getExpTimeEnd().substring(4, 6) + "-" + tbReqList.getExpTimeEnd().substring(6, 8));
        if (tbReqList.getRateTimeStart() != null && tbReqList.getRateTimeStart().trim().length() > 0)
            tbReqList.setRateTimeStart(tbReqList.getRateTimeStart().substring(0, 4) + "-" + tbReqList.getRateTimeStart().substring(4, 6) + "-" + tbReqList.getRateTimeStart().substring(6, 8));
        if (tbReqList.getRateTimeEnd() != null && tbReqList.getRateTimeEnd().trim().length() > 0)
            tbReqList.setRateTimeEnd(tbReqList.getRateTimeEnd().substring(0, 4) + "-" + tbReqList.getRateTimeEnd().substring(4, 6) + "-" + tbReqList.getRateTimeEnd().substring(6, 8));
        if (tbReqList.getBalanceTimeStart() != null && tbReqList.getBalanceTimeStart().trim().length() > 0)
            tbReqList.setBalanceTimeStart(tbReqList.getBalanceTimeStart().substring(0, 4) + "-" + tbReqList.getBalanceTimeStart().substring(4, 6) + "-" + tbReqList.getBalanceTimeStart().substring(6, 8));
        if (tbReqList.getBalanceTimeEnd() != null && tbReqList.getBalanceTimeEnd().trim().length() > 0)
            tbReqList.setBalanceTimeEnd(tbReqList.getBalanceTimeEnd().substring(0, 4) + "-" + tbReqList.getBalanceTimeEnd().substring(4, 6) + "-" + tbReqList.getBalanceTimeEnd().substring(6, 8));

        setAttribute("TbReqListDTO", tbReqList);
        setAttr(tbReqList);
        return basePath + "/PUB/tbReqStatistics/tbReqDetail/tbReqDetailAdd";


    }

    /**
     * @param tbReqList
     * @throws Exception
     */
    private void setAttr(TbReqList tbReqList) throws Exception {
        int reqType = tbReqList.getReqType();

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //到期量、净增量必填项，利率、余额非必填项
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "到期量");
        map1.put("code", "expNum");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "净增量");
        map2.put("code", "reqNum");
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "预计新发生利率(%)");
        map3.put("code", "rate");
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "预计期末时点余额");
        map4.put("code", "balance");
        String numTypeStr = tbReqList.getNumType();
        if (numTypeStr.contains("1")) {
            combAmountNameList.add(map1);
        }
        if (numTypeStr.contains("2")) {
            combAmountNameList.add(map2);
        }
        if (numTypeStr.contains("4")) {
            combAmountNameList.add(map3);
        }
        if (numTypeStr.contains("8")) {
            combAmountNameList.add(map4);
        }

        setAttribute("combAmountNameList", combAmountNameList);
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());

        if (reqType == 0) {
            List<Map<String, String>> combList = new ArrayList<>();
            String combListStr = tbReqList.getReqCombList();
            String[] combArr = combListStr.split(",");

            for (LoanCombDO loanCombDO : loanCombDOS) {
                for (int i = 0; i < combArr.length; i++) {
                    if (combArr[i].equals(loanCombDO.getCombCode())) {
                        Map<String, String> combMap = new HashMap<>(2);
                        combMap.put("combCode", combArr[i]);
                        combMap.put("combName", loanCombDO.getCombName());
                        combMap.put("combLevel", String.valueOf(loanCombDO.getCombLevel()));
                        combList.add(combMap);
                        break;
                    }
                }
            }
            setAttribute("combList", combList);
        }
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-01-04-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(String reqId, HttpSession session) throws Exception {
        TbReqList tbReqList = tbReqListService.selectByPK(Integer.valueOf(reqId));
        if (tbReqList.getReqDateStart() != null && tbReqList.getReqDateStart().trim().length() > 0)
            tbReqList.setReqDateStart(tbReqList.getReqDateStart().substring(0, 4) + "-" + tbReqList.getReqDateStart().substring(4, 6) + "-" + tbReqList.getReqDateStart().substring(6, 8));
        if (tbReqList.getReqDateEnd() != null && tbReqList.getReqDateEnd().trim().length() > 0)
            tbReqList.setReqDateEnd(tbReqList.getReqDateEnd().substring(0, 4) + "-" + tbReqList.getReqDateEnd().substring(4, 6) + "-" + tbReqList.getReqDateEnd().substring(6, 8));
        if (tbReqList.getReqTimeStart() != null && tbReqList.getReqTimeStart().trim().length() > 0)
            tbReqList.setReqTimeStart(tbReqList.getReqTimeStart().substring(0, 4) + "-" + tbReqList.getReqTimeStart().substring(4, 6) + "-" + tbReqList.getReqTimeStart().substring(6, 8));
        if (tbReqList.getReqTimeEnd() != null && tbReqList.getReqTimeEnd().trim().length() > 0)
            tbReqList.setReqTimeEnd(tbReqList.getReqTimeEnd().substring(0, 4) + "-" + tbReqList.getReqTimeEnd().substring(4, 6) + "-" + tbReqList.getReqTimeEnd().substring(6, 8));
        if (tbReqList.getExpTimeStart() != null && tbReqList.getExpTimeStart().trim().length() > 0)
            tbReqList.setExpTimeStart(tbReqList.getExpTimeStart().substring(0, 4) + "-" + tbReqList.getExpTimeStart().substring(4, 6) + "-" + tbReqList.getExpTimeStart().substring(6, 8));
        if (tbReqList.getExpTimeEnd() != null && tbReqList.getExpTimeEnd().trim().length() > 0)
            tbReqList.setExpTimeEnd(tbReqList.getExpTimeEnd().substring(0, 4) + "-" + tbReqList.getExpTimeEnd().substring(4, 6) + "-" + tbReqList.getExpTimeEnd().substring(6, 8));
        if (tbReqList.getRateTimeStart() != null && tbReqList.getRateTimeStart().trim().length() > 0)
            tbReqList.setRateTimeStart(tbReqList.getRateTimeStart().substring(0, 4) + "-" + tbReqList.getRateTimeStart().substring(4, 6) + "-" + tbReqList.getRateTimeStart().substring(6, 8));
        if (tbReqList.getRateTimeEnd() != null && tbReqList.getRateTimeEnd().trim().length() > 0)
            tbReqList.setRateTimeEnd(tbReqList.getRateTimeEnd().substring(0, 4) + "-" + tbReqList.getRateTimeEnd().substring(4, 6) + "-" + tbReqList.getRateTimeEnd().substring(6, 8));
        if (tbReqList.getBalanceTimeStart() != null && tbReqList.getBalanceTimeStart().trim().length() > 0)
            tbReqList.setBalanceTimeStart(tbReqList.getBalanceTimeStart().substring(0, 4) + "-" + tbReqList.getBalanceTimeStart().substring(4, 6) + "-" + tbReqList.getBalanceTimeStart().substring(6, 8));
        if (tbReqList.getBalanceTimeEnd() != null && tbReqList.getBalanceTimeEnd().trim().length() > 0)
            tbReqList.setBalanceTimeEnd(tbReqList.getBalanceTimeEnd().substring(0, 4) + "-" + tbReqList.getBalanceTimeEnd().substring(4, 6) + "-" + tbReqList.getBalanceTimeEnd().substring(6, 8));

        setAttribute("TbReqListDTO", tbReqList);
        setAttribute("reqId", reqId);
        setAttr(tbReqList);
        return basePath + "/PUB/tbReqStatistics/tbReqDetail/tbReqDetailEdit";
    }

    /**
     * 详情页数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getReqDetailList")
    @SystemLog(tradeName = "信贷需求", funCode = "PUB2", funName = "信贷计划详情数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanDetailData(String reqId, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        TbReqDetail tbReqDetail = new TbReqDetail();
        tbReqDetail.setReqdRefId(Integer.valueOf(reqId));
        tbReqDetail.setReqdOrgan(organCode);
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        Map<String, Integer> combCodeLevelMap = new HashMap<>();
        for (LoanCombDO tempComb : loanCombDOS) {
            combCodeLevelMap.put(tempComb.getCombCode(), tempComb.getCombLevel());
        }
        List<TbReqDetail> tbReqDetailList = tbReqDetailService.selectByAttr(tbReqDetail);
        BigDecimal unit = new BigDecimal(1);
        int reqUnit = tbReqDetailList.get(0).getReqdUnit();
        if (reqUnit == 2) {
            unit = new BigDecimal(10000);
        }
        for (TbReqDetail tbReqDetail1 : tbReqDetailList) {
            tbReqDetail1.setReqdExpnum(BigDecimalUtil.divide(tbReqDetail1.getReqdExpnum(), unit, 8));
            tbReqDetail1.setReqdReqnum(BigDecimalUtil.divide(tbReqDetail1.getReqdReqnum(), unit, 8));
            tbReqDetail1.setReqdBalance(BigDecimalUtil.divide(tbReqDetail1.getReqdBalance(), unit, 8));
            tbReqDetail1.setReqdRefId(combCodeLevelMap.get(tbReqDetail1.getReqdCombCode()));
        }
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("tbReqDetailList", tbReqDetailList);
        return JSON.toJSONString(resultMap);
    }


    /**
     * 查询tb_req_list分页数据--查看上级机构的批次信息
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-10-29      txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "showFindPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "PUB-01-04", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showFindPage(HttpSession session, TbReqList tbReqList) throws Exception {

        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        FdOrgan fdOrgan = fdOrganService.selectByPK(organCode);
        String upOrganCode = fdOrgan.getUporgan();
        tbReqList.setReqOrgan(upOrganCode);
        tbReqList.setReqTo(TbReqList.REQ_TO_CONSUMER);
        tbReqList.setReqType(0);
        tbReqList.setReqState(TbReqDetail.STATE_ISSUED);
        TbReqDetail tbReqDetail = new TbReqDetail();
        tbReqDetail.setReqdOrgan(organCode);
        List<TbReqList> list = tbReqDetailService.selectTbreqList(tbReqDetail, tbReqList);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //起始页码
        int pageNum = Integer.valueOf(request.getParameter("pageNo"));
        //每页显示条数
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        List<TbReqList> newList = getListPage(pageNum, pageSize, list);
        //返回页面的分页数据
        Map<String, Object> results = new Hashtable<String, Object>();
        if (newList == null) {
            results.put("pager.pageNo", 1);
            results.put("pager.totalRows", 0);
            results.put("rows", new ArrayList<>());
            return JsonUtils.toJson(results);
        }
        //用PageInfo对结果进行包装
        results.put("pager.pageNo", pageNum);
        results.put("pager.totalRows", list.size());
        results.put("rows", newList);
        return JsonUtils.toJson(results);
    }

    /**
     * 手动分页功能
     *
     * @param page
     * @param pageSize
     * @param list
     * @return
     */
    private static List<TbReqList> getListPage(int page, int pageSize, List<TbReqList> list) {
        if (list != null) {
            int totalCount = list.size();
            page = page - 1;
            int fromIndex = page * pageSize;
            if (fromIndex < totalCount) {
                int toIndex = (page + 1) * pageSize;
                if (toIndex > totalCount) {
                    toIndex = totalCount;
                }
                return list.subList(fromIndex, toIndex);
            }

        }
        return null;
    }

    /**
     * TODO 查询tb_req_detail分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-10-29      txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "PUB-01-04", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbReqDetail tbReqDetail) throws Exception {
        setPageParam();
        List<TbReqDetail> list = tbReqDetailService.selectByAttr(tbReqDetail);
        return pageData(list);
    }


    /**
     * 查询贷种组合数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-10-29      txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "findComb", method = RequestMethod.POST)
    @SystemLog(tradeName = "维护罚息参数", funCode = "PUB-01-04", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findComb() throws Exception {
        List<LoanCombDO> loanCombDOS = loanCombMapper.getCombByLevel(3);
        JSONObject listObj = new JSONObject();
        List<Map<String, String>> list = new ArrayList<>();
        for (LoanCombDO tb : loanCombDOS) {
            Map<String, String> map = new HashMap<>();
            map.put("value", tb.getCombCode());
            map.put("key", tb.getCombName());
            list.add(map);
        }
        listObj.put("combList", list);
        return listObj.toString();
    }


    /**
     * TODO 新增tb_req_detail.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年11月18日    	    txn    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-01-04", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(HttpServletRequest request, HttpSession session) throws Exception {
//        //最后操作员
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();

        int testNum = Integer.parseInt(request.getParameter("testNum"));
        PlainResult<String> result = new PlainResult<>();
        try {
            List<TbReqDetail> tbPlanDetailList = getListNew(request, organCode);
            if (testNum == 9999) {
                TbReqDetail searchTb = new TbReqDetail();
                searchTb.setReqdRefId(tbPlanDetailList.get(0).getReqdRefId());
                searchTb.setReqdOrgan(getSessionOrgan().getThiscode());
                List<TbReqDetail> tempList = tbReqDetailService.selectByAttr(searchTb);
                if (tempList != null && tempList.size() > 0) {
                    result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "当前批次的需求已录入，请转至菜单查看");
                    String reqId = request.getParameter("reqId");
                    WebMsg webMsg = new WebMsg();
                    webMsg.setMsgType(DicCache.getKeyByName_("录入需求", "MSG_TYPE"));
                    webMsg.setWebMsgStatus("1");
                    webMsg.setOperDescribe("录入需求：" + reqId + "_" + getSessionOrgan().getThiscode());
                    List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
                    if (webMsgs != null && webMsgs.size() != 0) {
                        for (int i = 0; i < webMsgs.size(); i++) {
                            //当前批次id的 当前 机构号的 所有消息都删掉
                            webMsgService.deleteByPK(webMsgs.get(i).getMsgNo());
                        }
                    }
                    return JSON.toJSONString(result);
                } else {
                    result.error(HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED, "当前批次的需求已录入，请转至菜单查看");

                    return JSON.toJSONString(result);
                }
            }

            tbReqDetailService.insertBatch(tbPlanDetailList);
        } catch (Exception e) {
            e.printStackTrace();
            result = result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add credit plan.");
        }
        String reqId = request.getParameter("reqId");
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("录入需求", "MSG_TYPE"));
        webMsg.setRepUserCode(getSessionOperInfo().getOperCode());
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("录入需求：" + reqId + "_" + getSessionOrgan().getThiscode());
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            for (int i = 0; i < webMsgs.size(); i++) {
                //当前批次id的 当前 机构号的 所有消息都删掉
                webMsgService.deleteByPK(webMsgs.get(i).getMsgNo());
            }
        }
        result = result.success("add", "add credit plan success.");
        return JSON.toJSONString(result);
    }


    /**
     * 解析前台传来的参数
     *
     * @param request
     * @param organCode
     * @return
     */
    private List<TbReqDetail> getList(HttpServletRequest request, String organCode) {
        String tbReqDetailStr = request.getParameter("tbReqDetail");
        String reqUnit = request.getParameter("reqUnit");
        String reqId = request.getParameter("reqId");
        String state = request.getParameter("state");

        List<TbReqDetail> tbPlanDetailList = new ArrayList<>();
        String[] planDetailArr = tbReqDetailStr.split("&");
        for (int i = 0; i < planDetailArr.length / 4; i++) {
            String[] planDetailArr1 = planDetailArr[4 * i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_"); //code,numType
            String[] planDetailArr3 = planDetailArr[4 * i + 1].split("=");
            String[] planDetailArr5 = planDetailArr[4 * i + 2].split("=");
            String[] planDetailArr7 = planDetailArr[4 * i + 3].split("=");
            //eg: 11005293_x001=123 (贷种code_金额分类=金额&贷种code_金额分类=金额)
            String expNum = planDetailArr1[1];
            String reqNum = planDetailArr3[1];
            String rate = planDetailArr5[1];
            String balance = planDetailArr7[1];
            String combCode = planDetailArr2[0]; //code
            TbReqDetail tbReqDetail = new TbReqDetail();
            tbReqDetail.setReqdCombCode(combCode);
            tbReqDetail.setReqdUnit(Integer.valueOf(reqUnit));
            BigDecimal unit = new BigDecimal(1);
            if (Integer.valueOf(reqUnit) == 2) {
                unit = new BigDecimal(10000);
            }
            tbReqDetail.setReqdRefId(Integer.valueOf(reqId));
            tbReqDetail.setReqdExpnum(new BigDecimal(expNum).multiply(unit));
            tbReqDetail.setReqdReqnum(new BigDecimal(reqNum).multiply(unit));
            tbReqDetail.setReqdRate(new BigDecimal(rate));
            tbReqDetail.setReqdBalance(new BigDecimal(balance).multiply(unit));
            tbReqDetail.setReqdOrgan(organCode);
            tbReqDetail.setReqdState(Integer.valueOf(state));
            tbReqDetail.setReqdCreateTime(BocoUtils.getTime());
            tbReqDetail.setReqdUpdateTime(BocoUtils.getTime());
            tbPlanDetailList.add(tbReqDetail);
        }
        return tbPlanDetailList;
    }


    /**
     * 解析前台传来的参数
     *
     * @param request
     * @param organCode
     * @return
     */
    private List<TbReqDetail> getListNew(HttpServletRequest request, String organCode) {
        String tbReqDetailStr = request.getParameter("tbReqDetail");
        String reqUnit = request.getParameter("reqUnit");
        String reqId = request.getParameter("reqId");
        String state = request.getParameter("state");

        List<TbReqDetail> tbPlanDetailList = new ArrayList<>();
        String[] planDetailArr = tbReqDetailStr.split("&");

        Map<String, TbReqDetail> map = new HashMap<>();
        for (int i = 0; i < planDetailArr.length; i++) {
            String[] planDetailArr1 = planDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_"); //code,numType
            //eg: 11005293_x001=123 (贷种code_金额分类=金额&贷种code_金额分类=金额)
            String num = planDetailArr1[1]; //一个数值
            String combCode = planDetailArr2[0]; //code
            String numTypeCode = planDetailArr2[1]; //expNum;reqNum;rate;balance
            TbReqDetail tbReqDetail = map.get(combCode);
            if (tbReqDetail == null) {
                tbReqDetail = new TbReqDetail();
                tbReqDetail.setReqdCombCode(combCode);
            }
            tbReqDetail.setReqdUnit(Integer.valueOf(reqUnit));
            BigDecimal unit = new BigDecimal(1);
            if (Integer.valueOf(reqUnit) == 2) {
                unit = new BigDecimal(10000);
            }
            tbReqDetail.setReqdRefId(Integer.valueOf(reqId));
            if ("expNum".equals(numTypeCode)) {
                tbReqDetail.setReqdExpnum(new BigDecimal(num).multiply(unit));
            } else if ("reqNum".equals(numTypeCode)) {
                tbReqDetail.setReqdReqnum(new BigDecimal(num).multiply(unit));
            } else if ("rate".equals(numTypeCode)) {
                tbReqDetail.setReqdRate(new BigDecimal(num));
            } else if ("balance".equals(numTypeCode)) {
                tbReqDetail.setReqdBalance(new BigDecimal(num).multiply(unit));
            }
            tbReqDetail.setReqdOrgan(organCode);
            tbReqDetail.setReqdState(Integer.valueOf(state));
            tbReqDetail.setReqdCreateTime(BocoUtils.getTime());
            tbReqDetail.setReqdUpdateTime(BocoUtils.getTime());
            map.put(combCode, tbReqDetail);
        }
        Collection<TbReqDetail> tbReqDetails = map.values();
        for (TbReqDetail tb : tbReqDetails) {
            tbPlanDetailList.add(tb);
        }

        return tbPlanDetailList;
    }

    /**
     * TODO 修改tb_req_detail.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年11月18日    	    txn    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-01-04", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {
        //最后操作员
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        PlainResult<String> result = new PlainResult<>();
        try {
            String reqId = request.getParameter("reqId");
            List<TbReqDetail> tbPlanDetailList = getListNew(request, organCode);
            TbReqDetail tbReqDetail = new TbReqDetail();
            tbReqDetail.setReqdOrgan(organCode);
            tbReqDetail.setReqdRefId(Integer.valueOf(reqId));
            tbReqDetailService.deleteByAttr(tbReqDetail);
            tbReqDetailService.insertBatch(tbPlanDetailList);
        } catch (Exception e) {
            result = result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update req detail error");
        }
        result = result.success("update", "update req detail success.");
        return JSON.toJSONString(result);
    }


    /**
     * 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectReqdId")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-04", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectReqdId(TbReqDetail tbReqDetail, HttpServletRequest request) throws Exception {
        String reqdId = request.getParameter("reqdId").replace("'", "");
        if (reqdId != null && "".equals(reqdId)) {
            tbReqDetail.setReqdId(Integer.valueOf(reqdId));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, Integer>> tbReqDetailList = tbReqDetailService.selectReqdId(tbReqDetail);
        for (Map<String, Integer> deptInfo : tbReqDetailList) {
            String data = String.valueOf(deptInfo.get("reqd_id"));
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
    @RequestMapping(value = "selectReqdRefId")
    @SystemLog(tradeName = "时间计划维护", funCode = "PUB-01-04", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectReqdRefId(TbReqDetail tbReqDetail, HttpServletRequest request) throws Exception {
        String reqdRefId = request.getParameter("reqdRefId").replace("'", "");
        if (reqdRefId != null && "".equals(reqdRefId)) {
            tbReqDetail.setReqdRefId(Integer.valueOf(reqdRefId));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> webOperInfoList = tbReqDetailService.selectReqdRefId(tbReqDetail);
        for (Map<String, String> deptInfo : webOperInfoList) {
            String data = deptInfo.get("reqd_ref_id");
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