package com.example.shopping.category;

import com.example.shopping.util.TextModel;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Integer id;
    private TextModel name;
    private String image;
    private Boolean isVisible;
}
