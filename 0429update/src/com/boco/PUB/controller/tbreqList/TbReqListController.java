package com.boco.PUB.controller.tbreqList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.boco.PM.service.IFdOrganService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PUB.service.ITbLineReqDetailService;
import com.boco.PUB.service.ITbReqDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.StringUtil;
import com.boco.SYS.util.WebContextUtil;
import com.boco.TONY.biz.line.POJO.DO.ProductLineDetailDO;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
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
 * ������Ҫ���·� ���α�
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbTradeManger/tbReqList/")
public class TbReqListController extends BaseController {
    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    private ITbReqDetailService tbReqDetailService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private LineProductDetailMapper lineProductDetailsMapper;
    @Autowired
    private IWebMsgService webMsgService;
    @Autowired
    private IFdOrganService organService;
    @Autowired
    private ITbLineReqDetailService tbLineReqDetailService;
    @Autowired
    private LineProductMapper lineProductMapper;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private LoanCombDetailMapper loanCombDetailMapper;
    @Autowired
    private WebOperLineMapper webOperLineMapper;
    @Autowired
    private WebOperRoleMapper webOperRoleMapper;
    @Autowired
    private WebOperInfoMapper webOperInfoMapper;

    /*һ�ֻ���¼��Ա��ɫ����*/
    @Value("${organ.oper.code.one}")
    private String organOperCodeOne;


    /*���ֻ���¼��Ա��ɫ����*/
    @Value("${organ.oper.code.two}")
    private String organOperCodeTwo;

    /*��������¼��Ա��ɫ����*/
    @Value("${line.oper.code.zero}")
    private String lineOperCodeZero;


    /*һ������¼��Ա��ɫ����*/
    @Value("${line.oper.code.one}")
    private String lineOperCodeOne;


    @RequestMapping("listUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-01-02", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbReqStatistics/tbReqList/tbReqListList";
    }

    @RequestMapping("showListUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-01-03", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String showListUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbReqStatistics/tbReqShowList/tbReqShowList";
    }


    @RequestMapping("infoUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-01-02-04", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbReqList tbReqList) throws Exception {
        TbReqList tbReqList1 = tbReqListService.selectByPK(tbReqList.getReqId());
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
        selectComb("info", String.valueOf(tbReqList.getReqId()));
        selectLineList("info", String.valueOf(tbReqList.getReqId()));
        selectOrganList("info", String.valueOf(tbReqList.getReqId()));
        selectNumType("info", String.valueOf(tbReqList.getReqId()));
        return basePath + "/PUB/tbReqStatistics/tbReqList/tbReqListInfo";
    }

    @RequestMapping("showChildUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-01-02-06", funName = "�����¼�����ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String onShowChildUI(String reqId) throws Exception {
        setAttribute("reqId", reqId);
        TbReqList tbReqList = tbReqListService.selectByPK(Integer.valueOf(reqId));
        WebOperInfo webOperInfo = getSessionOperInfo();
        String organCode = webOperInfo.getOrganCode();
        FdOrgan thisOrgan = organService.selectByPK(organCode);
        int reqType = tbReqList.getReqType();
        String combListStr = "";
        String organListStr = "";
        if (reqType == 0) {
            List<Map<String, String>> organList = new ArrayList<>();
            organListStr = tbReqList.getReqOrganList();
            String[] organArr = organListStr.split(",");
            FdOrgan searchOrgan = new FdOrgan();
            searchOrgan.setUporgan(thisOrgan.getThiscode());
            List<FdOrgan> fdOrganList = organService.selectByAttr(searchOrgan);
            String organStr = "12004390&33000072&37000013&44008995&50016751&11000013&21014952&31000017&35008816";
            if (organStr.contains(thisOrgan.getThiscode())) {
                FdOrgan searchOrgan_3 = new FdOrgan();
                searchOrgan_3.setProvincecode(thisOrgan.getThiscode());
                searchOrgan_3.setOrganlevel("3");
                List<FdOrgan> organList_3 = organService.selectByAttr(searchOrgan_3);
                for (FdOrgan fdOrgan1 : organList_3) {
                    fdOrganList.add(fdOrgan1);
                }
            }

            //���Ϸ��������ѯ
            String organStr2 = "41022445";
            if (organStr2.contains(thisOrgan.getThiscode())) {
                FdOrgan searchOrgan_4 = new FdOrgan();
                searchOrgan_4.setUporgan(thisOrgan.getThiscode());
                searchOrgan_4.setOrganlevel("3");
                List<FdOrgan> organList_3 = fdOrganService.selectByAttr(searchOrgan_4);
                for (FdOrgan fdOrgan1 : organList_3) {
                    fdOrganList.add(fdOrgan1);
                }
            }
            if ("1".equals(thisOrgan.getOrganlevel())) {
                FdOrgan searchOrgan_1 = new FdOrgan();
                searchOrgan_1.setProvincecode(thisOrgan.getThiscode());
                searchOrgan_1.setOrganlevel("1");
                List<FdOrgan> organList_1 = organService.selectByAttr(searchOrgan_1);
                FdOrgan fdOrgan = organList_1.get(0);
                fdOrgan.setOrganname(fdOrgan.getOrganname() + "����");
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
                organMap.put("organName", "��������");
                organList.add(organMap);
            }

            organMap = new HashMap<>();
            organMap.put("organCode", "TangLoveQianForever");
            organMap.put("organName", "�ܼ�");
            organList.add(organMap);
            setAttribute("organList", organList);
            List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
            List<Map<String, String>> combList = new ArrayList<>();
            List<Map<String, String>> combAmountNameList = new ArrayList<>();
            combListStr = tbReqList.getReqCombList();
            String[] combArr = combListStr.split(",");
            Map<String, String> mapUpdateTime = new HashMap<>(2);
            mapUpdateTime.put("name", "��  ��  ʱ  �� ");
            mapUpdateTime.put("code", "updateTime");
            combAmountNameList.add(mapUpdateTime);
            for (LoanCombDO loanCombDO : loanCombDOS) {
                for (int i = 0; i < combArr.length; i++) {
                    if (combArr[i].equals(loanCombDO.getCombCode())) {
                        Map<String, String> combMap = new HashMap<>(2);
                        //������ ������ | ���� ���
                        Map<String, String> map1 = new HashMap<>(2);
                        map1.put("name", loanCombDO.getCombName() + "������");
                        map1.put("code", combArr[i] + "_expNum");
                        Map<String, String> map2 = new HashMap<>(2);
                        map2.put("name", loanCombDO.getCombName() + "������");
                        map2.put("code", combArr[i] + "_reqNum");
                        Map<String, String> map3 = new HashMap<>(2);
                        map3.put("name", loanCombDO.getCombName() + "Ԥ���·�������(%)");
                        map3.put("code", combArr[i] + "_rate");
                        Map<String, String> map4 = new HashMap<>(2);
                        map4.put("name", loanCombDO.getCombName() + "Ԥ����ĩʱ�����");
                        map4.put("code", combArr[i] + "_balance");
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
                        break;
                    }
                }
            }
            Map<String, String> map1 = new HashMap<>(2);
            map1.put("name", "�������ϼ�");
            map1.put("code", "total_expNum");
            Map<String, String> map2 = new HashMap<>(2);
            map2.put("name", "�������ϼ�");
            map2.put("code", "total_reqNum");
            Map<String, String> map3 = new HashMap<>(2);
            map3.put("name", "Ԥ���·�������(%)�ϼ�");
            map3.put("code", "total_rate");
            Map<String, String> map4 = new HashMap<>(2);
            map4.put("name", "Ԥ����ĩʱ�����ϼ�");
            map4.put("code", "total_balance");
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
            setAttribute("TbReqListDTO", tbReqList);
            return basePath + "/PUB/tbReqStatistics/tbReqList/tbReqListShowChild";
        } else {
            TbLineReqDetail searchTbLineReqDetail = new TbLineReqDetail();
            searchTbLineReqDetail.setLineRefId(Integer.parseInt(reqId));
            List<TbLineReqDetail> list = tbLineReqDetailService.selectByAttr(searchTbLineReqDetail);
            if (list != null && list.size() > 0) {
                TbLineReqDetail tbLineReqDetail = list.get(0);

                String nameStr = "";
                for (TbLineReqDetail t : list) {
                    nameStr = nameStr + (t.getLineName() + "#");
                }
                nameStr = nameStr.substring(0, nameStr.length() - 1);
                tbLineReqDetail.setLineName(nameStr);
                setAttribute("tbLineReqDetail", tbLineReqDetail);

            }
            setAttr(list);
            setAttribute("lineRefId", reqId);
            return basePath + "/PUB/tbReqStatistics/tbReqList/tbLineReqListShowChild";
        }

    }


