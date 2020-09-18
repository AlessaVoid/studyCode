package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbLineOver;
import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbLineOver;

/**
 * 
 * 
 * 超限额申请信息表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbLineOverMapper extends GenericMapper<TbLineOver, Integer>{


    /**
     * @Author daice
     * @Description //查询已审批的信贷需求
     * @Date 下午6:44 2019/11/16
     * @Param [map]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);


    /**
     * @Author daice
     * @Description //查询待审批的信贷需求
     * @Date 下午2:18 2019/11/16
     * @Param [map]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getPendingAppReq(Map<String, Object> map);


    /**
     * @Author daice
     * @Description //查询已提交记录
     * @Date 上午9:51 2019/11/16
     * @Param [map]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getAuditRecordHist(Map<String, Object> map);



    /**
     * 通过超限额申请编号和机构号更新超限额申请状态
     */
    void updateQuotaApplyByQaIdAndOrganCode(TbLineOver tbLineTemp);


    /**
     * 联想输入申请编号
     *
     * @param tbLineTemp
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年月10日    	    txn   新建
     * </pre>
     */
    List<Map<String, Integer>> selectQaId(TbLineOver tbLineTemp);

    /**
     * 联想输入所属周期.
     *
     * @param tbLineTemp
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    List<Map<String, String>> selectQaMonth(TbLineOver tbLineTemp);

    /**
     * @Author daice
     * @Description //根据流程id获取流程发起人
     * @Date 上午11:16 2019/11/17
     * @Param [id]
     * @return java.lang.String
     **/
    String getStartWorkFlowPeople(String processInstanceId);
}