package com.boco.TONY.biz.product.service;

import com.boco.TONY.biz.loancomb.POJO.DO.productbase.ProductDO;
import com.boco.TONY.biz.loancomb.POJO.DTO.productbase.ProductDTO;
import com.boco.TONY.biz.product.exception.LoanProductException;
import com.boco.TONY.common.ListResult;

import java.util.List;

/**
 * 信贷产品业务逻辑层接口
 *
 * @author tony
 * @describe ProductService
 * @date 2019-09-17
 */
public interface IWebProductService {

    /**
     * 获取所有贷种组合产品
     */
    ListResult<ProductDTO> getAllProductComb(String productCode, String productName, String productLevel, String productSystemId);

    /**
     * 获取所有贷种组合产品
     */
    List<ProductDO> getAllProductComb2(ProductDO productDO) throws LoanProductException;

    /**
     * 获取所有未划分贷种的产品
     */
    ListResult<ProductDTO> listAllAvailableProduct();

    /**
     * 获取所有已经划贷种的产品
     */
    ListResult<ProductDTO> listAllUnavailableCombProduct();

    /**
     * 获取已选中的贷种产品
     */
    List<ProductDO> getSelectedCombProduct(List<String> productCodeList);

    /**
     * 根据产品名称选择产品信息
     */

    List<String> selectProductName(String productName);

    /**
     * 根据产品id选择产品信息
     */

    List<String> selectProductCode(String productId);

    /**
     * 根据系统id选择产品信息
     */
    List<String> selectSystemId(String systemId);
}
