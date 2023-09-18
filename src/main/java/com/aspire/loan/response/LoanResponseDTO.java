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
    private LoanApplicationResponseDTO loanApplication;
    private LoanStatus status;
    private BigDecimal amountRepaid;
}

