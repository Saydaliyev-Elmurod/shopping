package com.example.shopping.mail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer> {
    Integer countByEmailAndCreatedDateAfter(String email, LocalDateTime emailLimitTime);
    Page<EmailHistoryEntity> getByEmail(String  email, Pageable pageable);
    Page<EmailHistoryEntity> getByCreatedDateBetween(LocalDateTime fDate,LocalDateTime toDate, Pageable pageable);
}
