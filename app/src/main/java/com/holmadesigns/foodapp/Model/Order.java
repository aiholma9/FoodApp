package com.holmadesigns.foodapp.Model;

public class Order {
    private String ProductName;
    private String Quantity;
    private long Price;
    private long Discount;

    public Order() {
    }

    public Order(String productName, String quantity, long price, long discount) {
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public long getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }

    public long getDiscount() {
        return Discount;
    }

    public void setDiscount(long discount) {
        Discount = discount;
    }
}
