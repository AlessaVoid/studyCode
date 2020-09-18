package com.boco.TONY.biz.loancomb.POJO.DO.productbase;

import java.io.Serializable;

/**
 * ���ֲ�Ʒ״̬DO
 *
 * @author tony
 * @describe ProductStatusDO
 * @date 2019-09-19
 */
public class ProductStatusDO implements Serializable {
    private static final long serialVersionUID = 4978194048737541948L;
    /***��Ʒ����*/
    private String productId;

    /***��Ʒ״̬*/
    private int productStatus;

    public String getProductId() {
        return productId;
    }

    public ProductStatusDO setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public ProductStatusDO setProductStatus(int productStatus) {
        this.productStatus = productStatus;
        return this;
    }

    @Override
    public String toString() {
        return "ProductStatusDO{" +
                "productId='" + productId + '\'' +
                ", productStatus=" + productStatus +
                '}';
    }
}
