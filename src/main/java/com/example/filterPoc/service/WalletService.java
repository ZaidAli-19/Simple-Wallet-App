package com.example.filterPoc.service;

import com.example.filterPoc.request.PaginationRequest;
import com.example.filterPoc.request.WalletRequest;
import com.example.filterPoc.response.TransactionResponse;
import com.example.filterPoc.response.WalletResponse;
import com.example.filterPoc.response.WalletsResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface WalletService {
    String createWallet(WalletRequest walletRequest);

    List<TransactionResponse> getRecentTransactionsByUserId(String walletId, PaginationRequest request);

    WalletResponse getInfo(String id);

    List<WalletsResponse> getAllInfo(String uuid);

    String showBalance(String walletId);

    void downloadTransactions(String walletId, HttpServletResponse response) throws IOException;

    String deleteWallet(String walletId);
}
