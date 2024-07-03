package com.example.prm391_orchidora.Models.Orchid;

public class OrchidResponse {
    private String id;
    private int price;
    private String category;
    private String color;
    private String description;
    private String img;
    private int quantity;
    private String status;
    private String name;

    public OrchidResponse(String id, int price, String category, String color, String description, String img, int quantity, String status, String name) {
        this.id = id;
        this.price = price;
        this.category = category;
        this.color = color;
        this.description = description;
        this.img = img;
        this.quantity = quantity;
        this.status = status;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
