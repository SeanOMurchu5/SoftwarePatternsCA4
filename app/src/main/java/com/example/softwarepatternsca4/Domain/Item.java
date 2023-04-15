package com.example.softwarepatternsca4.Domain;

import java.io.Serializable;

public class Item implements Serializable {

    private String title;
    private String manufacturer;
    private String uniqueId;
    private double price;
    private String category;
    private String image;

    private int stock;
    private int numberInCart;


    public Item(String title, String manufacturer, String uniqueId, double price, String category, String image,int s) {
        this.title = title;
        this.manufacturer = manufacturer;
        this.uniqueId = uniqueId;
        this.price = price;
        this.category = category;
        this.image = image;
        this.stock  = s;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public int compareTo(Item other) {
        return title.compareTo(other.getTitle());
    }
}
