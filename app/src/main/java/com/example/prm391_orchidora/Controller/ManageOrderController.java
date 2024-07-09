package com.example.prm391_orchidora.Controller;

import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.TransactionHistory.TransactionHistoryResponse;
import com.example.prm391_orchidora.Services.ApiService;
import com.example.prm391_orchidora.Services.TransactionHistoryService;
import com.google.gson.Gson;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ManageOrderController {
    private TransactionHistoryService transactionService;
    private ManageOrderCallback manageOrderCallback;

    public ManageOrderController(ManageOrderCallback manageOrderCallback, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        transactionService = retrofit.create(TransactionHistoryService.class);
        this.manageOrderCallback = manageOrderCallback;
    }

    public void getAllOrders(String status) {
        Call<TransactionHistoryResponse> call = transactionService.getAllOrders(status);
        call.enqueue(new Callback<TransactionHistoryResponse>() {
            @Override
            public void onResponse(Call<TransactionHistoryResponse> call, Response<TransactionHistoryResponse> response) {
                if (response.isSuccessful()) {
                    manageOrderCallback.onManageOrderSuccess(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            manageOrderCallback.onManageOrderError(errorResponse);
                        } else {
                            manageOrderCallback.onManageOrderError(new ErrorResponse("Error", "Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        manageOrderCallback.onManageOrderError(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<TransactionHistoryResponse> call, Throwable t) {
                manageOrderCallback.onManageOrderError(new ErrorResponse("Error", "Request failed"));
            }
        });
    }

    public interface ManageOrderCallback {
        void onManageOrderSuccess(TransactionHistoryResponse transactionHistoryResponse);
        void onManageOrderError(ErrorResponse errorResponse);
    }
}
