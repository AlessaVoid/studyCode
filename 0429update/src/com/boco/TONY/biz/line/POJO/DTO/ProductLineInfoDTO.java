package com.boco.TONY.biz.line.POJO.DTO;

import java.io.Serializable;

/**
 * ��������ҵ���߼���DTO
 *
 * @author tony
 * @describe ProductLineInfoDTO
 * @date 2019-09-23
 */
public class ProductLineInfoDTO implements Serializable {
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
     * ����״̬ 1��ʾ����,2��ʾ��ɾ��
     */
    private int lineStatus;

    /**
     * ���ߴ�����
     */
    private String lineCreator;

    /**
     * ���ߴ���ʱ��
     */
    private String createTime;

    /**
     * ���߸�����
     **/
    private String lineUpdater;

    /**
     * ���߸���ʱ��
     */
    private String updateTime;

    /***���߹����Ļ���*/
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
