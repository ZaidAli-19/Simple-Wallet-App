package com.example.walletApp.response;

import com.example.walletApp.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WalletsResponse {
    private String walletId;
    private Double balance;
    private User user;
}
