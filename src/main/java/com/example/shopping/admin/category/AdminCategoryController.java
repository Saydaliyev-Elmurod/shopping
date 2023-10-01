package com.example.shopping.admin.category;


import com.example.shopping.client.category.CategoryDto;
import com.example.shopping.client.category.CategoryRequestDto;
import com.example.shopping.client.category.CategoryService;
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
@RequestMapping("/admin/category")
@SecurityRequirement(name = "online shop ")
public class AdminCategoryController {
    private final CategoryService categoryService;

    @Operation(description = " this api use add new category")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryRequestDto.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CategoryRequestDto dto) {
        return ResponseEntity.ok(categoryService.add(dto));
    }

    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryDto.class))})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "Not found")
    @Operation(description = "get by id for admin get isVisible true or false ")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdmin(@PathVariable("id") Integer id) {
        return categoryService.getByIdForAdmin(id);
    }

    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "No content")
    @Operation(description = "get all category by admin all by isVisible true or false")
    @GetMapping("all")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return categoryService.getAllAdmin(page, size);
    }

    @ApiResponse(responseCode = "200", content = {@Content()})
    @DeleteMapping("{id}")
    @Operation(description = "delete this category only admin can this")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return categoryService.delete(id);
    }

    @ApiResponse(responseCode = "200", content = {@Content()})
    @GetMapping("visible/{id}")
    @Operation(description = " update visible if visible true set false on the contrary")
    public ResponseEntity<?> updateVisible(@PathVariable Long id) {
        return categoryService.updateVisible(id);
    }

    @ApiResponse(responseCode = "200", content = {@Content()})
    @GetMapping("search")
    @Operation(description = " search category by name")
    public ResponseEntity<?> search(@RequestParam(value = "search") String search,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return categoryService.search(search, page, size);
    }

    @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CategoryRequestDto.class)))})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "No content")
    @PutMapping("/update")
    @Operation(description = " update category")
    public ResponseEntity<?> update(@RequestBody CategoryDto dto) {
        return categoryService.update(dto);
    }
}