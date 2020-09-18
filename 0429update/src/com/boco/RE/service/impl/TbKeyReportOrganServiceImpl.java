package com.boco.RE.service.impl;

import com.boco.RE.service.TbKeyReportOrganService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbKeyReportOrgan;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * 报表-机构重点行信息表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbKeyReportOrganServiceImpl extends GenericService<TbKeyReportOrgan, String> implements TbKeyReportOrganService {
	//有自定义方法时使用
	//@Autowired
	//private TbKeyReportOrganMapper mapper;
}