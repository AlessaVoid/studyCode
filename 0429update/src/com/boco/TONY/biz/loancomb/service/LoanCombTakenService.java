package com.boco.TONY.biz.loancomb.service;

import com.boco.TONY.biz.loancomb.POJO.DTO.combtaken.CombTakenDetailDTO;
import com.boco.TONY.biz.loancomb.exception.LoanCombException;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;

/**
 * �Ŵ����ռ�÷����
 *
 * @author tony
 * @describe LoanCombTakenService
 * @date 2019-10-27
 */
public interface LoanCombTakenService {
    /**
     * ���ռ��
     *
     * @param gridData       ռ�ù���
     * @param parentCombCode �����������
     * @param takenType      ����ռ������
     * @return ִ�н��
     * @throws LoanCombException ����ռ���쳣
     */
    PlainResult<String> takeLoanCombInfo(String gridData, String parentCombCode, int takenType) throws LoanCombException;

    /**
     * ͨ����id��ѯ��������б�
     *
     * @param parentId ������ϸ�id
     * @return ��������б�
     */
    ListResult<CombTakenDetailDTO> selectLoanCombTakenByParentId(String parentId);

    Integer selectInterTakentype();
    /**
     * ͨ��parentype��ѯtakenype
     */
    Integer getTakenTypeByCombParent(String combCode);

}
