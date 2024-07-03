package com.example.prm391_orchidora.Services;

import com.example.prm391_orchidora.Models.Orchid.OrchidDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OrchidService {
    @GET("orchids")
    Call<OrchidDataResponse> getOrchids();
}
