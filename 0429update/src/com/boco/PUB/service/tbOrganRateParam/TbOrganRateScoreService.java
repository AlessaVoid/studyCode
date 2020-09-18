package com.boco.PUB.service.tbOrganRateParam;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbOrganRateScore;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.util.Pager;
import com.boco.TONY.common.PlainResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * �����������α�ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbOrganRateScoreService extends IGenericService<TbOrganRateScore, String>{

    /**
     * @Author: Liujinbo
     * @Description:  �����¶�����
     * @Date: 2020/2/3

     * @return: void
     **/
     void addMonthOragnRateScore() throws Exception;

     /**
      * @Author: Liujinbo
      * @Description:  ���ɼ�������
      * @Date: 2020/2/4

      * @return: void
      **/
    void addQuarterOrganRateScore() throws Exception;

    /**
     * @Author: Liujinbo
     * @Description:   �޸��¶�����
     * @Date: 2020/2/5
     * @param request :
     * @param operCode :
     * @param organCode :
     * @return: com.boco.TONY.common.PlainResult<java.lang.String>
     **/
    PlainResult<String> updateTbOrganRateScoreMonth(HttpServletRequest request, String operCode, String organCode) throws Exception;

    /**
     * @Author: Liujinbo
     * @Description:  �޸ļ�������
     * @Date: 2020/2/8
     * @param request :
     * @param operCode :
     * @param organCode :
     * @return: com.boco.TONY.common.PlainResult<java.lang.String>
     **/
    PlainResult<String> updateTbOrganRateScoreQuarter(HttpServletRequest request, String operCode, String organCode);

    /**
     * @Author: Liujinbo
     * @Description:  ����������������
     * @Date: 2020/2/7
     * @param id :
     * @param organCode :
     * @param operCode :
     * @param operName :
     * @param auditOper :
     * @param comment
     * @return: com.boco.TONY.common.PlainResult<java.lang.String>
     **/
    PlainResult<String> startRateScoreAuditProcess(String id, String organCode, String operCode, String operName, String auditOper, String comment)throws Exception;
    /**
     * @Author: Liujinbo
     * @Description:   ��������������������
     * @Date: 2020/2/9
     * @param id :
     * @param organCode :
     * @param operCode :
     * @param operName :
     * @param auditOper :
     * @param comment
     * @return: com.boco.TONY.common.PlainResult<java.lang.String>
     **/
    PlainResult<String> startRateScoreAuditProcessQuarter(String id, String organCode, String operCode, String operName, String auditOper, String comment)throws Exception;

    /**
     * @Author: Liujinbo
     * @Description:   ��ѯ���ύ������
     * @Date: 2020/2/7
     * @param sort
     * @param operCode :
     * @param auditStatus :
     * @param rateMonth :
     * @param sessionOperInfo :
     * @param rateType :
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getAuditRecordHistRecord(String sort, String operCode, String auditStatus, String rateMonth, WebOperInfo sessionOperInfo, int rateType) throws Exception;

    /**
     * @Author: Liujinbo
     * @Description:  ��ѯ������������
     * @Date: 2020/2/7
     * @param sort
     * @param operCode :
     * @param rateMonth :
     * @param auditStatus :
     * @param pager :
     * @param rateType :
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getPendingAppReq(String sort, String operCode, String rateMonth, String auditStatus, Pager pager, int rateType) throws Exception;

    /**
     * @Author: Liujinbo
     * @Description:  ��������
     * @Date: 2020/2/7
     * @param taskId :
     * @param comment :
     * @param varMap :
     * @param msgMap :
     * @return: void
     **/
    void completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception;
    /**
     * @Author: Liujinbo
     * @Description: ������������
     * @Date: 2020/2/9
     * @param taskId :
     * @param comment :
     * @param varMap :
     * @param msgMap :
     * @return: void
     **/
    void completeTaskAuditQuarter(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception;

    /**
     * @Author: Liujinbo
     * @Description:   ��ѯ������������
     * @Date: 2020/2/7
     * @param sort
     * @param operCode :
     * @param auditStatus :
     * @param rateMonth :
     * @param rateType :
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getApprovedRecord(String sort, String operCode, String auditStatus, String rateMonth, int rateType)throws Exception;
}