package com.example.prm391_orchidora.Models.Orchid;

import java.util.List;

public class OrchidDataResponse {
    private List<OrchidResponse> data;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OrchidResponse> getData() {
        return data;
    }

    public void setData(List<OrchidResponse> data) {
        this.data = data;
    }
}
