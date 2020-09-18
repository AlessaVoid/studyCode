package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbCalculateOneImportData;

import java.util.HashMap;

/**
 * 
 * 
 * TbCalculateOneImportData���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbCalculateOneImportDataMapper extends GenericMapper<TbCalculateOneImportData, String>{
    /**
     * @Description �����·ݺ�����ɾ�������¼
     * @Author liujinbo
     * @Date 2020/3/10
     * @param queryMap
     * @Return void
     */
    void deleteByMonthAndType(HashMap<String, Object> queryMap);
}