package com.example.walletApp.controller;

import com.example.walletApp.request.PaginationRequest;
import com.example.walletApp.request.WalletRequest;
import com.example.walletApp.response.TransactionResponse;
import com.example.walletApp.response.WalletResponse;
import com.example.walletApp.response.WalletsResponse;
import com.example.walletApp.service.WalletService;
import jakarta.servlet.http.HttpServletResponse;
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

    //this api will return recent transactions of a wallet, Pagination is implemented in this api.
    @PostMapping("/getRecentTransactions/{walletId}")
    public List<TransactionResponse> getRecentTransactions(@PathVariable String walletId, @RequestBody PaginationRequest request){
        return walletService.getRecentTransactionsByWalletId(walletId,request);
    }

    //this returns a wallet's transaction history
    @GetMapping("/getInfoById/{id}")
    public WalletResponse getInfo(@PathVariable String id) {
        return walletService.getInfoByWalletId(id);
    }


    //this will return all the wallets of a user by userId
    @GetMapping("/getUserWallets/{uuid}")
    public List<WalletsResponse> wallets(@PathVariable String uuid) {
        return walletService.getAllWalletsByUuid(uuid);
    }


    //this api will return the current balance of a wallet by walletId
    @GetMapping("/showBalance/{walletId}")
    public String showBalance(@PathVariable String walletId) {
        return walletService.showBalance(walletId);
    }


    //this api will download all the transactions of a wallet.
    @PostMapping("/downloadPdf/{walletId}")
    public void downloadTransactions(@PathVariable String walletId, HttpServletResponse response) throws IOException {
        walletService.downloadTransactions(walletId, response);
    }


    @DeleteMapping("/delete/{walletId}")
    public String delete(@PathVariable String walletId){
   return walletService.deleteWallet(walletId);
    }
}