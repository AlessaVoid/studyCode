package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbOrganRateScore;

import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * 机构评分批次表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbOrganRateScoreMapper extends GenericMapper<TbOrganRateScore, String>{

    /**
     * @Author: Liujinbo
     * @Description:  查询已提交的评分
     * @Date: 2020/2/7
     * @param map :
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getAuditRecordHist(Map<String, Object> map);

    /**
     * @Author: Liujinbo
     * @Description:  查询待审批的评分
     * @Date: 2020/2/7
     * @param map :
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getPendingAppReq(Map<String, Object> map);

    /**
     * @Author: Liujinbo
     * @Description:  查询已审批的评分
     * @Date: 2020/2/7
     * @param map :
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getApprovedRecord(Map<String, Object> map);

    /**
     * @Author: Liujinbo
     * @Description:  查询所有的两小，其他贷种  1,2,3级
     * @Date: 2020/2/13

     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> selectLoanKindOfTwo();
}