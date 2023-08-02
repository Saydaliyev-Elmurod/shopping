package com.example.shopping.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    public Page<CategoryEntity> findAllByDeletedFalseAndUserId(Integer userId, Pageable page);
}
