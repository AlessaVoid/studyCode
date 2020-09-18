package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbReportComb;
import com.boco.TONY.biz.line.POJO.DO.ProductLineCombDO;
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
public interface ReportCombMapper  extends GenericMapper<TbReportComb, String> {
    /**
     * 获取所有贷种组合
     *
     * @return LoanCombDOList
     * @throws LoanCombException 异常
     */
    List<TbReportComb> getAllLoanCombInfoList(TbReportComb tbReportComb) ;

    /**
     * 获取贷种组合combcode
     *
     * @return LoanCombDOList
     * @throws LoanCombException 异常
     * @param loanCombDO
     */
    List<TbReportComb> getCombCodeList(int loanCombDO) throws LoanCombException;


    /**
     * 查询贷种组合通过条件
     *
     * @param combLevel 贷种级别
     * @return LoanCombDOList
     * @throws LoanCombException 异常
     */
    List<TbReportComb> getOrganLoanProductInfoByLevel(int combLevel) throws LoanCombException;

    /**
     * 查询贷种组合通过条件
     *
     * @param combLevel 贷种级别
     * @return LoanCombDOList
     * @throws LoanCombException 异常
     */
    List<TbReportComb> getCombByLevel(int combLevel) throws LoanCombException;

    /**
     * 条线关联三级贷种组合查询贷种组合
     *
     * @param productLineCombDO 贷种级别
     * @return LoanCombDOList
     * @throws LoanCombException 异常
     */
    List<TbReportComb> getLineLoanProductInfoByLevelAndOrganCode(ProductLineCombDO productLineCombDO) throws LoanCombException;

    /**
     * 通过id获取贷种组合信息
     *
     * @param combCode 贷种编码
     * @return
     * @throws LoanCombException
     */
    TbReportComb getLoanCombInfoByCombCode(String combCode) throws LoanCombException;
    String getProdInfoByProdCode(String combCode) throws LoanCombException;
    /**
     * 通过id获取贷种组合name
     *
     * @param combCode 贷种编码
     * @return
     * @throws LoanCombException
     */
    TbReportComb getLoanCombNameByCombCode(String combCode) throws LoanCombException;

    /**
     * 通过贷种组合名称获取组合信息
     *
     * @param name 贷种名称
     * @return LoanCombDOList
     * @throws LoanCombException 异常
     */
    List<TbReportComb> getLoanCombInfoByName(String name) throws LoanCombException;

    /**
     * 添加贷种组合
     *
     * @param loanCombDO
     * @throws LoanCombException
     */
    void insertLoanCombInfo(TbReportComb loanCombDO) throws LoanCombException;

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
    void updateLoanCombInfo(TbReportComb loanCombDO) throws LoanCombException;
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
    TbReportComb selectExactlyCombName(String combName);

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

    /**
     * 通过一级贷种 查出所属该一级贷种的所有贷种（含自身) ，查询不包含某个贷种
     * @param map
     * @return
     */
    List<Map<String, Object>> getCombAllByLevelOne(HashMap<String, Object> map);

    /**
     * 产品表--查询机构计划，汇总到一级
     * @param param
     * @return
     */
    List<Map<String, Object>> getPlanByLevelOne(HashMap<String, Object> param);

    /**
     * 产品表--查询机构计划，汇总到二级
     * @param param
     * @return
     */
    List<Map<String, Object>> getPlanByLevelTwo(HashMap<String, Object> param);

    /**
     * 查询三级贷种的一级贷种
     * @return
     */
    List<Map<String, String>> getThreeToOneMap();

    /**
     * 查询三级贷种的二级贷种
     * @return
     */
    List<Map<String, String>> getThreeToTwoMap();

    /**
     * 机构表--查询机构计划，汇总到一级
     * @param param
     * @return
     */
    List<Map<String, Object>> getOrganPlanByLevelOne(HashMap<String, Object> param);

    /**
     * 机构表--查询机构计划，汇总到二级
     * @param param
     * @return
     */
    List<Map<String, Object>> getOrganPlanByLevelTwo(HashMap<String, Object> param);

    /**
     * 根据机构分组，查询计划
     * @param param
     * @return
     */
    List<Map<String, Object>> getOrganPlan(HashMap<String, Object> param);

}

