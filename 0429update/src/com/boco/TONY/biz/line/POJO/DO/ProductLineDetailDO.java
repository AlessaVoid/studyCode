package com.boco.TONY.biz.line.POJO.DO;

import java.io.Serializable;

/**
 * ����������ϸ�־ò�DO
 *
 * @author tony
 * @describe ProductLineDetailsDO
 * @date 2019-09-23
 */
public class ProductLineDetailDO implements Serializable {
    private static final long serialVersionUID = -2689810809776778324L;
    //Ψһ��ʶ
    private long id;
    // ����Ψһ��ʶ
    private String lineId;
    //����������ϱ���
    private String productId;
    //��ʶ����״̬
    private int status;

    public long getId() {
        return id;
    }

    public ProductLineDetailDO setId(long id) {
        this.id = id;
        return this;
    }

    public String getLineId() {
        return lineId;
    }

    public ProductLineDetailDO setLineId(String lineId) {
        this.lineId = lineId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public ProductLineDetailDO setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public ProductLineDetailDO setStatus(int status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "ProductLineDetailsDO{" +
                "id=" + id +
                ", line_id='" + lineId + '\'' +
                ", comb_code='" + productId + '\'' +
                ", status=" + status +
                '}';
    }
}
