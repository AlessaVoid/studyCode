package com.boco.TONY.biz.loanplan.exception;

/**
 * 信贷计划详情异常
 *
 * @author tony
 * @describe TbPlanDetailException
 * @date 2019-09-29
 */
public class TbPlanDetailException extends Exception {
    private static final long serialVersionUID = 6328919895975899256L;

    public TbPlanDetailException() {
        super();
    }

    public TbPlanDetailException(String message) {
        super(message);
    }

    public TbPlanDetailException(String message, Throwable cause) {
        super(message, cause);
    }

    public TbPlanDetailException(Throwable cause) {
        super(cause);
    }

    protected TbPlanDetailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
