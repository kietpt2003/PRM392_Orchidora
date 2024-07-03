package com.example.prm391_orchidora.Models.Orchid;

public class OrchidOld {

    private String imageUrl;
    private String name;
    private String category;
    private double price;

    public OrchidOld() {
    }

    public OrchidOld(String imageUrl, String name, String category, double price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.category = category;
        this.price = price;
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
}
