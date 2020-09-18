package com.boco.TONY.biz.loanplan.service;

import com.boco.SYS.entity.FdOrgan;
import com.boco.TONY.biz.loanplan.POJO.DO.FdOrganPlanInfo;
import com.boco.SYS.entity.TbPlan;
import com.boco.TONY.biz.loanplan.POJO.DTO.TbPlanDetailDTO;
import com.boco.TONY.biz.loanplan.exception.TBPlanException;
import com.boco.TONY.biz.loanreq.POJO.DO.TbReqPlanInfo;
import com.boco.TONY.biz.planadjust.POJO.DO.TbPlanAdjustDetailDO;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * �Ŵ��ƻ��ƶ�ҵ���߼���ӿ�
 *
 * @author tony
 * @describe ILoanPlanService
 * @date 2019-09-29
 */
public interface ILoanPlanService {
    /**
     * �����Ŵ��ƻ�
     *
     * @param request HTTP REQUEST
     * @return PlainResult
     * @throws TBPlanException �Ŵ�����ƻ��쳣
     */
    PlainResult<String> insertLoanPlan(HttpServletRequest request, String operCode, String organCode) throws TBPlanException;

    /**
     * ��ȡ����������Ϣ
     *
     * @return ������Ϣ
     */
    ListResult<TbReqPlanInfo> getAllTbReqSpInfo();

    /**
     * �����Ŵ��ƻ�
     *
     * @param request HTTP
     * @return PlainResult
     * @throws TBPlanException �Ŵ��ƻ��쳣
     */
    PlainResult<String> updateLoanPlanInfo(HttpServletRequest request, String operCode) throws TBPlanException;

    /**
     * ɾ���Ŵ��ƻ�
     *
     * @param planId planId
     * @return PlainResult
     * @throws TBPlanException �Ŵ��ƻ��쳣
     */
    PlainResult<String> deleteLoanPlanInfo(String planId) throws TBPlanException;

    /**
     * ͨ��ID��ѯ�Ŵ��ƻ�
     *
     * @param planId �Ŵ�Id
     * @return ��ѯ�Ŵ��ƻ�
     * @throws TBPlanException �Ŵ��쳣
     */
    PlainResult<TbPlan> selectLoanPlanByPlanId(String planId) throws TBPlanException;

    /**
     * ͨ���·ݲ�ѯ�Ŵ��ƻ�
     *
     * @param planMonth �Ŵ�Id
     * @return ��ѯ�Ŵ��ƻ�
     * @throws TBPlanException �Ŵ��쳣
     */
    ListResult<TbPlan> selectLoanPlanByPlanMonth(String planMonth) throws TBPlanException;

    /**
     * ��ѯ�����Ŵ��ƻ�
     *
     * @return ��ѯ�Ŵ��ƻ�
     * @throws TBPlanException �Ŵ��쳣
     */
    Map<String, Object> selectAllLoanPlanInfo(String organCode, int pageNo, int pageSize) throws TBPlanException;

    /**
     * ��ѯ�Ŵ��ƻ���ϸ��Ϣ
     *
     * @param planId �Ŵ��ƻ�����ID
     * @return �Ŵ��ƻ���ϸ
     */
    PlainResult<TbPlanDetailDTO> selectPlanDetailByPlanId(String planId);

    /**
     * �����Ŵ��ƻ�����
     *
     * @param planId �Ŵ��ƻ�id
     * @return ���½��
     */
    PlainResult<String> getAdjustPlanDetailInfoByPlanId(String planId);

    /**
     * �����Ŵ��ƻ�����
     *
     * @param tbPlanAdjustDetail �Ŵ��ƻ�����VO
     * @return ���½��
     */
    PlainResult<String> adjustPlanDetailInfo(TbPlanAdjustDetailDO tbPlanAdjustDetail);

    /**
     * ��ʼ���Ŵ��ƻ�
     *
     * @param planId      �ƻ�ID
     * @param fdOrganList ������Ϣ�б�
     * @return List
     */
    List<FdOrganPlanInfo> initPlanDetailOrganInfo(String planId, List<FdOrgan> fdOrganList);

    /**
     * ��ʼ���Ŵ��ƻ�����
     *
     * @param planId      �ƻ�ID
     * @param fdOrganList ������Ϣ�б�
     * @return List
     */
    List<FdOrganPlanInfo> initPlanDetailAdjustOrganInfo(String planId, List<FdOrgan> fdOrganList);

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
     * ���ݼƻ�id��ѯ�ƻ���������
     *
     * @param planId �ƻ�����
     * @return
     */
    List<TbPlanAdjustDetailDO> selectLoanPlanAdjustmentInfoById(String planId);


    /**
     * @Description ����tbplandetail
     * @Author liujinbo
     * @Date 2019/11/20
     * @param request
     * @param operCode
     * @param thiscode
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> updateLoanPlan(HttpServletRequest request, String operCode, String organCode);

    /**
     * @Description ��ȡ�Ŵ��ƻ�ҳ�����ֵ
     * @Author liujinbo
     * @Date 2019/11/20
     * @param planId
     * @param thiscode
     * @Return java.util.List<com.boco.TONY.biz.loanplan.POJO.DO.FdOrganPlanInfo>
     */
    List<Map<String,Object>> getFdOrganPlanInfoList(String planId, String thiscode);

    /**
     * @Description ��ȡ�Ŵ��ƻ�ҳ�����ֵ ¼���Ŵ��ƻ�
     * @Author liujinbo
     * @Date 2019/11/20
     * @param thiscode
     * @Return java.util.List<com.boco.TONY.biz.loanplan.POJO.DO.FdOrganPlanInfo>
     */
    List<Map<String,Object>> getFdOrganPlanInfoListNotPlanId(String thiscode);

/**
 * @Description  ��ѯ�б��������
 * @Author liujinbo
 * @Date 2019/11/21
 * @param organCode
 * @param pageNo
 * @param pageSize
 * @Return java.util.Map<java.lang.String,java.lang.Object>
 */
    Map<String, Object> selectTbPlanAndTradeParam(String organCode, int pageNo, int pageSize) ;
}
