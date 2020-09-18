package com.boco.TONY.biz.loancomb.service;

import com.boco.TONY.biz.loancomb.exception.LoanCombException;
import com.boco.TONY.common.PlainResult;

import java.util.List;

/**
 * 信贷产品详情业务逻辑层
 *
 * @author tony
 * @describe IWebLoanDetailService
 * @date 2019-09-18
 */
public interface IWebLoanCombDetailService {
    /**
     * 通过id获取贷种组合信息
     *
     * @param combCode 贷种组合编码
     * @return 贷种组合id列表
     * @throws LoanCombException 贷种组合异常
     */
    List<String> getLoanCombDetailInfoByCombCode(String combCode) throws LoanCombException;

    /**
     * 插入贷种组合子节点
     *
     * @param combId    贷种组合编码
     * @param productId 产品组合编码
     * @return 执行结果
     * @throws LoanCombException 贷种组合异常
     */
    @SuppressWarnings("unused")
    PlainResult<String> insertLoanCombChildProduct(String combId, String productId) throws LoanCombException;

    /**
     * 更新贷种组合详情信息
     *
     * @param combId    贷种组合编码
     * @param productId 下级贷种组合列表
     * @return PlainResult
     * @throws LoanCombException 贷种组合异常
     */
    @SuppressWarnings("unused")
    PlainResult<String> updateLoanCombProductDetailInfo(String combId, String productId) throws LoanCombException;

}
