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


@Tag(name = "Admin Category APIs ", description = "Admin category all API s")
@RestController
@AllArgsConstructor
@RequestMapping("category")
@SecurityRequirement(name = "online shop ")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(description = " this api use add new category")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryDto.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    @PostMapping("admin/add")
    public ResponseEntity<?> add(@RequestBody CategoryDto dto) {
        return ResponseEntity.ok(categoryService.add(dto));
    }

    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryDto.class))})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "Not found")
    @Operation(description = "get by id for user get isVisible true or false ")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return categoryService.getByIdForUser(id);
    }

    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryDto.class))})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "Not found")
    @Operation(description = "get by id for admin get isVisible true or false ")
    @GetMapping("admin/{id}")
    public ResponseEntity<?> getAdmin(@PathVariable Long id) {
        return categoryService.getByIdForAdmin(id);
    }

    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "No content")
    @Operation(description = "get all category by admin all by isVisible true or false")
    @GetMapping("admin/all")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return categoryService.getAllAdmin(page, size);
    }

    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "No content")
    @Operation(description = "get all category by user (get only isVisible true))")
    @GetMapping("all")
    public ResponseEntity<?> getAllUser(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        return categoryService.getAllForUser(page, size);
    }

    @ApiResponse(responseCode = "200", content = {@Content()})
    @DeleteMapping("admin/{id}")
    @Operation(description = "delete this category only admin can this")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return categoryService.delete(id);
    }

    @ApiResponse(responseCode = "200", content = {@Content()})
    @DeleteMapping("admin/visible/{id}")
    @Operation(description = " update visible if visible true set false on the contrary")
    public ResponseEntity<?> updateVisible(@PathVariable Long id) {
        return categoryService.updateVisible(id);
    }
}
