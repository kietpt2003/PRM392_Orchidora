package com.example.prm391_orchidora.Models.Order;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;

public class OrderItemResponse implements Parcelable {
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

    protected OrderItemResponse(Parcel in) {
        orchid = in.readParcelable(OrchidResponse.class.getClassLoader());
        name = in.readString();
        price = in.readInt();
        quantity = in.readInt();
    }

    public static final Creator<OrderItemResponse> CREATOR = new Creator<OrderItemResponse>() {
        @Override
        public OrderItemResponse createFromParcel(Parcel in) {
            return new OrderItemResponse(in);
        }

        @Override
        public OrderItemResponse[] newArray(int size) {
            return new OrderItemResponse[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(orchid, i);
        parcel.writeString(name);
        parcel.writeInt(price);
        parcel.writeInt(quantity);
    }
}
