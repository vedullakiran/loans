package com.aspire.loan.request;

import lombok.Data;

@Data
public class LoanApplicationFilterRequestDTO {
    private String status;
    private String userId;
}
