package com.boco.TONY.biz.line.POJO.DTO;

import java.io.Serializable;

/**
 * ����������ϸҵ���߼���DTO
 *
 * @author tony
 * @describe ProductLineDetailDTO
 * @date 2019-09-23
 */
public class ProductLineDetailDTO implements Serializable {
    private static final long serialVersionUID = -2689810809776778324L;
    /**
     * Ψһ��ʶ
     */
    private long id;

    /**
     * ����Ψһ��ʶ
     */
    private String lineId;

    /**
     * ����������ϱ���
     */
    private String combCode;

    /**
     * ��ʶ�Ƿ����
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
