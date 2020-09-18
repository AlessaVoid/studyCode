package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbCalculateOneResult;

import java.util.HashMap;

/**
 * 
 * 
 * TbCalculateOneResult数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbCalculateOneResultMapper extends GenericMapper<TbCalculateOneResult,String>{
    /**
     * @Author: Liujinbo
     * @Description:  根据月份删除测算结果
     * @Date: 2020/3/2

     * @return: void
     *@param map */
    void deleteByMonthAndType(HashMap<String, Object> map);
}