package com.example.shopping.client.product;

import com.example.shopping.client.category.CategoryDto;
import com.example.shopping.client.category.CategoryEntity;
import com.example.shopping.client.category.CategoryResponseDto;
import com.example.shopping.client.profile.ProfileEntity;
import com.example.shopping.util.TextModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDto {
    private Integer id;
    private TextModel name;
    private Integer userId;

    private List<String> images;
    private CategoryDto category;
    private Integer categoryId;
    private Double cost;
    private Boolean isVisible;
    private TextModel description;
}
