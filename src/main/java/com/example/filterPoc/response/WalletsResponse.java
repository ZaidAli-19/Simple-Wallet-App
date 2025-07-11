package com.example.filterPoc.response;

import com.example.filterPoc.model.User;
import com.example.filterPoc.model.Wallet;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WalletsResponse {
    private String walletId;
    private Double balance;
    private User user;
}
