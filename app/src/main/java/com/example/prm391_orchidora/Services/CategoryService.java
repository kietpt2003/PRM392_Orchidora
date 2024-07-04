package com.example.prm391_orchidora.Services;

import com.example.prm391_orchidora.Models.Category.CategoryDataResponse;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.Query;
public interface CategoryService {
    @GET("categories")
    Call<CategoryDataResponse> getCategories();

    @PUT("categories/{id}")
    Call<CategoryResponse> updateCategory(@Query("id") String categoryId, @Body CategoryResponse category);

    @DELETE("categories/{id}")
    Call<Void> deleteCategory(@Query("id") String categoryId);

    @POST("categories")
    Call<CategoryResponse> createCategory(@Body CategoryResponse category);
}
