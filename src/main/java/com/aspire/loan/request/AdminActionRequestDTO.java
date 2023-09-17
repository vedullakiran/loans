package com.aspire.loan.request;

import com.aspire.loan.entities.enums.LoanApplicationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class AdminActionRequestDTO {
    @JsonIgnore private String adminId;
    private LoanApplicationStatus status;
    private BigDecimal approvedAmount;
}

