package com.example.prm391_orchidora.Services;

import com.example.prm391_orchidora.Models.Order.CreateOrderRequest;
import com.example.prm391_orchidora.Models.Order.OrderResponse;
import com.example.prm391_orchidora.Models.Payment.PaymentResponseData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PaymentService {
    @POST("orders")
    Call<PaymentResponseData> postOrder(@Body CreateOrderRequest createOrderRequest);

    @GET("orders/{id}")
    Call<OrderResponse> getOrderById(@Query("id") String id);
}
