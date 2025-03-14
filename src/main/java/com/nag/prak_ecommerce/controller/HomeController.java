package com.nag.prak_ecommerce.controller;

import com.nag.prak_ecommerce.service.OpenSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.io.IOException;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class HomeController {

    @Autowired
    private OpenSearchService openSearchService;

    @GetMapping("/")
    public String yo(@RequestParam String searchValue){
        System.out.println("hit came from angular");
        return "welcome ";
    }

    @GetMapping("/product")
    public String home(){
        return "welcome to home page";
    }

    @GetMapping("/products/search")
    public List<Map<String, Object>> searchProducts(@RequestParam String searchValue) throws IOException {
        System.out.println("product keyword is :"+searchValue);
        return openSearchService.searchProducts("products", searchValue);
    }

    @GetMapping("/movies")
    public List<Map<String, Object>> searchMovies(@RequestParam String keyword) throws IOException {
        System.out.println("movies keyword is :"+keyword);
        return openSearchService.searchMovies("movies", keyword);
    }

}
