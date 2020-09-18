package com.boco.TONY.biz.loancomb.POJO.DTO.combtaken;

import java.io.Serializable;

/**
 * ������ռ��DTO
 *
 * @author tony
 * @describe CombInnerTakenDTO
 * @date 2019-10-17
 */
public class CombTakenDetailDTO implements Serializable {
    private static final long serialVersionUID = 3990510029842763639L;

    /***�����ڲ�ռ��Ψһ��ʶ*/
    private int id;

    /***�������ռ�÷�*/
    private String combId;
    /***����������*/
    private String combName;
    /***�����ڱ�ռ�õĴ������*/
    private String combTakenId;
    /*** ����ռ������*/
    private String combTakenName;
    /***������ռ�õ��ϼ��������*/
    private String combParentId;

    /***������ռ������*/
    private int takeType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCombId() {
        return combId;
    }

    public void setCombId(String combId) {
        this.combId = combId;
    }

    public String getCombTakenId() {
        return combTakenId;
    }

    public void setCombTakenId(String combTakenId) {
        this.combTakenId = combTakenId;
    }

    public String getCombParentId() {
        return combParentId;
    }

    public void setCombParentId(String combParentId) {
        this.combParentId = combParentId;
    }

    public int getTakeType() {
        return takeType;
    }

    public void setTakeType(int takeType) {
        this.takeType = takeType;
    }

    public String getCombName() {
        return combName;
    }

    public void setCombName(String combName) {
        this.combName = combName;
    }

    public String getCombTakenName() {
        return combTakenName;
    }

    public void setCombTakenName(String combTakenName) {
        this.combTakenName = combTakenName;
    }
}
