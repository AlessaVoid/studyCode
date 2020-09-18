package com.boco.TONY.biz.line.POJO.DTO;

import java.io.Serializable;

/**
 * 机构条线明细业务逻辑层DTO
 *
 * @author tony
 * @describe ProductLineDetailDTO
 * @date 2019-09-23
 */
public class ProductLineDetailDTO implements Serializable {
    private static final long serialVersionUID = -2689810809776778324L;
    /**
     * 唯一标识
     */
    private long id;

    /**
     * 行线唯一标识
     */
    private String lineId;

    /**
     * 三级贷种组合编码
     */
    private String combCode;

    /**
     * 标识是否可用
     */
    private int status;

    public long getId() {
        return id;
    }

    public ProductLineDetailDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getLineId() {
        return lineId;
    }

    public ProductLineDetailDTO setLineId(String lineId) {
        this.lineId = lineId;
        return this;
    }

    public String getCombCode() {
        return combCode;
    }

    public ProductLineDetailDTO setCombCode(String combCode) {
        this.combCode = combCode;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public ProductLineDetailDTO setStatus(int status) {
        this.status = status;
        return this;
    }
}
