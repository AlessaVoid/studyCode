package com.boco.PUB.service.lineTemp;

import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbLineTemp;
import com.boco.SYS.base.IGenericService;
import com.boco.SYS.util.Pager;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 
 * ���޶�������Ϣ��ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbLineTempService extends IGenericService<TbLineTemp, Integer>{



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
    PlainResult<String> startLoanReqAuditProcess(int qaId, String organCode, String operCode, String operName, String assignee, String processKey,String comment) throws Exception;

    /**
     * @Author txn
     * @Description //��ѯ���ύ���޶�����
     * @Date ����9:32 2019/11/14
     * @Param [operCode, auditStatus, reqMonth]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    List<Map<String,Object>> getAuditRecordHistRecord(String operCode, String auditStatus, String reqMonth) throws Exception;

    /**
     * @Author txn
     * @Description //��ѯ�������ĳ��޶�����
     * @Date ����2:17 2019/11/16
     * @Param [operCode, reqMonth]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getPendingAppReq(String type, String operCode, String reqMonth, String auditStatus, Pager pager) throws Exception;


     Map<String, Object> findIsNotAgreeInfo(Task task, String custType, Map<String, Object> variables)	throws Exception;



    ProcessInstance completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception;

    void completeTask(ProcessInstance  processInstance, Map<String, Object> varMap, Map msgMap) throws Exception;


    /**
     * @Author txn
     * @Description //��ѯ�������ĳ��޶�����
     * @Date ����6:41 2019/11/16
     * @Param [operCode, auditStatus, reqMonth]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getApprovedRecord(String operCode, String auditStatus, String reqMonth) throws Exception;



    /**
     * ��������������
     *
     * @param tbQuotaApply
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019����10��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, Integer>> selectQaId(TbLineTemp tbQuotaApply);

    /**
     * ����������������.
     *
     * @param tbQuotaApply
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    public List<Map<String, String>> selectQaMonth(TbLineTemp tbQuotaApply);
}