package com.boco.SYS.mapper;

import com.boco.TONY.common.POJO.DO.TbAuditRecordHistDO;

import java.util.List;

/**
 * ����ÿ����ʷ��¼
 *
 * @author tony
 * @describe TbAuditRecordHistMapper
 * @date 2019-10-27
 */
public interface TbAuditRecordHistMapper {
    /**
     * ����������¼
     *
     * @param tbAuditRecordHistDO ������ʷ��¼
     */
    void insertRecordHist(TbAuditRecordHistDO tbAuditRecordHistDO);

    /**
     * ��ȡ��ʷ������¼
     *
     * @param tbAuditRecordHistDO ��ѯ����
     * @return TbAuditRecordHistDO
     */
    List<TbAuditRecordHistDO> getRecordHistByAssignee(TbAuditRecordHistDO tbAuditRecordHistDO);

    /**
     * ��ȡ��ʷ������¼
     *
     * @param tbAuditRecordHistDO ��ѯ����
     * @return TbAuditRecordHistDO
     */
    List<TbAuditRecordHistDO> getReqRecordHistByAssignee(TbAuditRecordHistDO tbAuditRecordHistDO);

    /**
     * ��ȡ��ʷ������¼
     *
     * @param tbAuditRecordHistDO ��ѯ����
     * @return TbAuditRecordHistDO
     */
    List<TbAuditRecordHistDO> getPlanRecordHistByAssignee(TbAuditRecordHistDO tbAuditRecordHistDO);

    /**
     * �Ŵ��ƻ�����״̬
     *
     * @param tbAuditRecordHistDO �Ŵ��ƻ�������¼��ʩ
     */
    void updateRecordHistAuditState(TbAuditRecordHistDO tbAuditRecordHistDO);

    /**
     * �Ŵ��ƻ�����״̬
     *
     * @param tbAuditRecordHistDO �Ŵ��ƻ�������¼��ʩ
     */
    void updateRecordHistPosition(TbAuditRecordHistDO tbAuditRecordHistDO);

    /**
     * ͨ�������²�ѯ
     *
     * @param reqMonth ������
     * @return
     */
    String selectSubmitMonth(String reqMonth);

    /**
     * ͨ������״̬
     *
     * @param reqState ����״̬
     * @return
     */
    String selectSubmitReqState(String reqState);

}
