package com.boco.PUB.service.loanReqReset;

import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.util.Pager;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ILoanReqAppService
 * @Description ���޶���������
 * @Author txn
 * @Date 2019/11/17
 * @Version 1.0
 **/
public interface ILoanReqResetService {

    /**
     * �������޶�������������
     *
     * @param qaId     ���޶��������α�ʶ
     * @param organCode ������
     * @param operCode  ��Ա��
     * @param operName  ��Ա����
     * @param assignee  ǩ����
     * @return PlainResult
     */
    PlainResult<String> startLoanReqAuditProcess(int qaId, String organCode, String operCode, String operName, String assignee,String comment) throws Exception;

    /**
     * @Author txn
     * @Description //��ѯ���ύ���޶�����
     * @Date ����9:32 2019/11/14
     * @Param [operCode, auditStatus, reqMonth]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    List<Map<String,Object>> getAuditRecordHistRecord(WebOperInfo sessionOperInfo, String auditStatus) throws Exception;

    /**
     * @Author txn
     * @Description //��ѯ�������ĳ��޶�����
     * @Date ����2:17 2019/11/16
     * @Param [operCode, reqMonth]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getPendingAppReq(String type,WebOperInfo sessionOperInfo, String auditStatus, Pager pager) throws Exception;

    /**
     * @Author txn
     * @Description //���޶���������
     * @Date ����5:06 2019/11/16
     * @Param [request, operCode, thiscode, operName]
     * @return com.boco.SYS.base.PlainResult<java.lang.String>
     **/
    PlainResult<String> auditLoanReqRequest(HttpServletRequest request, String operCode, String thiscode, String operName);

    public Map<String, Object> findIsNotAgreeInfo(Task task, String custType, Map<String, Object> variables)	throws Exception;

    ProcessInstance completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception;
    void completeTask(ProcessInstance  processInstance, Map<String, Object> varMap, Map msgMap) throws Exception;

    /**
     * @Author txn
     * @Description //��ѯ�������ĳ��޶�����
     * @Date ����6:41 2019/11/16
     * @Param [operCode, auditStatus, reqMonth]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getApprovedRecord(WebOperInfo sessionOperInfo, String auditStatus) throws Exception;
}
