package com.boco.TONY.biz.line.POJO.DO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机构条线持久层DO
 *
 * @author tony
 * @describe ProductLineInfoDO
 * @date 2019-09-23
 */
public class ProductLineInfoDO implements Serializable {
    private static final long serialVersionUID = -6742776326172942463L;
    /**
     * 条线唯一标识
     */
    private String lineId;

    /**
     * 条线名称
     */
    private String lineName;

    /**
     * 条线版本
     */
    private int lineVersion;

    /**
     * 条件描述
     */
    private String lineDescription;

    /**
     * 条线状态 -1表示已删除,0表示有效
     */
    private int lineStatus;

    /**
     * 条线创建者
     */
    private String lineCreator;

    /**
     * 条线创建时间
     */
    private LocalDateTime createTime;

    /**
     * 条线更新者
     **/
    private String lineUpdater;

    /**
     * 条线更新时间
     */
    private LocalDateTime updateTime;

    /***条线关联的机构*/
    private String organCode;

    public String getLineId() {
        return lineId;
    }

    public ProductLineInfoDO setLineId(String lineId) {
        this.lineId = lineId;
        return this;
    }

    public String getLineName() {
        return lineName;
    }

    public ProductLineInfoDO setLineName(String lineName) {
        this.lineName = lineName;
        return this;
    }

    public int getLineVersion() {
        return lineVersion;
    }

    public ProductLineInfoDO setLineVersion(int lineVersion) {
        this.lineVersion = lineVersion;
        return this;
    }

    public String getLineDescription() {
        return lineDescription;
    }

    public ProductLineInfoDO setLineDescription(String lineDescription) {
        this.lineDescription = lineDescription;
        return this;
    }

    public int getLineStatus() {
        return lineStatus;
    }

    public ProductLineInfoDO setLineStatus(int lineStatus) {
        this.lineStatus = lineStatus;
        return this;
    }

    public String getLineCreator() {
        return lineCreator;
    }

    public ProductLineInfoDO setLineCreator(String lineCreator) {
        this.lineCreator = lineCreator;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public ProductLineInfoDO setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getLineUpdater() {
        return lineUpdater;
    }

    public ProductLineInfoDO setLineUpdater(String lineUpdater) {
        this.lineUpdater = lineUpdater;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public ProductLineInfoDO setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getOrganCode() {
        return organCode;
    }

    public ProductLineInfoDO setOrganCode(String organCode) {
        this.organCode = organCode;
        return this;
    }

    @Override
    public String toString() {
        return "ProductLineInfoDO{" +
                "lineId='" + lineId + '\'' +
                ", lineName='" + lineName + '\'' +
                ", lineVersion=" + lineVersion +
                ", lineDescription='" + lineDescription + '\'' +
                ", lineStatus=" + lineStatus +
                ", lineCreator='" + lineCreator + '\'' +
                ", createTime=" + createTime +
                ", lineUpdater='" + lineUpdater + '\'' +
                ", updateTime=" + updateTime +
                ", organCode='" + organCode + '\'' +
                '}';
    }
}
