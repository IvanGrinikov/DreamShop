package com.example.DreamShop.product.services;

import com.example.DreamShop.Command;
import com.example.DreamShop.exceptions.ErrorMessages;
import com.example.DreamShop.exceptions.ProductNotValidException;
import com.example.DreamShop.product.ProductRepository;
import com.example.DreamShop.product.model.Product;
import com.example.DreamShop.product.model.ProductDTO;
import com.example.DreamShop.product.validators.ProductValidator;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateProductService implements Command<Product, ProductDTO> {

    private final ProductRepository productRepository;

    public CreateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(Product product) {
        ProductValidator.execute(product);
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDTO(savedProduct));
    }
}
