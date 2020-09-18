package com.boco.TONY.biz.loanplan.service.impl;

import com.alibaba.fastjson.JSON;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.loanplan.POJO.DO.FdOrganPlanInfo;
import com.boco.TONY.biz.loanplan.POJO.DTO.TbPlanDTO;
import com.boco.TONY.biz.loanplan.POJO.DTO.TbPlanDetailDTO;
import com.boco.TONY.biz.loanplan.exception.TBPlanException;
import com.boco.TONY.biz.loanplan.service.ILoanPlanService;
import com.boco.TONY.biz.loanreq.POJO.DO.TbReqPlanInfo;
import com.boco.TONY.biz.planadjust.POJO.DO.TbPlanAdjustDetailDO;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.utils.IDGeneratorUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 信贷计划业务逻辑层
 *
 * @author tony
 * @describe LoanPlanServiceImpl
 * @date 2019-09-29
 */
@Service
public class ILoanPlanServiceImpl implements ILoanPlanService {

    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    TbPlanMapper tbPlanMapper;

    @Autowired
    LoanPlanDetailMapper loanPlanDetailMapper;

    @Autowired
    TbReqDetailMapper tbReqDetailMapper;

    @Autowired
    LoanPlanAdjustmentMapper loanPlanAdjustmentMapper;

    @Autowired
    private LoanCombMapper loanCombMapper;

    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;



