package com.boco.TONY.enums;

/**
 * ��Ա״̬ö��
 *
 * @author tony
 * @describe OperStateEnum
 * @date 2019-09-11
 */
public enum OperStateEnum {
    //��¼
    OPER_SIGN_IN("0"),
    //�˳�
    OPER_SIGN_OUT("1"),
    //ǩ��
    OPER_SIGN_OFF("2");
    public String state;

    OperStateEnum(String state) {
        this.state = state;
    }
}
