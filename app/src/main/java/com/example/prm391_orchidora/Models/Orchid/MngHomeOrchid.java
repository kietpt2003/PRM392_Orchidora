package com.example.prm391_orchidora.Models.Orchid;

public class MngHomeOrchid {

    private String imageUrl;
    private String name;
    private String category;
    private double price;
    private int quantity;

    public MngHomeOrchid(String imageUrl, String name, String category, double price, int quantity) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
