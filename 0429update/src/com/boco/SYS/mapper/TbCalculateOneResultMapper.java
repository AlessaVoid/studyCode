package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbCalculateOneResult;

import java.util.HashMap;

/**
 * 
 * 
 * TbCalculateOneResult���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbCalculateOneResultMapper extends GenericMapper<TbCalculateOneResult,String>{
    /**
     * @Author: Liujinbo
     * @Description:  �����·�ɾ��������
     * @Date: 2020/3/2

     * @return: void
     *@param map */
    void deleteByMonthAndType(HashMap<String, Object> map);
}