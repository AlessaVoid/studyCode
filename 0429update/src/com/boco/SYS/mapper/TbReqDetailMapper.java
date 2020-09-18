package com.boco.SYS.mapper;


import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbReqDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 信贷需求录入详细表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbReqDetailMapper extends GenericMapper<TbReqDetail, Integer> {



    /**
     * 根据条件删除
     * @param tbReqDetail
     * @return
     */

    int deleteByAttr(TbReqDetail tbReqDetail);

    /**
     * 联想输入查询-id
     *
     * @param tbReqDetail
     * @return
     */
    List<Map<String, Integer>> selectReqdId(TbReqDetail tbReqDetail);

    /**
     * 联想输入查询-所属批次
     *
     * @param tbReqDetail
     * @return
     */
    List<Map<String, String>> selectReqdRefId(TbReqDetail tbReqDetail);

    /**
     * 通过批次编号和机构号更新信贷需求状态
     */
    void updateReqDetailByReqdRefIdAndOrganCode(TbReqDetail tbReqDetail);

    /**
     * 通过机构号及批次号需求净增量
     */
    double sumReqAmount(TbReqDetail tbReqDetail);

    /**
     * 通过机构号及批次号统计需求到期量总和
     */
    double sumReqExpire(TbReqDetail tbReqDetail);

    /**
     * @Author daice
     * @Description //查询已提交记录
     * @Date 上午9:51 2019/11/16
     * @Param [map]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getAuditRecordHist(Map<String, Object> map);

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
     * @Description //查询已审批的信贷需求
     * @Date 下午6:44 2019/11/16
     * @Param [map]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);

    /**
     * @Author daice
     * @Description //根据流程id获取流程发起人
     * @Date 上午11:16 2019/11/17
     * @Param [id]
     * @return java.lang.String
     **/
    String getStartWorkFlowPeople(String processInstanceId);


    /**
     * 根据机构分组，查询机构需求
     * @param param
     * @return
     */
    List<Map<String, Object>> getOrganReq(HashMap<String, Object> param);

}