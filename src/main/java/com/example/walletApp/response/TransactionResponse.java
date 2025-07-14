package com.example.walletApp.response;

import com.example.walletApp.util.TransactionType;
import lombok.*;

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
    private String holderName;
}
