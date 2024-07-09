package com.example.prm391_orchidora.Models.Payment;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PaymentResponse implements Parcelable {
    private String id;
    private String paymentId;
    private String method;

    public PaymentResponse(String id, String paymentId, String method) {
        this.id = id;
        this.paymentId = paymentId;
        this.method = method;
    }

    protected PaymentResponse(Parcel in) {
        id = in.readString();
        paymentId = in.readString();
        method = in.readString();
    }

    public static final Creator<PaymentResponse> CREATOR = new Creator<PaymentResponse>() {
        @Override
        public PaymentResponse createFromParcel(Parcel in) {
            return new PaymentResponse(in);
        }

        @Override
        public PaymentResponse[] newArray(int size) {
            return new PaymentResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(paymentId);
        parcel.writeString(method);
    }
}
