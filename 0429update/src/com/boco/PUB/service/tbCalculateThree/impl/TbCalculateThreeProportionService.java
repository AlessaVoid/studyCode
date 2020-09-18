package com.boco.PUB.service.tbCalculateThree.impl;


import com.boco.PUB.service.tbCalculateThree.ITbCalculateThreeProportionService;
import com.boco.SYS.mapper.TbCalculateThreeProportionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbCalculateThreeProportion;
import com.boco.SYS.base.GenericService;

/**
 * TbCalculateThreeProportion业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbCalculateThreeProportionService extends GenericService<TbCalculateThreeProportion, String> implements ITbCalculateThreeProportionService {

    //有自定义方法时使用
    @Autowired
    private TbCalculateThreeProportionMapper mapper;

    @Override
    public void deleteAll() {
        mapper.deleteAll();
    }

}