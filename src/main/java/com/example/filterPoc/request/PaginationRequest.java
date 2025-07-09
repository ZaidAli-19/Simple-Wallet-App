package com.example.filterPoc.request;


import com.mongodb.lang.Nullable;
import lombok.Data;

@Data
public class PaginationRequest {
    private String walletId;
    private Integer pageSize;
    private Integer pageNumber;
    private String sortBy;
}
