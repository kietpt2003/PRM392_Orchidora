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
    private CategoryPostCallBack categoryPostCallBack;
    private CategoryPutCallBack categoryPutCallBack;
    private CategoryDeleteCallBack categoryDeleteCallBack;

    public CategoryController(CategoryController.CategoryGetCallBack categoryGetCallBack, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        categoryService = retrofit.create(CategoryService.class);
        this.categoryGetCallBack = categoryGetCallBack;
    }
    public CategoryController(CategoryController.CategoryPostCallBack categoryPostCallBack, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        categoryService = retrofit.create(CategoryService.class);
        this.categoryPostCallBack = categoryPostCallBack;
    }
    public CategoryController(CategoryController.CategoryPutCallBack categoryPutCallBack, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        categoryService = retrofit.create(CategoryService.class);
        this.categoryPutCallBack = categoryPutCallBack;
    }
    public CategoryController(CategoryController.CategoryDeleteCallBack categoryDeleteCallBack, String authToken) {
        Retrofit retrofit = new ApiService().getRetrofitInstanceAuth(authToken);
        categoryService = retrofit.create(CategoryService.class);
        this.categoryDeleteCallBack = categoryDeleteCallBack;
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

    public void updateCategory(String categoryId, CategoryResponse category) {
        Call<CategoryResponse> call = categoryService.updateCategory(categoryId, category);
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    // Notify the UI about the successful update
                    categoryPutCallBack.onCategorySuccessPut(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            categoryPutCallBack.onCategoryErrorPut(errorResponse);
                        } else {
                            categoryPutCallBack.onCategoryErrorPut(new ErrorResponse("Error", "Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        categoryPutCallBack.onCategoryErrorPut(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                categoryPutCallBack.onCategoryErrorPut(new ErrorResponse("Error", "Request fail"));
            }
        });
    }

    public void deleteCategory(String categoryId) {
        Call<Void> call = categoryService.deleteCategory(categoryId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Notify the UI about the successful delete
                    categoryDeleteCallBack.onCategorySuccessDelete();
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            categoryDeleteCallBack.onCategoryErrorDelete(errorResponse);
                        } else {
                            categoryDeleteCallBack.onCategoryErrorDelete(new ErrorResponse("Error", "Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        categoryDeleteCallBack.onCategoryErrorDelete(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                categoryDeleteCallBack.onCategoryErrorDelete(new ErrorResponse("Error", "Request failed"));
            }
        });
    }
    public void createCategory(CategoryResponse category) {
        Call<CategoryResponse> call = categoryService.createCategory(category);
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    // Notify the UI about the successful creation
                    categoryPostCallBack.onCategorySuccessPost(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            categoryPostCallBack.onCategoryErrorPost(errorResponse);
                        } else {
                            categoryPostCallBack.onCategoryErrorPost(new ErrorResponse("Error", "Request failed with no additional information"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        categoryPostCallBack.onCategoryErrorPost(new ErrorResponse("Error", "An error occurred while processing the error response"));
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                categoryPostCallBack.onCategoryErrorPost(new ErrorResponse("Error", "Request failed"));
            }
        });
    }


    public interface CategoryGetCallBack{
        void onCategorySuccessGet(List<CategoryResponse> categories);
        void onCategoryErrorGet(ErrorResponse errorResponse);
    }

    public interface CategoryPostCallBack{
        void onCategorySuccessPost(CategoryResponse category);
        void onCategoryErrorPost(ErrorResponse errorResponse);
    }
    public interface CategoryPutCallBack{
        void onCategorySuccessPut(CategoryResponse category);
        void onCategoryErrorPut(ErrorResponse errorResponse);
    }
    public interface CategoryDeleteCallBack{
        void onCategorySuccessDelete();
        void onCategoryErrorDelete(ErrorResponse errorResponse);
    }
}
