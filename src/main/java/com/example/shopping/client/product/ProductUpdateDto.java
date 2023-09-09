package com.example.shopping.client.product;

import com.example.shopping.util.TextModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductUpdateDto {
    private Integer id;
    private TextModel name;
    private List<String> images;
    private Double cost;
    private TextModel description;
}
