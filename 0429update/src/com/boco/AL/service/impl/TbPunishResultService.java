package com.boco.AL.service.impl;


import com.boco.AL.service.ITbPunishResultService;
import com.boco.PUB.service.loanQuotaApply.impl.LoanQuotaApplyService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbPunishResult;
import com.boco.SYS.mapper.TbPunishResultMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TbPunishResultҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbPunishResultService extends GenericService<TbPunishResult, Integer> implements ITbPunishResultService {
    //���Զ��巽��ʱʹ��

    //���Զ��巽��ʱʹ��
    @Autowired
    private TbPunishResultMapper tbPunishResultMapper;
    public static Logger logger = Logger.getLogger(LoanQuotaApplyService.class);


    @Override
    public int updateByListId(TbPunishResult tbPunishResult) {

        return tbPunishResultMapper.updateByListId(tbPunishResult);

    }


    /**
     * ��������ɾ��
     *
     * @param tbPunishResult
     * @return
     */
    @Override
    public int deleteByAttr(TbPunishResult tbPunishResult) {
       return tbPunishResultMapper.deleteByAttr(tbPunishResult);
    }

    @Override
    public List<TbPunishResult> selectByListId(TbPunishResult tbPunishResult) {
        return tbPunishResultMapper.selectByListId(tbPunishResult);
    }


}