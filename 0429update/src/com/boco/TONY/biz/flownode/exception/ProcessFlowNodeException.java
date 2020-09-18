package com.boco.TONY.biz.flownode.exception;

/**
 * 流程节点维护异常
 * @author tony
 * @describe ProcessFlowNodeException
 * @date 2019-09-25
 */
public class ProcessFlowNodeException extends Exception {
    private static final long serialVersionUID = -2934995095176980889L;

    public ProcessFlowNodeException() {
        super();
    }

    public ProcessFlowNodeException(String message) {
        super(message);
    }

    public ProcessFlowNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessFlowNodeException(Throwable cause) {
        super(cause);
    }

    protected ProcessFlowNodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
