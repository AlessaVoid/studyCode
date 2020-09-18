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
 * 借据表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbLoanInfoServiceImpl extends GenericService<TbLoanInfo,HashMap<String,Object>> implements TbLoanInfoService {

    @Autowired
    private TbLoanInfoMapper tbLoanInfoMapper;

    /*查询借据*/
    @Override
    public List<Map<String, Object>> selectTbLoanInfo(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> list = tbLoanInfoMapper.selectTbLoanInfo(paramMap);
        return list;
    }

}