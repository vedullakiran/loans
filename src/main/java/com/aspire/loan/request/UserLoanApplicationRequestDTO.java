package com.aspire.loan.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserLoanApplicationRequestDTO {
    private String userId;
}
