package com.example.prm391_orchidora.Controller;

import android.util.Log;

import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidV2;
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
        Retrofit retrofit = ApiService.getRetrofitInstanceAuth(authToken);
        orchidService = retrofit.create(OrchidService.class);
        this.orchidGetCallback = orchidGetCallback;
    }

    public void fetchOrchids() {
        Call<OrchidResponse> call = orchidService.getOrchids();
        call.enqueue(new Callback<OrchidResponse>() {
            @Override
            public void onResponse(Call<OrchidResponse> call, Response<OrchidResponse> response) {
                if (response.isSuccessful()) {
                    orchidGetCallback.onSuccessGet(response.body().getData());
                } else {
                    Log.i("Kiet", "Error body: " + response.toString());
//                    try {
//                        // Xử lý phản hồi không thành công
//                        if (response.errorBody() != null) {
//                            String errorBody = response.errorBody().string();
//                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
//                            Gson gson = new Gson();
//                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
//                            orchidGetCallback.onError(errorResponse);
//                        } else {
//                            orchidGetCallback.onError(new ErrorResponse("Error","Request failed with no additional information"));
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        orchidGetCallback.onError(new ErrorResponse("Error", "An error occurred while processing the error response"));
//                    }
                }
            }

            @Override
            public void onFailure(Call<OrchidResponse> call, Throwable throwable) {
//                orchidGetCallback.onError(throwable.getMessage());
            }
        });
    }

    public interface OrchidGetCallback {
        void onSuccessGet(List<OrchidV2> orchids);

        void onError(ErrorResponse errorMessage);
    }

    public interface OrchidPostCallback {
        void onSuccessGet(List<OrchidV2> orchids);

        void onError(String errorMessage);
    }
}
