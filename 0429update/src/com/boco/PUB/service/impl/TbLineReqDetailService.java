package com.boco.PUB.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.PUB.service.ITbLineReqDetailService;
import com.boco.SYS.mapper.TbLineReqDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbLineReqDetail;
import com.boco.SYS.base.GenericService;

/**
 * 
 * 
 * ���������¼�����ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbLineReqDetailService extends GenericService<TbLineReqDetail, Integer> implements ITbLineReqDetailService {
	//���Զ��巽��ʱʹ��
	@Autowired
	private TbLineReqDetailMapper tbLineReqDetailMapper;



    /**
     * ����������������.
     *
     * @param tbLineReqDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> showLineReqMonth(TbLineReqDetail tbLineReqDetail) {
        List<Map<String, String>> list = tbLineReqDetailMapper.showLineReqMonth(tbLineReqDetail);
        return list;
    }

}