package com.example.filterPoc.repository;

import com.example.filterPoc.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends MongoRepository<Wallet,String> {
    Wallet findByWalletIdAndUser_Uuid(String walletId, String uuid);
   List<Wallet> findByUser_Uuid(String uuid);
}
