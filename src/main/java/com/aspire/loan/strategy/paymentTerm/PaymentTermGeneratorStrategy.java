package com.aspire.loan.strategy.paymentTerm;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.PaymentTerm;
import com.aspire.loan.entities.enums.PaymentTermType;

import java.util.List;

public interface PaymentTermGeneratorStrategy {
    PaymentTermType getPaymentTermType();
    List<PaymentTerm> generatePaymentTerms(Loan loan, LoanApplication loanApplication);
}
