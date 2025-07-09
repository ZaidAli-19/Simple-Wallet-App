package com.example.filterPoc.model;

import com.example.filterPoc.util.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Transaction {
    @Id
    private String transactionId;
    private TransactionType transactionType;
    private Double amount;
    private String description;
    private LocalDateTime dateTime;
    @DBRef
    private User user;
    @JsonIgnore
    @DBRef
    private Wallet wallet;
}
