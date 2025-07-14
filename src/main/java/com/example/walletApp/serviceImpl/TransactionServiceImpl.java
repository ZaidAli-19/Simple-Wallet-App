package com.example.walletApp.serviceImpl;

import com.example.walletApp.exceptionHandling.InsufficientBalanceException;
import com.example.walletApp.exceptionHandling.ResourceNotFoundException;
import com.example.walletApp.exceptionHandling.TransactionNotFoundException;
import com.example.walletApp.exceptionHandling.WalletNotFoundException;
import com.example.walletApp.model.Transaction;
import com.example.walletApp.model.Wallet;
import com.example.walletApp.repository.TransactionRepository;
import com.example.walletApp.repository.WalletRepository;
import com.example.walletApp.request.TransactionRequest;
import com.example.walletApp.response.TransactionResponse;
import com.example.walletApp.service.TransactionService;
import com.example.walletApp.util.ResponseMapper;
import com.example.walletApp.util.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    public TransactionServiceImpl(TransactionRepository repository, WalletRepository walletRepository) {
        this.transactionRepository = repository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public String createTransaction(TransactionRequest request) {
        Transaction userTransaction = new Transaction();
        userTransaction.setTransactionId(UUID.randomUUID().toString());
        if (request.getTransactionType() == TransactionType.CREDIT) {
            userTransaction.setTransactionType(TransactionType.CREDIT);
        }
        if (request.getTransactionType() == TransactionType.DEBIT) {
            userTransaction.setTransactionType(TransactionType.DEBIT);
        }
        userTransaction.setAmount(request.getAmount());
        userTransaction.setDescription(request.getDescription());
        userTransaction.setDateTime(LocalDateTime.now());
        userTransaction.setIsDeleted(false);


        Wallet userWallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletNotFoundException("Invalid wallet Id! PLease provide a valid wallet Id."));

        if (userWallet.getIsDeleted()) {
            throw new WalletNotFoundException("This wallet is already deleted.");
        }

        if (request.getAmount() == null || request.getAmount() == 0) {
            throw new ResourceNotFoundException("Amount cannot be null or zero!");
        }

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
        return "Transaction successful.";
    }

    @Override
    public TransactionResponse getInfoById(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException("The provided id is invalid!"));
        if (transaction.getIsDeleted()) {
            throw new TransactionNotFoundException("This transaction is already deleted.");
        }
        return ResponseMapper.toTransactionResponse(transaction);
    }


}

