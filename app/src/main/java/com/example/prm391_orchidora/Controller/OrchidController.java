package com.example.prm391_orchidora.Controller;

import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidDataResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.Services.ApiService;
import com.example.prm391_orchidora.Services.OrchidService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrchidController {
    private OrchidService orchidService;
    private OrchidGetCallback orchidGetCallback;

    private OrchidPostCallback orchidPostCallback;

    public OrchidController(OrchidGetCallback orchidGetCallback, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        orchidService = retrofit.create(OrchidService.class);
        this.orchidGetCallback = orchidGetCallback;
    }

    public void fetchOrchids() {
        Call<OrchidDataResponse> call = orchidService.getOrchids();
        call.enqueue(new Callback<OrchidDataResponse>() {
            @Override
            public void onResponse(Call<OrchidDataResponse> call, Response<OrchidDataResponse> response) {
                if (response.isSuccessful()) {
                    orchidGetCallback.onOrchidSuccessGet(response.body().getData());
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            orchidGetCallback.onOrchidErrorGet(errorResponse);
                        } else {
                            orchidGetCallback.onOrchidErrorGet(new ErrorResponse("Error","Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        orchidGetCallback.onOrchidErrorGet(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<OrchidDataResponse> call, Throwable throwable) {
                orchidGetCallback.onOrchidErrorGet(new ErrorResponse("Error", "Request fail"));
            }
        });
    }

    public void fetchOrchidsByCate() {
        Call<OrchidDataResponse> call = orchidService.getOrchids();
        call.enqueue(new Callback<OrchidDataResponse>() {
            @Override
            public void onResponse(Call<OrchidDataResponse> call, Response<OrchidDataResponse> response) {
                if (response.isSuccessful()) {
                    orchidGetCallback.onOrchidSuccessGet(response.body().getData());
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            orchidGetCallback.onOrchidErrorGet(errorResponse);
                        } else {
                            orchidGetCallback.onOrchidErrorGet(new ErrorResponse("Error","Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        orchidGetCallback.onOrchidErrorGet(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<OrchidDataResponse> call, Throwable throwable) {
                orchidGetCallback.onOrchidErrorGet(new ErrorResponse("Error", "Request fail"));
            }
        });
    }

    public interface OrchidGetCallback {
        void onOrchidSuccessGet(List<OrchidResponse> orchids);

        void onOrchidErrorGet(ErrorResponse errorMessage);
    }

    public interface OrchidPostCallback {
        void onOrchidSuccessPost(List<OrchidResponse> orchids);

        void onOrchidErrorPost(String errorMessage);
    }
}
