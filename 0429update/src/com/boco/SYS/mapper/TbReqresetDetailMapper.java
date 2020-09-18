package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbReqresetDetail;
import com.boco.SYS.base.GenericMapper;

/**
 * 调整申请报送要求录入详细表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbReqresetDetailMapper extends GenericMapper<TbReqresetDetail, Integer> {




    int deleteByAttr(TbReqresetDetail tbReqresetDetail);

    /**
     * 联想输入查询-id
     *
     * @param tbReqresetDetail
     * @return
     */
    List<Map<String, Integer>> selectReqdId(TbReqresetDetail tbReqresetDetail);

    /**
     * 联想输入查询-所属批次
     *
     * @param tbReqresetDetail
     * @return
     */
    List<Map<String, String>> selectReqdRefId(TbReqresetDetail tbReqresetDetail);

    /**
     * 通过批次编号和机构号更新信贷需求调整申请报送状态
     */
    void updateReqDetailByReqdRefIdAndOrganCode(TbReqresetDetail tbReqresetDetail);


    /**
     * @return java.util.List<java.util.Map   <   java.lang.String   ,   java.lang.Object>>
     * @Author daice
     * @Description //查询已提交记录
     * @Date 上午9:51 2019/11/16
     * @Param [map]
     **/
    List<Map<String, Object>> getAuditRecordHist(Map<String, Object> map);

    /**
     * @return java.util.List<java.util.Map   <   java.lang.String   ,   java.lang.Object>>
     * @Author daice
     * @Description //查询待审批的信贷需求
     * @Date 下午2:18 2019/11/16
     * @Param [map]
     **/
    List<Map<String, Object>> getPendingAppReq(Map<String, Object> map);

    /**
     * @return java.util.List<java.util.Map   <   java.lang.String   ,   java.lang.Object>>
     * @Author daice
     * @Description //查询已审批的信贷需求
     * @Date 下午6:44 2019/11/16
     * @Param [map]
     **/
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);

    /**
     * @return java.lang.String
     * @Author daice
     * @Description //根据流程id获取流程发起人
     * @Date 上午11:16 2019/11/17
     * @Param [id]
     **/
    String getStartWorkFlowPeople(String processInstanceId);
}