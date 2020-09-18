package com.boco.TONY.biz.line.exception;

/**
 * 机构条线详情异常
 *
 * @author tony
 * @describe LineProductDetailException
 * @date 2019-09-23
 */
public class LineProductDetailException extends Exception {

    private static final long serialVersionUID = -2317592233900999062L;

    public LineProductDetailException() {
        super();
    }

    public LineProductDetailException(String message) {
        super(message);
    }

    public LineProductDetailException(String message, Throwable cause) {
        super(message, cause);
    }

    public LineProductDetailException(Throwable cause) {
        super(cause);
    }

    protected LineProductDetailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
