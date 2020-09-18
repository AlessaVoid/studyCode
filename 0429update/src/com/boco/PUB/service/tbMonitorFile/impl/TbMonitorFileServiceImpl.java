package com.boco.PUB.service.tbMonitorFile.impl;

import com.boco.PUB.service.tbMonitorFile.TbMonitorFileService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbMonitorFile;
import com.boco.SYS.mapper.TbMonitorFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 
 * �ļ����ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbMonitorFileServiceImpl extends GenericService<TbMonitorFile,HashMap<String,Object>> implements TbMonitorFileService {

    //���Զ��巽��ʱʹ��
    @Autowired
    private TbMonitorFileMapper tbMonitorFileMapper;


    @Override
    public List<TbMonitorFile> selectTbMonitorFile(TbMonitorFile tbMonitorFile) {
        List<TbMonitorFile> list = tbMonitorFileMapper.selectTbMonitorFile(tbMonitorFile);
        return list;
    }

}