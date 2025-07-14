package com.example.walletApp.controller;

import com.example.walletApp.request.TransactionRequest;
import com.example.walletApp.response.TransactionResponse;
import com.example.walletApp.service.TransactionService;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/delete/{id}")
    public void deleteTransaction(@PathVariable String id){
        transactionService.deleteTransaction(id);
    }


    @GetMapping("/getInfoById/{id}")
    public TransactionResponse getInfoById(@PathVariable String id){
        return transactionService.getInfoById(id);
    }
    }

