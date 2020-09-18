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
     * 获取所有贷种组合
     *
     * @return LoanCombDOList
     * @throws LoanCombException 异常
     */
    List<LoanCombDO> getAllLoanCombInfoList(LoanCombDO loanCombDO) ;

    /**
     * 获取贷种组合combcode
     *
     * @return LoanCombDOList
     * @throws LoanCombException 异常
     * @param loanCombDO
     */
    List<LoanCombDO> getCombCodeList(int loanCombDO) throws LoanCombException;


    /**
     * 查询贷种组合通过条件
     *
     * @param combLevel 贷种级别
     * @return LoanCombDOList
     * @throws LoanCombException 异常
     */
    List<LoanCombDO> getOrganLoanProductInfoByLevel(int combLevel) throws LoanCombException;

    /**
     * 查询贷种组合通过条件
     *
     * @param combLevel 贷种级别
     * @return LoanCombDOList
     * @throws LoanCombException 异常
     */
    List<LoanCombDO> getCombByLevel(int combLevel) throws LoanCombException;

    /**
     * 条线关联三级贷种组合查询贷种组合
     *
     * @param productLineCombDO 贷种级别
     * @return LoanCombDOList
     * @throws LoanCombException 异常
     */
    List<LoanCombDO> getLineLoanProductInfoByLevelAndOrganCode(ProductLineCombDO productLineCombDO) throws LoanCombException;

    /**
     * 通过id获取贷种组合信息
     *
     * @param combCode 贷种编码
     * @return
     * @throws LoanCombException
     */
    LoanCombDO getLoanCombInfoByCombCode(String combCode) throws LoanCombException;
    String getProdInfoByProdCode(String combCode) throws LoanCombException;
    /**
     * 通过id获取贷种组合name
     *
     * @param combCode 贷种编码
     * @return
     * @throws LoanCombException
     */
    LoanCombDO getLoanCombNameByCombCode(String combCode) throws LoanCombException;

    /**
     * 通过贷种组合名称获取组合信息
     *
     * @param name 贷种名称
     * @return LoanCombDOList
     * @throws LoanCombException 异常
     */
    List<LoanCombDO> getLoanCombInfoByName(String name) throws LoanCombException;

    /**
     * 添加贷种组合
     *
     * @param loanCombDO
     * @throws LoanCombException
     */
    void insertLoanCombInfo(LoanCombDO loanCombDO) throws LoanCombException;

    /**
     * 需要检查是否为叶子节点,如果不为叶子节点,转换成叶子节点
     */
    void deleteLoanCombByCombCode(String combCode) throws LoanCombException;

    /**
     * 更新贷种产品状态
     *
     * @param loanCombStatusDO 贷种状态
     * @throws LoanCombException 异常
     */
    void updateLoanCombStatus(LoanCombStatusDO loanCombStatusDO) throws LoanCombException;
    /**
     * 更新贷种产品状态
     *
     * @param loanCombDO 贷种状态
     * @throws LoanCombException 异常
     */
    void updateLoanCombInfo(LoanCombDO loanCombDO) throws LoanCombException;
    /**
     * 根据组合名称获取贷种信息
     *
     * @param combName 贷种名称
     * @return
     */
    List<String> selectCombName(String combName);

    /**
     * 根据组合名称获取贷种信息
     *
     * @param combName 贷种名称
     * @return
     */
    LoanCombDO selectExactlyCombName(String combName);

    /**
     * 根据贷种编码获取贷种信息
     *
     * @param combCode 贷种编码
     * @return
     */
    List<String> selectCombCode(String combCode);

    /**
     * 计算出贷种组合列表数据总数
     *
     * @return
     */
    int countCombListSize();

    /**
     * 查询贷种
     * @param map
     * @return
     */
    List<Map<String, Object>> selectComb(Map<String, Object> map);

    /**
     * @Description 根据柜员绑定的条线查询贷种
     * @Author liujinbo
     * @Date 2019/12/27
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectCombByOpercode(HashMap<String, Object> map);
    /**
     * @Description 根据贷种号查询贷种
     * @Author liujinbo
     * @Date 2020/1/6 
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectCombBycombcode(HashMap<String, Object> map);
    /**
     * 查询被绑定的贷种
     * @param map
     * @return
     */
    List<Map<String, Object>> selectCombOfBind(Map<String, Object> combMap);


}
