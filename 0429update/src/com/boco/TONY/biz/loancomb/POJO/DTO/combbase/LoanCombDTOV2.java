package com.boco.TONY.biz.loancomb.POJO.DTO.combbase;

import java.io.Serializable;

/**
 * �������DTO
 *
 * @author tony
 * @describe LoanCombDTOV2
 * @date 2019-09-22
 */
public class LoanCombDTOV2 implements Serializable {
    private static final long serialVersionUID = -6827539778783669905L;
    /***����Ψһ��ʶ*/
    private String combId;

    /***������ϱ���*/
    private String combCode;

    /***�����������*/
    private String combName;

    /***������ϼ��� 1-һ����� 2-������� 3-�������*/
    private int combLevel;

    /***�������״̬ 0-������� 1-����� 2��ʾ�ѱ����*/
    private int combStatus;
    /***�������״̬ 0-������� 1-����� 2��ʾ�ѱ����*/
    private String combChildren;
    /***���ִ�����*/
    private String combCreator;

    /***������ϴ���ʱ��*/
    private String combCreateTime;

    /***���ָ�����*/
    private String combUpdater;

    /***������ϸ���ʱ��*/
    private String combUpdateTime;

    public String getCombId() {
        return combId;
    }

    public LoanCombDTOV2 setCombId(String combId) {
        this.combId = combId;
        return this;
    }

    public String getCombCode() {
        return combCode;
    }

    public LoanCombDTOV2 setCombCode(String combCode) {
        this.combCode = combCode;
        return this;
    }

    public String getCombName() {
        return combName;
    }

    public LoanCombDTOV2 setCombName(String combName) {
        this.combName = combName;
        return this;
    }

    public int getCombLevel() {
        return combLevel;
    }

    public LoanCombDTOV2 setCombLevel(int combLevel) {
        this.combLevel = combLevel;
        return this;
    }

    public int getCombStatus() {
        return combStatus;
    }

    public LoanCombDTOV2 setCombStatus(int combStatus) {
        this.combStatus = combStatus;
        return this;
    }

    public String getCombCreator() {
        return combCreator;
    }

    public LoanCombDTOV2 setCombCreator(String combCreator) {
        this.combCreator = combCreator;
        return this;
    }

    public String getCombCreateTime() {
        return combCreateTime;
    }

    public LoanCombDTOV2 setCombCreateTime(String combCreateTime) {
        this.combCreateTime = combCreateTime;
        return this;
    }

    public String getCombUpdateTime() {
        return combUpdateTime;
    }

    public LoanCombDTOV2 setCombUpdateTime(String combUpdateTime) {
        this.combUpdateTime = combUpdateTime;
        return this;
    }

    public String getCombUpdater() {
        return combUpdater;
    }

    public LoanCombDTOV2 setCombUpdater(String combUpdater) {
        this.combUpdater = combUpdater;
        return this;
    }

    public String getCombChildren() {
        return combChildren;
    }

    public void setCombChildren(String combChildren) {
        this.combChildren = combChildren;
    }

    @Override
    public String toString() {
        return "LoanCombDTOV2{" +
                "combId='" + combId + '\'' +
                ", combCode='" + combCode + '\'' +
                ", combName='" + combName + '\'' +
                ", combLevel=" + combLevel +
                ", combStatus=" + combStatus +
                ", combCreator='" + combCreator + '\'' +
                ", combCreateTime='" + combCreateTime + '\'' +
                ", combUpdater='" + combUpdater + '\'' +
                ", combUpdateTime='" + combUpdateTime + '\'' +
                '}';
    }
}
