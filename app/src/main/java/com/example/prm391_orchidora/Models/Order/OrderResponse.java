package com.example.prm391_orchidora.Models.Order;

import com.example.prm391_orchidora.Models.Account.AccountResponse;

import java.util.List;

public class OrderResponse {
    private String id;
    private AccountResponse account;
    private String accountName;
    private String phoneNumber;
    private String address;
    private String status;
    private String createAt;
    private List<OrderItemResponse> items;
    private OrderPaymentResponse orderPayment;

    public OrderResponse(String id, AccountResponse account, String accountName, String phoneNumber, String address, String status, String createAt, List<OrderItemResponse> items, OrderPaymentResponse orderPayment) {
        this.id = id;
        this.account = account;
        this.accountName = accountName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.createAt = createAt;
        this.items = items;
        this.orderPayment = orderPayment;
    }

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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
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
}
