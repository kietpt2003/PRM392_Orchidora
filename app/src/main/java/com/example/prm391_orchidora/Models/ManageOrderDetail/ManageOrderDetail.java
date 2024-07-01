package com.example.prm391_orchidora.Models.ManageOrderDetail;

public class ManageOrderDetail {
    private String img;
    private String name;
    private String category;
    private int quantity;
    private double price;

    public ManageOrderDetail(String img, String name, String category, int quantity, double price) {
        this.img = img;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
