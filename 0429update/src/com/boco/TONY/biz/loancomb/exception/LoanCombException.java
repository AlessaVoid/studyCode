package com.boco.TONY.biz.loancomb.exception;

/**
 * 贷种组合异常
 *
 * @author tony
 * @describe LoanCombException
 * @date 2019-09-18
 */
public class LoanCombException extends Exception {
    private static final long serialVersionUID = 3127870969290383920L;

    public LoanCombException() {
        super();
    }

    public LoanCombException(String message) {
        super(message);
    }

    public LoanCombException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoanCombException(Throwable cause) {
        super(cause);
    }

    protected LoanCombException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
