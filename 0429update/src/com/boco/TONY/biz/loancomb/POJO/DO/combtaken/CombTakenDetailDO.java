package com.boco.TONY.biz.loancomb.POJO.DO.combtaken;

/**
 * ������ռ��DTO
 *
 * @author tony
 * @describe CombInnerTakenDO
 * @date 2019-10-17
 */
public class CombTakenDetailDO {

    /***����ռ��Ψһ��ʶ*/
    private int id;

    /***�������ռ�÷�*/
    private String combId;

    /***��ռ�õĴ������*/
    private String combTakenId;


    /***������ռ�õ��ϼ��������*/
    private String combParentId;

    public int getId() {
        return id;
    }

    public CombTakenDetailDO setId(int id) {
        this.id = id;
        return this;
    }

    public String getCombId() {
        return combId;
    }

    public CombTakenDetailDO setCombId(String combId) {
        this.combId = combId;
        return this;
    }

    public String getCombTakenId() {
        return combTakenId;
    }

    public CombTakenDetailDO setCombTakenId(String combTakenId) {
        this.combTakenId = combTakenId;
        return this;
    }

    public String getCombParentId() {
        return combParentId;
    }

    public CombTakenDetailDO setCombParentId(String combParentId) {
        this.combParentId = combParentId;
        return this;
    }
}
