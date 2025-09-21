package com.example.product_service.service;

import com.example.product_service.dto.ProductRequest;
import com.example.product_service.entity.Product;
import com.example.product_service.repository.ProductRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(UUID id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product is not found!"));
    }

    public Product createProduct(ProductRequest request){
        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStatus(request.getStatus());

        productRepository.save(product);
        return product;
    }

    public Product updateProduct(UUID id,ProductRequest request){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product is not found!"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStatus(request.getStatus());

        productRepository.save(product);
        return product;
    }

    public void deleteProduct(UUID id){
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found!");
        }
        productRepository.deleteById(id);
    }
}
