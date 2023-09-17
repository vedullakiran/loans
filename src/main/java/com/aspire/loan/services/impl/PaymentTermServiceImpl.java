package com.aspire.loan.services.impl;


import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.PaymentTerm;
import com.aspire.loan.entities.enums.PaymentTermStatus;
import com.aspire.loan.repositories.PaymentTermRepository;
import com.aspire.loan.services.PaymentTermService;
import com.aspire.loan.strategy.paymentTerm.PaymentTermGeneratorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentTermServiceImpl implements PaymentTermService {
    private final PaymentTermGeneratorFactory paymentTermGeneratorFactory;
    private final PaymentTermRepository repository;

    @Override
    public List<PaymentTerm> createPaymentTerms(Loan loan, LoanApplication application) {
        List<PaymentTerm> paymentTerms = paymentTermGeneratorFactory.getPaymentTermGenerator(application.getTermType()).generatePaymentTerms(loan, application);
        paymentTerms = repository.saveAll(paymentTerms);
        return paymentTerms;
    }


    @Override
    public List<PaymentTerm> getPaymentTermsForLoanByStatus(Long loanId, Set<PaymentTermStatus> statusSet) {
        if (isNull(statusSet) || statusSet.isEmpty()) {
            // If the statusSet is empty, fetch all payment terms for the loan
            return repository.findByLoanIdOrderByDueDateAsc(loanId);
        } else {
            // Fetch payment terms for the loan with the specified statuses
            return repository.findByLoanIdAndStatusInOrderByDueDateAsc(loanId, statusSet);
        }
    }

    @Override
    public List<PaymentTerm> updatePaymentTerms(List<PaymentTerm> paymentTerms) {
        return repository.saveAll(paymentTerms);
    }
}
