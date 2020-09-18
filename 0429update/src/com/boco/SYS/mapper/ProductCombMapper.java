package com.boco.SYS.mapper;

import com.boco.TONY.biz.loancomb.POJO.DO.productbase.ProductDO;
import com.boco.TONY.biz.loancomb.POJO.DO.productbase.ProductStatusDO;
import com.boco.TONY.biz.product.exception.LoanProductException;

import java.util.List;

/**
 * 信贷产品DAO
 *
 * @author tony
 * @describe ProductMapper
 * @date 2019-09-17
 */
public interface ProductCombMapper {
    /**
     * 获取所有产品信息
     */
    List<ProductDO> getAllCombProductInfo(ProductDO productDO) throws LoanProductException;

    /**
     * 列出所有未被组合的产品
     */
    List<ProductDO> getAllAvailableCombProduct() throws LoanProductException;

    /**
     * 获取所有已选中的产品
     *
     * @param productCode 获取所有选中的产品
     * @return 所有已选中的产品
     */
    ProductDO getSelectedCombProduct(String productCode);

    /**
     * 更新信贷产品占用状态
     *
     * @param productStatusDO 信贷状态DO
     * @throws LoanProductException 异常
     */
    void updateCombProductStatus(ProductStatusDO productStatusDO) throws LoanProductException;

    /**
     * 列出已经被占用的产品
     */
    List<ProductDO> getAllUnavailableCombProduct() throws LoanProductException;


    /**
     * 根据产品名称选择产品信息
     *
     * @return
     */

    List<String> selectProductName(String productName);

    /**
     * 根据产品名称选择产品信息
     *
     * @return
     */

    List<String> selectProductCode(String productCode);

    /**
     * 根据系统id选择产品信息
     *
     * @return
     */
    List<String> selectSystemId(String systemId);


    List<ProductDO> getAllCombProduct(ProductDO productDO);

    List<String> selectProdCode(String productCode);
}
