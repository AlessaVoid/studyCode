package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbPunishList;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * TbPunishList���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbPunishListMapper extends GenericMapper<TbPunishList, Integer>{
    /**
     * ����������������.
     *
     * @param tbPunishList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    List<Map<String, String>> selectMonth(TbPunishList tbPunishList);

    /**
     * @Author daice
     * @Description //��ѯ���������Ŵ�����
     * @Date ����6:44 2019/11/16
     * @Param [map]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);


    /**
     * @Author daice
     * @Description //��ѯ���������Ŵ�����
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
     * ͨ�����޶������źͻ����Ÿ��³��޶�����״̬
     */
    void updateByIdAndOrganCode(TbPunishList tbPunishList);


    /**
     * ��������������
     *
     * @param tbPunishList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019����10��    	    txn   �½�
     * </pre>
     */
    List<Map<String, Integer>> selectQaId(TbPunishList tbPunishList);


    /**
     * @Author daice
     * @Description //��������id��ȡ���̷�����
     * @Date ����11:16 2019/11/17
     * @Param [id]
     * @return java.lang.String
     **/
    String getStartWorkFlowPeople(String processInstanceId);

}