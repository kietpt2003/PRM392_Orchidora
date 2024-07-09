package com.example.prm391_orchidora.Models.Order;

public class OrderPaymentResponse {
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
}
