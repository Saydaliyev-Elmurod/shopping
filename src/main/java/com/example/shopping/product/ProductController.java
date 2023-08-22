package com.example.shopping.product;

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

@Tag(name = "Client Product APIs ", description = "Client product all API s")
@SecurityRequirement(name = "online shop ")
@RequestMapping("product")
@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))})
    @ApiResponse(responseCode = "404", description = "Not found")
    @Operation(description = "get all product by category id")
    @GetMapping("")
    public ResponseEntity<?> list(@RequestParam(name = "categoryId") Integer id,
                                  @RequestParam(name = "page", defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return productService.getAllByCategoryForUser(id, page, size);
    }

    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))})
    @Operation(description = "get by id user")
    @GetMapping("{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id) {
        return productService.getByIdForUser(id);
    }

    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))})
    @GetMapping("top")
    public ResponseEntity<?> top(@PathVariable Integer id) {
        return productService.getByIdForUser(id);
    }
}
