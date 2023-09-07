package com.example.shopping.client.category;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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

    @Query("from CategoryEntity where nameEng ilike ?1 or nameRu ilike ?1 or nameUz ilike ?1")
    Page<CategoryEntity> search(String name, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update CategoryEntity set deleted=true  where id=?1")
    CategoryEntity delete(Integer id);

}
