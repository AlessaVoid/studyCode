package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbOrganRateScoreMonthDetailBackup;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * ���������¶����鱸�ݱ����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbOrganRateScoreMonthDetailBackupMapper extends GenericMapper<TbOrganRateScoreMonthDetailBackup, String>{
    /**
     * @Description ��ѯ�����ʷ���
     * @Author liujinbo
     * @Date 2020/3/5
     * @param
     * @param rateMonth
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectMaxHistoryNumber(String rateMonth);
}