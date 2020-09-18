package com.boco.TONY.biz.line.POJO.DTO;

import java.io.Serializable;

/**
 * 机构条线业务逻辑层DTO
 *
 * @author tony
 * @describe ProductLineInfoDTO
 * @date 2019-09-23
 */
public class ProductLineInfoDTO implements Serializable {
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
     * 条线状态 1表示可用,2表示已删除
     */
    private int lineStatus;

    /**
     * 条线创建者
     */
    private String lineCreator;

    /**
     * 条线创建时间
     */
    private String createTime;

    /**
     * 条线更新者
     **/
    private String lineUpdater;

    /**
     * 条线更新时间
     */
    private String updateTime;

    /***条线关联的机构*/
    private String organCode;

    public String getLineId() {
        return lineId;
    }

    public ProductLineInfoDTO setLineId(String lineId) {
        this.lineId = lineId;
        return this;
    }

    public String getLineName() {
        return lineName;
    }

    public ProductLineInfoDTO setLineName(String lineName) {
        this.lineName = lineName;
        return this;
    }

    public int getLineVersion() {
        return lineVersion;
    }

    public ProductLineInfoDTO setLineVersion(int lineVersion) {
        this.lineVersion = lineVersion;
        return this;
    }

    public String getLineDescription() {
        return lineDescription;
    }

    public ProductLineInfoDTO setLineDescription(String lineDescription) {
        this.lineDescription = lineDescription;
        return this;
    }

    public int getLineStatus() {
        return lineStatus;
    }

    public ProductLineInfoDTO setLineStatus(int lineStatus) {
        this.lineStatus = lineStatus;
        return this;
    }

    public String getLineCreator() {
        return lineCreator;
    }

    public ProductLineInfoDTO setLineCreator(String lineCreator) {
        this.lineCreator = lineCreator;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public ProductLineInfoDTO setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getLineUpdater() {
        return lineUpdater;
    }

    public ProductLineInfoDTO setLineUpdater(String lineUpdater) {
        this.lineUpdater = lineUpdater;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public ProductLineInfoDTO setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getOrganCode() {
        return organCode;
    }

    public ProductLineInfoDTO setOrganCode(String organCode) {
        this.organCode = organCode;
        return this;
    }

    @Override
    public String toString() {
        return "ProductLineInfoDTO{" +
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