    /**
     * ����ҳ����
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/lineReqDetailData")
    @SystemLog(tradeName = "�Ŵ�����", funCode = "PUB2", funName = "�Ŵ��ƻ���������", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String lineReqDetailData(String lineRefId, HttpSession session) throws Exception {
        TbLineReqDetail searchTb = new TbLineReqDetail();
        searchTb.setLineRefId(Integer.parseInt(lineRefId));
        searchTb.setLineState(TbLineReqDetail.STATE_APPROVED);
        List<TbLineReqDetail> list = tbLineReqDetailService.selectByAttr(searchTb);

        BigDecimal unit = new BigDecimal(1);
        int reqUnit = -1;
        if (list != null && list.size() > 0) {
            reqUnit = list.get(0).getLineUnit();
        }
        if (reqUnit == 2) {
            unit = new BigDecimal(10000);
        }
        List<TbLineReqDetailDTO> tbLineReqDetailDTOS = new ArrayList<>();
        BigDecimal totalExpNum = BigDecimal.ZERO;
        BigDecimal totalReqNum = BigDecimal.ZERO;
        BigDecimal totalBalanceNum = BigDecimal.ZERO;

        for (TbLineReqDetail tbLineReqDetail : list) {
            if (tbLineReqDetail != null) {
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
                for (int i = 0; i < lineCombArr.length; i++) {
                    TbLineReqDetailDTO tb = new TbLineReqDetailDTO();
                    tb.setLineCombCode(lineCombArr[i]);
                    tb.setLineExpnum(BigDecimalUtil.divide(BigDecimalUtil.getSafeCount(expNumArr[i]), unit));
                    tb.setLineReqnum(BigDecimalUtil.divide(BigDecimalUtil.getSafeCount(reqNumArr[i]), unit));
                    tb.setLineBalance(BigDecimalUtil.divide(BigDecimalUtil.getSafeCount(balanceArr[i]), unit));
                    tb.setLineRate(BigDecimalUtil.getSafeCount(rateArr[i]));
                    tb.setLineUpdateTime(tbLineReqDetail.getLineUpdateTime());
                    totalExpNum = totalExpNum.add(tb.getLineExpnum());
                    totalReqNum = totalReqNum.add(tb.getLineReqnum());
                    totalBalanceNum = totalBalanceNum.add(tb.getLineBalance());
                    tbLineReqDetailDTOS.add(tb);
                }
            }
        }

        TbLineReqDetailDTO totalTb = new TbLineReqDetailDTO();
        totalTb.setLineExpnum(totalExpNum);
        totalTb.setLineReqnum(totalReqNum);
        totalTb.setLineBalance(totalBalanceNum);
        totalTb.setLineUpdateTime("-");
        totalTb.setLineCombCode("totalNum");
        tbLineReqDetailDTOS.add(totalTb);
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("tbLineReqDetailDTOS", tbLineReqDetailDTOS);
        return JSON.toJSONString(resultMap);
    }


    /**
     * ͨ�÷���
     *
     * @param list
     * @throws Exception
     */
    private void setAttr(List<TbLineReqDetail> list) throws Exception {
        List<Map<String, String>> combList = new ArrayList<>();
        List<Map<String, String>> combListResult = new ArrayList<>();
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        Map<String, String> mapUpdateTime = new HashMap<>(2);
        mapUpdateTime.put("name", "��  ��  ʱ  �� ");
        mapUpdateTime.put("code", "updateTime");
        combAmountNameList.add(mapUpdateTime);
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
        TbReqList tbReqList = tbReqListService.selectByPK(list.get(0).getLineRefId());
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
        for (TbLineReqDetail tbLineReqDetail : list) {
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
        //����
        for (LoanCombDO loanCombDO : loanCombDOS) {
            for (Map<String, String> map : combList) {
                if (map.get("combCode").toString().equals(loanCombDO.getCombCode())) {
                    combListResult.add(map);
                    break;
                }
            }
        }
        Map<String, String> combMap = new HashMap<>(2);
        combMap.put("combCode", "totalNum");
        combMap.put("combName", "�ϼ�");
        combListResult.add(combMap);
        setAttribute("combList", combListResult);
    }


    /**
     * ����ҳ����
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getReqDetailList")
    @SystemLog(tradeName = "�Ŵ�����", funCode = "PUB2", funName = "�Ŵ��ƻ���������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanDetailData(String reqId, HttpSession session) throws Exception {
        TbReqDetail searchTbReqDetail = new TbReqDetail();
        searchTbReqDetail.setReqdRefId(Integer.valueOf(reqId));
        searchTbReqDetail.setReqdState(TbReqDetail.STATE_APPROVED);
        List<TbReqDetail> tbReqDetailList = tbReqDetailService.selectByAttr(searchTbReqDetail);
        tbReqDetailList = getDetailList(tbReqDetailList);
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("tbReqDetailList", tbReqDetailList);
        return JSON.toJSONString(resultMap);
    }

    //��������б���Ϣ ����excel
    @RequestMapping(value = "/exportExcel")
    @SystemLog(tradeName = "������������excel", funCode = "PUB2", funName = "������������excel", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void exportExcel(String reqId, HttpServletResponse response, HttpServletRequest request) throws Exception {
        TbReqList tbReqList = tbReqListService.selectByPK(Integer.valueOf(reqId));
        TbReqDetail searchTbReqDetail = new TbReqDetail();
        searchTbReqDetail.setReqdRefId(Integer.valueOf(reqId));
        searchTbReqDetail.setReqdState(TbReqDetail.STATE_APPROVED);
        List<TbReqDetail> tbReqDetailList = tbReqDetailService.selectByAttr(searchTbReqDetail);
        //��������List
        tbReqDetailList = getDetailList(tbReqDetailList);
        //�ļ���
        String fileName = tbReqList.getReqName() + ".xls";
        Map<String, List<Map<String, String>>> maps = getOrganDetailName(reqId);
        List<Map<String, String>> combAmountNameList = maps.get("combAmountNameList");
        List<Map<String, String>> organList = maps.get("organList");
        DownPOIUtils.downPoi(request, response, fileName, combAmountNameList, tbReqDetailList, organList);
    }


    //���������б���Ϣ ����excel
    @RequestMapping(value = "/exportLineExcel")
    @SystemLog(tradeName = "������������excel", funCode = "PUB2", funName = "����������������excel", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public void exportLineExcel(String lineRefId, HttpServletResponse response, HttpServletRequest request) throws Exception {
        TbReqList tbReqList = tbReqListService.selectByPK(Integer.valueOf(lineRefId));
        TbLineReqDetail searchTbReqDetail = new TbLineReqDetail();
        searchTbReqDetail.setLineRefId(Integer.valueOf(lineRefId));
        searchTbReqDetail.setLineState(TbReqDetail.STATE_APPROVED);
        List<TbLineReqDetail> tbReqDetailList = tbLineReqDetailService.selectByAttr(searchTbReqDetail);
        //��������List
        List<TbLineReqDetailDTO> tbReqLineDetailList = getDetailLineList(tbReqDetailList);
        //�ļ���
        String fileName = tbReqList.getReqName() + ".xls";
        TbLineReqDetail searchTbLineReqDetail = new TbLineReqDetail();
        searchTbLineReqDetail.setLineRefId(tbReqList.getReqId());
        List<TbLineReqDetail> list = tbLineReqDetailService.selectByAttr(searchTbLineReqDetail);

        Map<String, List<Map<String, String>>> maps = getLineDetailName(list, tbReqList);
        List<Map<String, String>> combAmountNameList = maps.get("combAmountNameList");
        List<Map<String, String>> combList = maps.get("combList");
        DownLinePOIUtils.downPoi(request, response, fileName, combAmountNameList, tbReqLineDetailList, combList);
    }


    private Map<String, List<Map<String, String>>> getLineDetailName(List<TbLineReqDetail> list, TbReqList tbReqList) {
        List<Map<String, String>> combList = new ArrayList<>();
        List<Map<String, String>> combListResult = new ArrayList<>();
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        Map<String, List<Map<String, String>>> maps = new HashMap<>();
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        //����������������������ʡ����Ǳ�����
        Map<String, String> mapUpdateTime = new HashMap<>(2);
        mapUpdateTime.put("name", "��  ��  ʱ  �� ");
        mapUpdateTime.put("code", "updateTime");
        combAmountNameList.add(mapUpdateTime);
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
        maps.put("combAmountNameList", combAmountNameList);

        for (TbLineReqDetail tbLineReqDetail : list) {
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

        //����
        for (LoanCombDO loanCombDO : loanCombDOS) {
            for (Map<String, String> map : combList) {
                if (map.get("combCode").toString().equals(loanCombDO.getCombCode())) {
                    combListResult.add(map);
                    break;
                }
            }
        }
        Map<String, String> combMap = new HashMap<>(2);
        combMap.put("combCode", "totalNum");
        combMap.put("combName", "�ϼ�");
        combListResult.add(combMap);
        maps.put("combList", combListResult);
        return maps;
    }

    /**
     * ����reqid ��ȡ ���� �������б�
     *
     * @param list
     * @return
     */
    private List<TbLineReqDetailDTO> getDetailLineList(List<TbLineReqDetail> list) {

        BigDecimal unit = new BigDecimal(1);
        int reqUnit = -1;
        if (list != null && list.size() > 0) {
            reqUnit = list.get(0).getLineUnit();
        }
        if (reqUnit == 2) {
            unit = new BigDecimal(10000);
        }
        BigDecimal totalExpNum = BigDecimal.ZERO;
        BigDecimal totalReqNum = BigDecimal.ZERO;
        BigDecimal totalBalanceNum = BigDecimal.ZERO;

        List<TbLineReqDetailDTO> tbLineReqDetailDTOS = new ArrayList<>();
        for (TbLineReqDetail tbLineReqDetail : list) {
            if (tbLineReqDetail != null) {
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
                for (int i = 0; i < lineCombArr.length; i++) {
                    TbLineReqDetailDTO tb = new TbLineReqDetailDTO();
                    tb.setLineCombCode(lineCombArr[i]);
                    tb.setLineExpnum(new BigDecimal(expNumArr[i]).divide(unit));
                    tb.setLineReqnum(new BigDecimal(reqNumArr[i]).divide(unit));
                    tb.setLineRate(new BigDecimal(rateArr[i]));
                    tb.setLineBalance(new BigDecimal(balanceArr[i]).divide(unit));

                    tb.setLineUpdateTime(tbLineReqDetail.getLineUpdateTime());
                    totalExpNum = totalExpNum.add(tb.getLineExpnum());
                    totalReqNum = totalReqNum.add(tb.getLineReqnum());
                    totalBalanceNum = totalBalanceNum.add(tb.getLineBalance());
                    tbLineReqDetailDTOS.add(tb);
                }
            }

        }
        TbLineReqDetailDTO totalTb = new TbLineReqDetailDTO();
        totalTb.setLineExpnum(totalExpNum);
        totalTb.setLineReqnum(totalReqNum);
        totalTb.setLineBalance(totalBalanceNum);
        totalTb.setLineUpdateTime("-");
        totalTb.setLineCombCode("totalNum");
        tbLineReqDetailDTOS.add(totalTb);
        return tbLineReqDetailDTOS;
    }


    /**
     * ��ȡ������excel��ͷname
     *
     * @param reqId
     * @return
     * @throws Exception
     */
    private Map<String, List<Map<String, String>>> getOrganDetailName(String reqId) throws Exception {
        TbReqList tbReqList = tbReqListService.selectByPK(Integer.valueOf(reqId));
        WebOperInfo webOperInfo = getSessionOperInfo();
        String organCode = webOperInfo.getOrganCode();
        FdOrgan thisOrgan = organService.selectByPK(organCode);
        String combListStr = "";
        String organListStr = "";
        List<Map<String, String>> organList = new ArrayList<>();
        organListStr = tbReqList.getReqOrganList();
        String[] organArr = organListStr.split(",");
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setOrganlevel(String.valueOf(Integer.parseInt(thisOrgan.getOrganlevel()) + 1));
        searchOrgan.setUporgan(thisOrgan.getThiscode());
        List<FdOrgan> fdOrganList = organService.selectByAttr(searchOrgan);
        String organStr = "12004390&33000072&37000013&44008995&50016751&11000013&21014952&31000017&35008816";
        if (organStr.contains(thisOrgan.getThiscode())) {
            FdOrgan searchOrgan_3 = new FdOrgan();
            searchOrgan_3.setProvincecode(thisOrgan.getThiscode());
            searchOrgan_3.setOrganlevel("3");
            List<FdOrgan> organList_3 = organService.selectByAttr(searchOrgan_3);
            for (FdOrgan fdOrgan1 : organList_3) {
                fdOrganList.add(fdOrgan1);
            }
        }

        //���Ϸ��������ѯ
        String organStr2 = "41022445";
        if (organStr2.contains(thisOrgan.getThiscode())) {
            FdOrgan searchOrgan_4 = new FdOrgan();
            searchOrgan_4.setUporgan(thisOrgan.getThiscode());
            searchOrgan_4.setOrganlevel("3");
            List<FdOrgan> organList_3 = fdOrganService.selectByAttr(searchOrgan_4);
            for (FdOrgan fdOrgan1 : organList_3) {
                fdOrganList.add(fdOrgan1);
            }
        }
        if ("1".equals(thisOrgan.getOrganlevel())) {
            FdOrgan searchOrgan_1 = new FdOrgan();
            searchOrgan_1.setProvincecode(thisOrgan.getThiscode());
            searchOrgan_1.setOrganlevel("1");
            List<FdOrgan> organList_1 = organService.selectByAttr(searchOrgan_1);
            FdOrgan fdOrgan = organList_1.get(0);
            fdOrgan.setOrganname(fdOrgan.getOrganname() + "����");
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
            organMap.put("organName", "��������");
            organList.add(organMap);
        }

        organMap = new HashMap<>();
        organMap.put("organCode", "TangLoveQianForever");
        organMap.put("organName", "�ܼ�");
        organList.add(organMap);

        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        List<Map<String, String>> combList = new ArrayList<>();
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        combListStr = tbReqList.getReqCombList();
        String[] combArr = combListStr.split(",");
        Map<String, String> mapUpdateTime = new HashMap<>(2);
        mapUpdateTime.put("name", "��  ��  ʱ  �� ");
        mapUpdateTime.put("code", "updateTime");
        combAmountNameList.add(mapUpdateTime);
        for (LoanCombDO loanCombDO : loanCombDOS) {
            for (int i = 0; i < combArr.length; i++) {
                if (combArr[i].equals(loanCombDO.getCombCode())) {
                    //������ ������ | ���� ���
                    Map<String, String> map1 = new HashMap<>(2);
                    map1.put("name", loanCombDO.getCombName() + "������");
                    map1.put("code", combArr[i] + "_expNum");
                    Map<String, String> map2 = new HashMap<>(2);
                    map2.put("name", loanCombDO.getCombName() + "������");
                    map2.put("code", combArr[i] + "_reqNum");
                    Map<String, String> map3 = new HashMap<>(2);
                    map3.put("name", loanCombDO.getCombName() + "Ԥ���·�������(%)");
                    map3.put("code", combArr[i] + "_rate");
                    Map<String, String> map4 = new HashMap<>(2);
                    map4.put("name", loanCombDO.getCombName() + "Ԥ����ĩʱ�����");
                    map4.put("code", combArr[i] + "_balance");
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
                    break;
                }
            }
        }
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "�������ϼ�");
        map1.put("code", "total_expNum");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "�������ϼ�");
        map2.put("code", "total_reqNum");
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "Ԥ���·�������(%)�ϼ�");
        map3.put("code", "total_rate");
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "Ԥ����ĩʱ�����ϼ�");
        map4.put("code", "total_balance");
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
        setAttribute("organList", organList);
        Map<String, List<Map<String, String>>> maps = new HashMap<>();
        maps.put("combAmountNameList", combAmountNameList);
        maps.put("organList", organList);
        return maps;
    }

    /**
     * ����tbReqDetailList
     * ���� �������� �鿴�¼�����������
     *
     * @param tbReqDetailList
     * @return
     */
    private List<TbReqDetail> getDetailList(List<TbReqDetail> tbReqDetailList) {
        //��λת��
        int unit = 1;
        if (tbReqDetailList != null && tbReqDetailList.size() > 0) {
            unit = tbReqDetailList.get(0).getReqdUnit();
        }
        BigDecimal tbUnit = new BigDecimal(1);
        if (unit == 2) {
            tbUnit = new BigDecimal(10000);
        }
        //�Դ������Ϊά�Ȼ��ܣ��������������Ļ���
        Map<String, TbReqDetail> totalMap = new HashMap<>(64);
        //�Ի�����Ϊά�Ȼ��ܣ��������������Ļ���
        Map<String, TbReqDetail> organMap = new HashMap<>(64);
        for (TbReqDetail tbReqDetail : tbReqDetailList) {
            String combCode = tbReqDetail.getReqdCombCode();
            TbReqDetail totalTbReqDetail = totalMap.get(combCode);
            if (null == totalTbReqDetail) {
                totalTbReqDetail = new TbReqDetail();
                totalTbReqDetail.setReqdOrgan("TangLoveQianForever");
                totalTbReqDetail.setReqdCombCode(combCode);
                totalTbReqDetail.setReqdReqnum(new BigDecimal(0));
                totalTbReqDetail.setReqdExpnum(new BigDecimal(0));
                totalTbReqDetail.setReqdBalance(new BigDecimal(0));
            }
            totalTbReqDetail.setReqdCreateTime(tbReqDetail.getReqdCreateTime());
            totalTbReqDetail.setReqdUpdateTime("-");
            totalTbReqDetail.setReqdReqnum(BigDecimalUtil.add(totalTbReqDetail.getReqdReqnum(), tbReqDetail.getReqdReqnum()));
            totalTbReqDetail.setReqdExpnum(BigDecimalUtil.add(totalTbReqDetail.getReqdExpnum(), tbReqDetail.getReqdExpnum()));
            totalTbReqDetail.setReqdBalance(BigDecimalUtil.add(totalTbReqDetail.getReqdBalance(), tbReqDetail.getReqdBalance()));
            totalMap.put(combCode, totalTbReqDetail);
        }


        Set<String> combSet = totalMap.keySet();
        for (String combStr : combSet) {
            tbReqDetailList.add(totalMap.get(combStr));
        }
        //�Ҳ����ӺͼӺ�
        for (TbReqDetail tbReqDetail : tbReqDetailList) {
            String oragnCode = tbReqDetail.getReqdOrgan();
            TbReqDetail organTempTb = organMap.get(oragnCode);
            if (organTempTb == null) {
                organTempTb = new TbReqDetail();
                organTempTb.setReqdOrgan(oragnCode);
                organTempTb.setTotalReqdExpnum(new BigDecimal(0));
                organTempTb.setTotalReqdExpnum(new BigDecimal(0));
                organTempTb.setTotalReqdBalance(new BigDecimal(0));
            }
            organTempTb.setTotalReqdReqnum(BigDecimalUtil.add(organTempTb.getTotalReqdReqnum(), tbReqDetail.getReqdReqnum()));
            organTempTb.setTotalReqdExpnum(BigDecimalUtil.add(organTempTb.getTotalReqdExpnum(), tbReqDetail.getReqdExpnum()));
            organTempTb.setTotalReqdBalance(BigDecimalUtil.add(organTempTb.getTotalReqdBalance(), tbReqDetail.getReqdBalance()));
            organMap.put(oragnCode, organTempTb);
        }
        for (TbReqDetail tempTb : tbReqDetailList) {
            String organCode = tempTb.getReqdOrgan();
            TbReqDetail tbReqDetail = organMap.get(organCode);
            if (tbReqDetail != null) {
                tempTb.setTotalReqdReqnum(tbReqDetail.getTotalReqdReqnum());
                tempTb.setTotalReqdBalance(tbReqDetail.getTotalReqdBalance());
                tempTb.setTotalReqdExpnum(tbReqDetail.getTotalReqdExpnum());
            }
        }

        for (TbReqDetail tb : tbReqDetailList) {
            tb.setReqdExpnum(BigDecimalUtil.divide(tb.getReqdExpnum(), tbUnit));
            tb.setReqdReqnum(BigDecimalUtil.divide(tb.getReqdReqnum(), tbUnit));
            tb.setReqdBalance(BigDecimalUtil.divide(tb.getReqdBalance(), tbUnit));
            tb.setTotalReqdExpnum(BigDecimalUtil.divide(tb.getTotalReqdExpnum(), tbUnit));
            tb.setTotalReqdBalance(BigDecimalUtil.divide(tb.getTotalReqdBalance(), tbUnit));
            tb.setTotalReqdReqnum(BigDecimalUtil.divide(tb.getTotalReqdReqnum(), tbUnit));
        }

        Map<String, BigDecimal> stringBigDecimalMap = new HashMap<>();
        for (TbReqDetail tbReqDetail : tbReqDetailList) {
            String organCode = tbReqDetail.getReqdOrgan();
            BigDecimal totalAmount = stringBigDecimalMap.get(organCode);
            if (totalAmount == null) {
                totalAmount = BigDecimal.ZERO;
            }
            totalAmount = totalAmount.add(tbReqDetail.getReqdReqnum());
            stringBigDecimalMap.put(organCode, totalAmount);
        }

        for (TbReqDetail tbReqDetail : tbReqDetailList) {
            String organCode = tbReqDetail.getReqdOrgan();
            tbReqDetail.setReqdCreateTime(stringBigDecimalMap.get(organCode).toString());
        }
        return tbReqDetailList;
    }


    /**
     * ����ҳ����
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/111")
    @SystemLog(tradeName = "�Ŵ�����", funCode = "PUB2", funName = "�Ŵ��ƻ���������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    Map<String, BigDecimal> creditPlanDetailData1111111111111111(String month) throws Exception {
        TbReqList searchTbReqList = new TbReqList();
        searchTbReqList.setReqMonth(month);//ͨ��Ψһ�·� ��tbreqList�� reqId
        List<TbReqList> lists = tbReqListService.selectByAttr(searchTbReqList);
        int reqId = 0;
        if (lists != null && lists.size() == 1) {
            reqId = lists.get(0).getReqId();
        }
        TbReqDetail searchTbReqDetail = new TbReqDetail();
        searchTbReqDetail.setReqdRefId(reqId);
        searchTbReqDetail.setReqdState(TbReqDetail.STATE_APPROVED);//reqid �õ� ������������������
        List<TbReqDetail> tbReqDetailList = tbReqDetailService.selectByAttr(searchTbReqDetail);
        int unit = 1;
        if (tbReqDetailList != null && tbReqDetailList.size() > 0) {
            unit = tbReqDetailList.get(0).getReqdUnit();
        }
        Map<String, BigDecimal> totalMap = new HashMap<>(36);
        for (TbReqDetail tbReqDetail : tbReqDetailList) {

            String organCode = tbReqDetail.getReqdOrgan(); //��ȡ�� ÿ����������Ļ��� ��key

            BigDecimal totalReqNum = totalMap.get(organCode);//map key:organCode;value ÿ���������ۼ���ֵ
            if (null == totalReqNum) {
                totalReqNum = BigDecimal.ZERO;
            }
            totalReqNum = totalReqNum.add(tbReqDetail.getReqdReqnum());

            totalMap.put(organCode, totalReqNum);
        }

        return totalMap;
    }


    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-01-02-01", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        selectOrganList("", "");
        selectLineList("", "0");
        return basePath + "/PUB/tbReqStatistics/tbReqList/tbReqListAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-01-02-02", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbReqList tbReqList) throws Exception {
        tbReqList = tbReqListService.selectByPK(tbReqList.getReqId());

        String reqCombList = tbReqList.getReqCombList();
        String tempStr = "";
        String[] split = reqCombList.split(",");
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        //����
        for (LoanCombDO loanCombDO : loanCombDOS) {
            for (int i = 0; i < split.length; i++) {
                if (split[i].equals(loanCombDO.getCombCode())) {
                    tempStr = tempStr + split[i] + ",";
                    break;
                }
            }
        }
        tbReqList.setReqCombList(tempStr);
        setAttribute("TbReqListDTO", tbReqList);
        selectComb("update", String.valueOf(tbReqList.getReqId()));
        selectLineList("update", String.valueOf(tbReqList.getReqId()));
        selectOrganList("update", String.valueOf(tbReqList.getReqId()));
        selectNumType("update", String.valueOf(tbReqList.getReqId()));
        return basePath + "/PUB/tbReqStatistics/tbReqList/tbReqListEdit";
    }


    /**
     * ��ѯ����ȫ����Ϣ
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findOrgan", method = RequestMethod.POST)
    @SystemLog(tradeName = "��ѯ���л���", funCode = "PUB-01-04", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findOrgan(HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        List<Map<String, String>> fdOrganList = organService.findNextOrganCodeList(organCode);
        com.alibaba.fastjson.JSONObject listObj = new com.alibaba.fastjson.JSONObject();
        Map<String, String> map = new HashMap<>(32);
        for (Map<String, String> map1 : fdOrganList) {
            map.put(map1.get("value"), map1.get("key"));
        }
        listObj.put("organMap", map);
        return listObj.toString();
    }


    /**
     * ��ѯtb_req_list��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "showChildPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "PUB-01-02", funName = "�����¼������б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showChildPage(TbReqList tbReqList, HttpSession session) throws Exception {
        setPageParam();

        TbReqDetail tbReqDetail = new TbReqDetail();
        tbReqDetail.setReqdRefId(tbReqList.getReqId());
        tbReqDetail.setReqdState(TbReqDetail.STATE_APPROVED);
        List<TbReqDetail> list = tbReqDetailService.selectByAttr(tbReqDetail);
        TbReqDetail totalDetail = new TbReqDetail();
        countTotal(totalDetail, list);
        list.add(totalDetail);
        return pageData(list);
    }


    /**
     * �б��������,������ܼ���
     *
     * @param totalDetail
     * @param list
     */
    private static void countTotal(TbReqDetail totalDetail, List<TbReqDetail> list) {
        totalDetail.setReqdOrgan("total");
        totalDetail.setReqdUnit(1);
    }

    /**
     * ��ѯtb_req_list��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "PUB", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbReqList tbReqList, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        tbReqList.setReqOrgan(organCode);
        setPageParam();
        List<TbReqList> list = tbReqListService.selectByAttr(tbReqList);
        return pageData(list);
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
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "PUB2", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findComb(WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        List<LoanCombDO> loanCombDOSLevel1 = loanCombMapper.getCombByLevel(1);
        List<LoanCombDO> loanCombDOSLevel2 = loanCombMapper.getCombByLevel(2);
        List<LoanCombDO> loanCombDOSLevel3 = loanCombMapper.getCombByLevel(3);
        List<LoanCombDO> loanCombDOS = loanCombDOSLevel1;
        for (int i = 0; i < loanCombDOSLevel2.size(); i++) {
            loanCombDOS.add(loanCombDOSLevel2.get(i));
        }
        for (int j = 0; j < loanCombDOSLevel3.size(); j++) {
            loanCombDOS.add(loanCombDOSLevel3.get(j));
        }
        String organlevel = WebContextUtil.getSessionOrgan().getOrganlevel();//��ǰ�����Ļ�������
        JSONObject listObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (organlevel.equals("0") || organlevel.equals("1")) {
            for (LoanCombDO tb : loanCombDOS) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value", tb.getCombCode());
                jsonObject.put("key", tb.getCombName());
                jsonArray.add(jsonObject);
            }
        }
        if (organlevel.equals("2")) {
            for (LoanCombDO tb : loanCombDOSLevel2) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value", tb.getCombCode());
                jsonObject.put("key", tb.getCombName());
                jsonArray.add(jsonObject);
            }
        }
        listObj.put("list", jsonArray);
        return listObj.toString();
    }


    /**
     * ��ѯ�����������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "showComb", method = RequestMethod.POST)
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "PUB2", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showComb() throws Exception {
        List<LoanCombDO> loanCombDOS1 = loanCombMapper.getCombByLevel(1);
        List<LoanCombDO> loanCombDOS2 = loanCombMapper.getCombByLevel(2);
        List<LoanCombDO> loanCombDOS3 = loanCombMapper.getCombByLevel(3);
        List<LoanCombDO> loanCombDOS = loanCombDOS1;
        loanCombDOS.addAll(loanCombDOS2);
        loanCombDOS.addAll(loanCombDOS3);
        JSONObject listObj = new JSONObject();
        Map<String, String> map = new HashMap<>(32);
        for (LoanCombDO tb : loanCombDOS) {
            map.put(tb.getCombCode(), tb.getCombName());
        }
        listObj.put("combMap", map);
        return listObj.toString();
    }


    /**
     * ��ѯ�����������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "showCombs", method = RequestMethod.POST)
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "SYS-02", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showCombs() throws Exception {
        List<LoanCombDO> loanCombDOS1 = loanCombMapper.getCombByLevel(1);
        List<LoanCombDO> loanCombDOS2 = loanCombMapper.getCombByLevel(2);
        List<LoanCombDO> loanCombDOS3 = loanCombMapper.getCombByLevel(3);
        List<LoanCombDO> loanCombDOS = loanCombDOS1;
        loanCombDOS.addAll(loanCombDOS2);
        loanCombDOS.addAll(loanCombDOS3);
        JSONObject listObj = new JSONObject();
        Map<String, String> map = new HashMap<>(32);
        for (LoanCombDO tb : loanCombDOS) {
            map.put(tb.getCombCode(), tb.getCombName());
        }
        listObj.put("combMap", map);
        return listObj.toString();
    }


    /**
     * ��ѯ�����������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "showOrgan", method = RequestMethod.POST)
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "PUB2", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showOrgan() throws Exception {
        FdOrgan fdOrgan1 = new FdOrgan();
        FdOrgan fdOrgan2 = new FdOrgan();
        FdOrgan fdOrgan3 = new FdOrgan();

        fdOrgan1.setOrganlevel("0");
        fdOrgan2.setOrganlevel("1");
        fdOrgan3.setOrganlevel("2");
        List<FdOrgan> organList1 = organService.selectByAttr(fdOrgan1);
        List<FdOrgan> organList2 = organService.selectByAttr(fdOrgan2);
        List<FdOrgan> organList3 = organService.selectByAttr(fdOrgan3);

        JSONObject listObj = new JSONObject();
        Map<String, String> map = new HashMap<>(128);
        for (FdOrgan fd : organList1) {
            map.put(fd.getThiscode(), "����");
        }
        for (FdOrgan fd : organList2) {
            map.put(fd.getThiscode(), fd.getOrganname());
        }
        for (FdOrgan fd : organList3) {
            map.put(fd.getThiscode(), fd.getOrganname());
        }
        listObj.put("organMap", map);
        return listObj.toString();
    }


    /**
     * ��ȡ�����¼������������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "selectOrgan", method = RequestMethod.POST)
    @SystemLog(tradeName = "��ȡ����������Ϣ", funCode = "PUB2", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public String selectOrgan(String method, String reqId) throws Exception {
        WebOperInfo webOperInfo = getSessionOperInfo();
        //��������
        FdOrgan thisOrgan = organService.selectByPK(webOperInfo.getOrganCode());
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setOrganlevel(String.valueOf(Integer.parseInt(thisOrgan.getOrganlevel()) + 1));
        searchOrgan.setUporgan(thisOrgan.getThiscode());
        List<FdOrgan> organList = organService.selectByAttr(searchOrgan);
        TbReqList tbReqList = new TbReqList();
        if (reqId != null && !"0".equals(reqId) && !"".equals(reqId) && reqId.trim().length() > 0) {
            tbReqList = tbReqListService.selectByPK(Integer.parseInt(reqId));
        }
        String organStr = "12004390&33000072&37000013&44008995&50016751&11000013&21014952&31000017&35008816";
        if (organStr.contains(thisOrgan.getThiscode())) {
            FdOrgan searchOrgan_3 = new FdOrgan();
            searchOrgan_3.setProvincecode(thisOrgan.getThiscode());
            searchOrgan_3.setOrganlevel("3");
            List<FdOrgan> organList_3 = organService.selectByAttr(searchOrgan_3);
            for (FdOrgan fdOrgan1 : organList_3) {
                organList.add(fdOrgan1);
            }
        }

        //���Ϸ��������ѯ
        String organStr2 = "41022445";
        if (organStr2.contains(thisOrgan.getThiscode())) {
            FdOrgan searchOrgan_4 = new FdOrgan();
            searchOrgan_4.setUporgan(thisOrgan.getThiscode());
            searchOrgan_4.setOrganlevel("3");
            List<FdOrgan> organList_3 = fdOrganService.selectByAttr(searchOrgan_4);
            for (FdOrgan fdOrgan1 : organList_3) {
                organList.add(fdOrgan1);
            }
        }

        if ("1".equals(thisOrgan.getOrganlevel())) {
            FdOrgan searchOrgan_1 = new FdOrgan();
            searchOrgan_1.setProvincecode(thisOrgan.getThiscode());
            searchOrgan_1.setOrganlevel("1");
            List<FdOrgan> organList_1 = organService.selectByAttr(searchOrgan_1);
            FdOrgan fdOrgan = organList_1.get(0);
            fdOrgan.setOrganname(fdOrgan.getOrganname() + "����");
            organList.add(fdOrgan);
        }
        String organNameStr = "";
        Map<String, Object> results = new Hashtable<String, Object>();
        List<Map<String, Object>> treelist = new ArrayList<Map<String, Object>>();
        Map<String, Object> treeMap = new HashMap<String, Object>();

        String selectedValue = "";

        treeMap.put("id", "TangLoveQian");
        treeMap.put("name", "ȫѡ");
        selectedValue += "TangLoveQian";
        treelist.add(treeMap);

        for (FdOrgan fd : organList) {
            treeMap = new HashMap<>(2);
            treeMap.put("id", fd.getThiscode());
            treeMap.put("name", fd.getOrganname());
            selectedValue += ("," + fd.getThiscode());
            treelist.add(treeMap);
        }
        if ("update".equals(method) || "info".equals(method)) {
            //0����,1����
            int reqType = tbReqList.getReqType();
            if (reqType == 0) {
                String organListStr = tbReqList.getReqOrganList();

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
        results.put("treeNodes", treelist);
        if ("info".equals(method)) {
            organNameStr.replaceAll("#ȫѡ", "");
        }
        setAttribute("organNameStr", organNameStr);
        setAttribute("organList", JsonUtils.toJson(results));
        return JsonUtils.toJson(results);
    }


    /**
     * ��ȡ�����¼������������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "selectOrganList", method = RequestMethod.POST)
    @SystemLog(tradeName = "��ȡ����������Ϣ", funCode = "PUB2", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public String selectOrganList(String method, String reqId) throws Exception {
        //��������
        FdOrgan thisOrgan = organService.selectByPK(getSessionOperInfo().getOrganCode());
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setUporgan(thisOrgan.getThiscode());
        List<FdOrgan> organList = organService.selectByAttr(searchOrgan);
        TbReqList tbReqList = new TbReqList();
        if (reqId != null && !"0".equals(reqId) && !"".equals(reqId) && reqId.trim().length() > 0) {
            tbReqList = tbReqListService.selectByPK(Integer.parseInt(reqId));
        }


        if ("1".equals(thisOrgan.getOrganlevel())) {
            FdOrgan searchOrgan_1 = new FdOrgan();
            searchOrgan_1.setProvincecode(thisOrgan.getThiscode());
            searchOrgan_1.setOrganlevel("1");
            List<FdOrgan> organList_1 = organService.selectByAttr(searchOrgan_1);
            FdOrgan fdOrgan = organList_1.get(0);
            fdOrgan.setOrganname(fdOrgan.getOrganname() + "����");
            organList.add(fdOrgan);
        }
        String organNameStr = "";
        Map<String, Object> results = new Hashtable<String, Object>();
        List<Map<String, Object>> treelist = new ArrayList<Map<String, Object>>();
        Map<String, Object> treeMap = new HashMap<String, Object>();


        treeMap.put("id", "TangLoveQian");
        treeMap.put("name", "ȫѡ");
        treeMap.put("parentId", "0");
        treelist.add(treeMap);

        for (FdOrgan fd : organList) {
            if (!fd.getThiscode().equals("11000013")) {
                treeMap = new HashMap<>(2);
                treeMap.put("id", fd.getThiscode());
                treeMap.put("name", fd.getOrganname());
                treeMap.put("parentId", "TangLoveQian");
                treelist.add(treeMap);
            }

        }

        if ("0".equals(thisOrgan.getOrganlevel())) {
            treeMap = new HashMap<>(2);
            treeMap.put("id", "11000013");
            treeMap.put("name", "��������");
            treeMap.put("parentId", "TangLoveQian");
            treelist.add(treeMap);
        }

        if ("update".equals(method) || "info".equals(method)) {
            //0����,1����
            int reqType = tbReqList.getReqType();
            if (reqType == 0) {
                String organListStr = tbReqList.getReqOrganList();

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
        results.put("treeNodes", treelist);
        if ("info".equals(method)) {
            organNameStr.replaceAll("#ȫѡ", "");
        }
        setAttribute("organNameStr", organNameStr);
        setAttribute("organList", JsonUtils.toJson(results));
        return JsonUtils.toJson(results);
    }


    /**
     * ��ѯtb_punish_param�б�����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findTradeParam", method = RequestMethod.POST)
    @SystemLog(tradeName = "ά����Ϣ����", funCode = "PUB2", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findTradeParam() throws Exception {
        TbReqList tbReqList = new TbReqList();
        tbReqList.setReqOrgan(getSessionOrgan().getThiscode());
        tbReqList.setReqState(TbReqDetail.STATE_ISSUED);
        List<TbReqList> tbReqListsResult = tbReqListService.selectByAttr(tbReqList);
        Map<String, TbReqList> map = new HashMap<>();
        for (TbReqList tb : tbReqListsResult) {
            map.put(tb.getReqMonth(), tb);
        }
        List<TbReqList> tbReqLists = new ArrayList<>();
        Set<String> monthSte = map.keySet();
        for (String month : monthSte) {
            if (!"".equals(month) && month != null) {
                tbReqLists.add(map.get(month));
            }
        }
        com.alibaba.fastjson.JSONObject listObj = new com.alibaba.fastjson.JSONObject();
        JSONArray jsonArray = new JSONArray();
        com.alibaba.fastjson.JSONObject jsonObject1 = new com.alibaba.fastjson.JSONObject();
        jsonObject1.put("value", "��ѡ��");
        jsonObject1.put("key", "��ѡ��");
        jsonArray.add(jsonObject1);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        int dateNum = Integer.parseInt(sdf.format(date));
        Integer arr[] = new Integer[tbReqLists.size()];

        for (int i = 0; i < tbReqLists.size(); i++) {
            if (!"".equals(tbReqLists.get(i).getReqMonth()) && tbReqLists.get(i).getReqMonth() != null) {
                arr[i] = Integer.parseInt(tbReqLists.get(i).getReqMonth());
            }
        }
        Comparator comp = new MyCom();
        try {
            Arrays.sort(arr, comp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && arr[i] > 0) {
                int paramPeriod = arr[i];
                for (int j = 0; j < tbReqLists.size(); j++) {
                    com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
                    if (dateNum <= paramPeriod) {
                        jsonObject.put("value", String.valueOf(paramPeriod));
                        jsonObject.put("key", String.valueOf(paramPeriod));
                        jsonArray.add(jsonObject);
                        break;
                    }
                }
            }
        }
        listObj.put("list", jsonArray);
        return listObj.toString();
    }


    class MyCom implements Comparator<Integer> {
        public int compare(Integer o1, Integer o2) {
            if ((int) o1 < (int) o2) {
                return 1;
            } else if ((int) o1 > (int) o2) {
                return -1;
            } else {
                return 0;
            }

        }
    }


    /**
     * ��ȡ���д�������������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * param method add;update;info
     * param reqId �������κ�
     * </pre>
     */
    @RequestMapping(value = "selectComb", method = RequestMethod.POST)
    @SystemLog(tradeName = "��ȡ����������Ϣ", funCode = "PUB2", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectComb(String method, String reqId) throws Exception {
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        TbCombDetail tbCombDetail = new TbCombDetail();
        tbCombDetail.setStatus(1);
        List<TbCombDetail> TbCombDetailS = loanCombDetailMapper.selectByAttr(tbCombDetail);
        TbReqList tbReqList = new TbReqList();
        if (reqId != null && !"0".equals(reqId)) {
            tbReqList = tbReqListService.selectByPK(Integer.parseInt(reqId));
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
        treeMap.put("name", "ȫѡһ��");
        treeList.add(treeMap);

        treeMap = new HashMap<>(4);
        treeMap.put("id", "totalTwo");
        treeMap.put("parentId", "0");
        treeMap.put("name", "ȫѡ����");
        treeList.add(treeMap);

        treeMap = new HashMap<>(4);
        treeMap.put("id", "totalThree");
        treeMap.put("parentId", "0");
        treeMap.put("name", "ȫѡ����");
        treeList.add(treeMap);

        if ("update".equals(method) || "info".equals(method)) {
            //0����,1����
            int reqType = tbReqList.getReqType();
            if (reqType == 0) {
                String combListStr = tbReqList.getReqCombList();

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
            combNameStr.replaceAll("#ȫѡ", "");
        }
        setAttribute("combNameStr", combNameStr);
        return JsonUtils.toJson(results);
    }

    @RequestMapping(value = "selectCombForWarn", method = RequestMethod.POST)
    @SystemLog(tradeName = "��ȡ����������Ϣ", funCode = "PUB", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectCombForWarn(String method, String reqId) throws Exception {
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        TbCombDetail tbCombDetail = new TbCombDetail();
        tbCombDetail.setStatus(1);
        List<TbCombDetail> TbCombDetailS = loanCombDetailMapper.selectByAttr(tbCombDetail);
        TbReqList tbReqList = new TbReqList();
        if (reqId != null && !"0".equals(reqId)) {
            tbReqList = tbReqListService.selectByPK(Integer.parseInt(reqId));
        }
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

        if ("update".equals(method) || "info".equals(method)) {
            //0����,1����
            int reqType = tbReqList.getReqType();
            if (reqType == 0) {
                String combListStr = tbReqList.getReqCombList();

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
            combNameStr.replaceAll("#ȫѡ", "");
        }
        setAttribute("combNameStr", combNameStr);
        return JsonUtils.toJson(results);
    }


    /**
     * ��ȡ���д�������������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectLine", method = RequestMethod.POST)
    @SystemLog(tradeName = "��ȡ����������Ϣ", funCode = "PUB2", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectLine(String method, String reqId) throws Exception {
        ProductLineInfoDO productLineInfo = new ProductLineInfoDO();
        productLineInfo.setOrganCode(getSessionOperInfo().getOrganCode());
        List<ProductLineInfoDO> productLineInfoDOS = lineProductMapper.getAllAliveProductLineInfoByOrganCode(productLineInfo);
        TbReqList tbReqList = new TbReqList();
        if (reqId != null && !"0".equals(reqId)) {
            tbReqList = tbReqListService.selectByPK(Integer.parseInt(reqId));
        }
        List<Map<String, Object>> treeList = new ArrayList();
        String lineNameStr = "";
        String selectedValue = "";
        Map<String, Object> treeMap = new HashMap<String, Object>();
        treeMap.put("id", "TangLoveQian");
        treeMap.put("name", "ȫѡ");
        selectedValue += "TangLoveQian";
        treeList.add(treeMap);
        Map<String, Object> results = new Hashtable<>();

        for (ProductLineInfoDO productLineInfoDO : productLineInfoDOS) {
            treeMap = new HashMap<>(2);
            String lineId = productLineInfoDO.getLineId();
            String lineName = productLineInfoDO.getLineName();
            treeMap.put("id", lineId);
            treeMap.put("name", lineName);
            selectedValue += ("," + lineId);
            treeList.add(treeMap);
        }
        if ("update".equals(method) || "info".equals(method)) {
            //0����,1����
            int reqType = tbReqList.getReqType();
            if (reqType == 1) {
                String lineListStr = tbReqList.getReqProdLine();
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
        setAttribute("lineSelectedValue", selectedValue);
        results.put("treeNodes", treeList);
        setAttribute("lineList", JsonUtils.toJson(results));
        if ("info".equals(method)) {
            lineNameStr.replaceAll("#ȫѡ", "");
        }
        setAttribute("lineNameStr", lineNameStr);
        return JsonUtils.toJson(results);
    }


    /**
     * ��ȡ�����·��� ��д��ֵ��
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectNumType", method = RequestMethod.POST)
    @SystemLog(tradeName = "��ȡ����������Ϣ", funCode = "PUB2", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectNumType(String method, String reqId) throws Exception {
        ProductLineInfoDO productLineInfo = new ProductLineInfoDO();
        productLineInfo.setOrganCode(getSessionOperInfo().getOrganCode());
        TbReqList tbReqList = new TbReqList();
        if (reqId != null && !"0".equals(reqId)) {
            tbReqList = tbReqListService.selectByPK(Integer.parseInt(reqId));
        }
        List<Map<String, Object>> treeList = new ArrayList();
        String NumTypeStr = "";
        Map<String, Object> treeMap = new HashMap<String, Object>();
        Map<String, Object> results = new Hashtable<>();

        treeMap = new HashMap<>(2);
        treeMap.put("id", 1);
        treeMap.put("name", "������");
        treeList.add(treeMap);
        treeMap = new HashMap<>(2);
        treeMap.put("id", 2);
        treeMap.put("name", "������");
        treeList.add(treeMap);
        treeMap = new HashMap<>(2);
        treeMap.put("id", 4);
        treeMap.put("name", "����");
        treeList.add(treeMap);
        treeMap = new HashMap<>(2);
        treeMap.put("id", 8);
        treeMap.put("name", "���");
        treeList.add(treeMap);
        if ("update".equals(method) || "info".equals(method)) {
            //0����,1����
            String numType = tbReqList.getNumType();
            String[] numTypeArr = numType.split(",");
            Set<String> lineSet = new HashSet<>(Arrays.asList(numTypeArr));
            for (Map<String, Object> map : treeList) {
                String id = map.get("id").toString();
                if (lineSet.contains(id)) {
                    map.put("checked", "true");
                    NumTypeStr += ("#" + map.get("name"));
                    lineSet.remove(id);
                }
            }
        }
        results.put("treeNodes", treeList);
        setAttribute("lineList", JsonUtils.toJson(results));
        setAttribute("numTypeStr", NumTypeStr);
        return JsonUtils.toJson(results);
    }


    /**
     * ��ȡ���д�������������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectLineList", method = RequestMethod.POST)
    @SystemLog(tradeName = "��ȡ����������Ϣ", funCode = "PUB2", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectLineList(String method, String reqId) throws Exception {
        ProductLineInfoDO productLineInfo = new ProductLineInfoDO();
        productLineInfo.setOrganCode(getSessionOperInfo().getOrganCode());
        List<ProductLineInfoDO> productLineInfoDOS = lineProductMapper.getAllAliveProductLineInfoByOrganCode(productLineInfo);
        TbReqList tbReqList = new TbReqList();
        if (reqId != null && !"0".equals(reqId)) {
            tbReqList = tbReqListService.selectByPK(Integer.parseInt(reqId));
        }
        List<Map<String, Object>> treeList = new ArrayList();
        String lineNameStr = "";
        String selectedValue = "";
        Map<String, Object> treeMap = new HashMap<String, Object>();
        treeMap.put("id", "TangLoveQian");
        treeMap.put("parentId", "0");
        treeMap.put("name", "ȫѡ");
        selectedValue += "TangLoveQian";
        treeList.add(treeMap);
        Map<String, Object> results = new Hashtable<>();

        for (ProductLineInfoDO productLineInfoDO : productLineInfoDOS) {
            treeMap = new HashMap<>(2);
            String lineId = productLineInfoDO.getLineId();
            String lineName = productLineInfoDO.getLineName();
            treeMap.put("id", lineId);
            treeMap.put("parentId", "TangLoveQian");
            treeMap.put("name", lineName);
            selectedValue += ("," + lineId);
            treeList.add(treeMap);
        }
        if ("update".equals(method) || "info".equals(method)) {
            //0����,1����
            int reqType = tbReqList.getReqType();
            if (reqType == 1) {
                String lineListStr = tbReqList.getReqProdLine();
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
        setAttribute("lineSelectedValue", selectedValue);
        results.put("treeNodes", treeList);
        setAttribute("lineList", JsonUtils.toJson(results));
        if ("info".equals(method)) {
            lineNameStr.replaceAll("#ȫѡ", "");
        }
        setAttribute("lineNameStr", lineNameStr);
        return JsonUtils.toJson(results);
    }


    /**
     * ��ѯtb_req_list��ҳ����--�鿴�ϼ�������������Ϣ
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "showFindPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "PUB-01-03", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showFindPage(HttpSession session) throws Exception {

        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        FdOrgan fdOrgan = fdOrganService.selectByPK(organCode);
        String upOrganCode = fdOrgan.getUporgan();
        TbReqDetail tbReqDetail = new TbReqDetail();
        tbReqDetail.setReqdOrgan(organCode);
        List<TbReqDetail> tbReqDetailList = tbReqDetailService.selectByAttr(tbReqDetail);

        setPageParam();
        List<TbReqList> list = tbReqListService.selectByReqTo(TbReqList.REQ_TO_CONSUMER, upOrganCode, tbReqDetailList);

        return pageData(list);
    }


    /**
     * TODO ����tb_req_list.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "��������", funCode = "PUB-01-02-01", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(HttpServletRequest request, HttpSession session) throws Exception {
        //������Ա
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();
        TbReqList tbReqList = new TbReqList();
        String reqMonth = request.getParameter("reqMonth");
        String reqType = request.getParameter("reqType");
        String reqDateStart = request.getParameter("reqDateStart");
        String reqDateEnd = request.getParameter("reqDateEnd");
        String reqTimeStart = request.getParameter("reqTimeStart");
        String reqTimeEnd = request.getParameter("reqTimeEnd");
        String expTimeStart = request.getParameter("expTimeStart");
        String expTimeEnd = request.getParameter("expTimeEnd");
        String rateTimeStart = request.getParameter("rateTimeStart");
        String rateTimeEnd = request.getParameter("rateTimeEnd");
        String balanceTimeStart = request.getParameter("balanceTimeStart");
        String balanceTimeEnd = request.getParameter("balanceTimeEnd");

        String reqNote = request.getParameter("reqNote");
        String reqName = request.getParameter("reqName");
        String reqUnit = request.getParameter("reqUnit");
        String combListStr = request.getParameter("reqCombListStr");
        String organListStr = request.getParameter("reqOrganListStr");
        String lineListStr = request.getParameter("reqProdLineStr");
        String numTypeStr = request.getParameter("numTypeStr");

        if ("��ѡ��".equals(reqMonth)) {
            reqMonth = "";
        }
        tbReqList.setReqMonth(reqMonth);
        tbReqList.setReqType(Integer.valueOf(reqType));
        tbReqList.setReqOrgan(organCode);

        if (!"".equals(reqMonth)) {
            List<TbReqList> checkTb = tbReqListService.selectByAttr(tbReqList);
            if (checkTb != null && (checkTb.size() > 0)) {
                if (checkTb.get(0).getReqType() == Integer.valueOf(reqType)) {
                    return this.json.returnMsg("false", "����ʧ��,���������ظ�!").toJson();
                }
            }
        }

        //ǰ̨������ �ַ���
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

        tbReqList.setReqUnit(Integer.valueOf(reqUnit));
        tbReqList.setReqOrganList(organListStr);
        tbReqList.setReqCombList(combListStr);
        tbReqList.setReqProdLine(lineListStr);
        tbReqList.setReqDateStart(reqDateStart);
        tbReqList.setNumType(numTypeStr);
        tbReqList.setReqDateEnd(reqDateEnd);
        tbReqList.setExpTimeStart(expTimeStart);
        tbReqList.setExpTimeEnd(expTimeEnd);
        tbReqList.setRateTimeStart(rateTimeStart);
        tbReqList.setRateTimeEnd(rateTimeEnd);
        tbReqList.setBalanceTimeStart(balanceTimeStart);
        tbReqList.setBalanceTimeEnd(balanceTimeEnd);
        tbReqList.setReqNote(reqNote);
        tbReqList.setReqName(reqName);
        tbReqList.setReqTimeStart(reqTimeStart);
        tbReqList.setReqTimeEnd(reqTimeEnd);
        tbReqList.setReqCreateOper(fdOper.getOpercode());
        tbReqList.setReqUpdateOper(fdOper.getOpercode());
        tbReqList.setReqCreatetime(BocoUtils.getTime());
        tbReqList.setReqUpdatetime(BocoUtils.getTime());
        tbReqList.setReqState(TbReqDetail.STATE_NEW);
        tbReqList.setReqTo(TbReqList.REQ_TO_PRODUCER);


        tbReqListService.insertEntity(tbReqList);
        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }


    /**
     * TODO �޸�tb_req_list.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "PUB-01-02-02", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {
        String reqMonth = request.getParameter("reqMonth");
        String oldMonth = request.getParameter("oldMonth");
        TbReqList tbReqList = new TbReqList();
        String reqType = request.getParameter("reqType");
        tbReqList.setReqMonth(reqMonth);
        tbReqList.setReqType(Integer.valueOf(reqType));
        if (!"".equals(reqMonth)) {
            List<TbReqList> checkTb = tbReqListService.selectByAttr(tbReqList);
            if (checkTb != null && (checkTb.size() > 0)) {
                if (!oldMonth.equals(checkTb.get(0).getReqMonth())) {
                    return this.json.returnMsg("false", "����ʧ��,���������·��ظ�!").toJson();
                }
            }
        }

        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String organCode = fdOper.getOrgancode();

        String reqId = request.getParameter("reqId");


        String reqDateStart = request.getParameter("reqDateStart");
        String reqDateEnd = request.getParameter("reqDateEnd");
        String reqTimeStart = request.getParameter("reqTimeStart");
        String reqTimeEnd = request.getParameter("reqTimeEnd");
        String expTimeStart = request.getParameter("expTimeStart");
        String expTimeEnd = request.getParameter("expTimeEnd");
        String rateTimeStart = request.getParameter("rateTimeStart");
        String rateTimeEnd = request.getParameter("rateTimeEnd");
        String balanceTimeStart = request.getParameter("balanceTimeStart");
        String balanceTimeEnd = request.getParameter("balanceTimeEnd");
        String reqNote = request.getParameter("reqNote");
        String reqName = request.getParameter("reqName");
        String combListStr = request.getParameter("reqCombListStr");
        String organListStr = request.getParameter("reqOrganListStr");
        String lineListStr = request.getParameter("reqProdLineStr");
        String numTypeStr = request.getParameter("numTypeStr");
        String reqUnit = request.getParameter("reqUnit");
        if ("��ѡ��".equals(reqMonth)) {
            reqMonth = "";
        }

        //ǰ̨������ �ַ���
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

        tbReqList.setReqMonth(reqMonth);
        tbReqList.setReqType(Integer.valueOf(reqType));
        tbReqList.setReqOrgan(organCode);
        tbReqList.setReqUnit(Integer.valueOf(reqUnit));
        tbReqList.setReqId(Integer.parseInt(reqId));
        tbReqList.setReqOrganList(organListStr);
        tbReqList.setReqCombList(combListStr);
        tbReqList.setNumType(numTypeStr);
        tbReqList.setReqProdLine(lineListStr);
        tbReqList.setReqDateStart(reqDateStart);
        tbReqList.setReqDateEnd(reqDateEnd);
        tbReqList.setReqNote(reqNote);
        tbReqList.setReqName(reqName);
        tbReqList.setReqTimeStart(reqTimeStart);
        tbReqList.setReqTimeEnd(reqTimeEnd);
        tbReqList.setExpTimeStart(expTimeStart);
        tbReqList.setExpTimeEnd(expTimeEnd);
        tbReqList.setRateTimeStart(rateTimeStart);
        tbReqList.setRateTimeEnd(rateTimeEnd);
        tbReqList.setBalanceTimeStart(balanceTimeStart);
        tbReqList.setBalanceTimeEnd(balanceTimeEnd);
        tbReqList.setReqCreateOper(fdOper.getOpercode());
        tbReqList.setReqUpdateOper(fdOper.getOpercode());
        tbReqList.setReqCreatetime(BocoUtils.getTime());
        tbReqList.setReqUpdatetime(BocoUtils.getTime());
        //Ĭ��0�� δ�·�
        tbReqList.setReqState(TbReqDetail.STATE_NEW);
        tbReqList.setReqTo(TbReqList.REQ_TO_PRODUCER);

        tbReqListService.updateByPK(tbReqList);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

    /**
     * TODO ɾ��tb_req_list
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "��������", funCode = "PUB-01-02-03", funName = "ɾ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbReqList tbReqList) throws Exception {
        tbReqListService.deleteByPK(tbReqList.getReqId());
        return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
    }

    /**
     * �·�����tb_req_list
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "issued")
    @SystemLog(tradeName = "�����·�", funCode = "PUB-01-02-05", funName = "�·�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String issued(TbReqList tbReqList, HttpSession session) throws Exception {
        tbReqList = tbReqListService.selectByPK(tbReqList.getReqId());
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbReqList.setReqUpdateOper(fdOper.getOpercode());
        tbReqList.setReqUpdatetime(BocoUtils.getTime());
        tbReqList.setReqState(TbReqDetail.STATE_ISSUED);
        tbReqList.setReqTo(TbReqList.REQ_TO_CONSUMER);
        int type = tbReqList.getReqType();
        /*
         * �·������߱Ƚ����⣬�������߽��½�������������
         * */

        String operCode = getSessionOperInfo().getOperCode();//��ǰ����Ա
        String lineUrl = "tbTradeManger/tbLineReqDetail/insertUI.htm?lineReqId=";

        String url = "tbTradeManger/tbReqDetail/insertUI.htm?reqId=" + tbReqList.getReqId();
        if (type == 1) {
            String lineStr = tbReqList.getReqProdLine();
            String[] lineArr = lineStr.replace("TangLoveQian,", "").split(",");
            List<TbLineReqDetail> tbLineReqDetails = new ArrayList<>();
            for (int i = 0; i < lineArr.length; i++) {
                TbLineReqDetail tbLineReqDetail = new TbLineReqDetail();
                tbLineReqDetail.setLineReqMonth(tbReqList.getReqMonth());
                tbLineReqDetail.setLineRefId(tbReqList.getReqId());
                tbLineReqDetail.setLineOrgan(tbReqList.getReqOrgan());
                tbLineReqDetail.setLineCode(lineArr[i]);
                ProductLineInfoDO productLineInfoDO = lineProductMapper.getProductLineInfoByLineId(lineArr[i]);
                tbLineReqDetail.setLineName(productLineInfoDO.getLineName());
                tbLineReqDetail.setLineState(TbLineReqDetail.STATE_ISSUED);
                tbLineReqDetail.setLineUnit(tbReqList.getReqUnit());
                tbLineReqDetail.setLineCreateTime(BocoUtils.getTime());
                tbLineReqDetail.setLineUpdateTime(BocoUtils.getTime());
                tbLineReqDetail.setLineReqName(tbReqList.getReqName());
                tbLineReqDetail.setLineReqNote(tbReqList.getReqNote());
                String combCodeStr = "";
                List<ProductLineDetailDO> productLineDetailDOList = lineProductDetailsMapper.getProductLineDetailById(lineArr[i]);
                for (ProductLineDetailDO productLineDetailDO : productLineDetailDOList) {
                    LoanCombDO loanComposeDO = loanCombMapper.getLoanCombInfoByCombCode(productLineDetailDO.getProductId());
                    if (Objects.nonNull(loanComposeDO)) {//
                        combCodeStr += (loanComposeDO.getCombCode() + ",");
                    }
                }
                tbLineReqDetail.setLineCombCode(combCodeStr);
                tbLineReqDetails.add(tbLineReqDetail);
            }
            //�·�֮�� ����
            tbLineReqDetailService.insertBatch(tbLineReqDetails);
        }
        TbLineReqDetail searchTbLineReqDetail = new TbLineReqDetail();
        searchTbLineReqDetail.setLineState(TbLineReqDetail.STATE_ISSUED);
        searchTbLineReqDetail.setLineReqMonth(tbReqList.getReqMonth());
        searchTbLineReqDetail.setLineRefId(tbReqList.getReqId());
        searchTbLineReqDetail.setLineOrgan(tbReqList.getReqOrgan());
        searchTbLineReqDetail.setLineUnit(tbReqList.getReqUnit());

        List<TbLineReqDetail> tbLineReqDetailList = tbLineReqDetailService.selectByAttr(searchTbLineReqDetail);

        //������ ¼��Ա��ɫ�� һ�� 310������¼��Ա 200
        //���� ��������¼��Ա 401��410��

        int level = Integer.parseInt(getSessionOrgan().getOrganlevel());

        if (level == 0 && type == 0) {
            //���л����·��� 36��һ��
            String organListStr = tbReqList.getReqOrganList().replace("TangLoveQian,", "");
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
                                saveMsg(msgNo, operCode, roleOper, url, tbReqList.getReqId().toString() + "_" + organCodeStr, tbReqList.getReqName());
                            }
                        }
                    }
                }
            }

        } else if (level == 1 && type == 0) {
            //һ�ֻ����������·�
            String organListStr = tbReqList.getReqOrganList().replace("TangLoveQian,", "");
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
                                saveMsg(msgNo, operCode, roleOper, url, tbReqList.getReqId().toString() + "_" + organCodeStr, tbReqList.getReqName());
                            }
                        }
                    }
                }
            }
        } else if (level == 0 && type == 1) {
            //�������� �·� ������
            String lineListStr = tbReqList.getReqProdLine().replace("TangLoveQian,", "");
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
                            for (TbLineReqDetail tb : tbLineReqDetailList) {
                                String msgNo = BocoUtils.getUUID();
                                if (lineCode.equals(tb.getLineCode())) {
                                    saveMsg(msgNo, operCode, operLineTemp.getOperCode(), lineUrl + tb.getLineReqId(), tb.getLineReqId().toString(), tbReqList.getReqName());
                                }
                            }
                        }
                    }
                }
            }

        } else if (level == 1 && type == 1) {
            //�������� �·� ������
            String lineListStr = tbReqList.getReqProdLine().replace("TangLoveQian,", "");
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
                            for (TbLineReqDetail tb : tbLineReqDetailList) {
                                String msgNo = BocoUtils.getUUID();
                                if (lineCode.equals(tb.getLineCode())) {
                                    saveMsg(msgNo, operCode, operLineTemp.getOperCode(), lineUrl + tb.getLineReqId(), tb.getLineReqId().toString(), tbReqList.getReqName());
                                }
                            }
                        }
                    }
                }
            }

        }
        tbReqListService.updateByPK(tbReqList);
        return this.json.returnMsg("true", "�·��ɹ�!").toJson();
    }


    /**
     * @param msgNo
     * @param operCode ��ǰ
     * @param assignee �¼�
     * @param msgUrl
     * @throws Exception
     */
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
        msg.setMsgType(DicCache.getKeyByName_("¼������", "MSG_TYPE"));
        msg.setOperName(reqName);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("¼������" + reqName);
        webMsgService.insertEntity(msg);
    }


    /**
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectReqId")
    @SystemLog(tradeName = "������Ҫ���·�", funCode = "PUB-01-02", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectReqId(TbReqList tbReqList, HttpServletRequest request, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbReqList.setReqOrgan(fdOper.getOrgancode());
        String reqId = request.getParameter("reqId");
        if (reqId != null && "".equals(reqId)) {
            tbReqList.setReqId(Integer.valueOf(reqId));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, Integer>> tbReqListList = tbReqListService.selectReqId(tbReqList);
        if (tbReqListList == null || tbReqListList.size() == 0) {
            resultMap.put("list", list);
            String json = JsonUtils.toJson(resultMap);
            return json;
        }
        for (Map<String, Integer> deptInfo : tbReqListList) {
            String data = String.valueOf(deptInfo.get("req_id"));
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
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectReqMonth")
    @SystemLog(tradeName = "������Ҫ���·�", funCode = "PUB2", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectReqMonth(TbReqList tbReqList, HttpServletRequest request, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbReqList.setReqOrgan(fdOper.getOrgancode());
        String reqMonth = request.getParameter("reqMonth").replace("'", "");
        if (reqMonth != null && "".equals(reqMonth)) {
            tbReqList.setReqMonth(reqMonth);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> tbReqListList = tbReqListService.selectReqMonth(tbReqList);
        if (tbReqListList == null || tbReqListList.size() == 0) {
            resultMap.put("list", list);
            String json = JsonUtils.toJson(resultMap);
            return json;
        }
        for (Map<String, String> deptInfo : tbReqListList) {
            String data = deptInfo.get("req_month");
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        Map<String, String> defaultMap = new HashMap<>();
        defaultMap.put("key", "---��ѡ��---");
        defaultMap.put("value", "");
        list.add(defaultMap);
        list.add(0, defaultMap);
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
    @RequestMapping(value = "selectReqOrgan")
    @SystemLog(tradeName = "������Ҫ���·�", funCode = "PUB-01-02", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectReqOrgan(TbReqList tbReqList, HttpServletRequest request, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbReqList.setReqOrgan(fdOper.getOrgancode());
        String reqOrgan = request.getParameter("reqOrgan").replace("'", "");
        if (reqOrgan != null && "".equals(reqOrgan)) {
            tbReqList.setReqOrgan(reqOrgan);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> tbReqListList = tbReqListService.selectReqOrgan(tbReqList);
        if (tbReqListList == null || tbReqListList.size() == 0) {
            resultMap.put("list", list);
            String json = JsonUtils.toJson(resultMap);
            return json;
        }
        for (Map<String, String> deptInfo : tbReqListList) {
            String data = deptInfo.get("req_organ");
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
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "showReqId")
    @SystemLog(tradeName = "������Ҫ���·�", funCode = "PUB-01-03", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showReqId(TbReqList tbReqList, HttpServletRequest request, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbReqList.setReqOrgan(fdOper.getOrgancode());
        FdOrgan fdOrgan = fdOrganService.selectByPK(fdOper.getOrgancode());
        String upOrganCode = "";
        if (fdOper.getOrgancode().equals("11005293")) {
            upOrganCode = "11005293";
        } else {
            upOrganCode = fdOrgan.getUporgan();
        }//��ȡ���ϼ������ţ�����ѯ�ϼ��·���������Ϣ
        String reqId = request.getParameter("reqId");
        if (reqId != null && "".equals(reqId)) {
            tbReqList.setReqId(Integer.valueOf(reqId));
        }
        tbReqList.setReqTo(TbReqList.REQ_TO_CONSUMER);
        tbReqList.setReqOrgan(upOrganCode);
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, Integer>> tbReqListList = tbReqListService.showReqId(tbReqList);
        if (tbReqListList == null || tbReqListList.size() == 0) {
            resultMap.put("list", list);
            String json = JsonUtils.toJson(resultMap);
            return json;
        }
        for (Map<String, Integer> deptInfo : tbReqListList) {
            String data = String.valueOf(deptInfo.get("req_id"));
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
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2019-09-19     txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "showReqMonth")
    @SystemLog(tradeName = "��ѯ������Ϣ", funCode = "PUB2", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String showReqMonth(TbReqList tbReqList, HttpServletRequest request, HttpSession session) throws Exception {
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbReqList.setReqOrgan(fdOper.getOrgancode());
        FdOrgan fdOrgan = fdOrganService.selectByPK(fdOper.getOrgancode());
        //��ȡ���ϼ������ţ�����ѯ�ϼ��·���������Ϣ
        String upOrganCode = "";
        if (fdOper.getOrgancode().equals("11005293")) {
            upOrganCode = "11005293";
        } else {
            upOrganCode = fdOrgan.getUporgan();
        }
        String reqMonth = request.getParameter("reqMonth");
        if (reqMonth != null && "".equals(reqMonth)) {
            tbReqList.setReqMonth(reqMonth);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        tbReqList.setReqTo(TbReqList.REQ_TO_CONSUMER);
        tbReqList.setReqOrgan(upOrganCode);
        List<Map<String, String>> tbReqListList = tbReqListService.showReqMonth(tbReqList);
        if (tbReqListList == null || tbReqListList.size() == 0) {
            resultMap.put("list", list);
            String json = JsonUtils.toJson(resultMap);
            return json;
        }
        for (Map<String, String> deptInfo : tbReqListList) {
            String data = deptInfo.get("req_month");
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