package com.boco.AL.controller;

import com.alibaba.fastjson.JSON;
import com.boco.AL.service.ITbPunishDetailService;
import com.boco.AL.service.ITbPunishListService;
import com.boco.AL.service.ITbPunishResultService;
import com.boco.AL.service.impl.PunishInterestService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbPunishDetail;
import com.boco.SYS.entity.TbPunishList;
import com.boco.SYS.entity.TbPunishResult;
import com.boco.SYS.global.Dic;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.PunishInterestMapper;
import com.boco.SYS.mapper.TbPlanDetailMapper;
import com.boco.SYS.util.BocoUtils;
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
 * Action���Ʋ�
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbTradeManger/tbPunishList/")
public class TbPunishListController extends BaseController {
    @Autowired
    private ITbPunishListService tbPunishListService;
    @Autowired
    private ITbPunishResultService tbPunishResultService;

    @Autowired
    private ITbPunishDetailService tbPunishDetailService;
    @Autowired
    private IFdOrganService organService;
    @Autowired
    private PunishInterestService punishInterestService;

    @Autowired
    private PunishInterestMapper punishInterestMapper;

    @Autowired
    private ITbTradeParamService tbTradeParamService;

    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;


    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "��������", funCode = "AL", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbPunishManage/tbPunishList/punishList/List";
    }


    /**
     * �·�tb_req_list
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "issued")
    @SystemLog(tradeName = "�����·�", funCode = "AL", funName = "�·�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String issued(int id) throws Exception {

        TbPunishResult tbPunishResult = new TbPunishResult();
        tbPunishResult.setPunishListId(id);
        tbPunishResult.setState(TbPunishResult.STATE_FILL);

        tbPunishResultService.updateByListId(tbPunishResult);
        TbPunishList tbPunishList = new TbPunishList();
        tbPunishList.setId(id);
        tbPunishList.setState(TbPunishList.STATE_FILL);
        tbPunishListService.updateByPK(tbPunishList);
        return this.json.returnMsg("true", "�·��ɹ�!").toJson();
    }


    @RequestMapping("infoUI")
    @SystemLog(tradeName = "��������", funCode = "AL", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbPunishList tbPunishList) throws Exception {
        tbPunishList = tbPunishListService.selectByPK(tbPunishList.getId());
        setAttribute("TbPunishList", tbPunishList);
        setAttr(tbPunishList);
        return basePath + "/AL/tbPunishManage/tbPunishList/punishList/Info";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��������", funCode = "AL", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/AL/tbPunishManage/tbPunishList/punishList/Add";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "AL", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbPunishList tbPunishList) throws Exception {
        tbPunishList = tbPunishListService.selectByPK(tbPunishList.getId());
        setAttribute("TbPunishList", tbPunishList);
        setAttr(tbPunishList);
        return basePath + "/AL/tbPunishManage/tbPunishList/punishList/Edit";
    }


    /**
     * @param tbPunishList ��Ϣ�б�
     * @throws Exception
     */
    public void setAttr(TbPunishList tbPunishList) throws Exception {

        FdOrgan thisOrgan = getSessionOrgan();
        List<Map<String, Object>> organList = organService.selectByUporgan(thisOrgan.getThiscode());
        setAttribute("organList", organList);
        List<Map<String, String>> combAmountNameList = new ArrayList<>();

        Map<String, String> map0 = new HashMap<>(2);
        map0.put("name", "״̬");
        map0.put("code", "state");
        combAmountNameList.add(map0);
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "�����ܼƻ�(��Ԫ)");
        map1.put("code", "planMount");
        combAmountNameList.add(map1);
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "��ĩ���ö��(��Ԫ)");
        map2.put("code", "monthVacancyAmt");
        combAmountNameList.add(map2);
        Map<String, String> map3 = new HashMap<>(2);
        map3.put("name", "��ĩ������(%)");
        map3.put("code", "monthVacancyRate");
        combAmountNameList.add(map3);
        Map<String, String> map4 = new HashMap<>(2);
        map4.put("name", "�����÷�(��Ԫ)");
        map4.put("code", "monthFiveVacancy");
        combAmountNameList.add(map4);
        Map<String, String> map5 = new HashMap<>(2);
        map5.put("name", "ʵ�忼�˼ƻ�(��Ԫ)");
        map5.put("code", "monthShitiPlanMount");
        combAmountNameList.add(map5);
        Map<String, String> map6 = new HashMap<>(2);
        map6.put("name", "ʵ����ĩ���ƻ����(��Ԫ)");
        map6.put("code", "monthShitiOverAmt");
        combAmountNameList.add(map6);
        Map<String, String> map7 = new HashMap<>(2);
        map7.put("name", "ʵ����ĩ���ƻ�����(%)");
        map7.put("code", "monthShitiOverRate");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "ʵ�峬�ƻ���(��Ԫ)");
        map7.put("code", "monthFiveShitiOver");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "Ʊ�ݿ��˼ƻ�(��Ԫ)");
        map7.put("code", "monthPiapjuPlanMount");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "Ʊ����ĩ���ƻ����(��Ԫ)");
        map7.put("code", "monthPiaojuOverAmt");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "Ʊ����ĩ���ƻ�����(%)");
        map7.put("code", "monthPiaojuOverRate");
        combAmountNameList.add(map7);
        map7 = new HashMap<>(2);
        map7.put("name", "Ʊ�ݳ��ƻ���(��Ԫ)");
        map7.put("code", "monthFivePiaojuOver");
        combAmountNameList.add(map7);
        Map<String, String> map8 = new HashMap<>(2);
        map8.put("name", "��Ϣ�ܼ�(��Ԫ)");
        map8.put("code", "monthTotalPunish");
        combAmountNameList.add(map8);
        map7 = new HashMap<>(2);
        map7.put("name", "�������");
        map7.put("code", "note");
        combAmountNameList.add(map7);
        setAttribute("combAmountNameList", combAmountNameList);

    }

    /**
     * ����ҳ����
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getReqDetailList")
    @SystemLog(tradeName = "�Ŵ�����", funCode = "AL", funName = "�Ŵ��ƻ���������", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanDetailData(String id) throws Exception {
        TbPunishResult searchTbPunishResult = new TbPunishResult();
        searchTbPunishResult.setPunishListId(Integer.valueOf(id));
        List<TbPunishResult> tbPunishResultList = tbPunishResultService.selectByListId(searchTbPunishResult);
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("tbPunishResultList", tbPunishResultList);
        return JSON.toJSONString(resultMap);
    }


    /**
     * TODO ��ѯtb_punish_list��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "AL", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbPunishList tbPunishList) throws Exception {
        setPageParam();
        List<TbPunishList> list = tbPunishListService.selectByAttr(tbPunishList);
        return pageData(list);
    }

    /**
     * TODO ����tb_punish_list.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "��������", funCode = "AL", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(TbPunishList tbPunishList, HttpSession session) throws Exception {

        tbPunishList.setState(TbPunishList.STATE_NEW);
        tbPunishList.setCreaeTime(BocoUtils.getTime());
        tbPunishList.setUpdateTime(BocoUtils.getTime());
        tbPunishListService.insertEntity(tbPunishList);
        tbPunishList = tbPunishListService.selectByAttr(tbPunishList).get(0);
        String month = tbPunishList.getMonth();
        int id = tbPunishList.getId();

        List<TbPunishResult> tbPunishResultList = new ArrayList<>();

        //TODO �������ɷ�Ϣ�������
        FdOrgan thisOrgan = getSessionOrgan();
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setOrganlevel(String.valueOf(Integer.parseInt(thisOrgan.getOrganlevel()) + 1));
        searchOrgan.setUporgan(thisOrgan.getThiscode());
        List<FdOrgan> fdOrganList = organService.selectByAttr(searchOrgan);

        String shiti = "shiti";
        String pjfft = "pjfft";
        String xianzhi = "totalLeft";


        for (FdOrgan organ : fdOrganList) {

            TbPunishDetail searchTbPunishDetail = new TbPunishDetail();
            searchTbPunishDetail.setOrgan(organ.getThiscode());
            searchTbPunishDetail.setMonth(month);
            List<TbPunishDetail> tbPunishDetailListFive = tbPunishDetailService.selectByAttr(searchTbPunishDetail);//length=15
            searchTbPunishDetail.setLeftDay(BigDecimal.ZERO);
            List<TbPunishDetail> tbPunishDetailList = tbPunishDetailService.selectByAttr(searchTbPunishDetail);//length=3


            if (tbPunishDetailList.size() == 3) {
                TbPunishResult tbPunishResult = new TbPunishResult();
                tbPunishResult.setOrganCode(organ.getThiscode());
                tbPunishResult.setOrganName(organ.getOrganname());
                TbPunishDetail shitiTbPunishDetail = null;
                TbPunishDetail pjfftTbPunishDetail = null;
                TbPunishDetail xianzhiTbPunishDetail = null;
                if (shiti.equals(tbPunishDetailList.get(0).getComb())) {
                    shitiTbPunishDetail = tbPunishDetailList.get(0);
                } else if (pjfft.equals(tbPunishDetailList.get(0).getComb())) {
                    pjfftTbPunishDetail = tbPunishDetailList.get(0);
                } else if (xianzhi.equals(tbPunishDetailList.get(0).getComb())) {
                    xianzhiTbPunishDetail = tbPunishDetailList.get(0);
                }
                if (shiti.equals(tbPunishDetailList.get(1).getComb())) {
                    shitiTbPunishDetail = tbPunishDetailList.get(1);
                } else if (pjfft.equals(tbPunishDetailList.get(1).getComb())) {
                    pjfftTbPunishDetail = tbPunishDetailList.get(1);
                } else if (xianzhi.equals(tbPunishDetailList.get(1).getComb())) {
                    xianzhiTbPunishDetail = tbPunishDetailList.get(1);
                }
                if (shiti.equals(tbPunishDetailList.get(2).getComb())) {
                    shitiTbPunishDetail = tbPunishDetailList.get(2);
                } else if (pjfft.equals(tbPunishDetailList.get(2).getComb())) {
                    pjfftTbPunishDetail = tbPunishDetailList.get(2);
                } else if (xianzhi.equals(tbPunishDetailList.get(2).getComb())) {
                    xianzhiTbPunishDetail = tbPunishDetailList.get(2);
                }

                BigDecimal unit = new BigDecimal(10000);

                BigDecimal planAmout = xianzhiTbPunishDetail.getPlan().divide(unit);
                tbPunishResult.setPlanMount(planAmout); //�ܼƻ� ��λ����Ԫ
                BigDecimal xianzhidiff = BigDecimal.ZERO.subtract(xianzhiTbPunishDetail.getDeparture().divide(unit));
                tbPunishResult.setMonthVacancyAmt(xianzhidiff);
                BigDecimal xianzhiratio = BigDecimal.ZERO;
                if (planAmout.compareTo(BigDecimal.ZERO) != 0) {
                    xianzhiratio = xianzhidiff.divide(planAmout.abs(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                }

                tbPunishResult.setMonthVacancyRate(xianzhiratio);
                BigDecimal xianzhipunishMoney = xianzhiTbPunishDetail.getPunishMoney();
                //�����ĩ ���÷�=0����=0���������0 ��ǰ�������
                if (xianzhipunishMoney.compareTo(BigDecimal.ZERO) != 0) {
                    xianzhipunishMoney = BigDecimal.ZERO;
                    for (TbPunishDetail tbPunishDetail : tbPunishDetailListFive) {
                        if (xianzhi.equals(tbPunishDetail.getComb())) {
                            xianzhipunishMoney = xianzhipunishMoney.add(tbPunishDetail.getPunishMoney());
                        }
                    }
                }
                tbPunishResult.setMonthFiveVacancy(xianzhipunishMoney);

                BigDecimal pjfft1plan = pjfftTbPunishDetail.getPlan().divide(unit);
                tbPunishResult.setMonthPiapjuPlanMount(pjfft1plan);
                BigDecimal pjfftdiff = pjfftTbPunishDetail.getDeparture().divide(unit);
                tbPunishResult.setMonthPiaojuOverAmt(pjfftdiff);
                BigDecimal pjfftratio = BigDecimal.ZERO;
                if (pjfft1plan.compareTo(BigDecimal.ZERO) != 0) {
                    pjfftratio = pjfftdiff.divide(pjfft1plan.abs(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                }
                tbPunishResult.setMonthPiaojuOverRate(pjfftratio);
                BigDecimal pjfftPuishMoney = pjfftTbPunishDetail.getPunishMoney();
                if (pjfftPuishMoney.compareTo(BigDecimal.ZERO) != 0) {
                    pjfftPuishMoney = BigDecimal.ZERO;
                    for (TbPunishDetail tbPunishDetail : tbPunishDetailListFive) {
                        if (pjfft.equals(tbPunishDetail.getComb())) {
                            pjfftPuishMoney = pjfftPuishMoney.add(tbPunishDetail.getPunishMoney());
                        }
                    }
                }
                tbPunishResult.setMonthFivePiaojuOver(pjfftPuishMoney);


                BigDecimal shitiplan = shitiTbPunishDetail.getPlan().divide(unit);
                tbPunishResult.setMonthShitiPlanMount(shitiplan);
                BigDecimal shitidiff = shitiTbPunishDetail.getDeparture().divide(unit);
                tbPunishResult.setMonthShitiOverAmt(shitidiff);
                BigDecimal shitiratio = BigDecimal.ZERO;
                if (shitiplan.compareTo(BigDecimal.ZERO) != 0) {
                    shitiratio = shitidiff.divide(shitiplan.abs(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                }

                tbPunishResult.setMonthShitiOverRate(shitiratio);
                BigDecimal shitipunishMoney = shitiTbPunishDetail.getPunishMoney();
                if (shitipunishMoney.compareTo(BigDecimal.ZERO) != 0) {
                    shitipunishMoney = BigDecimal.ZERO;
                    for (TbPunishDetail tbPunishDetail : tbPunishDetailListFive) {
                        if (shiti.equals(tbPunishDetail.getComb())) {
                            shitipunishMoney = shitipunishMoney.add(tbPunishDetail.getPunishMoney());
                        }
                    }
                }
                tbPunishResult.setMonthFiveShitiOver(shitipunishMoney);

                BigDecimal totalPunishMoney = shitipunishMoney.add(pjfftPuishMoney).add(xianzhipunishMoney);
                tbPunishResult.setMonthTotalPunish(totalPunishMoney);

                tbPunishResult.setEndTime(tbPunishList.getCollectEndTime());
                tbPunishResult.setCreateUserid(getSessionOperInfo().getOperCode());
                tbPunishResult.setUpdateUserid(getSessionOperInfo().getOperCode());
                tbPunishResult.setState(TbPunishList.STATE_NEW);
                tbPunishResult.setCreateTime(BocoUtils.getTime());
                tbPunishResult.setUpdateTime(BocoUtils.getTime());
                tbPunishResult.setPunishMonth(month);
                tbPunishResult.setPunishListId(id);
                tbPunishResult.setNote("");
                tbPunishResultList.add(tbPunishResult);
            }

        }

        if (tbPunishResultList == null || tbPunishResultList.size() == 0) {
            tbPunishListService.deleteByPK(id);
            return this.json.returnMsg("flase", "����ʧ�ܣ�������ʷ��Ϣ����!").toJson();
        }

        tbPunishResultService.insertBatch(tbPunishResultList);

        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }


    /**
     * TODO �޸�tb_punish_list.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "AL", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {

        TbPunishList tbPunishList = new TbPunishList();
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String month = request.getParameter("month");
        String collectEndTime = request.getParameter("collectEndTime");
        String note = request.getParameter("note");
        String tbPunishListDetail = request.getParameter("tbPunishListDetail");
        System.out.println("------------" + tbPunishListDetail);
        tbPunishList.setId(id);
        tbPunishList.setName(name);
        tbPunishList.setNote(note);
        tbPunishList.setCollectEndTime(collectEndTime);
        tbPunishList.setMonth(month);
        TbPunishResult tbPunishResult = new TbPunishResult();
        tbPunishResult.setPunishListId(id);
        List<TbPunishResult> oldList = tbPunishResultService.selectByAttr(tbPunishResult);
        List<TbPunishResult> tbPunishResultList = getList(tbPunishListDetail, tbPunishList, oldList);
        tbPunishList.setUpdateTime(BocoUtils.getTime());
        tbPunishListService.updateByPK(tbPunishList);


        tbPunishResultService.deleteByAttr(tbPunishResult);
        tbPunishResultService.insertBatch(tbPunishResultList);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }


    /**
     * ����ǰ̨�����Ĳ���
     *
     * @param tbPunishListDetail
     * @return
     */
    private List<TbPunishResult> getList(String tbPunishListDetail, TbPunishList tbPunishList, List<TbPunishResult> oldList) throws Exception {

        //organCode_name=1234&
        FdOrgan thisOrgan = getSessionOrgan();
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setOrganlevel(String.valueOf(Integer.parseInt(thisOrgan.getOrganlevel()) + 1));
        searchOrgan.setUporgan(thisOrgan.getThiscode());
        List<FdOrgan> fdOrganList = organService.selectByAttr(searchOrgan);
        Map<String, String> organMap = new HashMap<>(64);
        organMap = new HashMap<>(36);
        for (FdOrgan organ : fdOrganList) {
            organMap.put(organ.getThiscode(), organ.getOrganname());
        }
        List<TbPunishResult> tbPunishResultList = new ArrayList<>();
        String[] planDetailArr = tbPunishListDetail.split("&");
        Map<String, TbPunishResult> tbPunishResultMap = new HashMap<>();

        for (TbPunishResult temp : oldList) {
            tbPunishResultMap.put(temp.getOrganCode(), temp);
        }

        for (int i = 0; i < planDetailArr.length; i++) {
            String[] planDetailArr1 = planDetailArr[i].split("=");
            String[] planDetailArr2 = planDetailArr1[0].split("_");
            TbPunishResult tbPunishResult = tbPunishResultMap.get(planDetailArr2[0]);
            if (tbPunishResult == null) {
                tbPunishResult = new TbPunishResult();
            }
            String nameStr = planDetailArr2[1];
            tbPunishResult.setOrganCode(planDetailArr2[0]);
            tbPunishResult.setOrganName(organMap.get(planDetailArr2[0]));
            tbPunishResult.setEndTime(tbPunishList.getCollectEndTime());
            tbPunishResult.setPunishListId(tbPunishList.getId());
            String valueStr = planDetailArr1[1];
            if ("state".equals(nameStr)) {
                tbPunishResult.setState(Integer.parseInt(valueStr));
            } else if ("planMount".equals(nameStr)) {
                tbPunishResult.setPlanMount(new BigDecimal(valueStr));
            } else if ("monthVacancyAmt".equals(nameStr)) {
                tbPunishResult.setMonthVacancyAmt(new BigDecimal(valueStr));
            } else if ("monthVacancyRate".equals(nameStr)) {
                tbPunishResult.setMonthVacancyRate(new BigDecimal(valueStr));
            } else if ("monthFiveVacancy".equals(nameStr)) {
                tbPunishResult.setMonthFiveVacancy(new BigDecimal(valueStr));
            } else if ("monthShitiPlanMount".equals(nameStr)) {
                tbPunishResult.setMonthShitiPlanMount(new BigDecimal(valueStr));
            } else if ("monthShitiOverAmt".equals(nameStr)) {
                tbPunishResult.setMonthShitiOverAmt(new BigDecimal(valueStr));
            } else if ("monthShitiOverRate".equals(nameStr)) {
                tbPunishResult.setMonthShitiOverRate(new BigDecimal(valueStr));
            } else if ("monthFiveShitiOver".equals(nameStr)) {
                tbPunishResult.setMonthFiveShitiOver(new BigDecimal(valueStr));
            } else if ("monthPiapjuPlanMount".equals(nameStr)) {
                tbPunishResult.setMonthPiapjuPlanMount(new BigDecimal(valueStr));
            } else if ("monthPiaojuOverAmt".equals(nameStr)) {
                tbPunishResult.setMonthPiaojuOverAmt(new BigDecimal(valueStr));
            } else if ("monthPiaojuOverRate".equals(nameStr)) {
                tbPunishResult.setMonthPiaojuOverRate(new BigDecimal(valueStr));
            } else if ("monthFivePiaojuOver".equals(nameStr)) {
                tbPunishResult.setMonthFivePiaojuOver(new BigDecimal(valueStr));
            } else if ("monthTotalPunish".equals(nameStr)) {
                tbPunishResult.setMonthTotalPunish(new BigDecimal(valueStr));
            } else if ("note".equals(nameStr)) {
                tbPunishResult.setNote(valueStr);
            }
            tbPunishResult.setCreateTime(BocoUtils.getTime());
            tbPunishResult.setUpdateTime(BocoUtils.getTime());
            tbPunishResult.setPunishMonth(tbPunishList.getMonth());
            tbPunishResultMap.put(planDetailArr2[0], tbPunishResult);
        }
        Set<String> keySet = tbPunishResultMap.keySet();
        for (String key : keySet) {
            tbPunishResultList.add(tbPunishResultMap.get(key));
        }
        return tbPunishResultList;
    }


    /**
     * TODO ɾ��tb_punish_list
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "��������", funCode = "AL", funName = "ɾ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbPunishList tbPunishList, HttpSession session) throws Exception {
        tbPunishListService.deleteByPK(tbPunishList.getId());
        return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
    }


    /**
     * ���������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2020-2-6    txn      �����½�
     * </pre>
     */
    @RequestMapping(value = "selectPunishMonth")
    @SystemLog(tradeName = "ʱ��ƻ�ά��", funCode = "AL", funName = "��������", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectQaMonth(TbPunishList tbPunishList, HttpServletRequest request) throws Exception {
        String month = request.getParameter("month").replace("'", "");
        if (month != null) {
            tbPunishList.setMonth(month);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        List<Map<String, String>> tbPunishListList = tbPunishListService.selectMonth(tbPunishList);
        for (Map<String, String> deptInfo : tbPunishListList) {
            String data = deptInfo.get("month");
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