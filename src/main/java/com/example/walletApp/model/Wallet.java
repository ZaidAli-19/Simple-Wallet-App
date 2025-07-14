package com.example.walletApp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class Wallet {
    @Id
    private String walletId;
    private Double balance;
    @DBRef
    private User user;
    @DBRef
    private List<Transaction> transactionHistory = new ArrayList<>();
}
