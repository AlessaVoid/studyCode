package com.boco.TONY.common.service;

import com.boco.SYS.entity.FdOper;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;

import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @describe IBusinessProcessService
 * @date 2019-11-17
 */
public interface ICommonBusinessAuditService {

    PlainResult<String> startBusinessAuditProcess(String assignee, String businessKey, Map<String, Object> businessMap);

    PlainResult<String> auditBusinessTask(String currentAssignee, Map<String, Object> businessMap) throws Exception;

    void completeBusinessTask(String taskId, String comment, Map<String, Object> varMap, boolean lastUserType) throws Exception;

    ListResult<FdOper> getBusinessAuditorList(Map<String, Object> businessMap) throws Exception;

    List<Object> getAllWaitForAuditTaskList(String assignee, int pageNo, int pageSize) throws Exception;

    ListResult<Object> listHistoricBusinessTaskWithFinished(String assignee, int pageNumber, int pageSize) throws Exception;

    ListResult<Object> getAllRollbackTaskList(String assignee, int pageNo, int pageSize);

    PlainResult<String> resubmitRejectedTask(String assignee,String taskId,Map<String, Object> businessMap);

    ListResult<Object> getAuditRecordHistRecord(Map<String, Object> businessMap, int pageNo, int pageSize);
}
