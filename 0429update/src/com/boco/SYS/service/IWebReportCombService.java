package com.boco.SYS.service;

import com.boco.SYS.entity.TbReportComb;
import com.boco.SYS.util.TreeNode;
import com.boco.TONY.biz.loancomb.exception.LoanCombException;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 信贷产品业务逻辑层
 *
 * @author tony
 * @describe IWebLoanService
 * @date 2019-09-17
 */
public interface IWebReportCombService {
    /**
     * 通过贷种组合级别查询贷种组合信息
     */
    ListResult<TreeNode> getLoanCombInfoByLevel(int combLevel, boolean isLine);

    /**
     * 通过贷种组合级别查询贷种组合信息
     */
    ListResult<TbReportComb> getLoanCombInfoByLevel2(int combLevel);


    /**
     * 查询所有贷种组合信息列表
     */
    Map<String, Object> getAllLoanCombInfo3(String combCode, String combName, String combLevel, int pageNum, int pageSize);

    /**
     * 通过贷种组合编码查询贷种组合信息
     */
    PlainResult<TbReportComb> getLoanCombInfoByCombCode(String combCode);


    /**
     * 通过结合combCode列表生成贷种树
     */
    ListResult<TreeNode> getLoanCombDetailInfoByCombCode(String combCode, int combLevel);

    /**
     * 支持批量插入贷种组合
     */
    PlainResult<String> insertLoanCombInfo(String combCode, String combName, int combLevel, String combCreator, String productIds) throws LoanCombException;

    /**
     * 需要检查是否为叶子节点,如果不为叶子节点,转换成叶子节点
     */
    PlainResult<String> deleteLoanCombInfoByCombCode(String combCode, int combLevel);

    /**
     * 更新贷种组合信息新
     */
    PlainResult<String> updateLoanCombInfo(String combCode, int combLevel, String combName, String productIds, String combUpdater);

    /**
     * 通过贷种组合名称获取组合信息,匹配模糊查询
     */
    ListResult<TbReportComb> getLoanCombInfoByName(String name);

    /**
     * 通过贷种组合级别和机构获取贷种组合
     */
    ListResult<TreeNode> getLoanCombInfoByLevelAndOrganCode(int combLevel, String organCode) throws LoanCombException;

    /**
     * 通过贷种名称-联想
     */
    List<String> selectCombName(String combName);

    /**
     * 贷种编码-联想
     */
    List<String> selectCombCode(String combCode);

    /**
     * 贷种组合列表数目
     */
    @SuppressWarnings("unused")
    int countCombListSize();

    /**
     * 贷种组合检查
     */
    PlainResult<String> checkCombInfo(String combCode, String combName) throws LoanCombException;

    /**
     * 查询贷种
     * @param combMap
     * @return
     */
    List<Map<String, Object>> selectComb(Map<String, Object> combMap);

    /**
     * 查询被绑定的贷种
     * @param combMap
     * @return
     */
    List<Map<String, Object>> selectCombOfBind(Map<String, Object> combMap);

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
}
