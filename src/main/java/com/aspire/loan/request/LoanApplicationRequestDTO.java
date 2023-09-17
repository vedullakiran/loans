package com.aspire.loan.request;

import com.aspire.loan.entities.enums.PaymentTermType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class LoanApplicationRequestDTO {
    @JsonIgnore private String userId;
    private BigDecimal amount;
    private PaymentTermType termType;
    private Integer paymentTermCount;
}
