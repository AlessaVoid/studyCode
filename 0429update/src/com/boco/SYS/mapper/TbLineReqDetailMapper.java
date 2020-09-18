package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbLineReqDetail;
import com.boco.SYS.base.GenericMapper;

/**
 * ���������¼��������ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbLineReqDetailMapper extends GenericMapper<TbLineReqDetail, Integer> {



    /**
     * ����������������---
     *
     * @param tbLineReqDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
     List<Map<String, String>> showLineReqMonth(TbLineReqDetail tbLineReqDetail);


    /**
     * ��������ɾ��
     *
     * @param tbLineReqDetail
     * @return
     */

    int deleteByAttr(TbLineReqDetail tbLineReqDetail);


    /**
     * ͨ�����α�źͻ����Ÿ����Ŵ�����״̬
     */
    void updateReqDetailByReqdRefIdAndOrganCode(TbLineReqDetail tbLineReqDetail);

    /**
     * ͨ�������ż����κ���������
     */
    double sumReqAmount(TbLineReqDetail tbLineReqDetail);

    /**
     * ͨ�������ż����κ�ͳ�����������ܺ�
     */
    double sumReqExpire(TbLineReqDetail tbLineReqDetail);

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author txn
     * @Description //��ѯ���ύ��¼
     * @Date ����9:51 2019/11/16
     * @Param [map]
     **/
    List<Map<String, Object>> getAuditRecordHist(Map<String, Object> map);

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author txn
     * @Description //��ѯ���������Ŵ�����
     * @Date ����2:18 2019/11/16
     * @Param [map]
     **/
    List<Map<String, Object>> getPendingAppReq(Map<String, Object> map);

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author txn
     * @Description //��ѯ���������Ŵ�����
     * @Date ����6:44 2019/11/16
     * @Param [map]
     **/
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);

    /**
     * @return java.lang.String
     * @Author txn
     * @Description //��������id��ȡ���̷�����
     * @Date ����11:16 2019/11/17
     * @Param [id]
     **/
    String getStartWorkFlowPeople(String processInstanceId);


    /**
     * ���ݻ������飬��ѯ��������
     * @param param
     * @return
     */
    List<Map<String, Object>> getOrganLineReq(HashMap<String, Object> param);


}