package com.boco.PUB.service.tbSystemCtrlStatus;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbSystemCtrlStatus;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * TbSystemCtrlStatusҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbSystemCtrlStatusService extends IGenericService<TbSystemCtrlStatus, String>{
    /*��ѯ�б�ҳ*/
    List<Map<String, Object>> selectTbSystemCtrlStatus(TbSystemCtrlStatus tbSystemCtrlStatus);
}