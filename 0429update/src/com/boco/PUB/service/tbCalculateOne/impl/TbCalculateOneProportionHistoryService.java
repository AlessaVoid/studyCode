package com.boco.PUB.service.tbCalculateOne.impl;

import com.boco.PUB.service.tbCalculateOne.ITbCalculateOneProportionHistoryService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbCalculateOneProportionHistory;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * 测算 权重历史表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class TbCalculateOneProportionHistoryService extends GenericService<TbCalculateOneProportionHistory, String> implements ITbCalculateOneProportionHistoryService {
	//有自定义方法时使用
	//@Autowired
	//private TbCalculateOneProportionHistoryMapper mapper;
}