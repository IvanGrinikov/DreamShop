package com.example.DreamShop.product.services;

import com.example.DreamShop.Query;
import com.example.DreamShop.exceptions.ProductNotFoundException;
import com.example.DreamShop.product.ProductRepository;
import com.example.DreamShop.product.model.Product;
import com.example.DreamShop.product.model.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetProductService implements Query<Integer, ProductDTO> {

    private final ProductRepository productRepository;

    public GetProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(Integer input) {
        //account for null value if db can't find it
        Optional<Product> productOptional = productRepository.findById(input);
        if(productOptional.isPresent()){
            return ResponseEntity.ok(new ProductDTO(productOptional.get()));
        }

        throw new ProductNotFoundException();
    }
}
