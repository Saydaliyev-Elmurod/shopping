package com.example.shopping.client;

import com.example.shopping.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user")
@RestController
@AllArgsConstructor
public class ClientController {
    private final ProductService productService;

    @GetMapping("products/{categoryId}")
    public ResponseEntity<?> list(@PathVariable Integer categoryId,
                                  @RequestParam(name = "page", defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return productService.getAllByCategory(categoryId, page, size);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return productService.getByIdForUser(id);
    }
}
