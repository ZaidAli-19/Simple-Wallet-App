package com.example.filterPoc.controller;

import com.example.filterPoc.request.TransactionRequest;
import com.example.filterPoc.response.TransactionResponse;
import com.example.filterPoc.service.TransactionService;
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
    public void makeTransaction(@RequestBody TransactionRequest request){
        transactionService.createTransaction(request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTransaction(@PathVariable String id){
        transactionService.deleteTransaction(id);
    }

    @GetMapping("/getInfoById/{id}")
    public ResponseEntity<TransactionResponse> getInfoById(@PathVariable String id){
        return new ResponseEntity<>(transactionService.getInfoById(id),HttpStatus.OK);
    }
    }

