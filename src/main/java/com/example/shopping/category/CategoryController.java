package com.example.shopping.category;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("private/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/")
    public void add(@RequestBody CategoryDto dto) {
        categoryService.add(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(categoryService.getAll(page, size));
    }
}
