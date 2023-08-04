package com.example.shopping.product;

import com.example.shopping.util.SpringSecurityUtil;
import com.example.shopping.util.TextModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ResponseEntity<?> add(ProductDto dto) {
        if (dto == null) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        productRepository.save(toEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<?> get(Integer id) {
        if (id == null) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        Optional<ProductEntity> optional = productRepository.findByDeletedFalseAndId(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(toDto(optional.get()));
        }
        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<?> getAll(Integer id, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Integer userId = SpringSecurityUtil.getProfileId();
        Page<ProductEntity> pagination = productRepository.findAllByUserIdAndCategoryIdAndDeletedFalse(userId, id, pageable);
        if (pagination.isEmpty()) {
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        List<ProductDto> dtoList = new ArrayList<>();
        for (ProductEntity product : pagination.getContent()) {
            dtoList.add(toDto(product));
        }
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<?> update(ProductDto dto) {
        ProductEntity entity = toEntity(dto);
        entity.setId(dto.getId());
        productRepository.save(entity);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Integer id) {
        int r = productRepository.updateStatus(id, true);
        return ResponseEntity.ok(r == 1);
    }

    public ResponseEntity<?> updateStatus(Integer id, Boolean status) {
        int r = productRepository.updateStatus(id, status);
        return ResponseEntity.ok(r == 1);
    }

    private ProductEntity toEntity(ProductDto dto) {
        ProductEntity entity = new ProductEntity();
        entity.setNameEng(dto.getName().getEng());
        entity.setNameRu(dto.getName().getRu());
        entity.setNameUz(dto.getName().getUz());
        entity.setUserId(SpringSecurityUtil.getProfileId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setDescriptionEng(dto.getDescription().getEng());
        entity.setDescriptionRu(dto.getDescription().getRu());
        entity.setDescriptionUz(dto.getDescription().getUz());
        entity.setCost(dto.getCost());
        return entity;
    }

    private ProductDto toDto(ProductEntity entity) {
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setCategoryId(entity.getCategoryId());
        dto.setName(new TextModel(entity.getNameUz(), entity.getNameRu(), entity.getNameEng()));
        dto.setCost(entity.getCost());
        dto.setDescription(new TextModel(entity.getDescriptionUz(), entity.getDescriptionRu(), entity.getDescriptionEng()));
        return dto;
    }


}
