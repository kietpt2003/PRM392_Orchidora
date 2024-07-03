package com.example.prm391_orchidora.Models.Payment;

public class PaymentResponse {
    private String id;
    private String paymentId;
    private String method;

    public PaymentResponse(String id, String paymentId, String method) {
        this.id = id;
        this.paymentId = paymentId;
        this.method = method;
    }

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
}
