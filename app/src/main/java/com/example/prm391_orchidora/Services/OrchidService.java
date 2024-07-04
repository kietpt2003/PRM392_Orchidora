package com.example.prm391_orchidora.Services;

import com.example.prm391_orchidora.Models.Orchid.OrchidDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrchidService {
    @GET("orchids")
    Call<OrchidDataResponse> getOrchids(@Query("name") String name, @Query("status") String status);

    @GET("orchids/categories/{id}")
    Call<OrchidDataResponse> getOrchidsByCate(@Path("id") String id, @Query("name") String name);
}
