package com.example.prm391_orchidora.Controller;

import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Order.CreateOrderRequest;
import com.example.prm391_orchidora.Models.Order.OrderResponse;
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
    private GetPaymentByIdCallBack getPaymentByIdCallBack;
    private CancelPaymentCallBack cancelPaymentCallBack;
    private AcceptPaymentCallBack acceptPaymentCallBack;

    public PaymentController(PaymentController.PostPaymentCallBack postPaymentCallBack, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        paymentService = retrofit.create(PaymentService.class);
        this.postPaymentCallBack = postPaymentCallBack;
    }

    public PaymentController(PaymentController.GetPaymentByIdCallBack getPaymentByIdCallBack, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        paymentService = retrofit.create(PaymentService.class);
        this.getPaymentByIdCallBack = getPaymentByIdCallBack;
    }

    public PaymentController(PaymentController.CancelPaymentCallBack cancelPaymentCallBack, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        paymentService = retrofit.create(PaymentService.class);
        this.cancelPaymentCallBack = cancelPaymentCallBack;
    }

    public PaymentController(PaymentController.AcceptPaymentCallBack acceptPaymentCallBack, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        paymentService = retrofit.create(PaymentService.class);
        this.acceptPaymentCallBack = acceptPaymentCallBack;
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
                            postPaymentCallBack.onPostPaymentError(new ErrorResponse("Error", "Request failed with no additional information"));
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

    public void fetchOrderById(String id) {
        Call<OrderResponse> call = paymentService.getOrderById(id);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    getPaymentByIdCallBack.onGetPaymentByIdSuccess(response.body());
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            getPaymentByIdCallBack.onGetPaymentByIdError(errorResponse);
                        } else {
                            getPaymentByIdCallBack.onGetPaymentByIdError(new ErrorResponse("Error", "Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        getPaymentByIdCallBack.onGetPaymentByIdError(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable throwable) {
                getPaymentByIdCallBack.onGetPaymentByIdError(new ErrorResponse("Error", "Request fail"));
            }
        });
    }

    public void fetchCancelOrder(String id) {
        Call<Void> call = paymentService.cancelOrder(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    cancelPaymentCallBack.onCancelPaymentSuccess();
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            cancelPaymentCallBack.onCancelPaymentError(errorResponse);
                        } else {
                            cancelPaymentCallBack.onCancelPaymentError(new ErrorResponse("Error", "Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        cancelPaymentCallBack.onCancelPaymentError(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                cancelPaymentCallBack.onCancelPaymentError(new ErrorResponse("Error", "Request fail"));
            }
        });
    }

    public void fetchAcceptOrder(String id) {
        Call<Void> call = paymentService.acceptOrder(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    acceptPaymentCallBack.onAcceptPaymentSuccess();
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            acceptPaymentCallBack.onAcceptPaymentError(errorResponse);
                        } else {
                            acceptPaymentCallBack.onAcceptPaymentError(new ErrorResponse("Error", "Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        acceptPaymentCallBack.onAcceptPaymentError(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                acceptPaymentCallBack.onAcceptPaymentError(new ErrorResponse("Error", "Request fail"));
            }
        });
    }

    public interface GetPaymentByIdCallBack {
        void onGetPaymentByIdSuccess(OrderResponse orderResponse);

        void onGetPaymentByIdError(ErrorResponse errorResponse);
    }

    public interface PostPaymentCallBack {
        void onPostPaymentSuccess(PaymentResponseData paymentResponseData);

        void onPostPaymentError(ErrorResponse errorResponse);
    }

    public interface CancelPaymentCallBack {
        void onCancelPaymentSuccess();

        void onCancelPaymentError(ErrorResponse errorResponse);
    }

    public interface AcceptPaymentCallBack {
        void onAcceptPaymentSuccess();

        void onAcceptPaymentError(ErrorResponse errorResponse);
    }
}
