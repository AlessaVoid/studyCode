package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbPsbcRmbLoanDay;

import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * 人民币贷款日报表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbPsbcRmbLoanDayMapper extends GenericMapper<TbPsbcRmbLoanDay, String>{

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectListByPage(Map<String, Object> paramMap);
    /**
     * 根据统计日期校验唯一性
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectForCheckOnly(Map<String, Object> paramMap);
}