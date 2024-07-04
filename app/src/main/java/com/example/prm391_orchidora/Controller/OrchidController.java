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
    private OrchidActivateCallback orchidActivateCallback;
    private OrchidDeactivateCallback orchidDeactivateCallback;
    private OrchidPutCallback orchidPutCallback;

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

    public OrchidController(OrchidActivateCallback orchidActivateCallback, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        orchidService = retrofit.create(OrchidService.class);
        this.orchidActivateCallback = orchidActivateCallback;
    }

    public OrchidController(OrchidDeactivateCallback orchidDeactivateCallback, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        orchidService = retrofit.create(OrchidService.class);
        this.orchidDeactivateCallback = orchidDeactivateCallback;
    }

    public OrchidController(OrchidPutCallback orchidPutCallback, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        orchidService = retrofit.create(OrchidService.class);
        this.orchidPutCallback = orchidPutCallback;
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

    public void fetchOrchidsByCate(String id, String name, String status) {
        Call<OrchidDataResponse> call = orchidService.getOrchidsByCate(id, name,status);
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

    public void activationOrchid(String id) {
        Call<Void> call = orchidService.activateOrchid(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    orchidActivateCallback.onOrchidSuccessActivate();
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            orchidActivateCallback.onOrchidErrorActivate(errorResponse);
                        } else {
                            orchidActivateCallback.onOrchidErrorActivate(new ErrorResponse("Error","Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        orchidActivateCallback.onOrchidErrorActivate(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                orchidActivateCallback.onOrchidErrorActivate(new ErrorResponse("Error", "Request fail"));
            }
        });
    }

    public void deactivationOrchid(String id) {
        Call<Void> call = orchidService.deactivateOrchid(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    orchidDeactivateCallback.onOrchidSuccessDeactivate();
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            orchidDeactivateCallback.onOrchidErrorDeactivate(errorResponse);
                        } else {
                            orchidDeactivateCallback.onOrchidErrorDeactivate(new ErrorResponse("Error","Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        orchidDeactivateCallback.onOrchidErrorDeactivate(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                orchidDeactivateCallback.onOrchidErrorDeactivate(new ErrorResponse("Error", "Request fail"));
            }
        });
    }

    public void updateOrchid(String id,CreateOrchidRequest createOrchidRequest) {
        Call<OrchidResponse> call = orchidService.putOrchid(id,createOrchidRequest);
        call.enqueue(new Callback<OrchidResponse>() {
            @Override
            public void onResponse(Call<OrchidResponse> call, Response<OrchidResponse> response) {
                if (response.isSuccessful()) {
                    orchidPutCallback.onOrchidSuccessPut(response.body());
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            orchidPutCallback.onOrchidErrorPut(errorResponse);
                        } else {
                            orchidPutCallback.onOrchidErrorPut(new ErrorResponse("Error","Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        orchidPutCallback.onOrchidErrorPut(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<OrchidResponse> call, Throwable throwable) {
                orchidPutCallback.onOrchidErrorPut(new ErrorResponse("Error", "Request fail"));
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
    public interface OrchidActivateCallback {
        void onOrchidSuccessActivate();

        void onOrchidErrorActivate(ErrorResponse errorResponse);
    }
    public interface OrchidDeactivateCallback {
        void onOrchidSuccessDeactivate();

        void onOrchidErrorDeactivate(ErrorResponse errorResponse);
    }
    public interface OrchidPutCallback {
        void onOrchidSuccessPut(OrchidResponse orchid);

        void onOrchidErrorPut(ErrorResponse errorResponse);
    }
}
