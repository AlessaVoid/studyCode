package com.boco.SYS.mapper;

import org.apache.ibatis.annotations.Param;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebTradeTime;
/**
 * 
 * 
 * WebTradeTime数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebTradeTimeMapper extends GenericMapper<WebTradeTime,java.lang.String>{

	public void reSetTradeTime(@Param(value="menuCode")String menuCode);
}