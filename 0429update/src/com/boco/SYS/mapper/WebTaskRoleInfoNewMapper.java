package com.boco.SYS.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebTaskRoleInfoNew;
/**
 * 
 * 
 * WebTaskRoleInfoNew数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebTaskRoleInfoNewMapper extends GenericMapper<WebTaskRoleInfoNew,HashMap<String,Object>>{

	public void deleteByProcDefId(@Param(value="procDefId")String procDefId);
}