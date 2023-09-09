package com.example.shopping.client.product;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    Page<ProductEntity> findByCategoryIdAndDeletedFalseAndIsVisibleTrue(Integer categoryId, Pageable pageable);

    Page<ProductEntity> findByDeletedFalseAndIsVisibleTrue(Pageable pageable);

    Page<ProductEntity> findByCategoryIdAndDeletedFalse(Integer categoryId, Pageable pageable);

    Page<ProductEntity> findByDeletedFalse(Pageable pageable);

    Optional<ProductEntity> findByDeletedFalseAndId(Integer id);

    Optional<ProductEntity> findById(Integer id);

    @Modifying
    @Transactional
    @Query(" update ProductEntity set deleted = ?2 where id = ?1")
    Integer updateStatus(Integer id, Boolean status);

    @Modifying
    @Transactional
    @Query("UPDATE ProductEntity " +
            "SET isVisible = CASE " +
            "  WHEN isVisible = false THEN true " +
            "  WHEN isVisible = true THEN false " +
            "END " +
            "WHERE id = ?1 ")
    Integer updateVisible(Integer id);

    @Query("from ProductEntity  where deleted = false  and (nameUz ilike CONCAT('%', ?1, '%')  or nameRu ilike CONCAT('%', ?1, '%')  or nameEng ilike CONCAT('%', ?1, '%'))")
    Page<ProductEntity> search(String search, Pageable pageable);

}
