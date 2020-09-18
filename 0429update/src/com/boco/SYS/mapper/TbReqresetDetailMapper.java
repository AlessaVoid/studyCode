package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbReqresetDetail;
import com.boco.SYS.base.GenericMapper;

/**
 * �������뱨��Ҫ��¼����ϸ�����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbReqresetDetailMapper extends GenericMapper<TbReqresetDetail, Integer> {




    int deleteByAttr(TbReqresetDetail tbReqresetDetail);

    /**
     * ���������ѯ-id
     *
     * @param tbReqresetDetail
     * @return
     */
    List<Map<String, Integer>> selectReqdId(TbReqresetDetail tbReqresetDetail);

    /**
     * ���������ѯ-��������
     *
     * @param tbReqresetDetail
     * @return
     */
    List<Map<String, String>> selectReqdRefId(TbReqresetDetail tbReqresetDetail);

    /**
     * ͨ�����α�źͻ����Ÿ����Ŵ�����������뱨��״̬
     */
    void updateReqDetailByReqdRefIdAndOrganCode(TbReqresetDetail tbReqresetDetail);


    /**
     * @return java.util.List<java.util.Map   <   java.lang.String   ,   java.lang.Object>>
     * @Author daice
     * @Description //��ѯ���ύ��¼
     * @Date ����9:51 2019/11/16
     * @Param [map]
     **/
    List<Map<String, Object>> getAuditRecordHist(Map<String, Object> map);

    /**
     * @return java.util.List<java.util.Map   <   java.lang.String   ,   java.lang.Object>>
     * @Author daice
     * @Description //��ѯ���������Ŵ�����
     * @Date ����2:18 2019/11/16
     * @Param [map]
     **/
    List<Map<String, Object>> getPendingAppReq(Map<String, Object> map);

    /**
     * @return java.util.List<java.util.Map   <   java.lang.String   ,   java.lang.Object>>
     * @Author daice
     * @Description //��ѯ���������Ŵ�����
     * @Date ����6:44 2019/11/16
     * @Param [map]
     **/
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //��������id��ȡ���̷�����
     * @Date ����11:16 2019/11/17
     * @Param [id]
     **/
    String getStartWorkFlowPeople(String processInstanceId);
}