package com.example.DreamShop;

import com.example.DreamShop.exceptions.ProductNotFoundException;
import com.example.DreamShop.product.ProductRepository;
import com.example.DreamShop.product.model.Product;
import com.example.DreamShop.product.model.ProductDTO;
import com.example.DreamShop.product.services.GetProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetProductServiceTest {

    @Mock //shows what to mock
    ProductRepository productRepository;

    @InjectMocks // the thing we are testing
    private GetProductService getProductService;

    @BeforeEach //things we need to fo before the test
    public void setup(){
        //initializes repository and service class
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_product_exists_when_get_product_service_return_product_dto(){
        //given
        Product product = new Product();
        product.setId(1);
        product.setName("Product Name");
        product.setDescription("Product description which is at least 20 characters lalalala");
        product.setPrice(9.99);
        //says when but it's technically a setting up
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        //when
        ResponseEntity<ProductDTO> response = getProductService.execute(1);

        //then
        assertEquals(ResponseEntity.ok(new ProductDTO(product)), response);
        //asserts the product repository was only called once
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    public void given_product_does_not_exist_when_get_product_service_throw_product_not_found_exception(){
        //given
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        //when and then
        assertThrows(ProductNotFoundException.class, () -> getProductService.execute(1));
        verify(productRepository, times(1)).findById(1);
    }
}
