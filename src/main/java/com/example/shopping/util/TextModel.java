package com.example.shopping.util;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextModel {
    private String nameUz;
    private String nameRu;
    private String nameEng;
}
