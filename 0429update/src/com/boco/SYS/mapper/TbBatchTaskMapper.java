package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbBatchTask;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * ������������ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbBatchTaskMapper extends GenericMapper<TbBatchTask, String>{

    /**
     * ��ѯ�����б�
     * @param date
     * @return
     */
    List<Map<String, Object>> selectTaskList(String date);
}