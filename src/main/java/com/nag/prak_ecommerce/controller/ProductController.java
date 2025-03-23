package com.nag.prak_ecommerce.controller;

import com.nag.prak_ecommerce.service.OpenSearchProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/products")
public class ProductController {

    @Autowired
    private OpenSearchProductService openSearchProductService;

    @PostMapping
    public void addProduct(@RequestBody Map<String, Object> product) throws IOException {
//        openSearchProductService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) throws IOException {
        openSearchProductService.deleteProduct(id);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getProduct(@PathVariable String id) throws IOException {
        return openSearchProductService.getProduct(id);
    }

    @GetMapping
    public List<Map<String, Object>> getAllProducts() throws IOException {
        return openSearchProductService.getAllProducts();
    }
}