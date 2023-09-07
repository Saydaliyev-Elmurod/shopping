package com.example.shopping.admin.product;

import com.example.shopping.client.product.*;
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

@Tag(name = "Admin Product APIs ", description = "Admin product all API s")
@RequestMapping("admin/product")
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "online shop ")
public class AdminProductController {
    private final ProductService productService;

    @Operation(description = " add new product")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ProductRequestDto.class))})
    @PostMapping({"add"})
    public ResponseEntity<?> add(@RequestBody ProductRequestDto dto) {
        return productService.add(dto);
    }

    @Operation(description = " update product")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ProductUpdateDto.class))})
    @ApiResponse(responseCode = "404", description = "Not found")
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody ProductUpdateDto dto) {
        return productService.update(dto);
    }

    @Operation(description = " get all product by category id")
    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDto.class)))})
    @ApiResponse(responseCode = "404", description = "Not found")
    @GetMapping("")
    public ResponseEntity<?> list(@RequestParam(name = "categoryId") Integer cid,
                                  @RequestParam(name = "page", defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return productService.getAllByCategoryForAdmin(cid, page, size);
    }

    @Operation(description = " get all product")
    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDto.class)))})
    @ApiResponse(responseCode = "404", description = "Not found")
    @GetMapping("/all")
    public ResponseEntity<?> all(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return productService.getAllAdmin( page, size);
    }

    @Operation(description = " get  product by  id")
    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDto.class)))})
    @ApiResponse(responseCode = "404", description = "Not found")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return productService.getByIdForAdmin(id);
    }

    @Operation(description = " delete by id ")
    @ApiResponse(responseCode = "200")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return productService.delete(id);
    }

    @Operation(description = " update visible")
    @ApiResponse(responseCode = "200")
    @PutMapping("visible/{id}")
    public ResponseEntity<?> updateVisible(@PathVariable Integer id) {
        return productService.updateVisible(id);
    }
}
