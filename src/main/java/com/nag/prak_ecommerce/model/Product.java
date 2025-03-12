package com.nag.prak_ecommerce.model;

import lombok.Data;

@Data
public class Product {
    private String id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String brand;
    private String imageUrl;
    private Boolean availability;
    private Double rating;
    private String[] tags;
}
