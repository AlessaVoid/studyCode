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

    /**�������߲�Ʒ��ϸ*/
    void insertProductLineDetail(ProductLineDetailDO productLineDetailsDO) throws LineProductDetailException;

    /**�������߲�Ʒ��ϸ*/
    void updateProductLineDetailInfo(ProductLineDetailDO productLineDetailsDO) throws LineProductDetailException;

    /**ɾ�����߲�Ʒ��ϸ*/
    void deleteCombProductByProductId(ProductLineDetailDO productLineDetailsDO)  throws LineProductDetailException;

    /**��ȡ�������߲�Ʒ��ϸ ����ǰ��չʾ*/
    List<ProductLineDetailDO> getAllProductLineDetail() throws LineProductDetailException;

    /**��������ID��ѯ�����������Ĳ�Ʒ����*/
    List<ProductLineDetailDO> getProductLineDetailById(String lineId) throws LineProductDetailException;
}
