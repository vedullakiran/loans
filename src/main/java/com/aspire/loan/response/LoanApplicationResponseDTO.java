package com.aspire.loan.response;

import com.aspire.loan.entities.enums.LoanApplicationStatus;
import com.aspire.loan.entities.enums.PaymentTermType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class LoanApplicationResponseDTO {
    private Long id;
    private BigDecimal amountRequested;
    private BigDecimal amountApproved;
    private PaymentTermType termType;
    private Integer paymentTermCount;
    private LoanApplicationStatus status;
    private String reviewedBy;
    private Date reviewedAt;
}
