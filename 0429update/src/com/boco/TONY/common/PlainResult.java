package com.boco.TONY.common;

import java.io.Serializable;

/**
 * HTTP接口统一返回结果
 *
 * @author tony
 * @describe PlainResult
 * @date 2019-09-07
 */
public class PlainResult<T> implements Serializable {
    private static final long serialVersionUID = 357509687400002037L;

    private int code;
    private boolean success;
    private T data;
    private String message;
    private static final int SUCCESS = 200;

    public PlainResult<T> success(T data, String message) {
        this.code = SUCCESS;
        this.success = true;
        this.data = data;
        this.message = message;
        return this;
    }

    public PlainResult<T> build() {
        return this;
    }

    public int getCode() {
        return code;
    }

    public PlainResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public PlainResult<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public T getData() {
        return data;
    }

    public PlainResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public PlainResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public PlainResult<T> error(int code, String message) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.data = null;
        return this;
    }
}
