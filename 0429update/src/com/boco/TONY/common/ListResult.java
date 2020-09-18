package com.boco.TONY.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * RPC/HTTP接口统一返回结果
 * @author tony
 * @describe PlainResult
 * @date 2019-09-07
 */
public class ListResult<T> implements Serializable {
    private static final long serialVersionUID = 3833672216576011243L;

    private int code;
    private boolean success;
    private List<T> data;
    private String message;
    private static final int SUCCESS = 200;

    public ListResult<T> success(List<T> data, String message) {

        this.code = SUCCESS;
        this.success = true;
        this.data = (data == null ? new ArrayList<>() : data);
        this.message = message;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ListResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public ListResult<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public ListResult<T> setData(List<T> data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ListResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ListResult<T> error(int code, String message) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.data = new ArrayList<>();
        return this;
    }
}
