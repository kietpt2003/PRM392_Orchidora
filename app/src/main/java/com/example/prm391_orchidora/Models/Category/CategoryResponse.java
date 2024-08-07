package com.example.prm391_orchidora.Models.Category;

public class CategoryResponse {
    private String id;
    private String name;

    public CategoryResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryResponse() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
