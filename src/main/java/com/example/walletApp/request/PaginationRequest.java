package com.example.walletApp.request;


import lombok.Data;

@Data
public class PaginationRequest {
    private Integer pageSize;
    private Integer pageNumber;
    private String sortBy;
}
