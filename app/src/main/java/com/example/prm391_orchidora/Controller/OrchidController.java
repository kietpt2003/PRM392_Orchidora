package com.example.prm391_orchidora.Controller;

import android.util.Log;

import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.CreateOrchidRequest;
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
    private OrchidGetByIdCallback orchidGetByIdCallback;
    private OrchidGetCallback orchidGetCallback;
    private OrchidByCateGetCallBack orchidByCateGetCallBack;
    private OrchidPostCallback orchidPostCallback;
    public OrchidController(OrchidGetByIdCallback orchidGetByIdCallback, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        orchidService = retrofit.create(OrchidService.class);
        this.orchidGetByIdCallback = orchidGetByIdCallback;
    }

    public OrchidController(OrchidGetCallback orchidGetCallback, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        orchidService = retrofit.create(OrchidService.class);
        this.orchidGetCallback = orchidGetCallback;
    }

    public OrchidController(OrchidPostCallback orchidPostCallback, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        orchidService = retrofit.create(OrchidService.class);
        this.orchidPostCallback = orchidPostCallback;
    }
    public OrchidController(OrchidByCateGetCallBack orchidByCateGetCallBack, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        orchidService = retrofit.create(OrchidService.class);
        this.orchidByCateGetCallBack = orchidByCateGetCallBack;
    }

    public void fetchOrchidById(String id) {
        Call<OrchidResponse> call = orchidService.getOrchidById(id);
        call.enqueue(new Callback<OrchidResponse>() {
            @Override
            public void onResponse(Call<OrchidResponse> call, Response<OrchidResponse> response) {
                if (response.isSuccessful()) {
                    orchidGetByIdCallback.onOrchidByIdSuccessGet(response.body());
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            orchidGetByIdCallback.onOrchidByIdErrorGet(errorResponse);
                        } else {
                            orchidGetByIdCallback.onOrchidByIdErrorGet(new ErrorResponse("Error","Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        orchidGetByIdCallback.onOrchidByIdErrorGet(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<OrchidResponse> call, Throwable throwable) {
                orchidGetByIdCallback.onOrchidByIdErrorGet(new ErrorResponse("Error", "Request fail"));
            }
        });
    }

    public void fetchOrchids(String name, String status) {
        Call<OrchidDataResponse> call = orchidService.getOrchids(name, status);
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

    public void fetchOrchidsByCate(String id, String name) {
        Call<OrchidDataResponse> call = orchidService.getOrchidsByCate(id, name);
        call.enqueue(new Callback<OrchidDataResponse>() {
            @Override
            public void onResponse(Call<OrchidDataResponse> call, Response<OrchidDataResponse> response) {
                if (response.isSuccessful()) {
                    orchidByCateGetCallBack.onOrchidByCateSuccessGet(response.body().getData());
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            orchidByCateGetCallBack.onOrchidByCateErrorGet(errorResponse);
                        } else {
                            orchidByCateGetCallBack.onOrchidByCateErrorGet(new ErrorResponse("Error","Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        orchidByCateGetCallBack.onOrchidByCateErrorGet(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<OrchidDataResponse> call, Throwable throwable) {
                orchidByCateGetCallBack.onOrchidByCateErrorGet(new ErrorResponse("Error", "Request fail"));
            }
        });
    }

    public void createOrchid(CreateOrchidRequest createOrchidRequest) {
        Call<OrchidResponse> call = orchidService.postOrchid(createOrchidRequest);
        call.enqueue(new Callback<OrchidResponse>() {
            @Override
            public void onResponse(Call<OrchidResponse> call, Response<OrchidResponse> response) {
                if (response.isSuccessful()) {
                    orchidPostCallback.onOrchidSuccessPost(response.body());
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            orchidPostCallback.onOrchidErrorPost(errorResponse);
                        } else {
                            orchidPostCallback.onOrchidErrorPost(new ErrorResponse("Error","Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        orchidPostCallback.onOrchidErrorPost(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<OrchidResponse> call, Throwable throwable) {
                orchidPostCallback.onOrchidErrorPost(new ErrorResponse("Error", "Request fail"));
            }
        });
    }
    public interface OrchidGetByIdCallback {
        void onOrchidByIdSuccessGet(OrchidResponse orchid);

        void onOrchidByIdErrorGet(ErrorResponse errorMessage);
    }

    public interface OrchidGetCallback {
        void onOrchidSuccessGet(List<OrchidResponse> orchids);

        void onOrchidErrorGet(ErrorResponse errorMessage);
    }

    public interface OrchidByCateGetCallBack {
        void onOrchidByCateSuccessGet(List<OrchidResponse> orchids);

        void onOrchidByCateErrorGet(ErrorResponse errorMessage);
    }

    public interface OrchidPostCallback {
        void onOrchidSuccessPost(OrchidResponse orchid);

        void onOrchidErrorPost(ErrorResponse errorResponse);
    }
}
