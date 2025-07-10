package com.example.filterPoc.service;

import com.example.filterPoc.request.TransactionRequest;

public interface TransactionService {
    void createTransaction(TransactionRequest request);

    void deleteTransaction(String id);
}
