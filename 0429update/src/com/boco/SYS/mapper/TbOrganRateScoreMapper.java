package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbOrganRateScore;

import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * �����������α����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbOrganRateScoreMapper extends GenericMapper<TbOrganRateScore, String>{

    /**
     * @Author: Liujinbo
     * @Description:  ��ѯ���ύ������
     * @Date: 2020/2/7
     * @param map :
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getAuditRecordHist(Map<String, Object> map);

    /**
     * @Author: Liujinbo
     * @Description:  ��ѯ������������
     * @Date: 2020/2/7
     * @param map :
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getPendingAppReq(Map<String, Object> map);

    /**
     * @Author: Liujinbo
     * @Description:  ��ѯ������������
     * @Date: 2020/2/7
     * @param map :
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getApprovedRecord(Map<String, Object> map);

    /**
     * @Author: Liujinbo
     * @Description:  ��ѯ���е���С����������  1,2,3��
     * @Date: 2020/2/13

     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> selectLoanKindOfTwo();
}