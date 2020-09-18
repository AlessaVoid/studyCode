package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbCombDetail;
import com.boco.TONY.biz.loancomb.exception.LoanCombDetailException;

import java.util.List;

/**
 * @author tony
 * @describe LeanComposeMapper
 * @date 2019-09-17
 */
public interface LoanCombDetailMapper  extends GenericMapper<TbCombDetail, String> {
    /**
     * ͨ��combCode��ȡ���������Ϣ
     *
     * @param combCode ������ϱ���
     * @return
     * @throws LoanCombDetailException
     */
    List<String> getLoanComposeInfoByCombCode(String combCode) throws LoanCombDetailException;

    /**
     * ͨ��combCode��ȡ�Լ����¼����������Ϣ
     *
     * @param combCode ������ϱ���
     * @return
     * @throws LoanCombDetailException
     */
    List<String> getSelectedLoanComposeInfoByCombCode(String combCode) throws LoanCombDetailException;

    /**
     * �����������¼���Ʒ
     *
     * @param loanComposeDetailDO �����������
     * @throws LoanCombDetailException
     */
    void insertLoanComposeChildProduct(TbCombDetail loanComposeDetailDO) throws LoanCombDetailException;

    /**
     *
     */
    /**
     * ɾ���������
     *
     * @param loanComposeDetailDO �����������
     * @throws LoanCombDetailException
     */
    void updateLoanComposeProductInfo(TbCombDetail loanComposeDetailDO) throws LoanCombDetailException;

    void deleteLoanComposeProductInfo(TbCombDetail loanComposeDetailDO) throws LoanCombDetailException;


}
