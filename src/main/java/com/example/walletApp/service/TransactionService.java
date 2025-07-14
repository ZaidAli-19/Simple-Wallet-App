package com.example.walletApp.service;

import com.example.walletApp.request.TransactionRequest;
import com.example.walletApp.response.TransactionResponse;

public interface TransactionService {
    void createTransaction(TransactionRequest request);

    void deleteTransaction(String id);

    TransactionResponse getInfoById(String id);
}
