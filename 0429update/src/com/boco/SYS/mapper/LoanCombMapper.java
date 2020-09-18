package com.boco.SYS.mapper;

import com.boco.TONY.biz.line.POJO.DO.ProductLineCombDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombStatusDO;
import com.boco.TONY.biz.loancomb.exception.LoanCombException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @describe LeanComposeMapper
 * @date 2019-09-17
 */
public interface LoanCombMapper {
    /**
     * ��ȡ���д������
     *
     * @return LoanCombDOList
     * @throws LoanCombException �쳣
     */
    List<LoanCombDO> getAllLoanCombInfoList(LoanCombDO loanCombDO) ;

    /**
     * ��ȡ�������combcode
     *
     * @return LoanCombDOList
     * @throws LoanCombException �쳣
     * @param loanCombDO
     */
    List<LoanCombDO> getCombCodeList(int loanCombDO) throws LoanCombException;


    /**
     * ��ѯ�������ͨ������
     *
     * @param combLevel ���ּ���
     * @return LoanCombDOList
     * @throws LoanCombException �쳣
     */
    List<LoanCombDO> getOrganLoanProductInfoByLevel(int combLevel) throws LoanCombException;

    /**
     * ��ѯ�������ͨ������
     *
     * @param combLevel ���ּ���
     * @return LoanCombDOList
     * @throws LoanCombException �쳣
     */
    List<LoanCombDO> getCombByLevel(int combLevel) throws LoanCombException;

    /**
     * ���߹�������������ϲ�ѯ�������
     *
     * @param productLineCombDO ���ּ���
     * @return LoanCombDOList
     * @throws LoanCombException �쳣
     */
    List<LoanCombDO> getLineLoanProductInfoByLevelAndOrganCode(ProductLineCombDO productLineCombDO) throws LoanCombException;

    /**
     * ͨ��id��ȡ���������Ϣ
     *
     * @param combCode ���ֱ���
     * @return
     * @throws LoanCombException
     */
    LoanCombDO getLoanCombInfoByCombCode(String combCode) throws LoanCombException;
    String getProdInfoByProdCode(String combCode) throws LoanCombException;
    /**
     * ͨ��id��ȡ�������name
     *
     * @param combCode ���ֱ���
     * @return
     * @throws LoanCombException
     */
    LoanCombDO getLoanCombNameByCombCode(String combCode) throws LoanCombException;

    /**
     * ͨ������������ƻ�ȡ�����Ϣ
     *
     * @param name ��������
     * @return LoanCombDOList
     * @throws LoanCombException �쳣
     */
    List<LoanCombDO> getLoanCombInfoByName(String name) throws LoanCombException;

    /**
     * ��Ӵ������
     *
     * @param loanCombDO
     * @throws LoanCombException
     */
    void insertLoanCombInfo(LoanCombDO loanCombDO) throws LoanCombException;

    /**
     * ��Ҫ����Ƿ�ΪҶ�ӽڵ�,�����ΪҶ�ӽڵ�,ת����Ҷ�ӽڵ�
     */
    void deleteLoanCombByCombCode(String combCode) throws LoanCombException;

    /**
     * ���´��ֲ�Ʒ״̬
     *
     * @param loanCombStatusDO ����״̬
     * @throws LoanCombException �쳣
     */
    void updateLoanCombStatus(LoanCombStatusDO loanCombStatusDO) throws LoanCombException;
    /**
     * ���´��ֲ�Ʒ״̬
     *
     * @param loanCombDO ����״̬
     * @throws LoanCombException �쳣
     */
    void updateLoanCombInfo(LoanCombDO loanCombDO) throws LoanCombException;
    /**
     * ����������ƻ�ȡ������Ϣ
     *
     * @param combName ��������
     * @return
     */
    List<String> selectCombName(String combName);

    /**
     * ����������ƻ�ȡ������Ϣ
     *
     * @param combName ��������
     * @return
     */
    LoanCombDO selectExactlyCombName(String combName);

    /**
     * ���ݴ��ֱ����ȡ������Ϣ
     *
     * @param combCode ���ֱ���
     * @return
     */
    List<String> selectCombCode(String combCode);

    /**
     * �������������б���������
     *
     * @return
     */
    int countCombListSize();

    /**
     * ��ѯ����
     * @param map
     * @return
     */
    List<Map<String, Object>> selectComb(Map<String, Object> map);

    /**
     * @Description ���ݹ�Ա�󶨵����߲�ѯ����
     * @Author liujinbo
     * @Date 2019/12/27
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectCombByOpercode(HashMap<String, Object> map);
    /**
     * @Description ���ݴ��ֺŲ�ѯ����
     * @Author liujinbo
     * @Date 2020/1/6 
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectCombBycombcode(HashMap<String, Object> map);
    /**
     * ��ѯ���󶨵Ĵ���
     * @param map
     * @return
     */
    List<Map<String, Object>> selectCombOfBind(Map<String, Object> combMap);


}
