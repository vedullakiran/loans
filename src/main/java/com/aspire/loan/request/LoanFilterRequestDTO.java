package com.aspire.loan.request;

import com.aspire.loan.entities.enums.LoanStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Optional;

@Data
@Accessors(chain = true)
public class LoanFilterRequestDTO {
    private Optional<LoanStatus> status;
    private String userId;
}
