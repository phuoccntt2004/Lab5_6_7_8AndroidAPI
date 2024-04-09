package com.example.Lab_AndroidAPI.model;

import java.util.ArrayList;

public class GHNOrderRequest {
    private int payment_type_id;
    private String note, required_note, return_phone, return_address;
    private String to_name, to_phone, to_address, to_ward_code;
    private int to_district_id, cod_amount;
    private String content;
    private int weght, length, width, height, insurance_value, service_id, service_type_id;
    private ArrayList<GHNItem> items;

    public GHNOrderRequest() {
    }

    public GHNOrderRequest(int payment_type_id, String note, String required_note, String return_phone, String return_address, String to_name, String to_phone, String to_address, String to_ward_code, int to_district_id, int cod_amount, String content, int weght, int length, int width, int height, int insurance_value, int service_id, int service_type_id, ArrayList<GHNItem> items) {
        this.payment_type_id = payment_type_id;
        this.note = note;
        this.required_note = required_note;
        this.return_phone = return_phone;
        this.return_address = return_address;
        this.to_name = to_name;
        this.to_phone = to_phone;
        this.to_address = to_address;
        this.to_ward_code = to_ward_code;
        this.to_district_id = to_district_id;
        this.cod_amount = cod_amount;
        this.content = content;
        this.weght = weght;
        this.length = length;
        this.width = width;
        this.height = height;
        this.insurance_value = insurance_value;
        this.service_id = service_id;
        this.service_type_id = service_type_id;
        this.items = items;
    }

    public GHNOrderRequest(String to_name, String to_phone, String to_address, String to_ward_code, int to_district_id, ArrayList<GHNItem> items) {
        this.to_name = to_name;
        this.to_phone = to_phone;
        this.to_address = to_address;
        this.to_ward_code = to_ward_code;
        this.to_district_id = to_district_id;
        this.items = items;
        payment_type_id = 2;
        note = "vui long goi khach truoc khi giao";
        required_note = "KHONCHOXEMHANG";
        return_phone = "0359639564";
        return_address = "thanh khe da nang";
        content = "";
        cod_amount = 0;
        this.items.forEach(item -> {
            cod_amount += item.getPrice();
            weght += item.getWeight();
        });
        length = this.items.size();
        width = 19;
        height = 10;
        insurance_value =  cod_amount;
        service_id = 0;
        service_type_id = 2;
    }
}
