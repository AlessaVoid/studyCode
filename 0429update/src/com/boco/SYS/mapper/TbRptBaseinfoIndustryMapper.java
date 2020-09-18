package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbRptBaseinfoIndustry;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * 新报表，机构行业维度数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbRptBaseinfoIndustryMapper extends GenericMapper<TbRptBaseinfoIndustry,HashMap<String,Object>>{
    /**
     * 查询行业
     * @return
     */
    List<Map<String, Object>> selectIndustry();

    /**
     * 行业表-总行登录查询一分数据
     * @param param
     * @return
     */
    List<TbRptBaseinfoIndustry> selectLevelOneOrganData(HashMap<String, Object> param);

    /**
     * 行业表-一分登录查看二分数据
     * @param param
     * @return
     */
    List<TbRptBaseinfoIndustry> selectLevelOhterOrganData(HashMap<String, Object> param);
}