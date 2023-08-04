package com.example.shopping.product;

import com.example.shopping.category.CategoryEntity;
import com.example.shopping.profile.ProfileEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private Boolean deleted = false;
}
