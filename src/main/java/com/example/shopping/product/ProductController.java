package com.example.shopping.product;

import com.example.shopping.category.CategoryDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Admin Product APIs ", description = "Admin product all API s")
@SecurityRequirement(name = "online shop ")
@RequestMapping("private/product")
@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "Not found")
    @PostMapping({"/", ""})
    public ResponseEntity<?> add(@RequestBody ProductDto dto) {
        return productService.add(dto);
    }

    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ProductDto.class))})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "Not found")
    @ApiResponse(responseCode = "400")
    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return productService.getByIdForUser(id);
    }

    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "Not found")
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody ProductDto dto) {
        return productService.update(dto);
    }

    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))})
    @ApiResponse(responseCode = "404", description = "Not found")
    @GetMapping("all/{id}")
    public ResponseEntity<?> list(@PathVariable Integer id,
                                  @RequestParam(name = "page", defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return productService.getAllByCategory(id, page, size);
    }

    @ApiResponse(responseCode = "200")
    @DeleteMapping("id")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return productService.delete(id);
    }

    @ApiResponse(responseCode = "200")
    @PutMapping("{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestParam(name = "status") Boolean status) {
        return productService.updateStatus(id, status);
    }
}
