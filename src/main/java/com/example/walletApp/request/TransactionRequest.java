package com.example.walletApp.request;

import com.example.walletApp.util.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TransactionRequest {
    private String walletId;
    private Double amount;
    private TransactionType transactionType;
    private String description;
    private LocalDateTime dateTime;
}
