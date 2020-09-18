package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebDeptInfo;
/**
 * 
 * 
 * WebDeptInfo数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebDeptInfoMapper extends GenericMapper<WebDeptInfo,HashMap<String,Object>>{

	List<WebDeptInfo> select(WebDeptInfo webDeptInfo);

	int max(WebDeptInfo webDeptInfo);

	List<Map<String,Object>> count(WebDeptInfo dept);

	List<Map<String, String>> selectCode(WebDeptInfo webDeptInfo);

	List<Map<String, String>> selectName(WebDeptInfo webDeptInfo);

	List<Map<String, String>> selectUpCode(WebDeptInfo webDeptInfo);

	List<WebDeptInfo> selectUpDeptName(WebDeptInfo webDeptInfo);

}