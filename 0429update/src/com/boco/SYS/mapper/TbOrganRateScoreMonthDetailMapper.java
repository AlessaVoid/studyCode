package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbOrganRateScoreMonthDetail;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 
 * 机构评分月度详情表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbOrganRateScoreMonthDetailMapper extends GenericMapper<TbOrganRateScoreMonthDetail, String>{
    /**
     * @Author: Liujinbo
     * @Description:  根据月份和类型查询评分详情表
     * @Date: 2020/2/4
     * @param queryMap1 :
     * @return: java.util.List<com.boco.SYS.entity.TbOrganRateScoreMonthDetail>
     **/
    List<TbOrganRateScoreMonthDetail> selectOrganRateScoreDetailByMonthAndType(HashMap<String, Object> queryMap1);
}