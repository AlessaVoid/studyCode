package com.boco.PUB.controller.tbCalculateThree;

import java.math.BigDecimal;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.boco.PM.service.IFdOrganService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PUB.service.ITbLineReqDetailService;
import com.boco.PUB.service.ITbReqDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.PUB.service.tbCalculateThree.ITbCalculateThreeProportionService;
import com.boco.PUB.service.tbCalculateThree.ITbCalculateThreeResultService;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping(value = "/tbCalculateThreeResult/")
public class TbCalculateThreeResultController extends BaseController {
    @Autowired
    private ITbCalculateThreeResultService tbCalculateThreeResultService;
    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    private ITbReqDetailService tbReqDetailService;

    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IFdOrganService organService;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private ITbCalculateThreeProportionService tbCalculateThreeProportionService;


    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbCalculateThree/tbCalculateThreeResult/tbCalculateThreeResultList";
    }


    @RequestMapping("infoUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB", funName = "加载下级需求页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String onShowChildUI(String reqId) throws Exception {
        setAttribute("reqId", reqId);
        TbReqList tbReqList = tbReqListService.selectByPK(Integer.valueOf(reqId));
        WebOperInfo webOperInfo = getSessionOperInfo();
        String organCode = webOperInfo.getOrganCode();
        FdOrgan thisOrgan = organService.selectByPK(organCode);
        int reqType = tbReqList.getReqType();
        String combListStr = "";
        String organListStr = "";
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

        //河南分行特殊查询
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
            fdOrgan.setOrganname(fdOrgan.getOrganname() + "本部");
            fdOrganList.add(fdOrgan);
        }

        Map<String, String> organMap = new HashMap<>(16);
        for (FdOrgan fdOrgan : fdOrganList) {
            for (int i = 0; i < organArr.length; i++) {
                if (fdOrgan.getThiscode().equals(organArr[i])) {
                    organMap = new HashMap<>(2);
                    organMap.put("organCode", fdOrgan.getThiscode());
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
        setAttribute("organList", organList);
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        List<Map<String, String>> combList = new ArrayList<>();
        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        combListStr = tbReqList.getReqCombList();
        String[] combArr = combListStr.split(",");
        for (LoanCombDO loanCombDO : loanCombDOS) {
            for (int i = 0; i < combArr.length; i++) {
                if (combArr[i].equals(loanCombDO.getCombCode())) {
                    Map<String, String> combMap = new HashMap<>(2);
                    Map<String, String> map2 = new HashMap<>(2);
                    map2.put("name", loanCombDO.getCombName() + "需求量");
                    map2.put("code", combArr[i] + "_reqNum");
                    Map<String, String> map3 = new HashMap<>(2);
                    map3.put("name", loanCombDO.getCombName() + "满足后需求");
                    map3.put("code", combArr[i] + "_rate");
                    combAmountNameList.add(map2);
                    combAmountNameList.add(map3);
                    break;
                }
            }
        }
        setAttribute("combAmountNameList", combAmountNameList);
        setAttribute("TbReqListDTO", tbReqList);
        return basePath + "/PUB/tbCalculateThree/tbCalculateThreeResult/tbCalculateThreeResultInfo";

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
        TbReqDetail searchTbReqDetail = new TbReqDetail();
        searchTbReqDetail.setReqdRefId(Integer.valueOf(reqId));
        searchTbReqDetail.setReqdState(TbReqDetail.STATE_APPROVED);
        List<TbReqDetail> tbReqDetailList = tbReqDetailService.selectByAttr(searchTbReqDetail);
        tbReqDetailList = getDetailList(tbReqDetailList);
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("tbReqDetailList", tbReqDetailList);
        return JSON.toJSONString(resultMap);
    }

    /**
     * 处理tbReqDetailList
     *
     * @param tbReqDetailList
     * @return
     */
    private List<TbReqDetail> getDetailList(List<TbReqDetail> tbReqDetailList) throws Exception {
        List<TbCalculateThreeProportion> list = tbCalculateThreeProportionService.selectByAttr(new TbCalculateThreeProportion());
        Map<String, TbCalculateThreeProportion> tempMap = new HashMap<>();
        for (TbCalculateThreeProportion tb : list) {
            tempMap.put(tb.getCode(), tb);
        }


        int unit = 1;
        if (tbReqDetailList != null && tbReqDetailList.size() > 0) {
            unit = tbReqDetailList.get(0).getReqdUnit();
        }
        BigDecimal tbUnit = new BigDecimal(1);
        if (unit == 2) {
            tbUnit = new BigDecimal(10000);
        }
        Map<String, TbReqDetail> totalMap = new HashMap<>(8);
        for (TbReqDetail tbReqDetail : tbReqDetailList) {
            String combCode = tbReqDetail.getReqdCombCode();
            TbReqDetail totalTbReqDetail = totalMap.get(combCode);
            if (null == totalTbReqDetail) {
                totalTbReqDetail = new TbReqDetail();
                totalTbReqDetail.setReqdOrgan("TangLoveQianForever");
                totalTbReqDetail.setReqdCombCode(combCode);
                totalTbReqDetail.setReqdReqnum(new BigDecimal(0));
            }
            totalTbReqDetail.setReqdCreateTime(tbReqDetail.getReqdCreateTime());
            totalTbReqDetail.setReqdUpdateTime("-");
            totalTbReqDetail.setReqdReqnum(BigDecimalUtil.add(totalTbReqDetail.getReqdReqnum(), tbReqDetail.getReqdReqnum()));
            totalMap.put(combCode, totalTbReqDetail);
        }
        Set<String> combSet = totalMap.keySet();
        for (String combStr : combSet) {
            tbReqDetailList.add(totalMap.get(combStr));
        }

        for (TbReqDetail tb : tbReqDetailList) {
            tb.setReqdReqnum(BigDecimalUtil.divide(tb.getReqdReqnum(), tbUnit));
            String combCode = tb.getReqdCombCode();
            TbCalculateThreeProportion tempTb = tempMap.get(combCode);
            BigDecimal pro = tempTb.getProportion();
            BigDecimal comPro = tempTb.getCommonProportion();
            BigDecimal multiplier = pro;

            if (pro.compareTo(BigDecimal.ZERO) == 0) {
                multiplier = comPro;
            }
            tb.setReqdRate(BigDecimalUtil.multiply(tb.getReqdReqnum(), multiplier).divide(new BigDecimal(100)));
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
     * TODO 查询tb_calculate_three_result分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "PUB", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbReqList tbReqList, HttpSession session) throws Exception {
        tbReqList.setReqOrgan(getSessionOrgan().getThiscode());
        tbReqList.setReqTo(2);
        tbReqList.setReqType(0);
        setPageParam();
        List<TbReqList> list = tbReqListService.selectByAttr(tbReqList);
        return pageData(list);
    }


}