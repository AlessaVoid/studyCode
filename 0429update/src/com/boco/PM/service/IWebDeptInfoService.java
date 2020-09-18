package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.WebDeptInfo;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebDeptInfo业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebDeptInfoService extends IGenericService<WebDeptInfo,HashMap<String,Object>>{
	List<WebDeptInfo> select(WebDeptInfo webDeptInfo);
	Json insert(WebDeptInfo webDeptInfo) throws Exception;
	//自增部门编码
	int max(WebDeptInfo webDeptInfo);
	Json update(WebDeptInfo webDeptInfo) throws Exception;
	Json delete(WebDeptInfo webDeptInfo) throws Exception;
	List<Map<String, String>> selectCode(WebDeptInfo webDeptInfo);
	List<Map<String, String>> selectName(WebDeptInfo webDeptInfo);
	List<Map<String, String>> selectUpCode(WebDeptInfo webDeptInfo);
	List<WebDeptInfo> selectUpDeptName(WebDeptInfo webDeptInfo);

}