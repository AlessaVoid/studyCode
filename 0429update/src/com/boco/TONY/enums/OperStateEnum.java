package com.boco.TONY.enums;

/**
 * ¹ñÔ±×´Ì¬Ã¶¾Ù
 *
 * @author tony
 * @describe OperStateEnum
 * @date 2019-09-11
 */
public enum OperStateEnum {
    //µÇÂ¼
    OPER_SIGN_IN("0"),
    //ÍË³ö
    OPER_SIGN_OUT("1"),
    //Ç©ÍË
    OPER_SIGN_OFF("2");
    public String state;

    OperStateEnum(String state) {
        this.state = state;
    }
}
