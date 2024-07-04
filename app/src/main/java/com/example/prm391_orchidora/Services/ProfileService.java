package com.example.prm391_orchidora.Services;


import com.example.prm391_orchidora.Models.Account.AccountResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ProfileService {
    @GET("accounts/current")
    Call<AccountResponse> getAccount();


}
