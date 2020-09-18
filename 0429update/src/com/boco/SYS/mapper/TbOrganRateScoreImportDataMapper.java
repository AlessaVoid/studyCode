package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbOrganRateScoreImportData;

import java.util.HashMap;

/**
 * 
 * 
 * TbOrganRateScoreImportData���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbOrganRateScoreImportDataMapper extends GenericMapper<TbOrganRateScoreImportData, String>{
    /**
     * @Description �����·�ɾ��
     * @Author liujinbo
     * @Date 2020/3/10
     * @param queryMap
     * @Return void
     */
    void deleteByMonth(HashMap<String, Object> queryMap);
}