package com.aspire.loan.response;

import com.aspire.loan.entities.enums.LoanStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Accessors(chain = true)
public class LoanResponseDTO {
    private Long id;
    private Long loanApplicationId;
    private LoanStatus status;
    private String userId;
    private BigDecimal amountSanctioned;
    private BigDecimal amountRepaid;
    private LocalDate nextDueDate;
}

