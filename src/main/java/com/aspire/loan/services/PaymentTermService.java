package com.aspire.loan.services;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.PaymentTerm;
import com.aspire.loan.entities.enums.PaymentTermStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface PaymentTermService {

    List<PaymentTerm> createPaymentTerms(Loan loan, LoanApplication application);

    List<PaymentTerm> getPaymentTermsForLoanByStatus(Long loanId, Set<PaymentTermStatus> statusSet);

    List<PaymentTerm> updatePaymentTerms(List<PaymentTerm> paymentTerms);
}
