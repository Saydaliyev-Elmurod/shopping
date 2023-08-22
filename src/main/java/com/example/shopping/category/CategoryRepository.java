package com.example.shopping.category;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Page<CategoryEntity> findAllByDeletedFalse(Pageable page);

    Page<CategoryEntity> findByDeletedFalseAndIsVisibleTrue(Pageable pageable);

    Optional<CategoryEntity> findByIdAndDeletedFalse(Integer id);

    Optional<CategoryEntity> findByIdAndDeletedFalseAndIsVisibleTrue(Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE CategoryEntity " +
            "SET isVisible = CASE " +
            "  WHEN isVisible = false THEN true " +
            "  WHEN isVisible = true THEN false " +
            "END " +
            "WHERE id = ?1 ")
    Integer updateVisible(Long id);

}
