package com.example.filterPoc.response;

import com.example.filterPoc.model.Transaction;
import com.example.filterPoc.model.User;
import com.example.filterPoc.model.Wallet;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class WalletResponse {
    private String walletId;
    private Double balance;
    private User user;
    private List<TransactionResponse> transactionHistory;

    public WalletResponse(Wallet wallet) {
        this.balance = wallet.getBalance();
        this.transactionHistory = wallet.getTransactionHistory().stream().map(TransactionResponse::new).toList();
        this.user = wallet.getUser();
        this.walletId = wallet.getWalletId();
    }
}
