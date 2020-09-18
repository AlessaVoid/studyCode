package com.boco.SYS.service.impl;

import java.util.HashMap;

import com.boco.SYS.mapper.FdBusinessDateMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.FdBusinessDate;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.service.IFdBusinessDateService;

/**
 * 
 * 
 * FdBusinessDateҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ���      �����½�
 * </pre>
 */
@Service
public class FdBusinessDateService extends GenericService<FdBusinessDate,HashMap<String,Object>> implements IFdBusinessDateService{
	//���Զ��巽��ʱʹ��
	@Autowired
	private FdBusinessDateMapper mapper;
	@Autowired
	private DicCache dicCache;
	HashMap<String,Object> map = new HashMap<String, Object>();
    /**
     * ��ͨҵ��ʱ��
     * @return
     */
    @Override
    public String getCommonDateDetails() {
        map.put("busiTypeCode","02");
        map.put("subBusiType","00");
        return mapper.selectByPK(map).getCurDate();
    }

    /**
     * �����Ͳ�Ʒʱ��
     * @return
     */
    @Override
    public String getCurrencyDateDetails() {
        map.put("busiTypeCode","02");
        map.put("subBusiType","01");
        return mapper.selectByPK(map).getCurDate();
    }
    /**
     * ������״̬��ʶ
     */
    @Override
    public String getBusinessFlag(){
        map.put("busiTypeCode","02");
        map.put("subBusiType","00");
        return mapper.selectByPK(map).getBusiFlag();
    }

	@Override
	public FdBusinessDate getCommonFdBusinessDate() {
		// TODO Auto-generated method stub
        map.put("busiTypeCode","02");
        map.put("subBusiType","00");
		return mapper.selectByPK(map);
	}

	@Override
	public FdBusinessDate getCurrencyFdBusinessDate() {
		// TODO Auto-generated method stub
        map.put("busiTypeCode","02");
        map.put("subBusiType","01");
		return mapper.selectByPK(map);
	}
	/**
	 * TODO �����ֵ�����.
	 */
	@Override
	public void RestartParam() {
		dicCache.start();
	}
}