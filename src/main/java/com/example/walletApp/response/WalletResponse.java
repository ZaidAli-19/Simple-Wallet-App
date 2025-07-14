package com.example.walletApp.response;

import com.example.walletApp.model.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WalletResponse {
    private String walletId;
    private Double balance;
    private User user;
    private List<TransactionResponse> transactionHistory;
}
