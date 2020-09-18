package com.boco.PUB.service.tbLoanInfo.impl;

import com.boco.PUB.service.tbLoanInfo.TbLoanInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbLoanInfo;
import com.boco.SYS.mapper.TbLoanInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * ��ݱ�ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbLoanInfoServiceImpl extends GenericService<TbLoanInfo,HashMap<String,Object>> implements TbLoanInfoService {

    @Autowired
    private TbLoanInfoMapper tbLoanInfoMapper;

    /*��ѯ���*/
    @Override
    public List<Map<String, Object>> selectTbLoanInfo(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> list = tbLoanInfoMapper.selectTbLoanInfo(paramMap);
        return list;
    }

}