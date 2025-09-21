package com.example.product_service.dto;

import com.example.product_service.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductRequest {


    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 3, max = 20, message = "Product name must be between 3 and 20 characters")
    private String name;

    @NotBlank(message = "Product description cannot be blank")
    private String description;

    @NotNull(message = "product price cannot be null")
    private Double price;

    @NotNull(message = "Product status cannot be null")
    @Enumerated(EnumType.STRING)
    private Status status;

    public ProductRequest() {
    }

    public ProductRequest(String name, String description, Double price, Status status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
