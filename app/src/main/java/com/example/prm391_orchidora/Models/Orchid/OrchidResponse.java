package com.example.prm391_orchidora.Models.Orchid;

import java.util.List;

public class OrchidResponse {
    private List<OrchidV2> data;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OrchidV2> getData() {
        return data;
    }

    public void setData(List<OrchidV2> data) {
        this.data = data;
    }
}
