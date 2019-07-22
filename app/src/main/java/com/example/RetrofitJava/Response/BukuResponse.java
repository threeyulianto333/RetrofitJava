package com.example.RetrofitJava.Response;

import com.example.RetrofitJava.Item.SemuabukuItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BukuResponse<T> {
    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private T data;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
