package com.example.walletApp.controller;

import com.example.walletApp.request.TransactionRequest;
import com.example.walletApp.response.TransactionResponse;
import com.example.walletApp.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> makeTransaction(@RequestBody TransactionRequest request){
       return new ResponseEntity<>(transactionService.createTransaction(request),HttpStatus.CREATED);
    }

    @GetMapping("/getInfoById/{id}")
    public ResponseEntity<TransactionResponse> getInfoById(@PathVariable String id){
        return new ResponseEntity<>(transactionService.getInfoById(id),HttpStatus.OK);
    }
    }

