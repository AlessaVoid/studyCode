package com.boco.TONY.biz.line.exception;

/**
 * 机构条线异常
 *
 * @author tony
 * @describe LineProductException
 * @date 2019-09-23
 */
public class LineProductException extends Exception {
    private static final long serialVersionUID = 3289249474249758278L;

    public LineProductException() {
        super();
    }

    public LineProductException(String message) {
        super(message);
    }

    public LineProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public LineProductException(Throwable cause) {
        super(cause);
    }

    protected LineProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
