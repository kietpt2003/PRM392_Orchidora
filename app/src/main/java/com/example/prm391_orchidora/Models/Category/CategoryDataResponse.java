package com.example.prm391_orchidora.Models.Category;

import java.util.List;

public class CategoryDataResponse {
    private List<CategoryResponse> data;
    private int count;

    public CategoryDataResponse(List<CategoryResponse> data, int count) {
        this.data = data;
        this.count = count;
    }

    public List<CategoryResponse> getData() {
        return data;
    }

    public void setData(List<CategoryResponse> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
