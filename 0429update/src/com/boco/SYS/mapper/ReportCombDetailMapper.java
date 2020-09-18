package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbCombDetail;
import com.boco.SYS.entity.TbReportCombDetail;
import com.boco.TONY.biz.loancomb.exception.LoanCombDetailException;

import java.util.List;

/**
 * @author tony
 * @describe LeanComposeMapper
 * @date 2019-09-17
 */
public interface ReportCombDetailMapper extends GenericMapper<TbReportCombDetail, String> {
    /**
     * 通过combCode获取贷种组合信息
     *
     * @param combCode 贷种组合编码
     * @return
     * @throws LoanCombDetailException
     */
    List<String> getLoanComposeInfoByCombCode(String combCode) throws LoanCombDetailException;

    /**
     * 通过combCode获取自己的下级贷种组合信息
     *
     * @param combCode 贷种组合编码
     * @return
     * @throws LoanCombDetailException
     */
    List<String> getSelectedLoanComposeInfoByCombCode(String combCode) throws LoanCombDetailException;

    /**
     * 插入贷种组合下级产品
     *
     * @param loanComposeDetailDO 贷种组合详情
     * @throws LoanCombDetailException
     */
    void insertLoanComposeChildProduct(TbCombDetail loanComposeDetailDO) throws LoanCombDetailException;

    /**
     *
     */
    /**
     * 删除贷种组合
     *
     * @param loanComposeDetailDO 贷种组合详情
     * @throws LoanCombDetailException
     */
    void updateLoanComposeProductInfo(TbCombDetail loanComposeDetailDO) throws LoanCombDetailException;

    void deleteLoanComposeProductInfo(TbCombDetail loanComposeDetailDO) throws LoanCombDetailException;


}
