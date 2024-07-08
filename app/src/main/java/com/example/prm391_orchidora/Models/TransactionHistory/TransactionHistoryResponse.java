package com.example.prm391_orchidora.Models.TransactionHistory;

import java.util.List;

public class TransactionHistoryResponse {
    private List<TransactionHistory> data;
    private int count;

    // Constructor, getter và setter

    public List<TransactionHistory> getData() {
        return data;
    }

    public void setData(List<TransactionHistory> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}