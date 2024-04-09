package com.example.Lab_AndroidAPI.model;

public class GHNOrderRespone {
    private String order_code;

    public GHNOrderRespone() {
    }

    public GHNOrderRespone(String order_code) {
        this.order_code = order_code;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }
}
