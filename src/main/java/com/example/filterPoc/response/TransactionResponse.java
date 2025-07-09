package com.example.filterPoc.response;

import com.example.filterPoc.model.Transaction;
import com.example.filterPoc.model.User;
import com.example.filterPoc.model.Wallet;
import com.example.filterPoc.util.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private String transactionId;
    private TransactionType transactionType;
    private Double amount;
    private String description;
    private LocalDateTime dateTime;

    public TransactionResponse(Transaction transaction) {
        this.amount = transaction.getAmount();
        this.dateTime = transaction.getDateTime();
        this.description = transaction.getDescription();
        this.transactionId = transaction.getTransactionId();
        this.transactionType = transaction.getTransactionType();
    }
}
