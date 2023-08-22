package com.example.shopping.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "User Category APIs ", description = "User category all API s")
@RestController
@AllArgsConstructor
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryDto.class))})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "Not found")
    @Operation(description = "get by id for user get isVisible true or false ")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return categoryService.getByIdForUser(id);
    }




    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "No content")
    @Operation(description = "get all category by user (get only isVisible true))")
    @GetMapping("all")
    public ResponseEntity<?> getAllUser(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        return categoryService.getAllForUser(page, size);
    }

}
