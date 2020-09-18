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
 * �Ŵ��ƻ��������α�ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbPlanadjService extends IGenericService<TbPlanadj, String> {
    /**
     * @param sort
     * @param month
     * @Description ��ѯ¼���б�ҳ
     * @Author liujinbo
     * @Date 2019/11/23
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     */
    Map<String, Object> selectTbplanadjByMonth(String sort, String month, int pageNo, int pageSize);


    /**
     * @param planId
     * @param organCode
     * @param operCode
     * @param operName
     * @param assignee
     * @param comment
     * @Description �����Ŵ��ƻ�������
     * @Author liujinbo
     * @Date 2019/11/25
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> startLoanReqAuditProcess(String planadjId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception;

    /**
     * @param sort
     * @param operCode
     * @param auditStatus
     * @param reqMonth
     * @param sessionOperInfo
     * @Description ��ѯ���ύ���Ŵ��ƻ�����
     * @Author liujinbo
     * @Date 2019/11/25
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> getAuditRecordHistRecord(String sort, String operCode, String auditStatus, String reqMonth, WebOperInfo sessionOperInfo) throws Exception;

    /**
     * @param sort
     * @param operCode
     * @param reqMonth
     * @param auditStatus
     * @param pager
     * @Description ��ѯ���������Ŵ��ƻ�����
     * @Author liujinbo
     * @Date 2019/11/26
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> getPendingAppReq(String sort, String operCode, String reqMonth, String auditStatus, Pager pager) throws Exception;

    /**
     * @param taskId
     * @param comment
     * @param varMap
     * @param msgMap
     * @Description �����������
     * @Author liujinbo
     * @Date 2019/11/26
     * @Return void
     */
    void completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception;

    /**
     * @param sort
     * @param operCode
     * @param auditStatus
     * @param reqMonth
     * @Description ��ѯ���������Ŵ��ƻ�����
     * @Author liujinbo
     * @Date 2019/11/26
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> getApprovedRecord(String sort, String operCode, String auditStatus, String reqMonth) throws Exception;


    /**
     * @param planadjId
     * @Description ɾ���Ŵ��ƻ�����
     * @Author liujinbo
     * @Date 2019/11/28
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> deleteTbPlanadjDetail(String planadjId);

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author daice
     * @Description //��ȡ�������
     * @Date ����1:40 2019/11/29
     * @Param []
     **/
    List<Map<String, Object>> getCombList(int level);

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author daice
     * @Description //��ȡ�Ŵ��ƻ�
     * @Date ����2:24 2019/11/29
     * @Param [month]
     **/
    Map<String, Object> getCreditPlanDetail(String month) throws Exception;

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author daice
     * @Description //��ȡ�������������ϸ
     * @Date ����2:24 2019/11/29
     * @Param [month]
     **/
    Map<String, Object> getReqDetail(String month) throws Exception;

    /**
     * @return com.boco.TONY.common.PlainResult<java.lang.String>
     * @Author daice
     * @Description //�Ŵ��ƻ�������¼����
     * @Date ����5:54 2019/11/29
     * @Param [request, operCode, thiscode]
     **/
    PlainResult<String> savePlanadj(HttpServletRequest request, String operCode, String thiscode, String uporgan) throws Exception;

    /**
     * @return com.boco.TONY.common.PlainResult<java.lang.String>
     * @Author daice
     * @Description //�Ŵ��ƻ�������¼����
     * @Date ����8:40 2019/11/29
     * @Param [request, operCode, thiscode]
     **/
    PlainResult<String> updatePlanadj(HttpServletRequest request, String operCode, String thiscode, String uporgan) throws Exception;

    /**
     * @param tbPlanadjDetailList
     * @Description �ƻ��������������¼ƻ�
     * @Author liujinbo
     * @Date 2019/12/12
     * @param planadjId
     * @Return void
     */
    void updatePlanAndPlanadj(List<TbPlanadjDetail> tbPlanadjDetailList, String planadjId) throws Exception;

    /**
     * @param request
     * @param type
     * @param response
     * @param organlevel
     * @Description �����ƻ�����ģ��
     * @Author liujinbo
     * @Date 2020/3/16
     * @Return javax.servlet.http.HttpServletResponse
     */
    void downPlanadjTemplate(HttpServletRequest request, String type, HttpServletResponse response, String organlevel) throws Exception;

    /**
     * @param file
     * @param operCode
     * @param organCode
     * @param request
     * @param organlevel
     * @param uporgan
     * @Description ����ƻ�����
     * @Author liujinbo
     * @Date 2020/3/17
     * @Return java.util.Map<java.lang.String, java.lang.String>
     */
    Map<String, String> enterReportPlanadjByMonth(MultipartFile file, String operCode, String organCode, HttpServletRequest request, String organlevel, String uporgan);

    /**
     * ���ػ����ƻ�����
     *
     * @param response
     * @param request
     * @param organlevel
     */
    void downloadPlan(HttpServletResponse response, HttpServletRequest request, String organlevel) throws Exception;


    /**
     * ��ѯ�ƻ�����
     *
     * @param organ    �ƶ��ƻ��Ļ����� ����-������ �ƻ�-�ϼ�����
     * @param month    �·�
     * @param planType �ƻ����� 1-����  2-����  TbPlan.Plan
     * @return amount,   loantype
     */
    List<Map<String, Object>> getPlanDetail(String organ, String month, Integer planType);
}