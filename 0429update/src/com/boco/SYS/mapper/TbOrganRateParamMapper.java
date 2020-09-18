package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbOrganRateParam;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 机构评分参数表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbOrganRateParamMapper extends GenericMapper<TbOrganRateParam, String>{

    /**
     * @Author: Liujinbo
     * @Description:  评分参数列表
     * @Date: 2020/2/10

     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> selectByType();
}