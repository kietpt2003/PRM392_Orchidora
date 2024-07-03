package com.example.prm391_orchidora.Controller;

import com.example.prm391_orchidora.Models.Category.CategoryDataResponse;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidDataResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.Services.ApiService;
import com.example.prm391_orchidora.Services.CategoryService;
import com.example.prm391_orchidora.Services.OrchidService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryController {
    private CategoryService categoryService;
    private CategoryGetCallBack categoryGetCallBack;

    public CategoryController(CategoryController.CategoryGetCallBack categoryGetCallBack, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        categoryService = retrofit.create(CategoryService.class);
        this.categoryGetCallBack = categoryGetCallBack;
    }

    public void fetchCategories() {
        Call<CategoryDataResponse> call = categoryService.getCategories();
        call.enqueue(new Callback<CategoryDataResponse>() {
            @Override
            public void onResponse(Call<CategoryDataResponse> call, Response<CategoryDataResponse> response) {
                if (response.isSuccessful()) {
                    categoryGetCallBack.onCategorySuccessGet(response.body().getData());
                } else {
                    try {
                        // Xử lý phản hồi không thành công
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            // Chuyển đổi errorBody thành đối tượng ErrorResponse
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            categoryGetCallBack.onCategoryErrorGet(errorResponse);
                        } else {
                            categoryGetCallBack.onCategoryErrorGet(new ErrorResponse("Error","Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        categoryGetCallBack.onCategoryErrorGet(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryDataResponse> call, Throwable throwable) {
                categoryGetCallBack.onCategoryErrorGet(new ErrorResponse("Error", "Request fail"));
            }
        });
    }
    public interface CategoryGetCallBack{
        void onCategorySuccessGet(List<CategoryResponse> categories);

        void onCategoryErrorGet(ErrorResponse errorResponse);
    }
}
