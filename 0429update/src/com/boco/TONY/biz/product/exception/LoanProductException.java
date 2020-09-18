package com.boco.TONY.biz.product.exception;

/**
 * 信贷产品异常
 *
 * @author tony
 * @describe LoanProductException
 * @date 2019-09-19
 */
public class LoanProductException extends Exception {
    private static final long serialVersionUID = 3100862539966126422L;

    public LoanProductException() {
        super();
    }

    public LoanProductException(String message) {
        super(message);
    }

    public LoanProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoanProductException(Throwable cause) {
        super(cause);
    }

    protected LoanProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
