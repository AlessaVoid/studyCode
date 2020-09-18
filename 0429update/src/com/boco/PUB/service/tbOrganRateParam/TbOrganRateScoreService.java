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
 * 机构评分批次表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbOrganRateScoreService extends IGenericService<TbOrganRateScore, String>{

    /**
     * @Author: Liujinbo
     * @Description:  生成月度评分
     * @Date: 2020/2/3

     * @return: void
     **/
     void addMonthOragnRateScore() throws Exception;

     /**
      * @Author: Liujinbo
      * @Description:  生成季度评分
      * @Date: 2020/2/4

      * @return: void
      **/
    void addQuarterOrganRateScore() throws Exception;

    /**
     * @Author: Liujinbo
     * @Description:   修改月度评分
     * @Date: 2020/2/5
     * @param request :
     * @param operCode :
     * @param organCode :
     * @return: com.boco.TONY.common.PlainResult<java.lang.String>
     **/
    PlainResult<String> updateTbOrganRateScoreMonth(HttpServletRequest request, String operCode, String organCode) throws Exception;

    /**
     * @Author: Liujinbo
     * @Description:  修改季度评分
     * @Date: 2020/2/8
     * @param request :
     * @param operCode :
     * @param organCode :
     * @return: com.boco.TONY.common.PlainResult<java.lang.String>
     **/
    PlainResult<String> updateTbOrganRateScoreQuarter(HttpServletRequest request, String operCode, String organCode);

    /**
     * @Author: Liujinbo
     * @Description:  启动评分审批流程
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
     * @Description:   启动季度评分审批流程
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
     * @Description:   查询已提交的评分
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
     * @Description:  查询待审批的评分
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
     * @Description:  审批评分
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
     * @Description: 审批季度评分
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
     * @Description:   查询已审批的评分
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