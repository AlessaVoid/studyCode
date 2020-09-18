package com.boco.PUB.service.tbCalculateOne.impl;

import com.boco.PUB.service.tbCalculateOne.ITbCalculateOneProportionService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbCalculateOneProportion;
import com.boco.SYS.mapper.TbCalculateOneProportionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * 测算 权重表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class TbCalculateOneProportionService extends GenericService<TbCalculateOneProportion, String> implements ITbCalculateOneProportionService {
    //有自定义方法时使用
    @Autowired
    private TbCalculateOneProportionMapper mapper;


    @Override
    public void deleteAll() throws DataAccessException {
        mapper.deleteAll();
    }
}