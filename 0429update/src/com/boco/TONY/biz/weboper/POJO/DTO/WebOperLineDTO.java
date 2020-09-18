package com.boco.TONY.biz.weboper.POJO.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 柜员条线DTO
 * @author tony
 * @describe WebOperLineDTO
 * @date 2019-09-24
 */
public class WebOperLineDTO implements Serializable {
    private static final long serialVersionUID = 1654186459448350031L;

    /**唯一标识*/
    private long id;

    /**条线唯一标识*/
    private String lineId;

    /**柜员编码*/
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
