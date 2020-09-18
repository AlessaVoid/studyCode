package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbPunishResult;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * TbPunishResult���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbPunishResultMapper extends GenericMapper<TbPunishResult, Integer>{

    /**
     * ��������ɾ��
     * @param tbPunishResult
     * @return
     */

    int deleteByAttr(TbPunishResult tbPunishResult);


    /**
     *
     *
     * TODO ���������޸�һ����¼.
     *
     * @param tbPunishResult
     * @return
     *
     * <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��2��    	    ����    �½�
     * </pre>
     */
    public int updateByListId(TbPunishResult tbPunishResult) ;


    /**
     * @Author daice
     * @Description //��ѯ�������ķ�Ϣ���
     * @Date ����6:44 2019/11/16
     * @Param [map]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);


    /**
     * @Author daice
     * @Description //��ѯ�������ķ�Ϣ���
     * @Date ����2:18 2019/11/16
     * @Param [map]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getPendingAppReq(Map<String, Object> map);


    /**
     * @Author daice
     * @Description //��ѯ���ύ��¼
     * @Date ����9:51 2019/11/16
     * @Param [map]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getAuditRecordHist(Map<String, Object> map);



    /**
     * ͨ����Ϣ��������źͻ����Ÿ��·�Ϣ�������״̬
     */
    void updateQuotaApplyByQaIdAndOrganCode(TbPunishResult tbPunishResult);


    /**
     * ��������������
     *
     * @param tbPunishResult
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019����10��    	    txn   �½�
     * </pre>
     */
    List<Map<String, Integer>> selectQaId(TbPunishResult tbPunishResult);

    /**
     * ����������������.
     *
     * @param tbPunishResult
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    List<Map<String, String>> selectQaMonth(TbPunishResult tbPunishResult);

    /**
     * @Author daice
     * @Description //��������id��ȡ���̷�����
     * @Date ����11:16 2019/11/17
     * @Param [id]
     * @return java.lang.String
     **/
    String getStartWorkFlowPeople(String processInstanceId);


    List<TbPunishResult> selectByListId(TbPunishResult tbPunishResult);


}