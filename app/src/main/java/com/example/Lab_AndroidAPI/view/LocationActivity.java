package com.example.Lab_AndroidAPI.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.Lab_AndroidAPI.R;
import com.example.Lab_AndroidAPI.adapter.Adapter_Item_District_Select_GHN;
import com.example.Lab_AndroidAPI.adapter.Adapter_Item_Province_Select_GHN;
import com.example.Lab_AndroidAPI.adapter.Adapter_Item_Ward_Select_GHN;
import com.example.Lab_AndroidAPI.databinding.ActivityLocationBinding;
import com.example.Lab_AndroidAPI.model.District;
import com.example.Lab_AndroidAPI.model.DistrictRequest;
import com.example.Lab_AndroidAPI.model.Fruit;
import com.example.Lab_AndroidAPI.model.GHNItem;
import com.example.Lab_AndroidAPI.model.GHNOrderRequest;
import com.example.Lab_AndroidAPI.model.GHNOrderRespone;
import com.example.Lab_AndroidAPI.model.Order;
import com.example.Lab_AndroidAPI.model.Province;
import com.example.Lab_AndroidAPI.model.Response;
import com.example.Lab_AndroidAPI.model.ResponseGHN;
import com.example.Lab_AndroidAPI.model.Ward;
import com.example.Lab_AndroidAPI.services.GHNRequest;
import com.example.Lab_AndroidAPI.services.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class LocationActivity extends AppCompatActivity {
    private ActivityLocationBinding binding;
    private GHNRequest ghnRequest;
    private HttpRequest httpRequest;
    private String productId, productTypeId, productName, description, WardCode;
    private double rate, price;
    private int image, DistrictID, ProvinceID;
    private Adapter_Item_Province_Select_GHN adapter_item_province_select_ghn;
    private Adapter_Item_District_Select_GHN adapter_item_district_select_ghn;
    private Adapter_Item_Ward_Select_GHN adapter_item_ward_select_ghn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        httpRequest = new HttpRequest();
        ghnRequest = new GHNRequest();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productId = bundle.getString("productId");
            productTypeId = bundle.getString("productTypeId");
            productName = bundle.getString("productName");
            description = bundle.getString("description");
            rate = bundle.getDouble("rate");
            price = bundle.getDouble("price");
            image = bundle.getInt("image");
        }

        ghnRequest.callAPI().getListProvince().enqueue(responseProvince);
        binding.spProvince.setOnItemSelectedListener(onItemSelectedListener);
        binding.spDistrict.setOnItemSelectedListener(onItemSelectedListener);
        binding.spWard.setOnItemSelectedListener(onItemSelectedListener);
        binding.spProvince.setSelection(1);
        binding.spDistrict.setSelection(1);
        binding.spWard.setSelection(1);
        binding.btnOrder.setOnClickListener(v->{
            if(WardCode.equals("")) return;
            Fruit fruit = (Fruit) getIntent().getExtras().getSerializable("item");
            GHNItem ghnItem = new GHNItem();
            ghnItem.setName(fruit.getName());
            ghnItem.setPrice(Integer.parseInt(fruit.getPrice()));
            ghnItem.setCode(fruit.get_id());
            ghnItem.setQuantity(1);
            ghnItem.setWeight(50);
            ArrayList<GHNItem> items = new ArrayList<>();
            items.add(ghnItem);
            GHNOrderRequest ghnOrderRequest = new GHNOrderRequest(
                    binding.edtName.getText().toString(),
                    binding.edPhone.getText().toString(),
                    binding.edLocation.getText().toString(),
                    WardCode,
                    DistrictID,
                    items
            );
            Log.d("dong93", "onCreate: "+WardCode);
            ghnRequest.callAPI().GHNOrder(ghnOrderRequest).enqueue(responseOrder);
        });



    }

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.sp_province) {
                ProvinceID = ((Province) parent.getAdapter().getItem(position)).getProvinceID();
                DistrictRequest districtRequest = new DistrictRequest(ProvinceID);
                ghnRequest.callAPI().getListDistrict(districtRequest).enqueue(responseDistrict);
            } else if (parent.getId() == R.id.sp_district) {
                DistrictID = ((District) parent.getAdapter().getItem(position)).getDistrictID();
                ghnRequest.callAPI().getListWard(DistrictID).enqueue(responseWard);
            } else if (parent.getId() == R.id.sp_ward) {
                WardCode = ((Ward) parent.getAdapter().getItem(position)).getWardCode();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    Callback<ResponseGHN<ArrayList<Province>>> responseProvince = new Callback<ResponseGHN<ArrayList<Province>>>() {
        @Override
        public void onResponse(Call<ResponseGHN<ArrayList<Province>>> call, retrofit2.Response<ResponseGHN<ArrayList<Province>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getCode() == 200) {
                    ArrayList<Province> ds = new ArrayList<>(response.body().getData());
                    Log.d("dong128", "onResponse: "+ds.size());
                    SetDataSpinProvince(ds);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<ArrayList<Province>>> call, Throwable t) {
            Toast.makeText(LocationActivity.this, "Lấy dữ liệu bị lỗi", Toast.LENGTH_SHORT).show();
        }
    };

    Callback<ResponseGHN<ArrayList<District>>> responseDistrict = new Callback<ResponseGHN<ArrayList<District>>>() {
        @Override
        public void onResponse(Call<ResponseGHN<ArrayList<District>>> call, retrofit2.Response<ResponseGHN<ArrayList<District>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getCode() == 200) {
                    ArrayList<District> ds = new ArrayList<>(response.body().getData());
                    SetDataSpinDistrict(ds);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<ArrayList<District>>> call, Throwable t) {

        }
    };

    Callback<ResponseGHN<ArrayList<Ward>>> responseWard = new Callback<ResponseGHN<ArrayList<Ward>>>() {
        @Override
        public void onResponse(Call<ResponseGHN<ArrayList<Ward>>> call,retrofit2.Response<ResponseGHN<ArrayList<Ward>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getCode() == 200) {

                    if (response.body().getData() == null)
                        return;

                    ArrayList<Ward> ds = new ArrayList<>(response.body().getData());

                    ds.addAll(response.body().getData());
                    SetDataSpinWard(ds);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<ArrayList<Ward>>> call, Throwable t) {
            Toast.makeText(LocationActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
        }
    };
    Callback<ResponseGHN<GHNOrderRespone>> responseOrder = new Callback<ResponseGHN<GHNOrderRespone>>() {
        @Override
        public void onResponse(Call<ResponseGHN<GHNOrderRespone>> call, retrofit2.Response<ResponseGHN<GHNOrderRespone>> response) {
            if(response.isSuccessful()){
                if(response.body().getCode()==200){
                    Toast.makeText(LocationActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    Order order = new Order();
                    order.setOrder_code(response.body().getData().getOrder_code());
                    order.setId_user(getSharedPreferences("INFO",MODE_PRIVATE).getString("id",""));
                    httpRequest.callAPI().order(order).enqueue(responseOrderData);
                }

            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<GHNOrderRespone>> call, Throwable t) {
            Log.d("phuoc195", "onFailure: "+t.getMessage());
        }
    };
    Callback<Response<Order>> responseOrderData = new Callback<Response<Order>>() {
        @Override
        public void onResponse(Call<Response<Order>> call, retrofit2.Response<Response<Order>> response) {
            if (response.isSuccessful()) {
                if (response.isSuccessful()){
                    if (response.body().getStatus() == 200){
                        Toast.makeText(LocationActivity.this, "cam on ban dat hang", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Order>> call, Throwable t) {
            Log.d("phuoc212", "onFailure: "+t.getMessage());
        }
    };

    private void SetDataSpinProvince(ArrayList<Province> ds) {
        adapter_item_province_select_ghn = new Adapter_Item_Province_Select_GHN(this, ds);
        binding.spProvince.setAdapter(adapter_item_province_select_ghn);
    }

    private void SetDataSpinDistrict(ArrayList<District> ds) {
        adapter_item_district_select_ghn = new Adapter_Item_District_Select_GHN(this, ds);
        binding.spDistrict.setAdapter(adapter_item_district_select_ghn);
    }

    private void SetDataSpinWard(ArrayList<Ward> ds) {
        adapter_item_ward_select_ghn = new Adapter_Item_Ward_Select_GHN(this, ds);
        binding.spWard.setAdapter(adapter_item_ward_select_ghn);
    }

}