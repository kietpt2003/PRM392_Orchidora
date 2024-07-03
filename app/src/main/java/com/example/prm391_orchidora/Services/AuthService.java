package com.example.prm391_orchidora.Services;

import com.example.prm391_orchidora.Models.Account.AccountResponse;
import com.example.prm391_orchidora.Models.Account.CreateAccountRequest;
import com.example.prm391_orchidora.Models.Auth.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("accounts/auth")
    Call<AccountResponse> signIn(@Body LoginRequest requestBody);

    @POST("accounts")
    Call<AccountResponse> signUp(@Body CreateAccountRequest requestBody);
}
