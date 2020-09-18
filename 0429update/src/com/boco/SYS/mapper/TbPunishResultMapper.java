package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbPunishResult;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * TbPunishResult数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbPunishResultMapper extends GenericMapper<TbPunishResult, Integer>{

    /**
     * 根据条件删除
     * @param tbPunishResult
     * @return
     */

    int deleteByAttr(TbPunishResult tbPunishResult);


    /**
     *
     *
     * TODO 根据主键修改一条记录.
     *
     * @param tbPunishResult
     * @return
     *
     * <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月2日    	    杨滔    新建
     * </pre>
     */
    public int updateByListId(TbPunishResult tbPunishResult) ;


    /**
     * @Author daice
     * @Description //查询已审批的罚息结果
     * @Date 下午6:44 2019/11/16
     * @Param [map]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);


    /**
     * @Author daice
     * @Description //查询待审批的罚息结果
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
     * 通过罚息结果申请编号和机构号更新罚息结果申请状态
     */
    void updateQuotaApplyByQaIdAndOrganCode(TbPunishResult tbPunishResult);


    /**
     * 联想输入申请编号
     *
     * @param tbPunishResult
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年月10日    	    txn   新建
     * </pre>
     */
    List<Map<String, Integer>> selectQaId(TbPunishResult tbPunishResult);

    /**
     * 联想输入所属周期.
     *
     * @param tbPunishResult
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    List<Map<String, String>> selectQaMonth(TbPunishResult tbPunishResult);

    /**
     * @Author daice
     * @Description //根据流程id获取流程发起人
     * @Date 上午11:16 2019/11/17
     * @Param [id]
     * @return java.lang.String
     **/
    String getStartWorkFlowPeople(String processInstanceId);


    List<TbPunishResult> selectByListId(TbPunishResult tbPunishResult);


}