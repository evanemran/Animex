package com.evanemran.animex.models;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {
    int status_code = 0;
    String message = "";
    T data;
    String version = "";


    public int getStatus_code() {
        return status_code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String getVersion() {
        return version;
    }
}
