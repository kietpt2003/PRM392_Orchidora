package com.example.prm391_orchidora.Controller;

import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.CreateOrchidRequest;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.Models.Order.CreateOrderRequest;
import com.example.prm391_orchidora.Models.Payment.PaymentResponseData;
import com.example.prm391_orchidora.Services.ApiService;
import com.example.prm391_orchidora.Services.PaymentService;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaymentController {
    private PaymentService paymentService;
    private PostPaymentCallBack postPaymentCallBack;

    public PaymentController(PaymentController.PostPaymentCallBack postPaymentCallBack, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        paymentService = retrofit.create(PaymentService.class);
        this.postPaymentCallBack = postPaymentCallBack;
    }

    public void createOrder(CreateOrderRequest createOrderRequest) {
        Call<PaymentResponseData> call = paymentService.postOrder(createOrderRequest);
        call.enqueue(new Callback<PaymentResponseData>() {
            @Override
            public void onResponse(Call<PaymentResponseData> call, Response<PaymentResponseData> response) {
                if (response.isSuccessful()) {
                    postPaymentCallBack.onPostPaymentSuccess(response.body());
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            postPaymentCallBack.onPostPaymentError(errorResponse);
                        } else {
                            postPaymentCallBack.onPostPaymentError(new ErrorResponse("Error","Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        postPaymentCallBack.onPostPaymentError(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentResponseData> call, Throwable throwable) {
                postPaymentCallBack.onPostPaymentError(new ErrorResponse("Error", "Request fail"));
            }
        });
    }

    public interface PostPaymentCallBack{
        void onPostPaymentSuccess(PaymentResponseData paymentResponseData);
        void onPostPaymentError(ErrorResponse errorResponse);
    }
}
