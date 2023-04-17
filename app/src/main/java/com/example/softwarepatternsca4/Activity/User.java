package com.example.softwarepatternsca4.Activity;

import java.io.Serializable;

public class User implements Serializable {

    String userId;
    String name;
    Boolean admin;
    String shippingAddress;
    String paymentMethod;
    String email;
    int cart;

    boolean activeDiscount;

    public User(String name, String shippingAddress, String paymentMethod, String email,String userid,int cart) {
        this.name = name;
        this.admin = false;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.email = email;
        this.userId = userid;
        this.cart = cart;
    }

    public boolean isActiveDiscount() {
        return activeDiscount;
    }

    public void setActiveDiscount(boolean activeDiscount) {
        this.activeDiscount = activeDiscount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCart() {
        return cart;
    }

    public void setCart(int cart) {
        this.cart = cart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
