package com.example.shopping.client.category;

import com.example.shopping.util.SpringSecurityUtil;
import com.example.shopping.util.TextModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryDto add(CategoryRequestDto dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setImage(dto.getImage());
        if (null != dto.getName()) {
            entity.setNameUz(dto.getName().getUz());
            entity.setNameRu(dto.getName().getRu());
            entity.setNameEng(dto.getName().getEng());
        }
        entity.setUserId(SpringSecurityUtil.getProfileId());
        entity.setIsVisible(false);
        return toDto(categoryRepository.save(entity));
    }

    private CategoryEntity toEntity(CategoryDto dto) {
        CategoryEntity entity = new CategoryEntity();
        if (dto.getName() != null) {
            entity.setNameEng(dto.getName().getEng());
            entity.setNameRu(dto.getName().getRu());
            entity.setNameUz(dto.getName().getUz());
        }
        entity.setImage(dto.getImage());
        return entity;
    }

    public ResponseEntity<?> getByIdForAdmin(Integer id) {
        Optional<CategoryEntity> optional = categoryRepository.findByIdAndDeletedFalse(id);
        if (optional.isEmpty()) {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(toDto(optional.get()));
    }

    public ResponseEntity<?> getByIdForUser(Integer id) {
        Optional<CategoryEntity> optional = categoryRepository.findByIdAndDeletedFalseAndIsVisibleTrue(id);
        if (optional.isEmpty()) {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(toDto(optional.get()));
    }

    private CategoryDto toDto(CategoryEntity entity) {
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        TextModel name = new TextModel();
        name.setEng(entity.getNameEng());
        name.setRu(entity.getNameRu());
        name.setUz(entity.getNameUz());
        dto.setImage(entity.getImage());
        dto.setName(name);
        dto.setIsVisible(entity.getIsVisible());
        return dto;
    }

    public ResponseEntity<?> getAllAdmin(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<CategoryEntity> pageObj = categoryRepository.findAllByDeletedFalse(paging);
        long totalCount = pageObj.getTotalElements();

        List<CategoryEntity> entityList = pageObj.getContent();
        if (entityList.isEmpty()) {
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        List<CategoryDto> dtoList = new LinkedList<>();
        for (CategoryEntity entity : entityList) {
            dtoList.add(toDto(entity));
        }
        return ResponseEntity.ok(new PageImpl<>(dtoList, paging, totalCount));
    }

    public ResponseEntity<?> getAllForUser(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<CategoryEntity> pageObj = categoryRepository.findByDeletedFalseAndIsVisibleTrue(paging);
        long totalCount = pageObj.getTotalElements();
        List<CategoryEntity> entityList = pageObj.getContent();
        if (entityList.isEmpty()) {
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        List<CategoryDto> dtoList = new LinkedList<>();
        for (CategoryEntity entity : entityList) {
            dtoList.add(toDto(entity));
        }
        return ResponseEntity.ok(new PageImpl<>(dtoList, paging, totalCount));
    }

    public ResponseEntity<?> delete(Integer id) {
        categoryRepository.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<?> updateVisible(Long id) {
        return ResponseEntity.ok(categoryRepository.updateVisible(id) == 1 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> search(String name, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable paging = PageRequest.of(page - 1, size, sort);
        return ResponseEntity.ok(categoryRepository.search(name, paging));
    }

    public ResponseEntity<?> update(CategoryDto dto) {
        CategoryEntity entity = toEntity(dto);
        return ResponseEntity.ok(categoryRepository.save(entity));
    }
}
