package com.zl.app.util.net;

/**
 * Created by admin on 2016/1/21.
 */
public class SimpleHttpResponse {

    private String result;

    private String status;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SimpleHttpResponse{" +
                "result='" + result + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
