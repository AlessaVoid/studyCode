package com.boco.PUB.service.loanPlan;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.SYS.entity.TbPlanadjDetail;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.util.Pager;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.task.Task;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description:  �Ŵ��ƻ�����
 * @Date: 2019/11/18
 * @Version: 1.0
 */
public interface TbPlanService extends IGenericService<TbPlan, String> {

    /**
     * @Description ��ѯ���ύ���Ŵ��ƻ�
     * @Author liujinbo
     * @Date 2019/11/18
     * @param operCode
     * @param auditStatus
     * @param reqMonth
     * @param sessionOperInfo
     * @param sort
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> getAuditRecordHistRecord(String operCode, String auditStatus, String reqMonth, WebOperInfo sessionOperInfo, Integer planType, String sort) throws Exception;

    /**
     * @Description ��ѯ���������Ŵ��ƻ�
     * @Author liujinbo
     * @Date 2019/11/19
     * @param sort
     * @param operCode
     * @param auditStatus
     * @param reqMonth
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getApprovedRecord(String sort, String operCode, String auditStatus, String reqMonth, Integer planType) throws Exception;

    /**
     * @Description �����Ŵ��ƻ���������
     * @Author liujinbo
     * @Date 2019/11/19
     * @param planId
     * @param organCode
     * @param operCode
     * @param operName
     * @param assignee
     * @param comment
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> startLoanPlanAuditProcess(String planId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception;
    /**
     * @Description �����Ŵ�������������
     * @Author liujinbo
     * @Date 2019/11/19
     * @param planId
     * @param organCode
     * @param operCode
     * @param operName
     * @param assignee
     * @param comment
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> startLoanplanStripeAuditProcess(String planId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception;



    /**
     * @Description ��ѯ���������Ŵ��ƻ�
     * @Author liujinbo
     * @Date 2019/11/19
     * @param sort
     * @param operCode
     * @param reqMonth
     * @param auditStatus
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getPendingAppReq(String sort, String operCode, String reqMonth, String auditStatus, Pager pager, Integer planType) throws Exception;
    /**
     * @Description ��ѯ�����������߼ƻ�
     * @Author liujinbo
     * @Date 2019/11/19
     * @param sort
     * @param operCode
     * @param reqMonth
     * @param auditStatus
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getPendingPlanStripe(String sort, String operCode, String reqMonth, String auditStatus, Pager pager, Integer planType) throws Exception;


    Map<String, Object> findIsNotAgreeInfo(Task task, String custType, Map<String, Object> variables) throws Exception;

    void completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception;

    void completeTaskAuditPlanStripe(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception;



    /**
     * @Description ¼��ƻ��ļ�
     * @Author liujinbo
     * @Date 2019/12/13
     * @param file
     * @param operCode
     * @param organCode
     * @param request
     * @param organlevel
     * @param uporgan
     * @Return void
     */
    Map<String,String> enterReportPlanByMonth(MultipartFile file, String operCode, String organCode, HttpServletRequest request, String organlevel, String uporgan) throws Exception;

    /**
     * @Description ¼�������ļ�
     * @Author liujinbo
     * @Date 2019/12/13
     * @param file
     * @param operCode
     * @param organCode
     * @param request
     * @param organlevel
     * @param uporgan
     * @Return void
     */
    Map<String,String>  enterReportPlanStripeByMonth(MultipartFile file, String operCode, String organCode, HttpServletRequest request, String organlevel, String uporgan) throws Exception;

    /**
     * @Description ���ؼƻ�ģ��
     * @Author liujinbo
     * @Date 2019/12/26
     * @param request
     * @param type
     * @param response
     * @param organlevel
     * @Return javax.servlet.http.HttpServletResponse
     */
    void downloadPlanTemplate(HttpServletRequest request, String type, HttpServletResponse response, String organlevel) throws Exception;

    /**
     * @Description ά��ʱ��ƻ��󣬸����Ŵ��ƻ��ļƻ�������
     * @Author liujinbo
     * @Date 2020/1/2
     * @param plan  ������planOrgan���·�planMonth,������ planIncrement
     * @Return void
     */
    void updatePlanAndPlanadj(TbPlan plan);

    /**
     * @Description ά��ʱ��ƻ��󣬸������߼ƻ��ļƻ�������
     * @Author liujinbo
     * @Date 2020/1/2
     * @param plan ������planOrgan���·�planMonth,������ planIncrement
     * @Return void
     */
    void updatePlanStripeAndPlanadjStripe(TbPlan plan);

    /*���޶��ϴ�����*/
     String uploadFile(MultipartFile file);
     HttpServletResponse downloadFile(String filePath, HttpServletResponse response);

    /**
     * ���ػ����ƻ�
     * @param response
     * @param request
     * @param organlevel
     */
    void downloadPlan(HttpServletResponse response, HttpServletRequest request, String organlevel) throws Exception;

    /**
     * �������߼ƻ�
     * @param response
     * @param request
     * @param organlevel
     */
    void downloadPlanStripe(HttpServletResponse response, HttpServletRequest request, String organlevel) throws Exception;

    /**
     * ��ȡ�ƻ�����map  ���ڶ������ִ���Ϊһ������   <upcombCode,amount>
     * @param tbPlanDetailList �ƻ�����list
     * @param combLevel list�Ĵ��ּ���
     * @return <upcomb,amount>
     */
    Map<String, BigDecimal> getPlanCombMapAndTransCombLevel(List<TbPlanDetail> tbPlanDetailList, int combLevel);

    /**
     * ��ȡ�ƻ�����map    list--> <upcombCode,amount>
     * @param tbPlanDetailList �ƻ�����list
     * @return <upcomb,amount>
     */
    Map<String, BigDecimal> getPlanCombMap(List<TbPlanDetail> tbPlanDetailList);

    /**
     * ��ȡ���и��������ƶ��ļƻ�  <combCode,amount>  ���ڴ��ּ��𲻴���
     *
     * @param planMonth
     * @param upOrganCode
     * @param organCode
     * @return
     */
    Map<String, BigDecimal> getUporganCombMap(String planMonth, String upOrganCode, String organCode);

    /**
     * ��ȡ���и��������ƶ��ļƻ� ���ڶ������ִ���Ϊһ������ <combCode,amount>
     *
     * @param planMonth
     * @param upOrganCode
     * @param organCode
     * @return
     */
    Map<String, BigDecimal> getUporganCombMapAndTransCombLevel(String planMonth, String upOrganCode, String organCode, int combLevel);


    /**
     * ��ȡ�ƻ���������map  ���ڶ������ִ���Ϊһ������   <upcombCode,amount>
     * @param tbPlanadjDetailList �ƻ���������list
     * @param combLevel list�Ĵ��ּ���
     * @return <upcomb,amount>
     */
    Map<String, BigDecimal> getPlanadjCombMapAndTransCombLevel(List<TbPlanadjDetail> tbPlanadjDetailList, int combLevel);

    /**
     * ��ȡ�ƻ���������map    list--> <upcombCode,amount>
     * @param tbPlanadjDetailList �ƻ���������list
     * @return <upcomb,amount>
     */
    Map<String, BigDecimal> getPlanadjCombMap(List<TbPlanadjDetail> tbPlanadjDetailList);

    /**
     * ���в鿴�ϼ��������Լ��ƶ��ļƻ�����
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectLowOrganIncrement(Map<String, Object> paramMap);
}
