package com.zl.app.util.net;

/**
 * Created by fengxiang on 2016/1/21.
 */
public class BaseResponse<T> {

    private String status;

    private String message;

    private T result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status='" + status + '\'' +
                ", result=" + result +
                '}';
    }
}
