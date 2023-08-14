package com.example.shopping.product.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FilterDto {
    private String name;
    private String categoryId;
    private Double startCost;
    private Double endCost;

    private LocalDate startDate;
    private LocalDate endDate;

}
