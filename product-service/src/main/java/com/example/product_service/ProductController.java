package com.example.product_service;

import com.example.product_service.dto.ProductRequest;
import com.example.product_service.dto.ProductResponse;
import com.example.product_service.entity.Product;
import com.example.product_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        List<Product> allProduct = productService.getAllProducts();
        List<ProductResponse> responses = new ArrayList<>();
        for (Product product : allProduct) {
            responses.add(productMapper(product));
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id){
        ProductResponse response = productMapper(productService.getProductById(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
        Product product = productService.createProduct(request);
        ProductResponse response = productMapper(product);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id ,@Valid @RequestBody ProductRequest request){
        Product product = productService.updateProduct(id,request);
        ProductResponse response = productMapper(product);

        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id){
        productService.deleteProduct(id);

        return new ResponseEntity<>("Product deleted successfully",HttpStatus.ACCEPTED);
    }

    private ProductResponse productMapper(Product product){
        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStatus(product.getStatus());
        response.setAddedAt(product.getAddedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        return response;
    }
}
