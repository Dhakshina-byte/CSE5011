package com.example.cse5011.model;

public class Product {
    private int id;
    private String title;
    private String description;
    private String price;
    private int imageRes;

    public Product(int id, String title, String description, String price, int imageRes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageRes = imageRes;
    }

    public Product(String title, String description, String price, int imageRes) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageRes = imageRes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}
