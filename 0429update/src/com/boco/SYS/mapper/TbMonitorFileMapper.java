package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.entity.TbMonitorFile;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * �ļ�������ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbMonitorFileMapper extends GenericMapper<TbMonitorFile,HashMap<String,Object>>{
    /**
     * ��ѯ�ļ����
     * @param tbMonitorFile
     * @return
     */
    List<TbMonitorFile> selectTbMonitorFile(TbMonitorFile tbMonitorFile);
}