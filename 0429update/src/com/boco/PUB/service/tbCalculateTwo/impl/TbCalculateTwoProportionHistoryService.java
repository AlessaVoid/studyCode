package com.boco.PUB.service.tbCalculateTwo.impl;

import java.util.HashMap;

import com.boco.PUB.service.tbCalculateTwo.ITbCalculateTwoProportionHistoryService;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbCalculateTwoProportionHistory;
import com.boco.SYS.base.GenericService;

/**
 * 
 * 
 * 测算二 权重参数 历史表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbCalculateTwoProportionHistoryService extends GenericService<TbCalculateTwoProportionHistory, String> implements ITbCalculateTwoProportionHistoryService {
	//有自定义方法时使用
	//@Autowired
	//private TbCalculateTwoProportionHistoryMapper mapper;
}