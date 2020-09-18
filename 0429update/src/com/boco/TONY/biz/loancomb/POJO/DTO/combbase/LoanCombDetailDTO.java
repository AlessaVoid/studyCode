package com.boco.TONY.biz.loancomb.POJO.DTO.combbase;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ��������DO
 *
 * @author tony
 * @describe LoanCombDetailDO
 * @date 2019-09-18
 */
public class LoanCombDetailDTO implements Serializable {
    private static final long serialVersionUID = -1872861480888608733L;
    /***������ϱ���*/
    private String combCode;
    /***��Ʒ����*/
    private String productId;
    /***1��ʶ��Ʒ���� -1��ʶ��Ʒ������*/
    private int status;
    /*** ������Ʒϵͳid
     **/
    private String productSystemId;

    /***������ϸ����ʱ��*/
    private LocalDateTime createTime;

    public String getProductId() {
        return productId;
    }

    public LoanCombDetailDTO setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getCombCode() {
        return combCode;
    }

    public LoanCombDetailDTO setCombCode(String combCode) {
        this.combCode = combCode;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public LoanCombDetailDTO setStatus(int status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LoanCombDetailDTO setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getProductSystemId() {
        return productSystemId;
    }

    public void setProductSystemId(String productSystemId) {
        this.productSystemId = productSystemId;
    }

    @Override
    public String toString() {
        return "LoanComposeDetailDO{" +
                "productId='" + productId + '\'' +
                ", combId='" + combCode + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
