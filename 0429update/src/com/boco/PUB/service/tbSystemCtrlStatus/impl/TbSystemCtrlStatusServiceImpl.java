package com.boco.PUB.service.tbSystemCtrlStatus.impl;

import com.boco.PUB.service.tbSystemCtrlStatus.TbSystemCtrlStatusService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbSystemCtrlStatus;
import com.boco.SYS.mapper.TbSystemCtrlStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * TbSystemCtrlStatusҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class TbSystemCtrlStatusServiceImpl extends GenericService<TbSystemCtrlStatus, String> implements TbSystemCtrlStatusService {
    //���Զ��巽��ʱʹ��
    @Autowired
    private TbSystemCtrlStatusMapper tbSystemCtrlStatusMapper;

    @Override
    public List<Map<String, Object>> selectTbSystemCtrlStatus(TbSystemCtrlStatus tbSystemCtrlStatus) {
        List<Map<String, Object>> list = tbSystemCtrlStatusMapper.selectTbSystemCtrlStatus(tbSystemCtrlStatus);
        return list;

    }

}