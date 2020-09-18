package com.boco.TONY.biz.weboper.service;

import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.biz.weboper.POJO.VO.WebOperRoleVO;
import com.boco.TONY.biz.line.POJO.DTO.ProductLineInfoDTO;

import java.util.List;

/**
 * 柜员角色业务逻辑层接口
 * @author tony
 * @describe IWebOperRoleService
 * @date 2019-09-07
 */
public interface IWebOperRoleService {
    /**更新柜员-角色关系*/
    PlainResult<String> updateOperAndRoleRelation(WebOperRoleVO webOperRoleVO);

    /**查询柜员的角色*/
    List<String> selectOwnRoleByOperCode(String operCode);

    /**选择拥有的角色*/
    ListResult<String> selectOwnRoleByOperCodeForListResult(String operCode);

    /**查询已经拥有的条线*/
    ListResult<ProductLineInfoDTO> selectProductLineInfo(String operCode);
}
