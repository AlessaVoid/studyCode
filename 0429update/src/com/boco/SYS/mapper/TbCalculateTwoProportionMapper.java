package com.boco.SYS.mapper;

import java.util.HashMap;

import com.boco.SYS.entity.TbCalculateTwoProportion;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * ����� Ȩ�ز������ñ����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbCalculateTwoProportionMapper extends GenericMapper<TbCalculateTwoProportion, String>{
    void deleteAll();
}