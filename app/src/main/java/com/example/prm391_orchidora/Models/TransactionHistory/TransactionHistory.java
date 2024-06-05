package com.example.prm391_orchidora.Models.TransactionHistory;

public class TransactionHistory {
    private int storeIcon;
    private String storeId;
    private String imageUrl;
    private String orchidName;
    private String orchidCategory;
    private int orchidQuantity;
    private String total;
    private int statusIcon;
    private String statusText;

    public TransactionHistory(int storeIcon, String storeId, String imageUrl, String orchidName, String orchidCategory, int orchidQuantity, String total, int statusIcon, String statusText) {
        this.storeIcon = storeIcon;
        this.storeId = storeId;
        this.imageUrl = imageUrl;
        this.orchidName = orchidName;
        this.orchidCategory = orchidCategory;
        this.orchidQuantity = orchidQuantity;
        this.total = total;
        this.statusIcon = statusIcon;
        this.statusText = statusText;
    }

    public int getStoreIcon() {
        return storeIcon;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getOrchidName() {
        return orchidName;
    }

    public String getOrchidCategory() {
        return orchidCategory;
    }

    public int getOrchidQuantity() {
        return orchidQuantity;
    }

    public String getTotal() {
        return total;
    }

    public int getStatusIcon() {
        return statusIcon;
    }

    public String getStatusText() {
        return statusText;
    }
}