package com.example.filterPoc.service;

import com.example.filterPoc.model.Transaction;
import com.example.filterPoc.request.TransactionRequest;
import com.example.filterPoc.response.TransactionResponse;

public interface TransactionService {
    void createTransaction(TransactionRequest request);

    void deleteTransaction(String id);

    TransactionResponse getInfoById(String id);
}
