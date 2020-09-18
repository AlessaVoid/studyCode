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
 * 信贷计划调整批次表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbPlanadjService extends IGenericService<TbPlanadj, String> {
    /**
     * @param sort
     * @param month
     * @Description 查询录入列表页
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
     * @Description 启动信贷计划审批流
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
     * @Description 查询已提交的信贷计划调整
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
     * @Description 查询待审批的信贷计划调整
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
     * @Description 完成任务流程
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
     * @Description 查询已审批的信贷计划调整
     * @Author liujinbo
     * @Date 2019/11/26
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> getApprovedRecord(String sort, String operCode, String auditStatus, String reqMonth) throws Exception;


    /**
     * @param planadjId
     * @Description 删除信贷计划调整
     * @Author liujinbo
     * @Date 2019/11/28
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> deleteTbPlanadjDetail(String planadjId);

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author daice
     * @Description //获取贷种组合
     * @Date 下午1:40 2019/11/29
     * @Param []
     **/
    List<Map<String, Object>> getCombList(int level);

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author daice
     * @Description //获取信贷计划
     * @Date 下午2:24 2019/11/29
     * @Param [month]
     **/
    Map<String, Object> getCreditPlanDetail(String month) throws Exception;

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author daice
     * @Description //获取分行需求调整明细
     * @Date 下午2:24 2019/11/29
     * @Param [month]
     **/
    Map<String, Object> getReqDetail(String month) throws Exception;

    /**
     * @return com.boco.TONY.common.PlainResult<java.lang.String>
     * @Author daice
     * @Description //信贷计划调整记录保存
     * @Date 下午5:54 2019/11/29
     * @Param [request, operCode, thiscode]
     **/
    PlainResult<String> savePlanadj(HttpServletRequest request, String operCode, String thiscode, String uporgan) throws Exception;

    /**
     * @return com.boco.TONY.common.PlainResult<java.lang.String>
     * @Author daice
     * @Description //信贷计划调整记录更新
     * @Date 下午8:40 2019/11/29
     * @Param [request, operCode, thiscode]
     **/
    PlainResult<String> updatePlanadj(HttpServletRequest request, String operCode, String thiscode, String uporgan) throws Exception;

    /**
     * @param tbPlanadjDetailList
     * @Description 计划调整审批完后更新计划
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
     * @Description 导出计划调整模板
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
     * @Description 导入计划调整
     * @Author liujinbo
     * @Date 2020/3/17
     * @Return java.util.Map<java.lang.String, java.lang.String>
     */
    Map<String, String> enterReportPlanadjByMonth(MultipartFile file, String operCode, String organCode, HttpServletRequest request, String organlevel, String uporgan);

    /**
     * 下载机构计划调整
     *
     * @param response
     * @param request
     * @param organlevel
     */
    void downloadPlan(HttpServletResponse response, HttpServletRequest request, String organlevel) throws Exception;


    /**
     * 查询计划详情
     *
     * @param organ    制定计划的机构号 条线-本机构 计划-上级机构
     * @param month    月份
     * @param planType 计划类型 1-机构  2-条线  TbPlan.Plan
     * @return amount,   loantype
     */
    List<Map<String, Object>> getPlanDetail(String organ, String month, Integer planType);
}