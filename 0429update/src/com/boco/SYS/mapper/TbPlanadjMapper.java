package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbPlanadj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 信贷计划调整批次表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbPlanadjMapper extends GenericMapper<TbPlanadj, String>{
    /**
     * @Description 根据条件查询信贷计划调整批次
     * @Author liujinbo
     * @Date 2019/11/23
     * @param queryMap
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectPlanadj(HashMap<String, Object> queryMap);

    /**
     * @Description 查询已提交的信贷计划调整
     * @Author liujinbo
     * @Date 2019/11/25
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getAuditRecordHist(Map<String, Object> map);

    /**
     * @Description 查询待审批的信贷计划调整
     * @Author liujinbo
     * @Date 2019/11/26
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getPendingAppReq(Map<String, Object> map);

    /**
     * @Description 查询已审批的信贷计划调整
     * @Author liujinbo
     * @Date 2019/11/26
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);


    /** 根据refId删除planadjDetail
     * @Description
     * @Author liujinbo
     * @Date 2019/11/28
     * @param planadjId
     * @Return void
     */
    void deleteTbPlanadjDetailBYRefId(String planadjId);

    /**
     * @Author daice
     * @Description //根据级别获取贷种组合信息
     * @Date 下午1:42 2019/11/29
     * @Param [level]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getCombList(int level);

    /**
     * @Author daice
     * @Description //获取指定月份的信贷计划
     * @Date 下午4:57 2019/11/29
     * @Param [month]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *
     * @param month*/
    List<Map<String, Object>> getCreditPlanDetail(HashMap<String, Object> month);

    /**
     * @Author daice
     * @Description //获取信贷机构需求
     * @Date 下午4:57 2019/11/29
     * @Param [month]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *
     * @param month*/
    List<Map<String, Object>> getReqDetail(Map<String, Object> month);

    /**
     * @Author daice
     * @Description //获取信贷条线需求
     * @Date 下午4:57 2019/11/29
     * @Param [month]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *
     * @param month*/
    List<Map<String, Object>> getLineReqDetail(Map<String, Object> month);

    void updatePlanadj(TbPlanadj tbPlanadj);
}