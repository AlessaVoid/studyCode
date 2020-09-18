package com.boco.PUB.service.loanPlanadj.impl;

import com.boco.PUB.service.loanPlanadj.TbPlanadjDetailService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbPlanadjDetail;
import com.boco.SYS.mapper.TbPlanadjDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * 信贷计划调整批次详情表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class TbPlanadjDetailServiceImpl extends GenericService<TbPlanadjDetail, String> implements TbPlanadjDetailService{
	//有自定义方法时使用
	@Autowired
	private TbPlanadjDetailMapper tbPlanadjDetailMapper;
}