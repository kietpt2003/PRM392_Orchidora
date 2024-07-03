package com.example.prm391_orchidora.Services;

import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidV2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OrchidService {
    @GET("orchids")
    Call<OrchidResponse> getOrchids();
}
