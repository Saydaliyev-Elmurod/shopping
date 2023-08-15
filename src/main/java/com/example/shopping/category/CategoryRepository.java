package com.example.shopping.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Page<CategoryEntity> findAllByDeletedFalse( Pageable page);

    Page<CategoryEntity> findByDeletedFalseAndIsVisibleTrue(Integer userId, Pageable pageable);

    Optional<CategoryEntity> findByIdAndDeletedFalse(Long id);

    Optional<CategoryEntity> findByIdAndDeletedFalseAndIsVisibleTrue(Long id);

    @Query("UPDATE CategoryEntity " +
            "SET isVisible = CASE " +
            "  WHEN isVisible = false THEN true " +
            "  WHEN isVisible = true THEN false " +
            "END " +
            "WHERE id = ?1 ")
    void updateVisible(Long id);

}
