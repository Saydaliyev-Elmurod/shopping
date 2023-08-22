package com.example.shopping.product;

import com.example.shopping.category.CategoryRepository;
import com.example.shopping.exp.AppBadRequestException;
import com.example.shopping.exp.ItemNotFoundException;
import com.example.shopping.util.SpringSecurityUtil;
import com.example.shopping.util.TextModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
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
    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> add(ProductDto dto) {
        if (dto == null) {
            throw new AppBadRequestException(" dto is null ");
        }
        if (categoryRepository.findById(dto.getCategoryId()).isEmpty()) {
            throw new ItemNotFoundException("category not found");
        }
        ProductEntity entity = toEntity(dto);
        entity.setIsVisible(false);
        entity = productRepository.save(entity);
        return ResponseEntity.ok(toDto(entity));
    }

    public ResponseEntity<?> getByIdForUser(Integer id) {
        if (id == null) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        Optional<ProductEntity> optional = productRepository.findByDeletedFalseAndId(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(toDto(optional.get()));
        }
        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getByIdForAdmin(Integer id) {
        if (id == null) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        Optional<ProductEntity> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(toDto(optional.get()));
        }
        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<?> getAllByCategoryForAdmin(Integer cid, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ProductEntity> pagination = productRepository.findByCategoryIdAndDeletedFalse(cid, pageable);
        if (pagination.isEmpty()) {
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        List<ProductDto> dtoList = new ArrayList<>();
        for (ProductEntity product : pagination.getContent()) {
            dtoList.add(toDto(product));
        }
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<?> getAllByCategoryForUser(Integer cid, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ProductEntity> pagination = productRepository.findByCategoryIdAndDeletedFalseAndIsVisibleTrue(cid, pageable);
        if (pagination.isEmpty()) {
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        List<ProductDto> dtoList = new ArrayList<>();
        for (ProductEntity product : pagination.getContent()) {
            dtoList.add(toDto(product));
        }
        return ResponseEntity.ok(new PageImpl<>(dtoList,pageable,pagination.getTotalElements()));
    }

    public ResponseEntity<?> update(ProductDto dto) {
        if (dto.getId() == null) {
            ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        ProductEntity entity = toEntity(dto);
        entity.setId(dto.getId());
        entity = productRepository.save(entity);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Integer id) {
        int r = productRepository.updateStatus(id, true);
        return ResponseEntity.ok(r == 1 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updateVisible(Integer id) {
        int r = productRepository.updateVisible(id);
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
        entity.setImages(dto.getImages());
        return entity;
    }

    private ProductDto toDto(ProductEntity entity) {
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setCategoryId(entity.getCategoryId());
        dto.setName(new TextModel(entity.getNameUz(), entity.getNameRu(), entity.getNameEng()));
        dto.setCost(entity.getCost());
        dto.setImages(entity.getImages());
        dto.setIsVisible(entity.getIsVisible());
        dto.setDescription(new TextModel(entity.getDescriptionUz(), entity.getDescriptionRu(), entity.getDescriptionEng()));
        return dto;
    }


}
