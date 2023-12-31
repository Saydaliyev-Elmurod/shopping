package com.example.shopping.client.product;

import com.example.shopping.client.category.CategoryDto;
import com.example.shopping.client.category.CategoryEntity;
import com.example.shopping.client.category.CategoryRepository;
import com.example.shopping.client.category.CategoryResponseDto;
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

    public ResponseEntity<?> add(ProductRequestDto dto) {
        if (dto == null) {
            throw new AppBadRequestException(" dto is null ");
        }
        if (categoryRepository.findById(dto.getCategoryId()).isEmpty()) {
            throw new ItemNotFoundException("category not found");
        }
        ProductEntity entity = toEntity(dto);
        entity.setUserId(SpringSecurityUtil.getProfileId());
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
        List<ProductResponseDto> dtoList = new ArrayList<>();
        for (ProductEntity product : pagination.getContent()) {
            dtoList.add(toDto(product));
        }
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<?> getAllByCategoryForUser(Integer cid, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ProductEntity> pagination = productRepository.findByCategoryIdAndDeletedFalseAndIsVisibleTrue(cid, pageable);
        return getResponse(pagination, pageable);
    }

    public ResponseEntity<?> update(ProductUpdateDto dto) {
        if (dto == null || dto.getId() == null || dto.getId() < 1) {
            ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        ProductEntity entity = toUpdate(dto);
        entity.setId(dto.getId());
        entity = productRepository.save(entity);
        return ResponseEntity.ok(toResponse(entity));
    }

    public ResponseEntity<?> delete(Integer id) {
        int r = productRepository.updateStatus(id, true);
        return ResponseEntity.ok(r == 1 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updateVisible(Integer id) {
        int r = productRepository.updateVisible(id);
        return ResponseEntity.ok(r == 1 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    private ProductEntity toEntity(ProductRequestDto dto) {
        ProductEntity entity = new ProductEntity();
        entity.setNameEng(dto.getName().getEng());
        entity.setNameRu(dto.getName().getRu());
        entity.setNameUz(dto.getName().getUz());
        entity.setCategoryId(dto.getCategoryId());
        entity.setDescriptionEng(dto.getDescription().getEng());
        entity.setDescriptionRu(dto.getDescription().getRu());
        entity.setDescriptionUz(dto.getDescription().getUz());
        entity.setCost(dto.getCost());
        entity.setImages(dto.getImages());
        return entity;
    }

    private ProductEntity toUpdate(ProductUpdateDto dto) {
        ProductEntity entity = new ProductEntity();
        if(dto.getName()!=null){
            entity.setNameEng(dto.getName().getEng());
            entity.setNameRu(dto.getName().getRu());
            entity.setNameUz(dto.getName().getUz());
        }
       if(dto.getDescription()!=null){
           entity.setDescriptionEng(dto.getDescription().getEng());
           entity.setDescriptionRu(dto.getDescription().getRu());
           entity.setDescriptionUz(dto.getDescription().getUz());
       }
        entity.setCost(dto.getCost());
        entity.setImages(dto.getImages());
        return entity;
    }

    private ProductResponseDto toDto(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(entity.getId());
        dto.setName(new TextModel(entity.getNameUz(), entity.getNameRu(), entity.getNameEng()));
        dto.setCost(entity.getCost());
        dto.setImages(entity.getImages());
        if(entity.getCategory()!=null){
            dto.setCategory(new CategoryDto(entity.getCategory().getId(),entity.getCategory().getImage(),new TextModel(entity.getNameUz(), entity.getNameRu(), entity.getNameEng()),entity.getCategory().getIsVisible()));
        }
        dto.setIsVisible(entity.getIsVisible());
        dto.setDescription(new TextModel(entity.getDescriptionUz(), entity.getDescriptionRu(), entity.getDescriptionEng()));
        return dto;
    }

    public ResponseEntity<?> getAllAdmin(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ProductEntity> pagination = productRepository.findByDeletedFalse(pageable);
        return getResponse(pagination, pageable);
    }

    public ResponseEntity<?> getAllUser(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ProductEntity> pagination = productRepository.findByDeletedFalseAndIsVisibleTrue(pageable);
        return getResponse(pagination, pageable);
    }

    private ResponseEntity<?> getResponse(Page<ProductEntity> pagination, Pageable pageable) {
        if (pagination.isEmpty()) {
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        List<ProductResponseDto> dtoList = new ArrayList<>();
        for (ProductEntity product : pagination.getContent()) {
            dtoList.add(toResponse(product));
        }
        return ResponseEntity.ok(new PageImpl<>(dtoList, pageable, pagination.getTotalElements()));
    }

    private ProductResponseDto toResponse(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(entity.getId());
        dto.setName(new TextModel(entity.getNameUz(), entity.getNameRu(), entity.getNameEng()));
        dto.setCost(entity.getCost());
        if(entity.getCategory()!=null){
            dto.setCategory(new CategoryDto(entity.getCategory().getId(),entity.getCategory().getImage(),new TextModel(entity.getNameUz(), entity.getNameRu(), entity.getNameEng()),entity.getCategory().getIsVisible()));
        }
        dto.setImages(entity.getImages());
        dto.setIsVisible(entity.getIsVisible());
        dto.setDescription(new TextModel(entity.getDescriptionUz(), entity.getDescriptionRu(), entity.getDescriptionEng()));
        return dto;
    }

    public ResponseEntity<?> search(String search, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ProductEntity> pagination = productRepository.search(search, pageable);
        return getResponse(pagination, pageable);
    }
}
