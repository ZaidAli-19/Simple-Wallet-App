package com.example.filterPoc.controller;

import com.example.filterPoc.model.Transaction;
import com.example.filterPoc.request.PaginationRequest;
import com.example.filterPoc.request.TransactionRequest;
import com.example.filterPoc.response.TransactionResponse;
import com.example.filterPoc.service.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public void makeTransaction(@RequestBody TransactionRequest request){
        transactionService.createTransaction(request);
    }

    @PostMapping("/getRecentTransactions/{uuid}")
    public List<Transaction> getRecentTransactionsByUserId(@PathVariable String uuid, @RequestBody PaginationRequest request){
        return transactionService.getRecentTransactionsByUserId(uuid,request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTransaction(@PathVariable String id){
        transactionService.deleteTransaction(id);
    }

    }

