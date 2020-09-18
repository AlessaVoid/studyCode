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
 * TbPunishResult业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbPunishResultService extends GenericService<TbPunishResult, Integer> implements ITbPunishResultService {
    //有自定义方法时使用

    //有自定义方法时使用
    @Autowired
    private TbPunishResultMapper tbPunishResultMapper;
    public static Logger logger = Logger.getLogger(LoanQuotaApplyService.class);


    @Override
    public int updateByListId(TbPunishResult tbPunishResult) {

        return tbPunishResultMapper.updateByListId(tbPunishResult);

    }


    /**
     * 根据条件删除
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