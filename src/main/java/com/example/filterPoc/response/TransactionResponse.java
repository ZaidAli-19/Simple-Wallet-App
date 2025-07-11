package com.example.filterPoc.response;

import com.example.filterPoc.model.Transaction;
import com.example.filterPoc.model.User;
import com.example.filterPoc.model.Wallet;
import com.example.filterPoc.util.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TransactionResponse {
    private String transactionId;
    private TransactionType transactionType;
    private Double amount;
    private String description;
    private LocalDateTime dateTime;
    private String walletId;
}
