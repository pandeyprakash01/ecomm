package com.nag.prak_ecommerce;

import lombok.Data;

@Data
public class Product {
    private String id; // Use String for ID, or long if you are using a database.
    private String name;
    private String description;
    private double price;
    private String category;
    private String imageUrl;
}
