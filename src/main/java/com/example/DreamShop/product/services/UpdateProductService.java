package com.example.DreamShop.product.services;

import com.example.DreamShop.Command;
import com.example.DreamShop.exceptions.ProductNotFoundException;
import com.example.DreamShop.product.ProductRepository;
import com.example.DreamShop.product.model.Product;
import com.example.DreamShop.product.model.ProductDTO;
import com.example.DreamShop.product.model.UpdateProductCommand;
import com.example.DreamShop.product.validators.ProductValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateProductService implements Command<UpdateProductCommand, ProductDTO> {

    private ProductRepository productRepository;

    public UpdateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(UpdateProductCommand command) {
        Optional<Product> productOptional = productRepository.findById(command.getId());
        if(productOptional.isPresent()){
            Product product = command.getProduct();
            product.setId(command.getId());
            ProductValidator.execute(product);
            productRepository.save(product);
            return ResponseEntity.ok(new ProductDTO(product));
        }

        throw new ProductNotFoundException();
    }
}
