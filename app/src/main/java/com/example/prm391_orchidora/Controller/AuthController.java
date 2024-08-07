package com.example.prm391_orchidora.Controller;

import com.example.prm391_orchidora.Models.Account.AccountResponse;
import com.example.prm391_orchidora.Models.Account.CreateAccountRequest;
import com.example.prm391_orchidora.Models.Auth.LoginRequest;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Services.ApiService;
import com.example.prm391_orchidora.Services.AuthService;
import com.google.gson.Gson;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthController {
    private AuthService authService;
    private LoginCallBack loginCallBack;

    private RegisterCallBack registerCallBack;

    public AuthController(LoginCallBack loginCallBack) {
        Retrofit retrofit = new ApiService().getRetrofitInstance();
        authService = retrofit.create(AuthService.class);
        this.loginCallBack = loginCallBack;
    }

    public AuthController(RegisterCallBack registerCallBack) {
        Retrofit retrofit = new ApiService().getRetrofitInstance();
        authService = retrofit.create(AuthService.class);
        this.registerCallBack = registerCallBack;
    }

    public void fetchAuth(LoginRequest requestBody) {
        Call<AccountResponse> call = authService.signIn(requestBody);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    AccountResponse data = response.body();
                    loginCallBack.onLoginSuccess(data);
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            loginCallBack.onLoginError(errorResponse);
                        } else {
                            loginCallBack.onLoginError(new ErrorResponse("Error","Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        loginCallBack.onLoginError(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                loginCallBack.onLoginError(new ErrorResponse("Error", "Request fail"));
            }
        });
    }

    public void fetchRegister(CreateAccountRequest requestBody) {
        Call<AccountResponse> call = authService.signUp(requestBody);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    AccountResponse data = response.body();
                    registerCallBack.onRegisterSuccess(data);
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            registerCallBack.onRegisterError(errorResponse);
                        } else {
                            registerCallBack.onRegisterError(new ErrorResponse("Error","Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        registerCallBack.onRegisterError(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                registerCallBack.onRegisterError(new ErrorResponse("Error", "Request fail"));
            }
        });
    }

    public interface LoginCallBack {
        void onLoginSuccess(AccountResponse accountResponse);
        void onLoginError(ErrorResponse errorResponse);
    }

    public interface RegisterCallBack {
        void onRegisterSuccess(AccountResponse accountResponse);
        void onRegisterError(ErrorResponse errorResponse);
    }
}
