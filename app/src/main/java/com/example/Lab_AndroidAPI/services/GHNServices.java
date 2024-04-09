package com.example.Lab_AndroidAPI.services;

import com.example.Lab_AndroidAPI.model.District;
import com.example.Lab_AndroidAPI.model.DistrictRequest;
import com.example.Lab_AndroidAPI.model.GHNOrderRequest;
import com.example.Lab_AndroidAPI.model.GHNOrderRespone;
import com.example.Lab_AndroidAPI.model.Province;
import com.example.Lab_AndroidAPI.model.ResponseGHN;
import com.example.Lab_AndroidAPI.model.Ward;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GHNServices {
    public static String GHN_URL = "https://dev-online-gateway.ghn.vn/";

    @GET("shiip/public-api/master-data/province")
    Call<ResponseGHN<ArrayList<Province>>> getListProvince();

    @POST("shiip/public-api/master-data/district")
    Call<ResponseGHN<ArrayList<District>>> getListDistrict(@Body DistrictRequest districtRequest);

    @GET("shiip/public-api/master-data/ward")
    Call<ResponseGHN<ArrayList<Ward>>> getListWard(@Query("district_id") int district_id);

    @POST("shiip/public-api/v2/shipping-order/create")
    Call<ResponseGHN<GHNOrderRespone>> GHNOrder(@Body GHNOrderRequest ghnOrderRequest);

}
