package com.example.prm391_orchidora.Models.Order;

import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;

public class OrderItemResponse {
    private OrchidResponse orchid;
    private String name;
    private int price;
    private int quantity;

    public OrderItemResponse(OrchidResponse orchid, String name, int price, int quantity) {
        this.orchid = orchid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public OrchidResponse getOrchid() {
        return orchid;
    }

    public void setOrchid(OrchidResponse orchid) {
        this.orchid = orchid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
