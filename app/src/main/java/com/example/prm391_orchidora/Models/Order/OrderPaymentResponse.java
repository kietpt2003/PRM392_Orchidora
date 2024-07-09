package com.example.prm391_orchidora.Models.Order;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class OrderPaymentResponse implements Parcelable {
    private int amount;
    private String paymentMethod;
    private String paidOn;
    private int orderCode;

    public OrderPaymentResponse(int amount, String paymentMethod, String paidOn, int orderCode) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paidOn = paidOn;
        this.orderCode = orderCode;
    }

    protected OrderPaymentResponse(Parcel in) {
        amount = in.readInt();
        paymentMethod = in.readString();
        paidOn = in.readString();
        orderCode = in.readInt();
    }

    public static final Creator<OrderPaymentResponse> CREATOR = new Creator<OrderPaymentResponse>() {
        @Override
        public OrderPaymentResponse createFromParcel(Parcel in) {
            return new OrderPaymentResponse(in);
        }

        @Override
        public OrderPaymentResponse[] newArray(int size) {
            return new OrderPaymentResponse[size];
        }
    };

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaidOn() {
        return paidOn;
    }

    public void setPaidOn(String paidOn) {
        this.paidOn = paidOn;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(amount);
        parcel.writeString(paymentMethod);
        parcel.writeString(paidOn);
        parcel.writeInt(orderCode);
    }
}
