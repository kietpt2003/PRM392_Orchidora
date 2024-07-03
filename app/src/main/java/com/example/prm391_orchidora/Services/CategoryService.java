package com.example.prm391_orchidora.Services;

import com.example.prm391_orchidora.Models.Category.CategoryDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {
    @GET("categories")
    Call<CategoryDataResponse> getCategories();
}
