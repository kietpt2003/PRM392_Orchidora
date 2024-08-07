package com.example.prm391_orchidora.Controller;

import android.util.Log;

import com.example.prm391_orchidora.Models.Account.AccountResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Services.ApiService;
import com.example.prm391_orchidora.Services.ProfileService;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileController {
    private ProfileService profileService;
    private ProfileGetCallback profileGetCallback;

    public ProfileController(ProfileGetCallback profileGetCallback, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        profileService = retrofit.create(ProfileService.class);
        this.profileGetCallback = profileGetCallback;
    }

    public void getProfile() {
        Call<AccountResponse> call = profileService.getAccount();
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    profileGetCallback.onProfileSuccess(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            profileGetCallback.onProfileError(errorResponse);
                        } else {
                            profileGetCallback.onProfileError(new ErrorResponse("Error", "Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        profileGetCallback.onProfileError(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                profileGetCallback.onProfileError(new ErrorResponse("Error", "Request failed"));
            }
        });
    }

    public interface ProfileGetCallback {
        void onProfileSuccess(AccountResponse accountResponse);
        void onProfileError(ErrorResponse errorResponse);
    }
}
