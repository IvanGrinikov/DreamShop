package com.example.DreamShop.product.model;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
