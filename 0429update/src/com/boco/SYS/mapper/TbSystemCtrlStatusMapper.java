package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbSystemCtrlStatus;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * TbSystemCtrlStatus数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbSystemCtrlStatusMapper extends GenericMapper<TbSystemCtrlStatus, String>{
    List<Map<String, Object>> selectTbSystemCtrlStatus(TbSystemCtrlStatus tbSystemCtrlStatus);
}