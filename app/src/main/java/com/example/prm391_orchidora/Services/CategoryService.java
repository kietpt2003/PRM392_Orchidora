package com.example.prm391_orchidora.Services;

import com.example.prm391_orchidora.Models.Category.CategoryDataResponse;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryService {
    @GET("categories")
    Call<CategoryDataResponse> getCategories();

    @PUT("categories/{id}")
    Call<CategoryResponse> updateCategory(@Path("id") String categoryId, @Body CategoryResponse category);
}
