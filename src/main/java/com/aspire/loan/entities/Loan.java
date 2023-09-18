package com.aspire.loan.entities;


import com.aspire.loan.entities.enums.LoanStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "loan")
public class Loan extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_application_id")
    private LoanApplication loanApplication;

    private LoanStatus status;
    private BigDecimal amountRepaid;
    public Loan setId(Long id) {
        super.setId(id);
        return this;
    }
}
