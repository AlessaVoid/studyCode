package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbCalculateOneProportionHistory;

import java.util.HashMap;

/**
 * 
 * 
 * ���� Ȩ����ʷ�����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbCalculateOneProportionHistoryMapper extends GenericMapper<TbCalculateOneProportionHistory, String>{
    /**
     * @Description �����·ݺ�����ɾ��������ʷ������
     * @Author liujinbo
     * @Date 2020/3/6
     * @param map
     * @Return void
     */
    void deleteByMonthAndType(HashMap<String, Object> map);
}