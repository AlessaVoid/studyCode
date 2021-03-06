package com.boco.PUB.service.tbCalculateOne;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbCalculateOneProportion;
import org.springframework.dao.DataAccessException;

/**
 * 测算 权重表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface ITbCalculateOneProportionService extends IGenericService<TbCalculateOneProportion, String> {

    void deleteAll() throws DataAccessException;

}