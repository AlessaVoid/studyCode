package com.boco.TONY.biz.line.POJO.DTO;

import java.io.Serializable;

/**
 * ��������ҵ���߼���DTO
 * @author tony
 * @describe ProductLineInfoDTOV2
 * @date 2019-09-23
 */
public class ProductLineInfoDTOV2 implements Serializable {
    private static final long serialVersionUID = 6170861706973583308L;

    /**����Ψһ��ʶ*/
    private String lineId;

    /**��������*/
    private String lineName;

    /**���߰汾*/
    private int lineVersion;

    /**��������*/
    private String lineDescription;

    /**����״̬ 1��ʾ����,2��ʾ��ɾ��*/
    private int lineStatus;

    /**���ߴ�����*/
    private String lineCreator;

    /**���ߴ���ʱ��*/
    private String createTime;

    /**���߸�����**/
    private String lineUpdater;

    /**���߸���ʱ��*/
    private String updateTime;

    public String getLineId() {
        return lineId;
    }

    public ProductLineInfoDTOV2 setLineId(String lineId) {
        this.lineId = lineId;
        return this;
    }

    public String getLineName() {
        return lineName;
    }

    public ProductLineInfoDTOV2 setLineName(String lineName) {
        this.lineName = lineName;
        return this;
    }
    public int getLineVersion() {
        return lineVersion;
    }

    public ProductLineInfoDTOV2 setLineVersion(int lineVersion) {
        this.lineVersion = lineVersion;
        return this;
    }

    public String getLineDescription() {
        return lineDescription;
    }

    public ProductLineInfoDTOV2 setLineDescription(String lineDescription) {
        this.lineDescription = lineDescription;
        return this;
    }

    public int getLineStatus() {
        return lineStatus;
    }

    public ProductLineInfoDTOV2 setLineStatus(int lineStatus) {
        this.lineStatus = lineStatus;
        return this;
    }

    public String getLineCreator() {
        return lineCreator;
    }

    public ProductLineInfoDTOV2 setLineCreator(String lineCreator) {
        this.lineCreator = lineCreator;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public ProductLineInfoDTOV2 setCreateTime(String createTime) {
        this.createTime = createTime;
        return  this;
    }

    public String getLineUpdater() {
        return lineUpdater;
    }

    public ProductLineInfoDTOV2 setLineUpdater(String lineUpdater) {
        this.lineUpdater = lineUpdater;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public ProductLineInfoDTOV2 setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    public String toString() {
        return "ProductLineInfoDO{" +
                "lineId='" + lineId + '\'' +
                ", lineName='" + lineName + '\'' +
                ", lineVersion='" + lineVersion + '\'' +
                ", lineDescription='" + lineDescription + '\'' +
                ", lineStatus='" + lineStatus + '\'' +
                ", lineCreator='" + lineCreator + '\'' +
                ", createTime=" + createTime +
                ", lineUpdater='" + lineUpdater + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
