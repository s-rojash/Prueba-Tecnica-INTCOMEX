package com.prueba.controller;

import com.github.javafaker.Faker;
import com.prueba.model.Category;
import com.prueba.model.Product;
import com.prueba.service.CategoryService;
import com.prueba.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
@RestController
public class ProductController {

    private final Faker faker;

    Random random = new Random();

    @Autowired()
    private ProductService productService;
    private CategoryService categoryService;

    public ProductController() {
        this.faker = new Faker();
    }

    @PostMapping("Product/")
    public ResponseEntity<Product> post(@RequestBody Product product) {
        try {
            List<Category> categoryList = categoryService.getAll();
            for (int indice=0; indice <= 100_000; indice++) {
                Product product1 = new Product();

                product1.setProductName(String.valueOf(faker.name().name()));
                product1.setSupplierId(faker.number().randomNumber());
                product1.setCategory(categoryList.get(random.nextInt(categoryList.size())));
                product1.setQuantityPerUnit((int) faker.number().randomNumber());
                product1.setUnitPrice((int) faker.number().randomNumber());
                product1.setUnitsInStock((int) faker.number().randomNumber());
                product1.setUnitsOnOrder((int) faker.number().randomNumber());
                product1.setReorderLevel((int) faker.number().randomNumber());
                product1.setDiscontinued(faker.bool().bool());

                productService.save(product1);
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(product);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("Products/")
    public ResponseEntity<Page<Product>> getAll(Pageable pageable) {
        return ResponseEntity.ok(productService.getAll(pageable));
    }

    @GetMapping("Product/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }
}
