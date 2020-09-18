package com.boco.SYS.mapper;


import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbReqDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �Ŵ�����¼����ϸ�����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbReqDetailMapper extends GenericMapper<TbReqDetail, Integer> {



    /**
     * ��������ɾ��
     * @param tbReqDetail
     * @return
     */

    int deleteByAttr(TbReqDetail tbReqDetail);

    /**
     * ���������ѯ-id
     *
     * @param tbReqDetail
     * @return
     */
    List<Map<String, Integer>> selectReqdId(TbReqDetail tbReqDetail);

    /**
     * ���������ѯ-��������
     *
     * @param tbReqDetail
     * @return
     */
    List<Map<String, String>> selectReqdRefId(TbReqDetail tbReqDetail);

    /**
     * ͨ�����α�źͻ����Ÿ����Ŵ�����״̬
     */
    void updateReqDetailByReqdRefIdAndOrganCode(TbReqDetail tbReqDetail);

    /**
     * ͨ�������ż����κ���������
     */
    double sumReqAmount(TbReqDetail tbReqDetail);

    /**
     * ͨ�������ż����κ�ͳ�����������ܺ�
     */
    double sumReqExpire(TbReqDetail tbReqDetail);

    /**
     * @Author daice
     * @Description //��ѯ���ύ��¼
     * @Date ����9:51 2019/11/16
     * @Param [map]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getAuditRecordHist(Map<String, Object> map);

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
     * @Description //��ѯ���������Ŵ�����
     * @Date ����6:44 2019/11/16
     * @Param [map]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);

    /**
     * @Author daice
     * @Description //��������id��ȡ���̷�����
     * @Date ����11:16 2019/11/17
     * @Param [id]
     * @return java.lang.String
     **/
    String getStartWorkFlowPeople(String processInstanceId);


    /**
     * ���ݻ������飬��ѯ��������
     * @param param
     * @return
     */
    List<Map<String, Object>> getOrganReq(HashMap<String, Object> param);

}