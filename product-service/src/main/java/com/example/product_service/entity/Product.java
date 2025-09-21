package com.example.product_service.entity;

import com.example.product_service.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 3, max = 20, message = "Product name must be between 3 and 20 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Product description cannot be blank")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "product price cannot be null")
    @Column(nullable = false)
    private Double price;

    @NotNull(message = "Product status cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime addedAt;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Product() {}

    public Product(String name, String description,Double price, Status status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        this.addedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

//    public void setId(UUID id) {
//        this.id = id;
//    }

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

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}