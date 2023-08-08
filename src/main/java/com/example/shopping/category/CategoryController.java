package com.example.shopping.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Admin Category APIs ", description = "Admin category all API s")
@RestController
@AllArgsConstructor
@RequestMapping("private/category")
@SecurityRequirement(name = "online shop ")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(
            summary = " add new category",
            description = " this api use add new category",
            tags = " admin ,category"
    )
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryDto.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    @PostMapping("/")
    public void add(@RequestBody CategoryDto dto) {
        categoryService.add(dto);
    }

    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryDto.class))})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "Not found")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))})
    @GetMapping("all")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(categoryService.getAll(page, size));
    }
}
