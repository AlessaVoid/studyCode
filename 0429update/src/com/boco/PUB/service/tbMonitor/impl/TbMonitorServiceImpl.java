package com.boco.PUB.service.tbMonitor.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.mapper.TbMonitorMapper;
import com.boco.SYS.service.TbMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbMonitor;
import com.boco.SYS.base.GenericService;

/**
 * 
 * 
 * ������״̬ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbMonitorServiceImpl extends GenericService<TbMonitor,HashMap<String,Object>> implements TbMonitorService {
    //���Զ��巽��ʱʹ��
    @Autowired
    private TbMonitorMapper tbMonitorMapper;


    /*��ȡ���ҳ��Ļ���ֵ*/
    @Override
    public List<Map<String, Object>> selectRunCount() {
        List<Map<String, Object>> monitorList = tbMonitorMapper.selectRunCount();
        return monitorList;
    }
}