    /**
     * 插入信贷计划
     *
     * @param request HTTP
     * @return PlainResult
     */
    @Override
    public PlainResult<String> insertLoanPlan(HttpServletRequest request, String operCode, String organCode) {
        PlainResult<String> result = new PlainResult<>();
        String planMonth = request.getParameter("planMonth");
        if (planMonth == null || "".equals(planMonth)) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "planMonth not to be null");
        }
        // 单位
        int planUnit = 1;
        try {
            planUnit = Integer.parseInt(request.getParameter("planUnit"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            List<TbPlan> planMonthList = tbPlanMapper.selectLoanPlanByPlanMonth(planMonth);
            //库中不存在记录插入
            if (CollectionUtils.isEmpty(planMonthList)) {
                //主记录
                String planId = IDGeneratorUtils.getSequence();
                TbPlan tbPlanDO = buildLoanPlanDO(request, operCode, organCode);
                tbPlanDO.setPlanCreateTime(BocoUtils.getTime());
                tbPlanDO.setPlanUnit(planUnit);
                tbPlanDO.setPlanId(planId)
                        .setPlanUpdater(operCode)
                        .setPlanUpdateTime(BocoUtils.getTime());

                //构建信贷详情
                List<TbPlanDetail> tbPlanDetailList = buildTbPlanDetailDO(request);
                int finalPlanUnit = planUnit;
                List<TbPlanDetail> collectList = tbPlanDetailList.stream().peek(item -> {
                    String pdId = IDGeneratorUtils.getSequence();
                    item.setPdId(pdId);
                    item.setPdRefId(planId);
                    item.setPdMonth(planMonth);
                    item.setPdUnit(finalPlanUnit);
                    item.setPdCreateTime(BocoUtils.getTime());
                    item.setPdUpdateTime(BocoUtils.getTime());
                }).collect(Collectors.toList());

                tbPlanMapper.insertEntity(tbPlanDO);
                // loanPlanDetailMapper.insertLoanPlanDetail(collectList);
                tbPlanDetailMapper.insertBatch(collectList);
                return result.success("insert", "insert loan plan detail success");
            } else {
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "the month is existed.");
            }
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "insert loan plan failed.");
        }
    }

    /**
     * 插入信贷计划详情信息
     *
     * @param request HTTP REQUEST
     * @return 结果
     */
    /**
     * 构建信贷计划DO
     *
     * @param request HTTP
     * @return TbPlanDO
     */
    private List<TbPlanDetail> buildTbPlanDetailDO(HttpServletRequest request) {
        String gridData = request.getParameter("dataGrid");

        ArrayList<TbPlanDetail> tbPlanDetailArrayList = new ArrayList<>();
        List<Map> list = JSON.parseArray(gridData, Map.class);

        LoanCombDO loanCombDO = new LoanCombDO();
        loanCombDO.setCombLevel(1);
        List<LoanCombDO> allLoanCombInfoList = loanCombMapper.getAllLoanCombInfoList(loanCombDO);

        // 组装list
        for (Map map : list) {

            for (LoanCombDO combDO : allLoanCombInfoList) {
                TbPlanDetail tbPlanDetail = new TbPlanDetail();
                tbPlanDetail.setPdOrgan(map.get("pdOrgan").toString());
                tbPlanDetail.setPdLoanType(combDO.getCombCode());
                tbPlanDetail.setPdAmount(new BigDecimal(map.get(combDO.getCombCode()).toString()));

                tbPlanDetailArrayList.add(tbPlanDetail);
            }
        }
        return tbPlanDetailArrayList;
    }

    public ListResult<TbReqPlanInfo> getAllTbReqSpInfo() {
        ListResult<TbReqPlanInfo> result = new ListResult<>();
        try {
            List<TbReqPlanInfo> tbReqSpInfoList = tbPlanMapper.selectReqPlanInfo();
            return result.success(tbReqSpInfoList, "get all tb  req simple info success");
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "get all tb req simple info failed");
        }
    }

    /**
     * 构建信贷计划DO
     *
     * @param request HTTP REQUEST
     * @return TbPlanDO
     */
    private TbPlan buildLoanPlanDO(HttpServletRequest request, String operCode, String organCode) {
        String planMonth = request.getParameter("planMonth");
        String planIncrement = request.getParameter("planIncrement");
        if (planIncrement == null) {
            planIncrement = "0";
        }


        TbPlan tbPlan = new TbPlan()
                .setPlanOrgan(organCode)
                .setPlanMonth(planMonth)
                .setPlanOper(operCode);
        tbPlan.setPlanStatus(TbReqDetail.STATE_NEW);
        tbPlan.setPlanIncrement(new BigDecimal(planIncrement));
        return tbPlan;
    }

    /**
     * 信贷计划制定
     *
     * @param request HTTP REQUEST
     * @return PR
     */
    @Override
    public PlainResult<String> updateLoanPlanInfo(HttpServletRequest request, String organCode) {
        PlainResult<String> result = new PlainResult<>();
        // try {
        //     String planId = request.getParameter("planId");
        //     String planMonth = request.getParameter("planMonth");
        //     List<TbPlanDetailDO> tbPlanDetailDOList = buildTbPlanDetailDO(request, planId);
        //     List<TbPlanDetailDO> filterList = tbPlanDetailDOList.stream().filter(item -> {
        //         if ("nochanged".equals(item.get__status())) {
        //             return false;
        //         }
        //         item.setPdMonth(planMonth);
        //         return true;
        //     }).collect(Collectors.toList());
        //     loanPlanDetailMapper.updateLoanPlanDetail(filterList);
        //     return result.success("update", "update loan plan detail success");
        // } catch (Exception e) {
        //     return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update loan plan failed.");
        // }
        return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update loan plan failed.");
    }

    /**
     * 删除信贷计划
     *
     * @param planId 信贷计划Id
     * @return PlainResult
     */
    @Override
    public PlainResult<String> deleteLoanPlanInfo(String planId) {
        PlainResult<String> result = new PlainResult<>();
        try {
            tbPlanMapper.deleteLoanPlanInfo(planId);
            loanPlanDetailMapper.deleteLoanPlanDetail(planId);
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "delete loan plan.");
        }
        return result.success("delete", "delete loan plan success.");
    }
    // TODO: 19-9-29  信贷计划定制查询可能关联级别

    /**
     * 查询信贷计划
     *
     * @param planId 信贷计划Id
     * @return PlainResult
     */
    @Override
    public PlainResult<TbPlan> selectLoanPlanByPlanId(String planId) {
        PlainResult<TbPlan> result = new PlainResult<>();
        try {
            TbPlan tbPlanDO = tbPlanMapper.selectLoanPlanByPlanId(planId);
            return result.success(tbPlanDO, "select loan plan success.");
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "select loan plan failed.");
        }
    }

    /**
     * 查询信贷计划
     *
     * @param planMonth 信贷计划MOonth
     * @return PlainResult
     */
    @Override
    public ListResult<TbPlan> selectLoanPlanByPlanMonth(String planMonth) {
        ListResult<TbPlan> result = new ListResult<>();
        try {
            List<TbPlan> tbPlanDO = tbPlanMapper.selectLoanPlanByPlanMonth(planMonth);
            return result.success(tbPlanDO, "select loan plan success.");
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "select loan plan failed.");
        }
    }

    /**
     * 信贷计划页面展示数据
     *
     * @return 信贷计划页面展示数据列表
     */
    @Override
    public Map<String, Object> selectAllLoanPlanInfo(String organCode, int pageNo, int pageSize) {
        Map<String, Object> results = new Hashtable<>();
        try {
            TbPlan tbPlanDO = new TbPlan().setPlanOrgan(organCode);
            PageHelper.startPage(pageNo, pageSize, true, true, true);
            List<TbPlan> tbPlanDOList = tbPlanMapper.selectAllLoanPlanInfo(tbPlanDO);
            //返回页面的分页数据
            if (CollectionUtils.isEmpty(tbPlanDOList)) {
                results.put("pager.pageNo", 1);
                results.put("pager.totalRows", 0);
                results.put("rows", new ArrayList<>());
            }
            PageInfo<TbPlan> page = new PageInfo<>(tbPlanDOList);
            results.put("pager.pageNo", page.getPageNum());
            results.put("pager.totalRows", page.getTotal());
            List<TbPlanDTO> tbPlanDTOS = buildPlanDTOList(tbPlanDOList);
            results.put("rows", tbPlanDTOS);
        } catch (TBPlanException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 通过计划ID查找信贷计划详情
     *
     * @param planId 信贷计划批次ID
     * @return PlainResult
     */
    @Override
    public PlainResult<TbPlanDetailDTO> selectPlanDetailByPlanId(String planId) {
        PlainResult<TbPlanDetailDTO> result = new PlainResult<>();
        // try {
        //     TbPlanDetailDO tbPlanDetailDO = loanPlanDetailMapper.selectLoanPlanDetailByPlanId(planId);
        //     TbPlanDetailDTO tbPlanDetailDTO = buildPlanDetailDTO(tbPlanDetailDO);
        //     return result.success(tbPlanDetailDTO, "select loan plan detail by plan id success");
        // } catch (Exception e) {
        //     return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "select loan plan detail by plan id failed");
        // }
        return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "select loan plan detail by plan id failed");
    }


    @Override
    public PlainResult<String> getAdjustPlanDetailInfoByPlanId(String planId) {
        return null;
    }

    /**
     * 信贷计划详情调整参数
     *
     * @param tbPlanAdjustDetail 信贷计划调整详情
     * @return PlainResult
     */
    public PlainResult<String> adjustPlanDetailInfo(TbPlanAdjustDetailDO tbPlanAdjustDetail) {
        PlainResult<String> result = new PlainResult<>();

        try {
            TbPlanAdjustDetailDO queryInfo = new TbPlanAdjustDetailDO();
            queryInfo.setPdRefId(tbPlanAdjustDetail.getPdRefId());
            queryInfo.setPdOrgan(tbPlanAdjustDetail.getPdOrgan());
            TbPlanAdjustDetailDO queryRes = loanPlanAdjustmentMapper.selectLoanPlanDetailByAttr(queryInfo);
            if (Objects.nonNull(queryRes)) {
                loanPlanAdjustmentMapper.updateLoanPlanAdjustmentInfo(tbPlanAdjustDetail);
            } else {
                tbPlanAdjustDetail.setPdCreateTime(LocalDateTime.now());
                loanPlanAdjustmentMapper.insertLoanPlanAdjustmentInfo(tbPlanAdjustDetail);
            }
            return result.success("insert or update", "select loan plan detail by plan id success");
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "modify plan detail by plan id failed");
        }
    }

    @Override
    public List<TbPlanAdjustDetailDO> selectLoanPlanAdjustmentInfoById(String planId) {
        try {
            return loanPlanAdjustmentMapper.selectLoanPlanAdInfoById(planId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /*更新tbplandetail*/
    @Override
    public PlainResult<String> updateLoanPlan(HttpServletRequest request, String operCode, String organCode) {
        PlainResult<String> result = new PlainResult<>();
        String planId = request.getParameter("planId");
        if (planId == null || "".equals(planId)) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "planId not to be null");
        }
        try {
            TbPlan tbPlanDO = tbPlanMapper.selectLoanPlanByPlanId(planId);
            //库中不存在记录
            if (tbPlanDO == null ) {
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update loan plan detail failed");
            } else {

                tbPlanDO.setPlanUpdateTime(BocoUtils.getTime());
                tbPlanDO.setPlanUpdater(operCode);

                String planMonth = tbPlanDO.getPlanMonth();
                List<TbPlanDetail> tbPlanDetailList = buildTbPlanDetailDO(request);
                List<TbPlanDetail> filterList = tbPlanDetailList.stream().filter(item -> {
                    if ("nochanged".equals(item.get__status())) {
                        return false;
                    }
                    item.setPdRefId(planId);
                    return true;
                }).collect(Collectors.toList());

                tbPlanMapper.updateByPK(tbPlanDO);
                // loanPlanDetailMapper.updateLoanPlanDetail(filterList);
                loanPlanDetailMapper.updateplanDetailList(filterList);
                return result.success("update", "update loan plan detail success");
            }
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "insert loan plan failed.");
        }
    }

    @Override
    public List<Map<String,Object>> getFdOrganPlanInfoList(String planId, String thiscode) {
        // HashMap<String, String> queryMap = new HashMap<>();
        // queryMap.put("planId",planId);
        // queryMap.put("thiscode",thiscode);
        //
        // List<Map<String,Object>> list = tbPlanMapper.getFdOrganPlanInfoList(queryMap);
        //
        // List<Map<String, Object>> resultList = orderOran(list);
        //
        //
        // return resultList;
        return null;
    }

    @Override
    public List<Map<String,Object>> getFdOrganPlanInfoListNotPlanId(String thiscode) {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put("thiscode",thiscode);

        List<Map<String,Object>> list = tbPlanMapper.getFdOrganPlanInfoListNotPlanId(queryMap);

        //对机构进行排序
        List<Map<String,Object>> resultList =  orderOran(list);

        return resultList;
    }

    /*对查询出来的机构进行排序*/
    private List<Map<String, Object>> orderOran(List<Map<String, Object>> list) {
        Iterator<Map<String, Object>> iterator = list.iterator();
        // 删除中国邮政储蓄银行 1199340Q 和总行机关 1199521Q  中国邮政储蓄银行股份有限公司 11005293
        // 取出 大连分行 21014952  宁波分行 33000072 厦门分行 35008816 青岛分行  37000013 深圳分行 44008995
        HashMap<String, Map<String,Object>> organMap = new HashMap<>();
        while (iterator.hasNext()) {
            Map<String, Object> map = iterator.next();
            if ("1199340Q".equals(map.get("organcode"))) {
                iterator.remove();
            } else if ("1199521Q".equals(map.get("organcode"))) {
                iterator.remove();
            }  else if ("11005293".equals(map.get("organcode"))) {
                iterator.remove();
            } else if ("21014952".equals(map.get("organcode"))) {
                organMap.put("21014952", map);
                iterator.remove();
            }else if ("33000072".equals(map.get("organcode"))) {
                organMap.put("33000072", map);
                iterator.remove();
            }else if ("35008816".equals(map.get("organcode"))) {
                organMap.put("35008816", map);
                iterator.remove();
            }else if ("37000013".equals(map.get("organcode"))) {
                organMap.put("37000013", map);
                iterator.remove();
            }else if ("44008995".equals(map.get("organcode"))) {
                organMap.put("44008995", map);
                iterator.remove();
            }
        }
        // 按照顺序把取出的分行添加进list
        list.add(organMap.get("21014952"));
        list.add(organMap.get("33000072"));
        list.add(organMap.get("35008816"));
        list.add(organMap.get("37000013"));
        list.add(organMap.get("44008995"));

        return list;
    }

    /*查询列表界面数据*/
    @Override
    public Map<String, Object> selectTbPlanAndTradeParam(String organCode, int pageNo, int pageSize) {
        Map<String, Object> results = new Hashtable<>();
        try {

            HashMap<String, Object> queryMap = new HashMap<>();
            queryMap.put("organCode", organCode);
            queryMap.put("planStatus", 0);

            PageHelper.startPage(pageNo, pageSize, true, true, true);
            List<Map<String,Object>>  resultList = tbPlanMapper.selectTbPlanAndTradeParam(queryMap);

            //返回页面的分页数据
            if (CollectionUtils.isEmpty(resultList)) {
                results.put("pager.pageNo", 1);
                results.put("pager.totalRows", 0);
                results.put("rows", new ArrayList<>());
            }
            PageInfo<Map<String, Object>> mapPageInfo = new PageInfo<>(resultList);
            results.put("pager.pageNo", mapPageInfo.getPageNum());
            results.put("pager.totalRows", mapPageInfo.getTotal());

            results.put("rows", resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }


    /**
     * 构建PlanDetailDTO
     *
     * @param tbPlanDetail PlanDetailDO
     * @return PlanDetailDTO
     */
    private TbPlanDetailDTO buildPlanDetailDTO(TbPlanDetail tbPlanDetail) {
        // TbPlanDetailDTO tbPlanDetailDTO = new TbPlanDetailDTO();
        // tbPlanDetailDTO.setPdOrgan(tbPlanDetail.getPdOrgan());
        // tbPlanDetailDTO.setPdCreateTime(tbPlanDetail.getPdCreateTime());
        // tbPlanDetailDTO.setPdState(LoanStateEnums.STATE_NEW.status);
        // tbPlanDetailDTO.setPdSmallAmountLoanReq(tbPlanDetail.getPdSmallAmountLoanReq());
        // tbPlanDetailDTO.setPdOtherLoanReq(tbPlanDetail.getPdOtherLoanReq());
        // tbPlanDetailDTO.setPdPerBusinessLoanReq(tbPlanDetail.getPdPerBusinessLoanReq());
        // tbPlanDetailDTO.setPdSmallBusinessReq(tbPlanDetail.getPdSmallBusinessReq());
        // tbPlanDetailDTO.setPdSmallBusinessFactorReq(tbPlanDetail.getPdSmallBusinessFactorReq());
        // tbPlanDetailDTO.setPdHouseMortLoanReq(tbPlanDetail.getPdHouseMortLoanReq());
        // tbPlanDetailDTO.setPdOtherConsumeLoanReq(tbPlanDetail.getPdOtherConsumeLoanReq());
        // tbPlanDetailDTO.setPdSupplyLineReq(tbPlanDetail.getPdSupplyLineReq());
        // tbPlanDetailDTO.setPdDomesticTradeFinanceReq(tbPlanDetail.getPdDomesticTradeFinanceReq());
        // tbPlanDetailDTO.setPdInterTradeFinanceReq(tbPlanDetail.getPdInterTradeFinanceReq());
        // tbPlanDetailDTO.setPdOtherCompanyLoanReq(tbPlanDetail.getPdOtherCompanyLoanReq());
        // tbPlanDetailDTO.setPdSanCompanyLoanReq(tbPlanDetail.getPdSanCompanyLoanReq());
        // tbPlanDetailDTO.setPdMergeLoanReq(tbPlanDetail.getPdMergeLoanReq());
        // tbPlanDetailDTO.setPdAllAdvanceLoanReq(tbPlanDetail.getPdAllAdvanceLoanReq());
        // tbPlanDetailDTO.setPdRepostReq(tbPlanDetail.getPdRepostReq());
        // tbPlanDetailDTO.setPdStraightReq(tbPlanDetail.getPdStraightReq());
        // tbPlanDetailDTO.setPdBuyForfeitingRMBReq(tbPlanDetail.getPdBuyForfeitingRMBReq());
        // tbPlanDetailDTO.setPdInterCompanyLoanReq(tbPlanDetail.getPdInterCompanyLoanReq());
        // tbPlanDetailDTO.setPdSpecialFinanceReq(tbPlanDetail.getPdSpecialFinanceReq());
        // tbPlanDetailDTO.setPdPersonOverdraftReq(tbPlanDetail.getPdPersonOverdraftReq());
        // tbPlanDetailDTO.setPdUnitOverdraftReq(tbPlanDetail.getPdUnitOverdraftReq());
        // tbPlanDetailDTO.setPdForeignCurrencyLoanReq(tbPlanDetail.getPdForeignCurrencyLoanReq());
        // tbPlanDetailDTO.setPdBuyForfeitingForeignCurReq(tbPlanDetail.getPdBuyForfeitingForeignCurReq());
        // tbPlanDetailDTO.setPdOtherReq(tbPlanDetail.getPdOtherReq());
        // tbPlanDetailDTO.setPdRefId(tbPlanDetail.getPdRefId());
        // return tbPlanDetailDTO;
        return null;
    }

    /**
     * 构建信贷计划DTO
     *
     * @param tbPlanDOList 信贷计划DO列表
     * @return 信贷计划DTO列表
     */
    private List<TbPlanDTO> buildPlanDTOList(List<TbPlan> tbPlanDOList) {
        List<TbPlanDTO> tbPlanDTOList = new ArrayList<>();
        for (TbPlan tbPlanDO : tbPlanDOList) {
            TbPlanDTO tbPlanDTO = new TbPlanDTO();
            tbPlanDTO.setPlanId(tbPlanDO.getPlanId());
            tbPlanDTO.setPlanOper(tbPlanDO.getPlanOper());
            tbPlanDTO.setPlanOrgan(tbPlanDO.getPlanOrgan());
            tbPlanDTO.setPlanMonth(tbPlanDO.getPlanMonth());
            tbPlanDTO.setPlanStatus(tbPlanDO.getPlanStatus());
            tbPlanDTO.setPlanUpdater(tbPlanDO.getPlanUpdater());
            tbPlanDTO.setPlanUpdateTime(tbPlanDO.getPlanUpdateTime());
            tbPlanDTO.setPlanUnit(tbPlanDO.getPlanUnit());
            tbPlanDTOList.add(tbPlanDTO);
        }
        return tbPlanDTOList;
    }

    public List<FdOrganPlanInfo> initPlanDetailOrganInfo(String planId, List<FdOrgan> fdOrganList) {
        // List<FdOrganPlanInfo> fdOrganPlanInfoList = new ArrayList<>();
        // for (FdOrgan fdOrgan : fdOrganList) {
        //     FdOrganPlanInfo fdOrganPlanInfo = new FdOrganPlanInfo();
        //     fdOrganPlanInfo.setOrganCode(fdOrgan.getThiscode());
        //     fdOrganPlanInfo.setOrganName(fdOrgan.getOrganname());
        //
        //     if (StringUtils.isNotBlank(planId)) {
        //         TbPlanDetailDO tbPlanDetailDO = new TbPlanDetailDO().setPdRefId(planId).setPdOrgan(fdOrgan.getThiscode());
        //         TbPlanDetailDO planDetailDO = loanPlanDetailMapper.selectLoanPlanDetailByAttr(tbPlanDetailDO);
        //         if (Objects.isNull(planDetailDO)) {
        //             fdOrganPlanInfo.setTbPlanDetailDTO(new TbPlanDetailDTO());
        //         } else {
        //             fdOrganPlanInfo.setTbPlanDetailDTO(buildPlanDetailDTO(planDetailDO));
        //         }
        //     } else {
        //         fdOrganPlanInfo.setTbPlanDetailDTO(new TbPlanDetailDTO());
        //     }
        //
        //     fdOrganPlanInfoList.add(fdOrganPlanInfo);
        // }
        // return fdOrganPlanInfoList;
        return null;
    }

    public List<FdOrganPlanInfo> initPlanDetailAdjustOrganInfo(String planId, List<FdOrgan> fdOrganList) {
        // List<FdOrganPlanInfo> fdOrganPlanInfoList = new ArrayList<>();
        // for (FdOrgan fdOrgan : fdOrganList) {
        //     FdOrganPlanInfo fdOrganPlanInfo = new FdOrganPlanInfo().setOrganCode(fdOrgan.getThiscode()).setOrganName(fdOrgan.getOrganname());
        //     TbPlanDetailDO tbPlanDetailDO = new TbPlanDetailDO().setPdRefId(planId).setPdOrgan(fdOrgan.getThiscode());
        //     TbPlanDetailDO planDetailDO = loanPlanDetailMapper.selectLoanPlanDetailByAttr(tbPlanDetailDO);
        //     TbPlanAdjustDetailDO tbPlanAdjustDetail = new TbPlanAdjustDetailDO();
        //     tbPlanAdjustDetail.setPdOrgan(fdOrgan.getThiscode());
        //     tbPlanAdjustDetail.setPdRefId(planId);
        //     TbPlanAdjustDetailDO tbPlanAdjustDetailRes = loanPlanAdjustmentMapper.selectLoanPlanDetailByAttr(tbPlanAdjustDetail);
        //     if (Objects.isNull(planDetailDO)) {
        //         fdOrganPlanInfo.setTbPlanDetailDTO(new TbPlanDetailDTO());
        //     } else {
        //         fdOrganPlanInfo.setTbPlanDetailDTO(buildPlanDetailDTO(planDetailDO));
        //     }
        //     if (Objects.isNull(tbPlanAdjustDetailRes)) {
        //         fdOrganPlanInfo.setTbPlanAdjustDetail(new TbPlanAdjustDetailDO());
        //     } else {
        //         fdOrganPlanInfo.setTbPlanAdjustDetail(tbPlanAdjustDetailRes);
        //     }
        //     fdOrganPlanInfoList.add(fdOrganPlanInfo);
        // }
        // return fdOrganPlanInfoList;
        return null;
    }

    /**
     * @param planId 计划编号
     * @return
     */
    @Override
    public List<String> selectByPlanId(String planId) {

        return tbPlanMapper.selectByPlanId(planId);
    }

    /**
     * @param planMonth 计划月份
     * @return
     */
    @Override
    public List<String> selectByPlanMonth(String planMonth) {
        return tbPlanMapper.selectByPlanMonth(planMonth);
    }
}
