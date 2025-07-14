package com.example.walletApp.util;

import com.example.walletApp.model.Transaction;
import com.example.walletApp.model.User;
import com.example.walletApp.model.Wallet;
import com.example.walletApp.response.TransactionResponse;
import com.example.walletApp.response.UserResponse;
import com.example.walletApp.response.WalletResponse;
import com.example.walletApp.response.WalletsResponse;

import java.util.List;

public class ResponseMapper {
    public static UserResponse toUserResponse(User user){
        UserResponse userResponse=new UserResponse();
        userResponse.setUserType(user.getUserType());
        userResponse.setEmail(user.getEmail());
        userResponse.setUuid(user.getUuid());
        userResponse.setGender(user.getGender());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        return userResponse;
    }

    public static TransactionResponse toTransactionResponse (Transaction transaction){
        TransactionResponse transactionResponse=new TransactionResponse();
        transactionResponse.setTransactionId(transaction.getTransactionId());
        transactionResponse.setTransactionType(transaction.getTransactionType());
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setDescription(transaction.getDescription());
        transactionResponse.setDateTime(transaction.getDateTime());
        transactionResponse.setWalletId(transaction.getWallet().getWalletId());
        transactionResponse.setHolderName(transaction.getWallet().getUser().getFirstName()+" "+transaction.getWallet().getUser().getLastName());
        return transactionResponse;
    }

    public static WalletResponse toWalletResponse(Wallet wallet){
        WalletResponse walletResponse=new WalletResponse();
        walletResponse.setWalletId(wallet.getWalletId());
        walletResponse.setUser(wallet.getUser());
        walletResponse.setBalance(wallet.getBalance());
        List<TransactionResponse> transactionHistory = wallet.getTransactionHistory().stream().map(ResponseMapper::toTransactionResponse).toList();
        walletResponse.setTransactionHistory(transactionHistory);
        return walletResponse;
    }

    public static WalletsResponse toWalletsResponse(Wallet wallet){
        WalletsResponse walletsResponse=new WalletsResponse();
        walletsResponse.setWalletId(wallet.getWalletId());
        walletsResponse.setUser(wallet.getUser());
        walletsResponse.setBalance(wallet.getBalance());
        return walletsResponse;
    }
}
