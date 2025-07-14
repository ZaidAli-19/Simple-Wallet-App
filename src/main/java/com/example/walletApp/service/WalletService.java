package com.example.walletApp.service;

import com.example.walletApp.request.PaginationRequest;
import com.example.walletApp.request.WalletRequest;
import com.example.walletApp.response.TransactionResponse;
import com.example.walletApp.response.WalletResponse;
import com.example.walletApp.response.WalletsResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface WalletService {
    String createWallet(WalletRequest walletRequest);

    List<TransactionResponse> getRecentTransactionsByWalletId(String walletId, PaginationRequest request);

    WalletResponse getInfoByWalletId(String id);

    List<WalletsResponse> getAllWalletsByUuid(String uuid);

    String showBalance(String walletId);

    void downloadTransactions(String walletId, HttpServletResponse response) throws IOException;

    String deleteWallet(String walletId);
}
