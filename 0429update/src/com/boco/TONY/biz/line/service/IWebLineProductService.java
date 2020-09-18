package com.boco.TONY.biz.line.service;

import com.boco.SYS.entity.FdOrgan;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDTO;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.line.POJO.DTO.ProductLineInfoDTO;
import com.boco.SYS.util.TreeNode;

import java.util.List;

/**
 * 条线产品业务类
 *
 * @author tony
 * @describe IWebLineProductService
 * @date 2019-09-23
 */
public interface IWebLineProductService {
    /**
     * 插入条线信息
     */
    PlainResult<String> insertProductLineInfo(String lineName, String description, String creator, String productIds, String organCode);

    /**
     * 更新条线信息
     */
    PlainResult<String> updateProductLineInfo(String lineId, String lineName, String description, String lineUpdater, String productIds);

    /**
     * 删除条线信息
     */
    PlainResult<String> deleteProductLineInfo(String lineId);

    /**
     * 获取所有条线 用于前端展示
     */
    ListResult<ProductLineInfoDTO> getProductLineInfoByOrganCode(String lineId, String lineName, String organCode);
    List<ProductLineInfoDO> getProductLineInfoByOrganCode2(ProductLineInfoDO productLineInfoDO);
    List<String> getProductLineInfoByOrganCode3(ProductLineInfoDO productLineInfoDO);

    /**
     * 通过条线编码获取条线信息
     */
    ProductLineInfoDTO getProductLineInfoByLineId(String lineId);

    /**
     * 根据条线标识查询条线
     */
    ListResult<LoanCombDTO> getProductLineById(String productId);

    /**
     * 模糊查询条线
     */
    void getProductLineInfoByName(String lineName);

    /**
     * 获取条线产品详情通过条线编码
     */
    ListResult<TreeNode> getProductLineDetailInfoByLineId(String lineId);

    /**
     * 非树构造,用于展示数据
     */
    ListResult<LoanCombDTO> getProductLineDetailInfoByLineIdWithoutTree(String lineId);


    /**
     * 根据组合名称获取贷种信息
     *
     * @param lineName 条线名称
     * @return
     */
    List<String> selectLineName(String lineName);


    List<String> selectLineNameByOrgan(String lineName, FdOrgan sessionOrgan);

    /**
     * 根据贷种编码获取贷种信息
     *
     * @param lineName 条线编码
     * @return
     */
    List<String> selectLineCode(String lineName);

    int countLineListSize();

}