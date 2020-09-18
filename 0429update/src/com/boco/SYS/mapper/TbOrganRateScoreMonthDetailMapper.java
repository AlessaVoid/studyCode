package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbOrganRateScoreMonthDetail;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 
 * ���������¶���������ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbOrganRateScoreMonthDetailMapper extends GenericMapper<TbOrganRateScoreMonthDetail, String>{
    /**
     * @Author: Liujinbo
     * @Description:  �����·ݺ����Ͳ�ѯ���������
     * @Date: 2020/2/4
     * @param queryMap1 :
     * @return: java.util.List<com.boco.SYS.entity.TbOrganRateScoreMonthDetail>
     **/
    List<TbOrganRateScoreMonthDetail> selectOrganRateScoreDetailByMonthAndType(HashMap<String, Object> queryMap1);
}