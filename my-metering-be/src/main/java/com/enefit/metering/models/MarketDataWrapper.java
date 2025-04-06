package com.enefit.metering.models;

public class MarketDataWrapper<T> {
    private T data;
//    public MarketDataWrapper(T data) {
//        this.data = data;
//    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
