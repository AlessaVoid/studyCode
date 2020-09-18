package com.boco.TONY.biz.weboper.POJO.DO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 柜员条线持久层DO
 * @author tony
 * @describe WebOperLineDO
 * @date 2019-09-24
 */
public class WebOperLineDO implements Serializable {

    private static final long serialVersionUID = 3529782200576994436L;
    /**唯一标识*/
    private long id;

    /**条线唯一标识*/
    private String lineId;

    /**柜员号*/
    private String operCode;

    /**当前柜员下条线状态*/
    private int status;

    /**创建时间*/
    private LocalDateTime createTime;

    /**更新时间*/
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
