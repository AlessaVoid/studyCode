package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbLineReqresetDetail;
import com.boco.SYS.base.GenericMapper;

/**
 * 条线需求调整记录详情表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbLineReqresetDetailMapper extends GenericMapper<TbLineReqresetDetail, Integer> {


    /**
     * 联想输入所属周期---
     *
     * @param tbLineReqresetDetail
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    List<Map<String, String>> showLineReqResetMonth(TbLineReqresetDetail tbLineReqresetDetail);


    /**
     * 根据条件删除
     *
     * @param tbLineReqresetDetail
     * @return
     */

    int deleteByAttr(TbLineReqresetDetail tbLineReqresetDetail);


    /**
     * 通过批次编号和机构号更新信贷需求状态
     */
    void updateReqDetailByReqdRefIdAndOrganCode(TbLineReqresetDetail tbLineReqresetDetail);

    /**
     * 通过机构号及批次号需求净增量
     */
    double sumReqAmount(TbLineReqresetDetail tbLineReqresetDetail);

    /**
     * 通过机构号及批次号统计需求到期量总和
     */
    double sumReqExpire(TbLineReqresetDetail tbLineReqresetDetail);

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author txn
     * @Description //查询已提交记录
     * @Date 上午9:51 2019/11/16
     * @Param [map]
     **/
    List<Map<String, Object>> getAuditRecordHist(Map<String, Object> map);

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author txn
     * @Description //查询待审批的信贷需求
     * @Date 下午2:18 2019/11/16
     * @Param [map]
     **/
    List<Map<String, Object>> getPendingAppReq(Map<String, Object> map);

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author txn
     * @Description //查询已审批的信贷需求
     * @Date 下午6:44 2019/11/16
     * @Param [map]
     **/
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);

    /**
     * @return java.lang.String
     * @Author txn
     * @Description //根据流程id获取流程发起人
     * @Date 上午11:16 2019/11/17
     * @Param [id]
     **/
    String getStartWorkFlowPeople(String processInstanceId);

}