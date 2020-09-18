package com.boco.SYS.mapper;

import java.util.HashMap;

import com.boco.SYS.entity.MsgPerson;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * 发送短信-人员表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface MsgPersonMapper extends GenericMapper<MsgPerson,String>{
}