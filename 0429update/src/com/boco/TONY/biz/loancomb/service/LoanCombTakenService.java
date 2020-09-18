package com.boco.TONY.biz.loancomb.service;

import com.boco.TONY.biz.loancomb.POJO.DTO.combtaken.CombTakenDetailDTO;
import com.boco.TONY.biz.loancomb.exception.LoanCombException;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;

/**
 * 信贷组合占用服务层
 *
 * @author tony
 * @describe LoanCombTakenService
 * @date 2019-10-27
 */
public interface LoanCombTakenService {
    /**
     * 组合占用
     *
     * @param gridData       占用规则
     * @param parentCombCode 父级贷种组合
     * @param takenType      贷种占用类型
     * @return 执行结果
     * @throws LoanCombException 贷种占用异常
     */
    PlainResult<String> takeLoanCombInfo(String gridData, String parentCombCode, int takenType) throws LoanCombException;

    /**
     * 通过父id查询贷种组合列表
     *
     * @param parentId 贷种组合父id
     * @return 贷种组合列表
     */
    ListResult<CombTakenDetailDTO> selectLoanCombTakenByParentId(String parentId);

    Integer selectInterTakentype();
    /**
     * 通过parentype查询takenype
     */
    Integer getTakenTypeByCombParent(String combCode);

}
