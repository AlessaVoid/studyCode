package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbOrganRateScoreMonthDetailBackup;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 机构评分月度详情备份表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbOrganRateScoreMonthDetailBackupMapper extends GenericMapper<TbOrganRateScoreMonthDetailBackup, String>{
    /**
     * @Description 查询最大历史编号
     * @Author liujinbo
     * @Date 2020/3/5
     * @param
     * @param rateMonth
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectMaxHistoryNumber(String rateMonth);
}