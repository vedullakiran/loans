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
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loanApplicationId")
    private LoanApplication loanApplication;

    private Long loanAppId;
    private LoanStatus status;
    private String userId;
    private BigDecimal amountSanctioned;
    private BigDecimal amountRepaid;
}
