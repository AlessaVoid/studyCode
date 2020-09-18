package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbRptBaseinfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 报表基础数据-每日生成-包含贷款余额，发生量，到期量等数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Repository
public interface TbRptBaseinfoMapper extends GenericMapper<TbRptBaseinfo,HashMap<String,Object>> {

    /**
     * 信贷日报统计查询（总行查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectForReportOrganOne(Map<String, Object> map);

    /**
     * 信贷日报统计查询（一级分行）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectForReportOrganTwo(Map<String, Object> map);

    /**
     * 首页 - 判断当前登录柜员是否有所属条线
     * @param operCode
     * @return
     */
    List<Map<String, Object>> selectOperLine(String operCode);

    /**
     * 首页 - 获取机构本月净增量信息 - 无条线（总行查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexOrganBarInfoOneNohasLine(Map<String, Object> map);

    /**
     * 首页 - 获取机构本月净增量信息 - 有条线（总行查询）
     * @param map
     * @return
     */
    Map<String, Object> selectIndexOrganBarInfoOnehasLine(Map<String, Object> map);

    /**
     * 首页 - 获取机构本月净增量信息 - 无条线（一级分行）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexOrganBarInfoTwoNohasLine(Map<String, Object> map);

    /**
     * 首页 - 获取机构本月净增量信息 - 有条线（一级分行）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexOrganBarInfoTwohasLine(Map<String, Object> map);

    /**
     * 首页 - 二级贷种本月净增量（总行查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexCombBarInfoOne(Map<String, Object> map);

    /**
     * 首页 - 二级贷种本月净增量（一分查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexCombBarInfoTwo(Map<String, Object> map);

    /**
     * 首页 - 二级贷种本月计划完成率 - 计划净增量（总行查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexCombBarInfoForPlanOne(Map<String, Object> map);

    /**
     * 首页 - 二级贷种本月计划完成率 - 计划净增量（一分查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexCombBarInfoForPlanTwo(Map<String, Object> map);

    /**
     * 首页 - 二级贷种本月计划完成率 - 实际净增量（总行查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexCombBarInfoForRealOne(Map<String, Object> map);

    /**
     * 首页 - 二级贷种本月计划完成率 - 实际净增量（一分查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexCombBarInfoForRealTwo(Map<String, Object> map);

    /**
     * 首页 - 预警线 - 完成率 （总行查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexWarnCompleteOne(Map<String, Object> map);

    /**
     * 首页 - 预警线 - 完成率 （一分查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexWarnCompleteTwo(Map<String, Object> map);

    /**
     * 首页 - 预警线 - 绝对值 （总行查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexWarnAbsOne(Map<String, Object> map);

    /**
     * 首页 - 预警线 - 绝对值 （一分查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexWarnAbsTwo(Map<String, Object> map);

    /**
     * 首页 - 二级贷种本月净增量（二分查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexCombBarInfoThree(Map<String, Object> map);

    /**
     * 首页 - 二级贷种本月计划完成率 - 计划净增量（二分查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexCombBarInfoForPlanThree(Map<String, Object> map);

    /**
     * 首页 - 二级贷种本月计划完成率 - 实际净增量（二分查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexCombBarInfoForRealThree(Map<String, Object> map);

    /**
     * 首页 - 预警线 - 完成率 （二分查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexWarnCompleteThree(Map<String, Object> map);

    /**
     * 首页 - 预警线 - 绝对值 （二分查询）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexWarnAbsThree(Map<String, Object> map);
}