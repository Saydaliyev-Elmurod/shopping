package com.example.shopping.product;

import com.example.shopping.category.CategoryEntity;
import com.example.shopping.profile.ProfileEntity;
import com.example.shopping.util.TextModel;
import jakarta.persistence.*;
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
    private ProfileEntity profile;
    private CategoryEntity category;
    private Integer categoryId;
    private Double cost;
    private Boolean isDisable;
    private TextModel description;
}
