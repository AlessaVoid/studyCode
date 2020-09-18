package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.TONY.biz.loanplan.exception.TBPlanException;
import com.boco.TONY.biz.loanreq.POJO.DO.TbReqPlanInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @describe LoanPlanMapper
 * @date 2019-09-29
 */
public interface TbPlanMapper extends GenericMapper<TbPlan, String> {

    /**
     * �����Ŵ��ƻ�
     *
     * @param tbPlanDO �Ŵ��ƻ���Ϣ
     */
    void insertLoanPlanInfo(TbPlan tbPlanDO);

    /**
     * �����Ŵ��ƻ�
     *
     * @param tbPlanDO �Ŵ��ƻ���Ϣ
     */
    void updateLoanPlanInfo(TbPlan tbPlanDO);

    /**
     * ɾ���Ŵ��ƻ�
     *
     * @param planId �Ŵ��ƻ���ʶ
     */
    void deleteLoanPlanInfo(String planId);

    /**
     * ͨ���Ŵ��ƻ�ID��ѯ�Ŵ��ƻ�
     *
     * @param planId �Ŵ��ƻ���ʶ
     * @return TbPlanDO
     */
    TbPlan selectLoanPlanByPlanId(String planId);
    /**
     * ͨ���Ŵ��ƻ�Month��ѯ�Ŵ��ƻ�
     *
     * @param planMonth �Ŵ��ƻ�Month
     * @return TbPlanDO
     */
    List<TbPlan>  selectLoanPlanByPlanMonth(String planMonth);

    /**
     * ��ѯ�����Ŵ��ƻ�
     *
     * @return List<TbPlanDO>
     * @throws TBPlanException �쳣
     */
    List<TbPlan> selectAllLoanPlanInfo(TbPlan tbPlanDO) throws TBPlanException;

    /**
     * ��ѯ�Ŵ�,��������ƶ��ƻ�
     *
     * @return List<TbReqPlanInfo>
     */
    List<TbReqPlanInfo> selectReqPlanInfo();

    /**
     * �����Ŵ��ƻ�״̬
     *
     * @param tbPlanDO �Ŵ��ƻ���ʶ
     */
    void updateLoanPlanState(TbPlan tbPlanDO);
    /**
     * �����Ŵ��ƻ�״̬
     *
     * @param tbPlanDO �Ŵ��ƻ���ʶ
     */
    void updateLoanPlanAdjustmentState(TbPlan tbPlanDO);

    /**
     * ͨ����Ա��ѯ�Ѿ��ƶ������Ѿ�������ɵ��Ŵ��ƻ�
     *
     * @param planOper �Ŵ��ƻ���Ա
     * @return List<TbPlanDO>
     */
    List<TbPlan> selectLoanPlanByPlanOper(String planOper);


    /**
     * ��������-�ƻ����
     *
     * @param planId �ƻ����
     * @return
     */
    List<String> selectByPlanId(String planId);

    /**
     * ��������-�ƻ��·�
     *
     * @param planMonth �ƻ��·�
     * @return
     */
    List<String> selectByPlanMonth(String planMonth);

    /**
     * @Description ��ѯ�Ŵ��ƻ�����
     * @Author liujinbo
     * @Date 2019/11/19
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getAuditRecordHist(Map<String, Object> map);

    /**
     * @Description ��ѯ���������Ŵ��ƻ�
     * @Author liujinbo
     * @Date 2019/11/19
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getPendingAppReq(Map<String, Object> map);

    /**
     * @Description ��ѯ���������Ŵ��ƻ�
     * @Author liujinbo
     * @Date 2019/11/20
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);


    /**
     * @Description ��ѯ�Ŵ��ƻ�����  ¼�����
     * @Author liujinbo
     * @Date 2019/11/20
     * @param queryMap
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getFdOrganPlanInfoListNotPlanId(HashMap<String, String> queryMap);

    /**
     * @Description ��ѯ�Ŵ��ƻ��б����
     * @Author liujinbo
     * @Date 2019/11/21
     * @param tbPlanDO
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectTbPlanAndTradeParam(Map<String, Object> map);

    /**
     * @Description ��ȡ�ƻ����������
     * @Author liujinbo
     * @Date 2019/11/23
     * @param
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getPlanCol();


    /**
     * ��ҳ��ѯ¼���б�ҳ
     * @Author zhuhongjiang
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectListByPage(Map<String, Object> paramMap);

    //��ѯ�����Ŵ��ƻ�
    List<Map<String, Object>> selectAll();

    /**
     * @Description ���¼ƻ�����
     * @Author liujinbo
     * @Date 2019/12/12
     * @param tbPlanDetail
     * @Return void
     */
    void updatePlanDetail(TbPlanDetail tbPlanDetail);

    List<TbPlan> selectByMonth(TbPlan tbPlan);

    void updatePlan(TbPlan tbPlan);

    /**
     * @Description ��ѯ���мƻ�������
     * @Author liujinbo
     * @Date 2020/1/6
     * @param planQueryMap
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectPlanOrganIncreament(HashMap<String, Object> planQueryMap);

    /**
     * @Description ��ѯ�û������ϼ��������û����ƶ��ļƻ����ܺ�
     * @Author liujinbo
     * @Date 2020/4/2
     * @param paramMap
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectUporganPlan(Map<String, Object> paramMap);

    /**
     * ���в鿴�ϼ��������Լ��ƶ��ļƻ�����
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectLowOrganIncrement(Map<String, Object> paramMap);
}
