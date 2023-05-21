package com.prueba.service;

import com.prueba.model.Product;
import com.prueba.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRespository;

    public Product save(Product product) {
        return productRespository.save(product);
    }

    public Page<Product> getAll(Pageable pageable) {
        return productRespository.findAll(pageable);
    }

    public Product findProductId(Long id) {
        return productRespository.findById(id).get();
    }
}
