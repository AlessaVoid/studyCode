package com.boco.TONY.biz.loancomb.service;

import com.boco.TONY.biz.loancomb.exception.LoanCombException;
import com.boco.TONY.common.PlainResult;

import java.util.List;

/**
 * �Ŵ���Ʒ����ҵ���߼���
 *
 * @author tony
 * @describe IWebLoanDetailService
 * @date 2019-09-18
 */
public interface IWebLoanCombDetailService {
    /**
     * ͨ��id��ȡ���������Ϣ
     *
     * @param combCode ������ϱ���
     * @return �������id�б�
     * @throws LoanCombException ��������쳣
     */
    List<String> getLoanCombDetailInfoByCombCode(String combCode) throws LoanCombException;

    /**
     * �����������ӽڵ�
     *
     * @param combId    ������ϱ���
     * @param productId ��Ʒ��ϱ���
     * @return ִ�н��
     * @throws LoanCombException ��������쳣
     */
    @SuppressWarnings("unused")
    PlainResult<String> insertLoanCombChildProduct(String combId, String productId) throws LoanCombException;

    /**
     * ���´������������Ϣ
     *
     * @param combId    ������ϱ���
     * @param productId �¼���������б�
     * @return PlainResult
     * @throws LoanCombException ��������쳣
     */
    @SuppressWarnings("unused")
    PlainResult<String> updateLoanCombProductDetailInfo(String combId, String productId) throws LoanCombException;

}
