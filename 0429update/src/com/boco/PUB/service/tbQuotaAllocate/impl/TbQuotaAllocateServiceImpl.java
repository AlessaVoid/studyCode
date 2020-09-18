package com.boco.PUB.service.tbQuotaAllocate.impl;

import com.boco.PM.service.IFdOrganService;
import com.boco.PUB.service.tbQuotaAllocate.TbQuotaAllocateService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.TbPlanMapper;
import com.boco.SYS.mapper.TbPlanadjMapper;
import com.boco.SYS.mapper.TbQuotaAllocateMapper;
import com.boco.SYS.util.WebContextUtil;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 额度分配表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class TbQuotaAllocateServiceImpl extends GenericService<TbQuotaAllocate, String> implements TbQuotaAllocateService {
    @Autowired
    private TbQuotaAllocateMapper tbQuotaAllocateMapper;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IWebLoanCombService loanCombService;
    @Autowired
    private TbPlanadjMapper planadjMapper;
    @Autowired
    private TbPlanMapper tbPlanMapper;

    /*计划调整后更新额度表*/
    @Override
    public void updatePlanTbQuota(List<TbPlanadjDetail> tbPlanadjDetailList, int planType, String planadjId) throws Exception {

        String organlevel = WebContextUtil.getSessionOrgan().getOrganlevel();

        TbPlanadj tbPlanadj = planadjMapper.selectByPK(planadjId);
        TbPlan tbPlanParam = new TbPlan();
        tbPlanParam.setPlanMonth(tbPlanadj.getPlanadjMonth());
        tbPlanParam.setPlanOrgan(tbPlanadj.getPlanadjOrgan());
        tbPlanParam.setPlanType(planType);
        TbPlan tbPlan = tbPlanMapper.selectByAttr(tbPlanParam).get(0);

        if (tbPlan.getQuotaStatus() != null && tbPlan.getQuotaStatus() == 0) {
            //额度跑批之后 才更新额度表

            for (TbPlanadjDetail tbPlanadjDetail : tbPlanadjDetailList) {
                TbQuotaAllocate tbQuotaAllocateQuery = new TbQuotaAllocate();
                //月份，机构，贷种，计划类型，机构级别
                tbQuotaAllocateQuery.setPaMonth(tbPlanadjDetail.getPlanadjdMonth());
                tbQuotaAllocateQuery.setPaOrgan(tbPlanadjDetail.getPlanadjdOrgan());
                tbQuotaAllocateQuery.setPaProdCode(tbPlanadjDetail.getPlanadjdLoanType());
                tbQuotaAllocateQuery.setQuotaType(planType);
                tbQuotaAllocateQuery.setQuotaLevel(Integer.parseInt(organlevel));

                List<TbQuotaAllocate> tbQuotaAllocateList = tbQuotaAllocateMapper.selectByAttr(tbQuotaAllocateQuery);

                if (tbQuotaAllocateList == null || tbQuotaAllocateList.size() == 0) {
                    //新增的贷种，需要新增额度表
                    TbQuotaAllocate quotaAllocate = new TbQuotaAllocate();
                    quotaAllocate.setPaMonth(tbPlanadjDetail.getPlanadjdMonth());
                    quotaAllocate.setPaOrgan(tbPlanadjDetail.getPlanadjdOrgan());
                    quotaAllocate.setPaProdCode(tbPlanadjDetail.getPlanadjdLoanType());
                    quotaAllocate.setPaQuotaAvail(tbPlanadjDetail.getPlanadjdAdjedAmount());
                    quotaAllocate.setQuotaType(planType);
                    quotaAllocate.setQuotaLevel(Integer.parseInt(organlevel));
                    quotaAllocate.setPaId(quotaAllocate.getPaMonth() + "_" + quotaAllocate.getPaOrgan() + "_" + quotaAllocate.getQuotaType() + quotaAllocate.getQuotaLevel() + "_" + quotaAllocate.getPaProdCode());
                    tbQuotaAllocateMapper.insertEntity(quotaAllocate);

                } else {
                    if (tbPlanadjDetail.getPlanadjdAdjAmount().compareTo(BigDecimal.ZERO) != 0) {
                        for (TbQuotaAllocate tbQuotaAllocate : tbQuotaAllocateList) {
                            //计算调整后的值
                            BigDecimal paQuotaAvail = tbQuotaAllocate.getPaQuotaAvail().add(tbPlanadjDetail.getPlanadjdAdjAmount().multiply(new BigDecimal("10000")));
                            // if (paQuotaAvail.compareTo(BigDecimal.ZERO) == -1) {
                            //     throw new RuntimeException("调整完后额度不能为负数");
                            // }
                            tbQuotaAllocate.setPaQuotaAvail(paQuotaAvail);
                            tbQuotaAllocateMapper.updateByPK(tbQuotaAllocate);
                        }
                    }
                }
            }
        }



    }

    /*查询额度列表页*/
    @Override
    public Map<String, Object> getQuotaAllocate(int pageNo, int pageSize, HttpServletRequest request, TbQuotaAllocate tbQuotaAllocate)throws Exception  {
        Map<String, Object> results = new Hashtable<>();
        try {
            PageHelper.startPage(pageNo, pageSize, true, true, true);
            List<TbQuotaAllocate> tbQuotaAllocateList = tbQuotaAllocateMapper.selectByAttr(tbQuotaAllocate);
            // List<TbQuotaAllocate> tbQuotaAllocateList = tbQuotaAllocateMapper.selectByLikeAttr(tbQuotaAllocate);

            ArrayList<Map<String,Object>> resultList = new ArrayList<>();

            //查询贷种名称
            HashMap<String, String> combMap = new HashMap<>();
            List<Map<String, Object>> combList = loanCombService.selectComb(new HashMap<>());
            for (Map<String, Object> map : combList) {
                combMap.put(map.get("combcode").toString(), map.get("combname").toString());
            }

            //机构名称
            HashMap<String, String> organMap = new HashMap<>();
            FdOrgan fdOrganQuery = new FdOrgan();
            //组装返回值
            for (TbQuotaAllocate quotaAllocate : tbQuotaAllocateList) {
                String s = organMap.get(quotaAllocate.getPaOrgan());
                if (s == null) {
                    fdOrganQuery.setThiscode(quotaAllocate.getPaOrgan());
                    List<FdOrgan> fdOrganList = fdOrganService.selectByAttr(fdOrganQuery);
                    FdOrgan fdOrgan = new FdOrgan();
                    if (fdOrganList != null && fdOrganList.size() != 0) {
                        fdOrgan = fdOrganService.selectByAttr(fdOrganQuery).get(0);
                    }
                    organMap.put(quotaAllocate.getPaOrgan(), fdOrgan.getOrganname());
                }

                HashMap<String, Object> map = new HashMap<>();
                map.put("paId", quotaAllocate.getPaId());
                map.put("paMonth", quotaAllocate.getPaMonth());
                map.put("paOrgan", quotaAllocate.getPaOrgan());
                map.put("paOrganName", organMap.get(quotaAllocate.getPaOrgan()));
                map.put("paProdCode", quotaAllocate.getPaProdCode());
                map.put("paProdCodeName", combMap.get(quotaAllocate.getPaProdCode()));
                map.put("paQuotaAvail", quotaAllocate.getPaQuotaAvail());
                map.put("quotaType", quotaAllocate.getQuotaType());

                resultList.add(map);
            }

            //返回页面的分页数据
            if (CollectionUtils.isEmpty(tbQuotaAllocateList)) {
                results.put("pager.pageNo", 1);
                results.put("pager.totalRows", 0);
                results.put("rows", new ArrayList<>());
            }
            PageInfo<TbQuotaAllocate> mapPageInfo = new PageInfo<>(tbQuotaAllocateList);
            results.put("pager.pageNo", mapPageInfo.getPageNum());
            results.put("pager.totalRows", mapPageInfo.getTotal());

            results.put("rows", resultList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

}