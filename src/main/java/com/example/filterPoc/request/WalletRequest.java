package com.example.filterPoc.request;

import com.example.filterPoc.model.User;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class WalletRequest {
    private String userId;
}
