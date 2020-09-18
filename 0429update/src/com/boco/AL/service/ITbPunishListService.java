package com.boco.AL.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPunishList;
import com.boco.SYS.util.Pager;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * TbPunishList业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface ITbPunishListService extends IGenericService<TbPunishList, Integer>{


    /**
     * 联想输入所属周期.
     *
     * @param tbPunishList
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    List<Map<String, String>> selectMonth(TbPunishList tbPunishList);


    /**
     * 启动超限额申请审批流程
     *
     * @param qaId     超限额申请批次标识
     * @param organCode 机构号
     * @param operCode  柜员号
     * @param operName  柜员名称
     * @param assignee  签收人
     * @return PlainResult
     */
    ProcessInstance startLoanReqAuditProcess(int qaId, String organCode, String operCode, String operName, String assignee, String processKey) throws Exception;
    /**
     * 启动超限额申请审批流程
     *
     * @param qaId     超限额申请批次标识
     * @param organCode 机构号
     * @param operCode  柜员号
     * @param assignee  签收人
     * @return PlainResult
     */
    PlainResult<String> compleLoanReqAuditProcess(ProcessInstance pi, String operCode, String assignee, Integer qaId, String organCode) throws Exception;

    /**
     * @Author txn
     * @Description //查询已提交超限额申请
     * @Date 下午9:32 2019/11/14
     * @Param [operCode, auditStatus, reqMonth]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    List<Map<String,Object>> getAuditRecordHistRecord(String operCode, String auditStatus, String reqMonth) throws Exception;

    /**
     * @Author txn
     * @Description //查询待审批的超限额申请
     * @Date 下午2:17 2019/11/16
     * @Param [operCode, reqMonth]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getPendingAppReq(String type, String operCode, String reqMonth, String auditStatus, Pager pager) throws Exception;

    /**
     * @Author txn
     * @Description //超限额申请审批
     * @Date 下午5:06 2019/11/16
     * @Param [request, operCode, thiscode, operName]
     * @return com.boco.SYS.base.PlainResult<java.lang.String>
     **/

    Map<String, Object> findIsNotAgreeInfo(Task task, String custType, Map<String, Object> variables)	throws Exception;



    ProcessInstance completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception;

    void completeTask(ProcessInstance processInstance, Map<String, Object> varMap, Map msgMap) throws Exception;


    /**
     * @Author txn
     * @Description //查询已审批的超限额申请
     * @Date 下午6:41 2019/11/16
     * @Param [operCode, auditStatus, reqMonth]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getApprovedRecord(String operCode, String auditStatus, String reqMonth) throws Exception;



    /**
     * 联想输入申请编号
     *
     * @param tbPunishList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年月10日    	    txn   新建
     * </pre>
     */
    public List<Map<String, Integer>> selectQaId(TbPunishList  tbPunishList);

}