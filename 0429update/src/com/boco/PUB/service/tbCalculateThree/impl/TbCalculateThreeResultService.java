package com.boco.PUB.service.tbCalculateThree.impl;


import com.boco.PUB.service.tbCalculateThree.ITbCalculateThreeResultService;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbCalculateThreeResult;
import com.boco.SYS.base.GenericService;

/**
 * 
 * 
 * TbCalculateThreeResult业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbCalculateThreeResultService extends GenericService<TbCalculateThreeResult, String> implements ITbCalculateThreeResultService {
	//有自定义方法时使用
	//@Autowired
	//private TbCalculateThreeResultMapper mapper;
}