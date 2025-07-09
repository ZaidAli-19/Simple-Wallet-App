package com.example.filterPoc.response;

import com.example.filterPoc.model.User;
import com.example.filterPoc.model.Wallet;
import lombok.Data;

import java.util.List;

@Data
public class WalletsResponse {
    private String walletId;
    private Double balance;
    private User user;

    public WalletsResponse(Wallet wallet) {
        this.balance = wallet.getBalance();
        this.user = wallet.getUser();
        this.walletId = wallet.getWalletId();
    }
}
