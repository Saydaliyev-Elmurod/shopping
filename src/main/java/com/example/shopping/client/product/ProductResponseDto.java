package com.example.shopping.client.product;

import com.example.shopping.client.category.CategoryDto;
import com.example.shopping.client.category.CategoryEntity;
import com.example.shopping.client.profile.ProfileEntity;
import com.example.shopping.util.TextModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResponseDto {
    private Integer id;
    private TextModel name;
    private List<String> images;
    private CategoryDto category;
    private Double cost;
    private Boolean isVisible;
    private TextModel description;
}