package com.boco.TONY.biz.line.POJO.DO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * �������߳־ò�DO
 *
 * @author tony
 * @describe ProductLineInfoDO
 * @date 2019-09-23
 */
public class ProductLineInfoDO implements Serializable {
    private static final long serialVersionUID = -6742776326172942463L;
    /**
     * ����Ψһ��ʶ
     */
    private String lineId;

    /**
     * ��������
     */
    private String lineName;

    /**
     * ���߰汾
     */
    private int lineVersion;

    /**
     * ��������
     */
    private String lineDescription;

    /**
     * ����״̬ -1��ʾ��ɾ��,0��ʾ��Ч
     */
    private int lineStatus;

    /**
     * ���ߴ�����
     */
    private String lineCreator;

    /**
     * ���ߴ���ʱ��
     */
    private LocalDateTime createTime;

    /**
     * ���߸�����
     **/
    private String lineUpdater;

    /**
     * ���߸���ʱ��
     */
    private LocalDateTime updateTime;

    /***���߹����Ļ���*/
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
