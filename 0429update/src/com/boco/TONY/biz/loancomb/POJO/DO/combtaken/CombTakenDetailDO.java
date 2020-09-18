package com.boco.TONY.biz.loancomb.POJO.DO.combtaken;

/**
 * 贷种内占用DTO
 *
 * @author tony
 * @describe CombInnerTakenDO
 * @date 2019-10-17
 */
public class CombTakenDetailDO {

    /***贷种占用唯一标识*/
    private int id;

    /***贷种组合占用方*/
    private String combId;

    /***被占用的贷种组合*/
    private String combTakenId;


    /***贷种内占用的上级贷种组合*/
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
