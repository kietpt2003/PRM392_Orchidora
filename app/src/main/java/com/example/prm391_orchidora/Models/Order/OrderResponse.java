package com.example.prm391_orchidora.Models.Order;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.prm391_orchidora.Models.Account.AccountResponse;

import java.util.List;

public class OrderResponse implements Parcelable {
    private String id;
    private AccountResponse account;
    private String accountName;
    private String phoneNumber;
    private String address;
    private String status;
    private String createdAt;
    private List<OrderItemResponse> items;
    private OrderPaymentResponse orderPayment;

    public OrderResponse(String id, AccountResponse account, String accountName, String phoneNumber, String address, String status, String createdAt, List<OrderItemResponse> items, OrderPaymentResponse orderPayment) {
        this.id = id;
        this.account = account;
        this.accountName = accountName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.createdAt = createdAt;
        this.items = items;
        this.orderPayment = orderPayment;
    }

    protected OrderResponse(Parcel in) {
        id = in.readString();
        account = in.readParcelable(AccountResponse.class.getClassLoader());
        accountName = in.readString();
        phoneNumber = in.readString();
        address = in.readString();
        status = in.readString();
        createdAt = in.readString();
        items = in.createTypedArrayList(OrderItemResponse.CREATOR);
        orderPayment = in.readParcelable(OrderPaymentResponse.class.getClassLoader());
    }

    public static final Creator<OrderResponse> CREATOR = new Creator<OrderResponse>() {
        @Override
        public OrderResponse createFromParcel(Parcel in) {
            return new OrderResponse(in);
        }

        @Override
        public OrderResponse[] newArray(int size) {
            return new OrderResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountResponse getAccount() {
        return account;
    }

    public void setAccount(AccountResponse account) {
        this.account = account;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreateAt(String createAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public OrderPaymentResponse getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(OrderPaymentResponse orderPayment) {
        this.orderPayment = orderPayment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeParcelable(account, i);
        parcel.writeString(accountName);
        parcel.writeString(phoneNumber);
        parcel.writeString(address);
        parcel.writeString(status);
        parcel.writeString(createdAt);
        parcel.writeTypedList(items);
        parcel.writeParcelable(orderPayment, i);
    }
}
