package com.example.shopping.product.filter;

import com.example.shopping.product.ProductDto;
import com.example.shopping.product.ProductEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class FilterRepository {
    private EntityManager entityManager;

    public PageImpl<ProductEntity> filter(FilterDto dto, Integer page, Integer size) {
        Map<Integer, Object> map = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        builder.append("from ProductEntity  where");
        int x = 0;
        if (dto.getCategoryId() != null) {
            builder.append(" and categoryId = ?");
            map.put(++x, dto.getCategoryId());
        }
        if (dto.getName() != null) {
            builder.append(" and nameEng ilike ? and nameRu ilike ? and " +
                    " nameUz ilike ? and descriptionEng ilike ? and descriptionRu ilike ? and descriptionUz ilike ? and isDisable =true and deleted = false");
            map.put(++x, dto.getName());
            map.put(++x, dto.getName());
            map.put(++x, dto.getName());
            map.put(++x, dto.getName());
            map.put(++x, dto.getName());
            map.put(++x, dto.getName());
        }

        if (dto.getStartCost() != null) {
            builder.append(" and cost > ?");
            map.put(++x, dto.getStartCost());
        }

        if (dto.getEndDate() != null) {
            builder.append(" and cost < ?");
            map.put(++x, dto.getEndCost());
        }

        if (dto.getStartDate() != null) {
            builder.append(" and createdDate > ? ");
            map.put(++x, dto.getStartDate());
            ;
        }

        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            builder.append(" and s.createdDate between ? and dateTo ");
            map.put(++x, LocalDateTime.of(dto.getStartDate(), LocalTime.MIN));
            map.put(++x, LocalDateTime.of(dto.getEndDate(), LocalTime.MIN));
        } else if (dto.getStartDate() != null) {
            builder.append(" and createdDate >= ? ");
            map.put(++x, LocalDateTime.of(dto.getStartDate(), LocalTime.MIN));
        } else if (dto.getEndDate() != null) {
            builder.append(" and createdDate <= ? ");
            map.put(++x, LocalDateTime.of(dto.getEndDate(), LocalTime.MIN));
        }

        StringBuilder selectBuilder = new StringBuilder("from ProductEntity  where  isDisable = true ");
        selectBuilder.append(builder);

        StringBuilder countBuilder = new StringBuilder("select count(s) from ProductEntity as s where isDisable = true ");
        countBuilder.append(builder);

        Query selectQuery = this.entityManager.createQuery(selectBuilder.toString());
        Query countQuery = this.entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<Integer, Object> param : map.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        selectQuery.setFirstResult((page - 1) * size); // offset
        selectQuery.setMaxResults(size);
        List<ProductEntity> entityList = selectQuery.getResultList();
        long totalCount = (long) countQuery.getSingleResult();


        return new PageImpl<>(entityList, PageRequest.of(page, size), totalCount);

    }
}
