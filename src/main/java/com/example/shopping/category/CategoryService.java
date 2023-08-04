package com.example.shopping.category;

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

    public void add(CategoryDto dto) {
        dto.setUserId(SpringSecurityUtil.getProfileId());
        categoryRepository.save(toEntity(dto));
    }

    private CategoryEntity toEntity(CategoryDto dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setNameEng(dto.getName().getEng());
        entity.setNameRu(dto.getName().getRu());
        entity.setNameUz(dto.getName().getUz());
        entity.setUserId(dto.getUserId());
        return entity;
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
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
        dto.setName(name);
        return dto;
    }

    public ResponseEntity<?> getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable paging = PageRequest.of(page - 1, size, sort);
        Integer userId = SpringSecurityUtil.getProfileId();
        Page<CategoryEntity> pageObj = categoryRepository.findAllByDeletedFalseAndUserId(userId, paging);
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
}
