package com.example.shopping.product;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("private/product")
@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping({"/", ""})
    public ResponseEntity<?> add(@RequestBody ProductDto dto) {
        return productService.add(dto);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return productService.get(id);
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody ProductDto dto) {
        return productService.update(dto);
    }

    @GetMapping("all/{id}")
    public ResponseEntity<?> list(@PathVariable Integer id,
                                  @RequestParam(name = "page", defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return productService.getAll(id, page, size);
    }

    @DeleteMapping("id")
    public ResponseEntity<?> delete(@PathVariable Integer id){
       return productService.delete(id);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id,@RequestParam (name = "status") Boolean status){
        return productService.updateStatus(id,status);
    }
}
