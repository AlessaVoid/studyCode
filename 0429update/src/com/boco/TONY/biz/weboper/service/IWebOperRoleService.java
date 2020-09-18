package com.boco.TONY.biz.weboper.service;

import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.biz.weboper.POJO.VO.WebOperRoleVO;
import com.boco.TONY.biz.line.POJO.DTO.ProductLineInfoDTO;

import java.util.List;

/**
 * ��Ա��ɫҵ���߼���ӿ�
 * @author tony
 * @describe IWebOperRoleService
 * @date 2019-09-07
 */
public interface IWebOperRoleService {
    /**���¹�Ա-��ɫ��ϵ*/
    PlainResult<String> updateOperAndRoleRelation(WebOperRoleVO webOperRoleVO);

    /**��ѯ��Ա�Ľ�ɫ*/
    List<String> selectOwnRoleByOperCode(String operCode);

    /**ѡ��ӵ�еĽ�ɫ*/
    ListResult<String> selectOwnRoleByOperCodeForListResult(String operCode);

    /**��ѯ�Ѿ�ӵ�е�����*/
    ListResult<ProductLineInfoDTO> selectProductLineInfo(String operCode);
}
