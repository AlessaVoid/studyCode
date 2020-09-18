package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbOrganRateScoreImportData;

import java.util.HashMap;

/**
 * 
 * 
 * TbOrganRateScoreImportData数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbOrganRateScoreImportDataMapper extends GenericMapper<TbOrganRateScoreImportData, String>{
    /**
     * @Description 根据月份删除
     * @Author liujinbo
     * @Date 2020/3/10
     * @param queryMap
     * @Return void
     */
    void deleteByMonth(HashMap<String, Object> queryMap);
}