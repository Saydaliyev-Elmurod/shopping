package com.example.shopping.client.product;

import com.example.shopping.client.category.CategoryEntity;
import com.example.shopping.client.profile.ProfileEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @ManyToOne(fetch = FetchType.EAGER)
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
    private Boolean isVisible = false;
    @Column
    private Boolean deleted = false;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image", nullable = false)
    private List<String> images;
    @Column
    private LocalDateTime createdDate;

}
