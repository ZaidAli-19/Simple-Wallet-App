package com.example.filterPoc.controller;

import com.example.filterPoc.exceptionHandling.CannotDeleteWalletException;
import com.example.filterPoc.exceptionHandling.UserNotFoundException;
import com.example.filterPoc.request.PaginationRequest;
import com.example.filterPoc.request.WalletRequest;
import com.example.filterPoc.response.TransactionResponse;
import com.example.filterPoc.response.WalletResponse;
import com.example.filterPoc.response.WalletsResponse;
import com.example.filterPoc.service.WalletService;
import com.example.filterPoc.serviceImpl.WalletServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/create")
    public String createWallet(@RequestBody WalletRequest walletRequest) {
        return walletService.createWallet(walletRequest);
    }

    @PostMapping("/getRecentTransactions/{walletId}")
    public List<TransactionResponse> getRecentTransactionsByUserId(@PathVariable String walletId, @RequestBody PaginationRequest request){
        return walletService.getRecentTransactionsByUserId(walletId,request);
    }


    //this returns a wallet with transaction history
    @GetMapping("/getInfoById/{id}")
    public WalletResponse getInfo(@PathVariable String id) {
        return walletService.getInfo(id);
    }


    //this returns all the wallets of a user by userId
    @GetMapping("/getUserWallets/{uuid}")
    public ResponseEntity<List<WalletsResponse>> wallets(@PathVariable String uuid) {
            return new ResponseEntity<>(walletService.getAllInfo(uuid), HttpStatus.OK);
    }

    @GetMapping("/showBalance/{walletId}")
    public String showBalance(@PathVariable String walletId) {
        return walletService.showBalance(walletId);
    }

    @PostMapping("/downloadPdf/{walletId}")
    public void downloadTransactions(@PathVariable String walletId, HttpServletResponse response) throws IOException {
        walletService.downloadTransactions(walletId, response);
    }
    @DeleteMapping("/delete/{walletId}")
    public ResponseEntity<String>delete(@PathVariable String walletId){
   return new ResponseEntity<>(walletService.deleteWallet(walletId),HttpStatus.OK);
    }
}