package com.example.prm391_orchidora.Models.Payment;

public class PaymentResponseData {
    private String id;
    private String paymentLinkId;
    private String orderCode;
    private String checkoutUrl;
    private String qrCode;

    public PaymentResponseData(String id, String paymentLinkId, String orderCode, String checkoutUrl, String qrCode) {
        this.id = id;
        this.paymentLinkId = paymentLinkId;
        this.orderCode = orderCode;
        this.checkoutUrl = checkoutUrl;
        this.qrCode = qrCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentLinkId() {
        return paymentLinkId;
    }

    public void setPaymentLinkId(String paymentLinkId) {
        this.paymentLinkId = paymentLinkId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public void setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
