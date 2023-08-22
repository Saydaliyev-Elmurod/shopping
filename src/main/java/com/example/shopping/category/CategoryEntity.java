package com.example.shopping.category;

import com.example.shopping.profile.ProfileEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String nameUz;
    @Column
    private String nameRu;
    @Column
    private String nameEng;
    @Column
    private String image;
    @Column
    private Boolean deleted = false;
    @Column(name = "user_id")
    private Integer userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private ProfileEntity profile;

    @Column
    private Boolean isVisible;
}
