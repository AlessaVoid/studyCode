package com.boco.TONY.biz.weboper.POJO.DO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ��Ա���߳־ò�DO
 * @author tony
 * @describe WebOperLineDO
 * @date 2019-09-24
 */
public class WebOperLineDO implements Serializable {

    private static final long serialVersionUID = 3529782200576994436L;
    /**Ψһ��ʶ*/
    private long id;

    /**����Ψһ��ʶ*/
    private String lineId;

    /**��Ա��*/
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

    public WebOperLineDO setId(long id) {
        this.id = id;
        return this;
    }

    public String getLineId() {
        return lineId;
    }

    public WebOperLineDO setLineId(String lineId) {
        this.lineId = lineId;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public WebOperLineDO setStatus(int status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public WebOperLineDO setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public WebOperLineDO setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public String getOperCode() {
        return operCode;
    }

    public WebOperLineDO setOperCode(String operCode) {
        this.operCode = operCode;
        return this;
    }

    @Override
    public String toString() {
        return "WebOperLineDO{" +
                "id=" + id +
                ", lineId='" + lineId + '\'' +
                ", operCode='" + operCode + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
