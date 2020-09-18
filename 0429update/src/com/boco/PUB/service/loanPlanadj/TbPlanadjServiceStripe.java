package com.boco.PUB.service.loanPlanadj;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPlanadj;
import com.boco.SYS.entity.TbPlanadjDetail;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.util.Pager;
import com.boco.TONY.common.PlainResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * �Ŵ��ƻ��������α�ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbPlanadjServiceStripe extends IGenericService<TbPlanadj, String>{
    /**
     * @Description ��ѯ¼���б�ҳ
     * @Author liujinbo
     * @Date 2019/11/23
     * @param sort
     * @param month
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String, Object> selectTbplanadjByMonth(String sort, String month, int pageNo, int pageSize);


    /**
     * @Description �����Ŵ��ƻ�������
     * @Author liujinbo
     * @Date 2019/11/25
     * @param planId
     * @param organCode
     * @param operCode
     * @param operName
     * @param assignee
     * @param comment
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> startLoanReqAuditProcess(String planadjId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception;

    /**
     * @Description ��ѯ���ύ���Ŵ��ƻ�����
     * @Author liujinbo
     * @Date 2019/11/25
     * @param sort
     * @param operCode
     * @param auditStatus
     * @param reqMonth
     * @param sessionOperInfo
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> getAuditRecordHistRecord(String sort, String operCode, String auditStatus, String reqMonth, WebOperInfo sessionOperInfo) throws Exception;

    /**
     * @Description ��ѯ���������Ŵ��ƻ�����
     * @Author liujinbo
     * @Date 2019/11/26
     * @param sort
     * @param operCode
     * @param reqMonth
     * @param auditStatus
     * @param pager
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getPendingAppReq(String sort, String operCode, String reqMonth, String auditStatus, Pager pager) throws Exception;

    /**
     * @Description �����������
     * @Author liujinbo
     * @Date 2019/11/26
     * @param taskId
     * @param comment
     * @param varMap
     * @param msgMap
     * @Return void
     */
    void completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap)  throws Exception;

    /**
     * @Description ��ѯ���������Ŵ��ƻ�����
     * @Author liujinbo
     * @Date 2019/11/26
     * @param sort
     * @param operCode
     * @param auditStatus
     * @param reqMonth
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getApprovedRecord(String sort, String operCode, String auditStatus, String reqMonth) throws Exception;


    /**
     * @Description ɾ���Ŵ��ƻ�����
     * @Author liujinbo
     * @Date 2019/11/28
     * @param planadjId
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> deleteTbPlanadjDetail(String planadjId);

    /**
     * @Author daice
     * @Description //��ȡ�������
     * @Date ����1:40 2019/11/29
     * @Param []
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getCombList(int level);

    /**
     * @Author daice
     * @Description //��ȡ�Ŵ��ƻ�
     * @Date ����2:24 2019/11/29
     * @Param [month]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> getCreditPlanDetail(String month) throws Exception;

    /**
     * @Author daice
     * @Description //��ȡ�������������ϸ
     * @Date ����2:24 2019/11/29
     * @Param [month]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> getReqDetail(String month)throws Exception;

    /**
     * @Author daice
     * @Description //�Ŵ��ƻ�������¼����
     * @Date ����5:54 2019/11/29
     * @Param [request, operCode, thiscode]
     * @return com.boco.TONY.common.PlainResult<java.lang.String>
     **/
    PlainResult<String> savePlanadj(HttpServletRequest request, String operCode, String thiscode, String uporgan)  throws Exception;

    /**
     * @Author daice
     * @Description //�Ŵ��ƻ�������¼����
     * @Date ����8:40 2019/11/29
     * @Param [request, operCode, thiscode]
     * @return com.boco.TONY.common.PlainResult<java.lang.String>
     **/
    PlainResult<String> updatePlanadj(HttpServletRequest request, String operCode, String thiscode, String uporgan) throws Exception;
    /**
     * @Description �ƻ��������������¼ƻ�
     * @Author liujinbo
     * @Date 2019/12/12
     * @param tbPlanadjDetailList
     * @param planadjId
     * @Return void
     */
    void updatePlanAndPlanadj(List<TbPlanadjDetail> tbPlanadjDetailList, String planadjId);

    /**
     * @Description ����ƻ�����
     * @Author liujinbo
     * @Date 2020/3/17
     * @param file
     * @param operCode
     * @param organCode
     * @param request
     * @param organlevel
     * @param uporgan
     * @Return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String, String> enterReportPlanadjByMonth(MultipartFile file, String operCode, String organCode, HttpServletRequest request, String organlevel, String uporgan);

    /**
     *�����ƻ�����
     * @param response
     * @param request
     * @param organlevel
     */
    void downloadPlan(HttpServletResponse response, HttpServletRequest request, String organlevel) throws Exception;
}