package com.example.walletApp.service;

import com.example.walletApp.request.TransactionRequest;
import com.example.walletApp.response.TransactionResponse;

public interface TransactionService {
    String createTransaction(TransactionRequest request);

    TransactionResponse getInfoById(String id);
}
