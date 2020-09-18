package com.boco.TONY.biz.weboper.exception;

/**
 * 柜员条线异常
 * @author tony
 * @describe OperLineException
 * @date 2019-09-24
 */
public class OperLineException extends Exception {
    private static final long serialVersionUID = -5399132389149219960L;

    public OperLineException() {
        super();
    }

    public OperLineException(String message) {
        super(message);
    }

    public OperLineException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperLineException(Throwable cause) {
        super(cause);
    }

    protected OperLineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
