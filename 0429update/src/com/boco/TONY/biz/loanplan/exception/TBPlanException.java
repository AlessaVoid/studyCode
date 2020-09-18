package com.boco.TONY.biz.loanplan.exception;

/**
 * 信贷计划异常
 *
 * @author tony
 * @describe TBPlanException
 * @date 2019-09-29
 */
public class TBPlanException extends Exception {

    private static final long serialVersionUID = -6875719991537122462L;

    public TBPlanException() {
        super();
    }

    public TBPlanException(String message) {
        super(message);
    }

    public TBPlanException(String message, Throwable cause) {
        super(message, cause);
    }

    public TBPlanException(Throwable cause) {
        super(cause);
    }

    protected TBPlanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
