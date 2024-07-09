package com.example.prm391_orchidora.Models.TransactionHistory;

import com.example.prm391_orchidora.Models.Order.OrderResponse;

import java.util.List;

public class TransactionHistoryResponse {
    private List<OrderResponse> data;
    private int count;

    // Constructor, getter và setter

    public List<OrderResponse> getData() {
        return data;
    }

    public void setData(List<OrderResponse> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}