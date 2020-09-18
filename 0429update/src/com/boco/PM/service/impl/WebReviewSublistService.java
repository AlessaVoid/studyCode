package com.boco.PM.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebReviewSublistService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.WebReviewSublist;
import com.boco.SYS.mapper.WebReviewSublistMapper;

/**
 * 
 * 
 * WebReviewSublist业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class WebReviewSublistService extends GenericService<WebReviewSublist,HashMap<String,Object>> implements IWebReviewSublistService{
	//有自定义方法时使用
	@Autowired
	private WebReviewSublistMapper webReviewSublistMapper;

	@Override
	public List<WebReviewSublist> selectAppNo(String appNo) throws Exception {
		return webReviewSublistMapper.selectAppNo(appNo);
	}
}