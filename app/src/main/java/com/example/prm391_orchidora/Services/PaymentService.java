package com.example.prm391_orchidora.Services;

import com.example.prm391_orchidora.Models.Order.CreateOrderRequest;
import com.example.prm391_orchidora.Models.Payment.PaymentResponseData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PaymentService {
    @POST("orders")
    Call<PaymentResponseData> postOrder(@Body CreateOrderRequest createOrderRequest);
}
