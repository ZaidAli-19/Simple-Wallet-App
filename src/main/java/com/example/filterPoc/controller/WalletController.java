package com.example.filterPoc.controller;

import com.example.filterPoc.model.Wallet;
import com.example.filterPoc.request.WalletRequest;
import com.example.filterPoc.response.WalletResponse;
import com.example.filterPoc.response.WalletsResponse;
import com.example.filterPoc.service.WalletService;
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


    //this returns a wallet with transaction history
    @GetMapping("/getInfoById/{id}")
    public WalletResponse getInfo(@PathVariable String id) {
        return walletService.getInfo(id);
    }


    //this returns all the wallets of a user by userId
    @GetMapping("/getUserWallets/{uuid}")
    public List<WalletsResponse> wallets(@PathVariable String uuid) {
        return walletService.getAllInfo(uuid);
    }

    @GetMapping("/showBalance/{walletId}")
    public String showBalance(@PathVariable String walletId) {
        return walletService.showBalance(walletId);
    }

    @GetMapping("/downloadPdf/{walletId}")
    public void downloadTransactions(@PathVariable String walletId, HttpServletResponse response) throws IOException {
        walletService.downloadTransactions(walletId, response);
    }
}