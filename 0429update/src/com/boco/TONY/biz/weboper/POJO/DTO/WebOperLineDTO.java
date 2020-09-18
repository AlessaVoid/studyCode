package com.boco.TONY.biz.weboper.POJO.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ��Ա����DTO
 * @author tony
 * @describe WebOperLineDTO
 * @date 2019-09-24
 */
public class WebOperLineDTO implements Serializable {
    private static final long serialVersionUID = 1654186459448350031L;

    /**Ψһ��ʶ*/
    private long id;

    /**����Ψһ��ʶ*/
    private String lineId;

    /**��Ա����*/
    private String operCode;

    /**��ǰ��Ա������״̬*/
    private int status;

    /**����ʱ��*/
    private LocalDateTime createTime;

    /**����ʱ��*/
    private LocalDateTime updateTime;

    public long getId() {
        return id;
    }

    public WebOperLineDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getLineId() {
        return lineId;
    }

    public WebOperLineDTO setLineId(String lineId) {
        this.lineId = lineId;
        return this;
    }

    public String getOperCode() {
        return operCode;
    }

    public WebOperLineDTO setOperCode(String operCode) {
        this.operCode = operCode;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public WebOperLineDTO setStatus(int status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public WebOperLineDTO setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public WebOperLineDTO setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    public String toString() {
        return "WebOperLineDTO{" +
                "id=" + id +
                ", lineId='" + lineId + '\'' +
                ", operCode='" + operCode + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
