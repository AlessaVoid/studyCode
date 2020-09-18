package com.boco.TONY.biz.loancomb.POJO.DTO.combtaken;

import java.io.Serializable;

/**
 * 贷种内占用DTO
 *
 * @author tony
 * @describe CombInnerTakenDTO
 * @date 2019-10-17
 */
public class CombTakenDetailDTO implements Serializable {
    private static final long serialVersionUID = 3990510029842763639L;

    /***贷种内部占用唯一标识*/
    private int id;

    /***贷种组合占用方*/
    private String combId;
    /***贷种组名称*/
    private String combName;
    /***贷种内被占用的贷种组合*/
    private String combTakenId;
    /*** 贷种占用名称*/
    private String combTakenName;
    /***贷种内占用的上级贷种组合*/
    private String combParentId;

    /***贷种内占用类型*/
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
