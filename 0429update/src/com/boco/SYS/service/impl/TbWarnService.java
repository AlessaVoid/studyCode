package com.boco.SYS.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.SYS.mapper.TbWarnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbWarn;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.service.ITbWarnService;

/**
 * 
 * 
 * Ԥ���߱�ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbWarnService extends GenericService<TbWarn, Integer> implements ITbWarnService{

    @Autowired
    private TbWarnMapper tbWarnMapper;
    /**
     * ��������������.
     *
     * @param tbWarn
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectWarnId(TbWarn tbWarn) {
        List<Map<String, Integer>> list = tbWarnMapper.selectWarnId(tbWarn);
        return list;
    }

    /**
     * ���������������.
     *
     * @param tbWarn
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectWarnName(TbWarn tbWarn) {
        List<Map<String, String>> list = tbWarnMapper.selectWarnName(tbWarn);
        return list;
    }

    @Override
    public List<Map<String, String>> selectWarnNameByOrgan(TbWarn tbWarn) {
        List<Map<String, String>> list = tbWarnMapper.selectWarnNameByOrgan(tbWarn);
        return list;
    }

    /**
     * ��������������.
     *
     * @param tbWarn
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectWarnOrgan(TbWarn tbWarn) {
        List<Map<String, String>> list = tbWarnMapper.selectWarnOrgan(tbWarn);
        return list;
    }

    @Override
    public List<Map<String, String>> selectWarnOrgan1(Map<String, String> map) {

        List<Map<String, String>> list = tbWarnMapper.selectWarnOrgan1(map);
        return list;
    }

    //���Զ��巽��ʱʹ��
	//@Autowired
	//private TbWarnMapper mapper;
}