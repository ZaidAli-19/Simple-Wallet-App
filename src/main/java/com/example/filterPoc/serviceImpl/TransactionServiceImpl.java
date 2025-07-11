package com.example.filterPoc.serviceImpl;

import com.example.filterPoc.exceptionHandling.InsufficientBalanceException;
import com.example.filterPoc.exceptionHandling.TransactionNotFoundException;
import com.example.filterPoc.exceptionHandling.WalletNotFoundException;
import com.example.filterPoc.model.Transaction;
import com.example.filterPoc.model.Wallet;
import com.example.filterPoc.repository.TransactionRepository;
import com.example.filterPoc.repository.WalletRepository;
import com.example.filterPoc.request.TransactionRequest;
import com.example.filterPoc.response.TransactionResponse;
import com.example.filterPoc.service.TransactionService;
import com.example.filterPoc.util.ResponseMapper;
import com.example.filterPoc.util.TransactionType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    public TransactionServiceImpl(TransactionRepository repository, WalletRepository walletRepository) {
        this.transactionRepository = repository;
        this.walletRepository = walletRepository;
    }

    public void createTransaction(TransactionRequest request) {
        Transaction userTransaction = new Transaction();
        if (request.getTransactionType() == TransactionType.CREDIT) {
            userTransaction.setTransactionType(TransactionType.CREDIT);
        }
        if (request.getTransactionType() == TransactionType.DEBIT) {
            userTransaction.setTransactionType(TransactionType.DEBIT);
        }
        userTransaction.setAmount(request.getAmount());
        userTransaction.setDescription(request.getDescription());
        userTransaction.setDateTime(LocalDateTime.now());


        Wallet userWallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletNotFoundException("Invalid wallet Id! PLease provide a valid wallet Id."));
        if (userTransaction.getTransactionType() == TransactionType.CREDIT) {
            userWallet.setBalance(userWallet.getBalance() + userTransaction.getAmount());
        }
        if (userTransaction.getTransactionType() == TransactionType.DEBIT) {
            if (userWallet.getBalance() < userTransaction.getAmount()) {
                throw new InsufficientBalanceException("Insufficient balance in your wallet with wallet id:" + userWallet.getWalletId() + ".");
            }
            userWallet.setBalance(userWallet.getBalance() - userTransaction.getAmount());
        }
        userTransaction.setWallet(userWallet);
        transactionRepository.save(userTransaction);

        userWallet.getTransactionHistory().add(userTransaction);
        walletRepository.save(userWallet);
    }

    public void deleteTransaction(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException("invalid id"));
        transactionRepository.delete(transaction);
    }

    @Override
    public TransactionResponse getInfoById(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException("The provided id is invalid!"));
        return ResponseMapper.toTransactionResponse(transaction);
    }


}

