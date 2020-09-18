package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.entity.TbRptBaseinfoLoankind;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * �±�����������ά�����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbRptBaseinfoLoankindMapper extends GenericMapper<TbRptBaseinfoLoankind,HashMap<String,Object>>{
    /**
     * ��ѯ��ǰ��¼����������������������
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectByDateAndOrgan(HashMap<String, Object> param);

    /**
     * �������ݵ�һ�ֻ���ά��
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectByDateAndComb(HashMap<String, Object> param);

    /**
     * ���ܵ�����ά��
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectAreaData(HashMap<String, Object> param);

    /**
     * ��ѯ��һ����������ص���
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectOtherKeyOrgan(HashMap<String, Object> param);

    /**
     * ��ȡһ�ֻ�������
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectLevelOtherOrganDataByDateAndComb(HashMap<String, Object> param);

    /**
     * һ�ֵ�½ ��ѯ��������
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectOrganDataByDateAndOrgan(HashMap<String, Object> param);

    /**
     * ������ - ���е�¼��ѯһ�ֻ���������
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectDataFlowLevelOneOrgan(HashMap<String, Object> param);

    /**
     * ������ - ���е�¼��ѯһ�ֻ���δ��������
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectDataFlowLevelOneOrganFuture(HashMap<String, Object> param);
    /**
     * ������ - һ�ֵ�¼��ѯ���ֻ���������
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectDataFlowLevelOtherOrgan(HashMap<String, Object> param);

    /**
     * ������ - һ�ֵ�¼��ѯ���ֻ���δ��������
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectDataFlowLevelOtherOrganFuture(HashMap<String, Object> param);

    /**
     * ������ - ��������
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectDataFlowAreaData(HashMap<String, Object> param);

    /**
     * ������ - ����δ������
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectDataFlowAreaDataFuture(HashMap<String, Object> param);

    /**
     * ���ޱ� -���е�¼��ѯһ�ֻ���������
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectTimeLimitLevelOneOrgan(HashMap<String, Object> param);

    /**
     * ���ޱ� - һ�ֵ�¼��ѯ���ֻ���������
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectTimeLimitLevelOtherOrgan(HashMap<String, Object> param);
}