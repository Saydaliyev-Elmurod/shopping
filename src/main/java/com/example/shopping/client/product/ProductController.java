package com.example.shopping.client.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Client Product APIs ", description = "Client product all API s")
@RequestMapping("product")
@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDto.class)))})
    @ApiResponse(responseCode = "404", description = "Not found")
    @Operation(description = "get all product by category id")
    @GetMapping("")
    public ResponseEntity<?> list(@RequestParam(name = "categoryId") Integer id,
                                  @RequestParam(name = "page", defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return productService.getAllByCategoryForUser(id, page, size);
    }

    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDto.class)))})
    @Operation(description = "get by id  for user")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return productService.getByIdForUser(id);
    }
//
//    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDto.class)))})
//    @GetMapping("top")
//    public ResponseEntity<?> top(@PathVariable Integer id) {
//        return productService.getByIdForUser(id);
//    }

    @Operation(description = " get all product")
    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDto.class)))})
    @ApiResponse(responseCode = "404", description = "Not found")
    @GetMapping("/all")
    public ResponseEntity<?> all(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return productService.getAllUser(page, size);
    }
}
