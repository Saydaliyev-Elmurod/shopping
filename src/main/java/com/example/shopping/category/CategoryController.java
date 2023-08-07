package com.example.shopping.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin Category APIs ", description = "Admin category all API s")
@RestController
@AllArgsConstructor
@RequestMapping("private/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(
            summary = " Summary",
            description = " description",
            tags = " admin ,category"
    )
    @ApiResponse(responseCode = "200",content = {@Content(schema = @Schema(implementation = CategoryDto.class),mediaType = "application/json")})
    @ApiResponse(responseCode = "400",content = {@Content(schema = @Schema())})
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
