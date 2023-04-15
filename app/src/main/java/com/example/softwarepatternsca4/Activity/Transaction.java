package com.example.softwarepatternsca4.Activity;

import com.example.softwarepatternsca4.Domain.Item;

import java.util.ArrayList;

public class Transaction {
    public String productId;
    public String userId;
    public Double totalCost;
    public String uniqueId;

    ArrayList<Item> productIdList;

    public boolean stamped;

    public Transaction(){

    }

    public Transaction(ArrayList productId, String userId, Double totalCost, String uniqueId,boolean stamped) {
        this.productIdList = productId;
        this.userId = userId;
        this.totalCost = totalCost;
        this.uniqueId = uniqueId;
        this.stamped = stamped;
    }

    public ArrayList<Item> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(ArrayList<Item> productIdList) {
        this.productIdList = productIdList;
    }

    public boolean isStamped() {
        return stamped;
    }

    public void setStamped(boolean stamped) {
        this.stamped = stamped;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }


    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
