package com.example.prm391_orchidora.Models.Order;

public class OrderItemRequest {
    private String orchidId;
    private int quantity;

    public OrderItemRequest(String orchidId, int quantity) {
        this.orchidId = orchidId;
        this.quantity = quantity;
    }

    public String getOrchidId() {
        return orchidId;
    }

    public void setOrchidId(String orchidId) {
        this.orchidId = orchidId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
