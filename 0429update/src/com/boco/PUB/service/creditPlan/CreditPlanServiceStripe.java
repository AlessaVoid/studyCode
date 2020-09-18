package com.boco.PUB.service.creditPlan;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPlan;
import com.boco.TONY.common.PlainResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * �Ŵ��ƻ�Service
 * @Author zhuhongjiang
 * @Date  ����2:18
 **/
public interface CreditPlanServiceStripe extends IGenericService<TbPlan, String> {

    /**
     * ¼���Ŵ��ƻ�-��ҳ��ѯ�б�ҳ����
     * @Author zhuhongjiang
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectListByPage(Map<String, Object> paramMap) ;

    /**
     * ɾ���Ŵ��ƻ�
     * @param planId
     * @return
     */
    PlainResult<String> deleteCreditPlan(String planId);

    /**
     * �����Ŵ��ƻ�
     * @param request
     * @param operCode
     * @param organCode
     * @param organlevel
     * @param uporgan
     * @return
     */
    PlainResult<String> addCreditPlan(HttpServletRequest request, String operCode, String organCode, Integer planType, String organlevel, String uporgan);

    /**
     * �޸��Ŵ��ƻ�
     * @param request
     * @param operCode
     * @param organCode
     * @param organlevel
     * @param uporgan
     * @return
     */
    PlainResult<String> updateCreditPlan(HttpServletRequest request, String operCode, String organCode, String organlevel, String uporgan);

    /**
     * @Description�жϸ��·��Ƿ���ڼƻ�
     * @Author liujinbo
     * @Date 2019/12/13
     * @param organlevel
     * @param request
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> creditPlanJudgeMonth(String organlevel, HttpServletRequest request, String organCode);

    /**
     * @Description�жϸ��·��Ƿ���ڼƻ�--����ҳ��
     * @Author liujinbo
     * @Date 2019/12/13
     * @param request
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> creditPlanEnterJudgeMonth(HttpServletRequest request, String organCode);


}
