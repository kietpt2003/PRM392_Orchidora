package com.example.prm391_orchidora.Services;

import com.example.prm391_orchidora.Models.TransactionHistory.TransactionHistoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TransactionHistoryService {
    @GET("orders/current")
    Call<TransactionHistoryResponse> getCurrentOrders(@Query("status") String status);
}
