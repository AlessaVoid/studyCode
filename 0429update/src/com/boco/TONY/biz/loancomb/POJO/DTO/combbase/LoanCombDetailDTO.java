package com.boco.TONY.biz.loancomb.POJO.DTO.combbase;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 贷种详情DO
 *
 * @author tony
 * @describe LoanCombDetailDO
 * @date 2019-09-18
 */
public class LoanCombDetailDTO implements Serializable {
    private static final long serialVersionUID = -1872861480888608733L;
    /***贷种组合编码*/
    private String combCode;
    /***产品编码*/
    private String productId;
    /***1标识产品可用 -1标识产品不可用*/
    private int status;
    /*** 三级产品系统id
     **/
    private String productSystemId;

    /***贷种明细创建时间*/
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
