package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbPlanadj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * �Ŵ��ƻ��������α����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbPlanadjMapper extends GenericMapper<TbPlanadj, String>{
    /**
     * @Description ����������ѯ�Ŵ��ƻ���������
     * @Author liujinbo
     * @Date 2019/11/23
     * @param queryMap
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectPlanadj(HashMap<String, Object> queryMap);

    /**
     * @Description ��ѯ���ύ���Ŵ��ƻ�����
     * @Author liujinbo
     * @Date 2019/11/25
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getAuditRecordHist(Map<String, Object> map);

    /**
     * @Description ��ѯ���������Ŵ��ƻ�����
     * @Author liujinbo
     * @Date 2019/11/26
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getPendingAppReq(Map<String, Object> map);

    /**
     * @Description ��ѯ���������Ŵ��ƻ�����
     * @Author liujinbo
     * @Date 2019/11/26
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);


    /** ����refIdɾ��planadjDetail
     * @Description
     * @Author liujinbo
     * @Date 2019/11/28
     * @param planadjId
     * @Return void
     */
    void deleteTbPlanadjDetailBYRefId(String planadjId);

    /**
     * @Author daice
     * @Description //���ݼ����ȡ���������Ϣ
     * @Date ����1:42 2019/11/29
     * @Param [level]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getCombList(int level);

    /**
     * @Author daice
     * @Description //��ȡָ���·ݵ��Ŵ��ƻ�
     * @Date ����4:57 2019/11/29
     * @Param [month]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *
     * @param month*/
    List<Map<String, Object>> getCreditPlanDetail(HashMap<String, Object> month);

    /**
     * @Author daice
     * @Description //��ȡ�Ŵ���������
     * @Date ����4:57 2019/11/29
     * @Param [month]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *
     * @param month*/
    List<Map<String, Object>> getReqDetail(Map<String, Object> month);

    /**
     * @Author daice
     * @Description //��ȡ�Ŵ���������
     * @Date ����4:57 2019/11/29
     * @Param [month]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *
     * @param month*/
    List<Map<String, Object>> getLineReqDetail(Map<String, Object> month);

    void updatePlanadj(TbPlanadj tbPlanadj);
}