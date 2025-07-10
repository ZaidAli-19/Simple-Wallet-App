package com.example.filterPoc.request;

import com.example.filterPoc.util.TransactionType;
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
