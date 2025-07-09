package com.example.filterPoc.service;

import com.example.filterPoc.model.User;
import com.example.filterPoc.model.Transaction;
import com.example.filterPoc.model.Wallet;
import com.example.filterPoc.repository.TransactionRepository;
import com.example.filterPoc.repository.UserRepository;
import com.example.filterPoc.repository.WalletRepository;
import com.example.filterPoc.request.PaginationRequest;
import com.example.filterPoc.request.TransactionRequest;
import com.example.filterPoc.response.TransactionResponse;
import com.example.filterPoc.util.TransactionType;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public TransactionService(TransactionRepository repository, UserRepository userRepository, WalletRepository walletRepository) {
        this.transactionRepository = repository;
        this.userRepository = userRepository;
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

        User user = userRepository.findById(request.getUuid()).orElseThrow(() -> new RuntimeException("invalid user id."));
        userTransaction.setUser(user);

        Wallet userWallet = walletRepository.findByWalletIdAndUser_Uuid(request.getWalletId(), request.getUuid());
        if (userTransaction.getTransactionType() == TransactionType.CREDIT) {
            userWallet.setBalance(userWallet.getBalance() + userTransaction.getAmount());
        }
        if (userTransaction.getTransactionType() == TransactionType.DEBIT) {
            if (userWallet.getBalance() < userTransaction.getAmount()) {
                throw new RuntimeException("insufficient balance");
            }
            userWallet.setBalance(userWallet.getBalance() - userTransaction.getAmount());
        }
        userTransaction.setWallet(userWallet);
        transactionRepository.save(userTransaction);

        userWallet.getTransactionHistory().add(userTransaction);
        walletRepository.save(userWallet);
    }

    public void deleteTransaction(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("invalid id"));
        transactionRepository.delete(transaction);
    }

    public List<Transaction> getRecentTransactionsByUserId(String userId, PaginationRequest request) {
        if (request.getPageSize() != null && request.getPageNumber() != null) {
            Pageable pageable = PageRequest.of(request.getPageNumber() - 1,
                    request.getPageSize(),
                    Sort.by(Sort.Direction.DESC,
                            request.getSortBy()));
            return transactionRepository.findByWallet_walletIdAndUser_Uuid(request.getWalletId(), userId, pageable);
        } else {
            Sort sort = Sort.by(request.getSortBy()).descending();
            return transactionRepository.findByWallet_walletIdAndUser_Uuid(request.getWalletId(),userId, sort);
        }
    }


    }

