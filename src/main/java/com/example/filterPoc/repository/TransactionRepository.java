package com.example.filterPoc.repository;

import com.example.filterPoc.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction,String> {
List<Transaction> findByWallet_walletId(String walletId, Pageable pageable);
List<Transaction> findByWallet_walletId(String walletId, Sort sort);
}
