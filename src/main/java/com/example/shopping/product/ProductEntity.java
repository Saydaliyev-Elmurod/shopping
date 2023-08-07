package com.example.shopping.product;

import com.example.shopping.category.CategoryEntity;
import com.example.shopping.profile.ProfileEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String nameUz;
    @Column
    private String nameRu;
    @Column
    private String nameEng;
    @Column(name = "user_id")
    private Integer userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private ProfileEntity profile;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column
    private Double cost;
    @Column
    private String descriptionUz;
    @Column
    private String descriptionRu;
    @Column
    private String descriptionEng;
    @Column
    private Boolean isDisable = false;
    @Column
    private Boolean deleted = false;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image", nullable = false)
    private List<String> images;
}
