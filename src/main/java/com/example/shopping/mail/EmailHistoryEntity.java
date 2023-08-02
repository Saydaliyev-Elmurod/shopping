package com.example.shopping.mail;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "email_history")
@Entity
public class EmailHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 1000)
    private String message;
    @Column
    private String email;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
