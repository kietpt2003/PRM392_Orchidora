package com.example.prm391_orchidora.Services;

import androidx.annotation.NonNull;

import com.example.prm391_orchidora.Models.Orchid.CreateOrchidRequest;
import com.example.prm391_orchidora.Models.Orchid.OrchidDataResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrchidService {
    @GET("orchids")
    Call<OrchidDataResponse> getOrchids(@Query("name") String name, @Query("status") String status);

    @GET("orchids/{id}")
    Call<OrchidResponse> getOrchidById(@Query("id") String id);

    @GET("orchids/categories/{id}")
    Call<OrchidDataResponse> getOrchidsByCate(@Query("id") String id, @Query("name") String name);

    @POST("orchids")
    Call<OrchidResponse> postOrchid(@Body CreateOrchidRequest createOrchidRequest);
}
