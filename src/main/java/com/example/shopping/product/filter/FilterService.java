package com.example.shopping.product.filter;

import com.example.shopping.product.ProductDto;
import com.example.shopping.product.ProductEntity;
import com.example.shopping.util.TextModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilterService {
    private final FilterRepository filterRepository;

    public PageImpl<ProductDto> filter(FilterDto filterDto, Integer page, Integer size) {
        Page<ProductEntity> pageable = filterRepository.filter(filterDto, page, size);
        return new PageImpl<>(toDtoList(pageable.getContent()), PageRequest.of(page,size),pageable.getTotalElements());
    }

    private List<ProductDto> toDtoList(List<ProductEntity> content) {
      return   content.stream().map(p->{
         return    toDto(p);
        }).collect(Collectors.toList());
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
