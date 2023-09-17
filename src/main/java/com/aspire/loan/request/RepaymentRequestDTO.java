package com.aspire.loan.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class RepaymentRequestDTO {
    @JsonIgnore private String userId;
    private BigDecimal amount;
    private String transactionId;
}
