package com.boco.SYS.mapper;

import com.boco.TONY.biz.loancomb.POJO.DO.productbase.ProductDO;
import com.boco.TONY.biz.loancomb.POJO.DO.productbase.ProductStatusDO;
import com.boco.TONY.biz.product.exception.LoanProductException;

import java.util.List;

/**
 * �Ŵ���ƷDAO
 *
 * @author tony
 * @describe ProductMapper
 * @date 2019-09-17
 */
public interface ProductCombMapper {
    /**
     * ��ȡ���в�Ʒ��Ϣ
     */
    List<ProductDO> getAllCombProductInfo(ProductDO productDO) throws LoanProductException;

    /**
     * �г�����δ����ϵĲ�Ʒ
     */
    List<ProductDO> getAllAvailableCombProduct() throws LoanProductException;

    /**
     * ��ȡ������ѡ�еĲ�Ʒ
     *
     * @param productCode ��ȡ����ѡ�еĲ�Ʒ
     * @return ������ѡ�еĲ�Ʒ
     */
    ProductDO getSelectedCombProduct(String productCode);

    /**
     * �����Ŵ���Ʒռ��״̬
     *
     * @param productStatusDO �Ŵ�״̬DO
     * @throws LoanProductException �쳣
     */
    void updateCombProductStatus(ProductStatusDO productStatusDO) throws LoanProductException;

    /**
     * �г��Ѿ���ռ�õĲ�Ʒ
     */
    List<ProductDO> getAllUnavailableCombProduct() throws LoanProductException;


    /**
     * ���ݲ�Ʒ����ѡ���Ʒ��Ϣ
     *
     * @return
     */

    List<String> selectProductName(String productName);

    /**
     * ���ݲ�Ʒ����ѡ���Ʒ��Ϣ
     *
     * @return
     */

    List<String> selectProductCode(String productCode);

    /**
     * ����ϵͳidѡ���Ʒ��Ϣ
     *
     * @return
     */
    List<String> selectSystemId(String systemId);


    List<ProductDO> getAllCombProduct(ProductDO productDO);

    List<String> selectProdCode(String productCode);
}
