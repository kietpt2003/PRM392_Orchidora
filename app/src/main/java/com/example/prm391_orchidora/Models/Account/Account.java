package com.example.prm391_orchidora.Models.Account;

import com.example.prm391_orchidora.Models.Payment.PaymentResponse;

import java.util.List;

public class Account {
    private String id;
    private String name;
    private String email;
    private String role;
    private String phoneNumber;
    private String address;
    private String status;
    private List<PaymentResponse> payments;


    public Account(String id, String name, String email, String role, String phoneNumber, String address, String status, List payments) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.payments = payments;
    }

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
}
