package com.example.shopping.client.category;

import com.example.shopping.util.TextModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDto {
    private TextModel name;
    private String image;
    private Boolean isVisible;


}
