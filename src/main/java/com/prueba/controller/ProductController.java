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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
@RestController
public class ProductController {

    private final Faker faker;

    Random random = new Random();

    @Autowired()
    private ProductService productService;
    @Autowired()
    private CategoryService categoryService;

    public ProductController() {
        this.faker = new Faker();
    }

    @PostMapping("Product/")
    public ResponseEntity<Product> post() {
        try {
            List<Category> categoryList = categoryService.getAll();
            for (int indice=0; indice < 100_000; indice++) {
                Product product1 = new Product();

                product1.setProductName(String.valueOf(faker.name().name()));
                product1.setSupplierId(faker.number().randomNumber());
                product1.setQuantityPerUnit((int) faker.number().randomNumber());
                product1.setUnitPrice((int) faker.number().randomNumber());
                product1.setUnitsInStock((int) faker.number().randomNumber());
                product1.setUnitsOnOrder((int) faker.number().randomNumber());
                product1.setReorderLevel((int) faker.number().randomNumber());
                product1.setDiscontinued(faker.bool().bool());
                product1.setCategory(categoryList.get(random.nextInt(categoryList.size())));

                productService.save(product1);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("Product/{quantity}")
    public ResponseEntity<Product> postCant(@PathVariable Long quantity) {
        try {
            List<Category> categoryList = categoryService.getAll();
            for (int indice=0; indice < quantity; indice++) {
                Product product2 = new Product();

                product2.setProductName(String.valueOf(faker.name().name()));
                product2.setSupplierId(faker.number().randomNumber());
                product2.setQuantityPerUnit((int) faker.number().randomNumber());
                product2.setUnitPrice((int) faker.number().randomNumber());
                product2.setUnitsInStock((int) faker.number().randomNumber());
                product2.setUnitsOnOrder((int) faker.number().randomNumber());
                product2.setReorderLevel((int) faker.number().randomNumber());
                product2.setDiscontinued(faker.bool().bool());
                product2.setCategory(categoryList.get(random.nextInt(categoryList.size())));

                productService.save(product2);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("Products/")
    public ResponseEntity<Page<Product>> getAll(Pageable pageable) {
        return ResponseEntity.ok(productService.getAll(pageable));
    }

    @GetMapping("Products/{id}")
    public ResponseEntity<Product> getId(@PathVariable Long id) {
        try{
            Product product = productService.findProductId(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("Product/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }
}
