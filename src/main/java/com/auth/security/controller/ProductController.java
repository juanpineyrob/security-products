package com.auth.security.controller;

import com.auth.security.entity.Product;
import com.auth.security.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productRepository.findAll();

        if(!products.isEmpty()) {
            return ResponseEntity.ok(products);
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('SAVE_ONE_PRODUCT')")
    @PostMapping
    public ResponseEntity<Product> createOne(@RequestBody @Valid Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                productRepository.save(product)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception exception, HttpServletRequest request) {
        Map<String, String> apiError = new HashMap<>();
        apiError.put("message", "");
        apiError.put("timestamp", "");
        apiError.put("url", request.getRequestURL().toString());
        apiError.put("http-method", request.getMethod());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if(exception instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        }

        return ResponseEntity.status(status).body(apiError);
    }


}
