package com.example.shopping.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    Page<ProductEntity> findAllByUserIdAndCategoryIdAndDeletedFalse(Integer userId, Integer categoryId, Pageable pageable);

    Optional<ProductEntity> findByDeletedFalseAndId(Integer id);

    @Query(" update ProductEntity set deleted = ?2 where id = ?1")
    Integer updateStatus(Integer id, Boolean status);

}
