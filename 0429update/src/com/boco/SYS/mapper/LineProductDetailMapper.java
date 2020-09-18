package com.boco.SYS.mapper;

import com.boco.TONY.biz.line.POJO.DO.ProductLineDetailDO;
import com.boco.TONY.biz.line.exception.LineProductDetailException;

import java.util.List;

/**
 * @author tony
 * @describe LineProductDetailMapper
 * @date 2019-09-23
 */
public interface LineProductDetailMapper {

    /**插入条线产品明细*/
    void insertProductLineDetail(ProductLineDetailDO productLineDetailsDO) throws LineProductDetailException;

    /**更新条线产品明细*/
    void updateProductLineDetailInfo(ProductLineDetailDO productLineDetailsDO) throws LineProductDetailException;

    /**删除条线产品明细*/
    void deleteCombProductByProductId(ProductLineDetailDO productLineDetailsDO)  throws LineProductDetailException;

    /**获取所有条线产品明细 用于前端展示*/
    List<ProductLineDetailDO> getAllProductLineDetail() throws LineProductDetailException;

    /**根据条线ID查询条线所包含的产品详情*/
    List<ProductLineDetailDO> getProductLineDetailById(String lineId) throws LineProductDetailException;
}
