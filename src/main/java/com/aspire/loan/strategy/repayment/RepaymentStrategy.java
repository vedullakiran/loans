package com.aspire.loan.strategy.repayment;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.Repayment;

public interface RepaymentStrategy {
    void processRepayment(Loan loan, Repayment repayment);
}
