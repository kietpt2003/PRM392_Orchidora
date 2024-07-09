package com.example.prm391_orchidora.Models.Account;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.prm391_orchidora.Models.Payment.PaymentResponse;

import java.util.List;

public class AccountResponse implements Parcelable {
    private String id;
    private String name;
    private String email;
    private String role;
    private String phoneNumber;
    private String address;
    private String status;
    private List<PaymentResponse> payments;


    public AccountResponse(String id, String name, String email, String role, String phoneNumber, String address, String status, List payments) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.payments = payments;
    }

    protected AccountResponse(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        role = in.readString();
        phoneNumber = in.readString();
        address = in.readString();
        status = in.readString();
        payments = in.createTypedArrayList(PaymentResponse.CREATOR);
    }

    public static final Creator<AccountResponse> CREATOR = new Creator<AccountResponse>() {
        @Override
        public AccountResponse createFromParcel(Parcel in) {
            return new AccountResponse(in);
        }

        @Override
        public AccountResponse[] newArray(int size) {
            return new AccountResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public List<PaymentResponse> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentResponse> payments) {
        this.payments = payments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(role);
        parcel.writeString(phoneNumber);
        parcel.writeString(address);
        parcel.writeString(status);
        parcel.writeTypedList(payments);
    }
}
