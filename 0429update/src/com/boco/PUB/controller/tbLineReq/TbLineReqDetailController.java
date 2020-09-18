package com.boco.PUB.controller.tbLineReq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boco.PM.service.IFdOrganService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PUB.service.ITbLineReqDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.TONY.common.PlainResult;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
 * ������ϸ��controller
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbTradeManger/tbLineReqDetail/")
public class TbLineReqDetailController extends BaseController {
    @Autowired
    private ITbLineReqDetailService tbLineReqDetailService;
    @Autowired
    IWebLoanCombService webLoanService;
    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    private IWebMsgService webMsgService;
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    WebOperRoleMapper webOperRoleMapper;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    LineProductMapper lineProductMapper;


    private static final int LINE_USABLE = 1;

    @RequestMapping("listUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-09", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbLineReqManage/tbLineReqDetail/list";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-09-01-04", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(String lineReqId, HttpSession session) throws Exception {
        TbLineReqDetail tbLineReqDetail = tbLineReqDetailService.selectByPK(Integer.valueOf(lineReqId));
        setAttribute("TbLineReqDetail", tbLineReqDetail);
        setAttribute("lineReqId", lineReqId);
        TbReqList tbReqList = tbReqListService.selectByPK(tbLineReqDetail.getLineRefId());
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

        setAttribute("TbReqList", tbReqList);
        setAttr(tbLineReqDetail);
        return basePath + "/PUB/tbLineReqManage/tbLineReqDetail/info";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-09-01-01", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI(String lineReqId, HttpSession session) throws Exception {
        TbLineReqDetail tbLineReqDetail = tbLineReqDetailService.selectByPK(Integer.valueOf(lineReqId));
        setAttribute("lineReqId", lineReqId);
        setAttribute("TbLineReqDetail", tbLineReqDetail);
        TbReqList tbReqList = tbReqListService.selectByPK(tbLineReqDetail.getLineRefId());
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

        setAttribute("TbReqList", tbReqList);
        setAttr(tbLineReqDetail);
        return basePath + "/PUB/tbLineReqManage/tbLineReqDetail/add";


    }


    /**
     * ͨ�÷���
     *
     * @param tbLineReqDetail
     * @throws Exception
     */
    private void setAttr(TbLineReqDetail tbLineReqDetail) throws Exception {
        String combCodeStr = tbLineReqDetail.getLineCombCode();

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //����������������������ʡ����Ǳ�����
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "������");
        map1.put("code", "expNum");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "������");
        map2.put("code", "reqNum");
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "Ԥ���·�������(%)");
        map3.put("code", "rate");
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "Ԥ����ĩʱ�����");
        map4.put("code", "balance");
        TbReqList tbReqList = tbReqListService.selectByPK(tbLineReqDetail.getLineRefId());
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
        List<Map<String, String>> combList = new ArrayList<>();
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
        setAttribute("combList", combList);
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-09-01-02", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(String lineReqId, HttpSession session) throws Exception {
        TbLineReqDetail tbLineReqDetail = tbLineReqDetailService.selectByPK(Integer.valueOf(lineReqId));
        setAttribute("TbLineReqDetail", tbLineReqDetail);
        setAttribute("lineReqId", lineReqId);
        TbReqList tbReqList = tbReqListService.selectByPK(tbLineReqDetail.getLineRefId());
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

        setAttribute("TbReqList", tbReqList);
        setAttr(tbLineReqDetail);
        return basePath + "/PUB/tbLineReqManage/tbLineReqDetail/edit";
    }

    /**
     * ����ҳ����
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getReqDetailList")
    @SystemLog(tradeName = "�Ŵ�����", funCode = "PUB2", funName = "�Ŵ��ƻ���������", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String creditPlanDetailData(String lineReqId, HttpSession session) throws Exception {
        TbLineReqDetail tbLineReqDetail = tbLineReqDetailService.selectByPK(Integer.valueOf(lineReqId));
        BigDecimal unit = new BigDecimal(1);
        int reqUnit = tbLineReqDetail.getLineUnit();
        if (reqUnit == 2) {
            unit = new BigDecimal(10000);
        }
        String lineCombtr = tbLineReqDetail.getLineCombCode();
        String[] lineCombArr = lineCombtr.split(",");
        String expNumStr = tbLineReqDetail.getLineExpnum();
        String[] expNumArr = expNumStr.split(",");
        String reqNumStr = tbLineReqDetail.getLineReqnum();
        String[] reqNumArr = reqNumStr.split(",");
        String rateStr = tbLineReqDetail.getLineRate();
        String[] rateArr = rateStr.split(",");
        String balanceStr = tbLineReqDetail.getLineBalance();
        String[] balanceArr = balanceStr.split(",");
        List<TbLineReqDetailDTO> tbLineReqDetailDTOS = new ArrayList<>();
        for (int i = 0; i < lineCombArr.length; i++) {
            TbLineReqDetailDTO tb = new TbLineReqDetailDTO();
            tb.setLineCombCode(lineCombArr[i]);
            tb.setLineExpnum(BigDecimalUtil.divide(BigDecimalUtil.getSafeCount(expNumArr[i]), unit));
            tb.setLineReqnum(BigDecimalUtil.divide(BigDecimalUtil.getSafeCount(reqNumArr[i]), unit));
            tb.setLineBalance(BigDecimalUtil.divide(BigDecimalUtil.getSafeCount(balanceArr[i]), unit));
            tb.setLineRate(BigDecimalUtil.getSafeCount(rateArr[i]));
            tbLineReqDetailDTOS.add(tb);
        }
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("tbLineReqDetailDTOS", tbLineReqDetailDTOS);
        return JSON.toJSONString(resultMap);
    }


    /**
     * ��ѯtb_line_req_list��ҳ����
     * ��������Ա�������߼� ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "showFindPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "PUB-09-01", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showFindPage(HttpSession session, String lineReqMonth) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        TbLineReqDetail tbLineReqDetail = new TbLineReqDetail();
        tbLineReqDetail.setLineState(7);
        tbLineReqDetail.setLineOrgan(organCode);
        tbLineReqDetail.setLineReqMonth(lineReqMonth);
        List<TbLineReqDetail> tbLineReqDetails = new ArrayList<>();
        try {
            List<TbLineReqDetail> list = tbLineReqDetailService.selectByAttr(tbLineReqDetail);
            WebOperLineDO webOperLineDO = new WebOperLineDO().setStatus(LINE_USABLE);
            webOperLineDO.setOperCode(fdOper.getOpercode());
            List<WebOperLineDO> webOperLineDOList = webOperLineMapper.getAllWebOperLineByOperCode(webOperLineDO);
            if (list != null && list.size() > 0) {
                for (WebOperLineDO operLineDO : webOperLineDOList) {
                    ProductLineInfoDO lineInfoDO = lineProductMapper.getProductLineInfoByLineId(operLineDO.getLineId());
                    if (Objects.nonNull(lineInfoDO)) {
                        String lineId = lineInfoDO.getLineId();
                        for (TbLineReqDetail tb : list) {
                            if (lineId.equals(tb.getLineCode())) {
                                tbLineReqDetails.add(tb);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        int pageNum = Integer.valueOf(request.getParameter("pageNo"));
        //ÿҳ��ʾ����
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        List<TbLineReqDetail> newList = getListPage(pageNum, pageSize, tbLineReqDetails);
        //����ҳ��ķ�ҳ����
        Map<String, Object> results = new Hashtable<String, Object>();
        if (newList == null) {
            results.put("pager.pageNo", 1);
            results.put("pager.totalRows", 0);
            results.put("rows", new ArrayList<>());
            return JsonUtils.toJson(results);
        }
        //��PageInfo�Խ�����а�װ
        results.put("pager.pageNo", pageNum);
        results.put("pager.totalRows", tbLineReqDetails.size());
        results.put("rows", newList);
        return JsonUtils.toJson(results);
    }

    private static List<TbLineReqDetail> getListPage(int page, int pageSize, List<TbLineReqDetail> list) {
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
     * ��ѯ�����������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findComb", method = RequestMethod.POST)
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "PUB-09-01", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
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
     * TODO ����tb_req_detail.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "��������", funCode = "PUB-09-01", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(HttpServletRequest request, HttpSession session) throws Exception {
//        //������Ա
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        PlainResult<String> result = new PlainResult<>();
        int testNum = Integer.parseInt(request.getParameter("testNum"));
        try {
            TbLineReqDetail tbLineReqDetails = getListNew(request);

            if (testNum == 9999) {

                TbLineReqDetail searchTb = new TbLineReqDetail();
                searchTb.setLineRefId(tbLineReqDetails.getLineRefId());
                searchTb.setLineState(2);
                searchTb.setLineReqId(tbLineReqDetails.getLineReqId());
                searchTb.setLineOrgan(getSessionOrgan().getThiscode());
                List<TbLineReqDetail> tempList = tbLineReqDetailService.selectByAttr(searchTb);
                if (tempList != null && tempList.size() > 0) {
                    result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "��ǰ���ε�����������¼�룬��ת���˵��鿴");
                    WebMsg webMsg = new WebMsg();
                    webMsg.setMsgType(DicCache.getKeyByName_("¼������", "MSG_TYPE"));
                    webMsg.setRepUserCode(getSessionOperInfo().getOperCode());
                    webMsg.setWebMsgStatus("1");
                    webMsg.setOperDescribe("¼������" + tbLineReqDetails.getLineReqId());
                    List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
                    if (webMsgs != null && webMsgs.size() != 0) {
                        for (int i = 0; i < webMsgs.size(); i++) {
                            //��ǰ����id�� ��ǰ �����ŵ� ������Ϣ��ɾ��
                            webMsgService.deleteByPK(webMsgs.get(i).getMsgNo());
                        }
                    }
                    return JSON.toJSONString(result);

                } else {
                    result.error(HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED, "��ǰ���ε�������¼�룬��ת���˵��鿴");

                    return JSON.toJSONString(result);
                }
            }


            tbLineReqDetailService.updateByPK(tbLineReqDetails);
            WebMsg webMsg = new WebMsg();
            webMsg.setMsgType(DicCache.getKeyByName_("¼������", "MSG_TYPE"));
            webMsg.setRepUserCode(getSessionOperInfo().getOperCode());
            webMsg.setWebMsgStatus("1");
            webMsg.setOperDescribe("¼������" + tbLineReqDetails.getLineReqId());
            List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
            if (webMsgs != null && webMsgs.size() != 0) {
                for (int i = 0; i < webMsgs.size(); i++) {
                    //��ǰ����id�� ��ǰ �����ŵ� ������Ϣ��ɾ��
                    webMsgService.deleteByPK(webMsgs.get(i).getMsgNo());
                }
            }

        } catch (Exception e) {
            result = result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add credit plan.");
        }

        result = result.success("add", "add credit plan success.");
        return JSON.toJSONString(result);
    }


    /**
     * ����ǰ̨�����Ĳ���
     *
     * @param request
     * @return
     */
    private TbLineReqDetail getList(HttpServletRequest request) {
        String tbLineReqDetailStr = request.getParameter("tbLineReqDetail");
        String lineUnit = request.getParameter("lineUnit");
        String lineReqId = request.getParameter("lineReqId");
        String lineRefId = request.getParameter("lineRefId");
        String state = request.getParameter("state");

        List<TbLineReqDetailDTO> tbLineReqDetails = new ArrayList<>();
        String[] lineReqDetailArr = tbLineReqDetailStr.split("&");
        for (int i = 0; i < lineReqDetailArr.length / 4; i++) {
            String[] planDetailArr1 = lineReqDetailArr[4 * i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_");
            String[] planDetailArr3 = lineReqDetailArr[4 * i + 1].split("=");
            String[] planDetailArr5 = lineReqDetailArr[4 * i + 2].split("=");
            String[] planDetailArr7 = lineReqDetailArr[4 * i + 3].split("=");
            //eg: 11005293_x001=123 (����code_������=���&����code_������=���)
            String expNum = planDetailArr1[1];
            String reqNum = planDetailArr3[1];
            String rate = planDetailArr5[1];
            String balance = planDetailArr7[1];
            String combCode = planDetailArr2[0];
            TbLineReqDetailDTO tbLineReqDetailDTO = new TbLineReqDetailDTO();
            tbLineReqDetailDTO.setLineCombCode(combCode);
            tbLineReqDetailDTO.setLineUnit(Integer.valueOf(lineUnit));
            BigDecimal unit = new BigDecimal(1);
            if (Integer.valueOf(lineUnit) == 2) {
                unit = new BigDecimal(10000);
            }
            tbLineReqDetailDTO.setLineReqId(Integer.valueOf(lineReqId));
            tbLineReqDetailDTO.setLineRefId(Integer.valueOf(lineRefId));
            tbLineReqDetailDTO.setLineExpnum(new BigDecimal(expNum).multiply(unit));
            tbLineReqDetailDTO.setLineReqnum(new BigDecimal(reqNum).multiply(unit));
            tbLineReqDetailDTO.setLineRate(new BigDecimal(rate));
            tbLineReqDetailDTO.setLineBalance(new BigDecimal(balance).multiply(unit));
            tbLineReqDetails.add(tbLineReqDetailDTO);
        }


        String expNumStr = "";
        String reqNumStr = "";
        String rateStr = "";
        String balanceStr = "";
        for (TbLineReqDetailDTO tbDTO : tbLineReqDetails) {
            expNumStr += (tbDTO.getLineExpnum() + ",");
            reqNumStr += (tbDTO.getLineReqnum() + ",");
            rateStr += (tbDTO.getLineRate() + ",");
            balanceStr += (tbDTO.getLineBalance() + ",");
        }
        if (expNumStr.endsWith(",")) {
            expNumStr = expNumStr.substring(0, expNumStr.length() - 1);
        }
        if (reqNumStr.endsWith(",")) {
            reqNumStr = reqNumStr.substring(0, reqNumStr.length() - 1);
        }
        if (rateStr.endsWith(",")) {
            rateStr = rateStr.substring(0, rateStr.length() - 1);
        }
        if (balanceStr.endsWith(",")) {
            balanceStr = balanceStr.substring(0, balanceStr.length() - 1);
        }
        TbLineReqDetail tbLineReqDetail = new TbLineReqDetail();
        tbLineReqDetail.setLineExpnum(expNumStr);
        tbLineReqDetail.setLineReqnum(reqNumStr);
        tbLineReqDetail.setLineRate(rateStr);
        tbLineReqDetail.setLineBalance(balanceStr);
        tbLineReqDetail.setLineState(Integer.valueOf(state));
        tbLineReqDetail.setLineUpdateTime(BocoUtils.getTime());
        tbLineReqDetail.setLineCreateTime(BocoUtils.getTime());
        tbLineReqDetail.setLineReqId(Integer.parseInt(lineReqId));
        tbLineReqDetail.setLineRefId(Integer.parseInt(lineRefId));

        return tbLineReqDetail;
    }

    /**
     * ����ǰ̨�����Ĳ���
     *
     * @param request
     * @return
     */
    private TbLineReqDetail getListNew(HttpServletRequest request) {
        String tbLineReqDetailStr = request.getParameter("tbLineReqDetail");
        String reqUnit = request.getParameter("lineUnit");
        String lineReqId = request.getParameter("lineReqId");
        String lineRefId = request.getParameter("lineRefId");
        String state = request.getParameter("state");

        List<TbLineReqDetailDTO> tbLineReqDetails = new ArrayList<>();
        String[] lineReqDetailArr = tbLineReqDetailStr.split("&");
        Map<String, TbLineReqDetailDTO> map = new HashMap<>();
        for (int i = 0; i < lineReqDetailArr.length; i++) {
            String[] planDetailArr1 = lineReqDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_"); //code,numType
            //eg: 11005293_x001=123 (����code_������=���&����code_������=���)
            String num = planDetailArr1[1]; //һ����ֵ
            String combCode = planDetailArr2[0]; //code
            String numTypeCode = planDetailArr2[1]; //expNum;reqNum;rate;balance
            TbLineReqDetailDTO tbReqDetail = map.get(combCode);
            if (tbReqDetail == null) {
                tbReqDetail = new TbLineReqDetailDTO();
                tbReqDetail.setLineCombCode(combCode);
            }
            tbReqDetail.setLineUnit(Integer.valueOf(reqUnit));
            BigDecimal unit = new BigDecimal(1);
            if (Integer.valueOf(reqUnit) == 2) {
                unit = new BigDecimal(10000);
            }
            tbReqDetail.setLineRefId(Integer.valueOf(lineRefId));
            if ("expNum".equals(numTypeCode)) {
                tbReqDetail.setLineExpnum(new BigDecimal(num).multiply(unit));
            } else if ("reqNum".equals(numTypeCode)) {
                tbReqDetail.setLineReqnum(new BigDecimal(num).multiply(unit));
            } else if ("rate".equals(numTypeCode)) {
                tbReqDetail.setLineRate(new BigDecimal(num));
            } else if ("balance".equals(numTypeCode)) {
                tbReqDetail.setLineBalance(new BigDecimal(num).multiply(unit));
            }
            tbReqDetail.setLineReqId(Integer.parseInt(lineReqId));
            map.put(combCode, tbReqDetail);
        }

        Collection<TbLineReqDetailDTO> tbReqDetails = map.values();
        for (TbLineReqDetailDTO tb : tbReqDetails) {
            tbLineReqDetails.add(tb);
        }

        String expNumStr = "";
        String reqNumStr = "";
        String rateStr = "";
        String balanceStr = "";
        for (TbLineReqDetailDTO tbDTO : tbLineReqDetails) {
            expNumStr += (tbDTO.getLineExpnum() + ",");
            reqNumStr += (tbDTO.getLineReqnum() + ",");
            rateStr += (tbDTO.getLineRate() + ",");
            balanceStr += (tbDTO.getLineBalance() + ",");
        }
        if (expNumStr.endsWith(",")) {
            expNumStr = expNumStr.substring(0, expNumStr.length() - 1);
        }
        if (reqNumStr.endsWith(",")) {
            reqNumStr = reqNumStr.substring(0, reqNumStr.length() - 1);
        }
        if (rateStr.endsWith(",")) {
            rateStr = rateStr.substring(0, rateStr.length() - 1);
        }
        if (balanceStr.endsWith(",")) {
            balanceStr = balanceStr.substring(0, balanceStr.length() - 1);
        }
        TbLineReqDetail tbLineReqDetail = new TbLineReqDetail();
        tbLineReqDetail.setLineExpnum(expNumStr);
        tbLineReqDetail.setLineReqnum(reqNumStr);
        tbLineReqDetail.setLineRate(rateStr);
        tbLineReqDetail.setLineBalance(balanceStr);
        tbLineReqDetail.setLineState(Integer.valueOf(state));
        tbLineReqDetail.setLineUpdateTime(BocoUtils.getTime());
        tbLineReqDetail.setLineCreateTime(BocoUtils.getTime());
        tbLineReqDetail.setLineReqId(Integer.parseInt(lineReqId));
        tbLineReqDetail.setLineRefId(Integer.parseInt(lineRefId));

        return tbLineReqDetail;
    }


    /**
     * TODO �޸�tb_req_detail.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "PUB-09-01", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {
        //������Ա
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        PlainResult<String> result = new PlainResult<>();
        try {
            TbLineReqDetail tbLineReqDetail = getListNew(request);
            tbLineReqDetailService.updateByPK(tbLineReqDetail);
        } catch (Exception e) {
            result = result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update req detail error");
        }
        result = result.success("update", "update req detail success.");
        return JSON.toJSONString(result);
    }


    /**
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "showLineReqMonth")
    @SystemLog(tradeName = "��ѯ���������·�", funCode = "PUB-09", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showReqMonth(TbLineReqDetail tbLineReqDetail, HttpServletRequest request, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbLineReqDetail.setLineOrgan(fdOper.getOrgancode());
        String lineReqMonth = request.getParameter("lineReqMonth");
        if (lineReqMonth != null && "".equals(lineReqMonth)) {
            tbLineReqDetail.setLineReqMonth(lineReqMonth);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> tbLineReqListList = tbLineReqDetailService.showLineReqMonth(tbLineReqDetail);
        if (tbLineReqListList == null || tbLineReqListList.size() == 0) {
            resultMap.put("list", list);
            String json = JsonUtils.toJson(resultMap);
            return json;
        }
        for (Map<String, String> deptInfo : tbLineReqListList) {
            String data = deptInfo.get("line_req_month");
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