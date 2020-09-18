package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbSystemCtrlStatus;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * TbSystemCtrlStatus���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbSystemCtrlStatusMapper extends GenericMapper<TbSystemCtrlStatus, String>{
    List<Map<String, Object>> selectTbSystemCtrlStatus(TbSystemCtrlStatus tbSystemCtrlStatus);
}