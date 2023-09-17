package com.aspire.loan.strategy.repayment;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.PaymentTerm;
import com.aspire.loan.entities.Repayment;
import com.aspire.loan.entities.enums.LoanStatus;
import com.aspire.loan.entities.enums.PaymentTermStatus;
import com.aspire.loan.services.PaymentTermService;
import com.aspire.loan.services.RepaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultRepaymentStrategy implements RepaymentStrategy {
    private final PaymentTermService paymentTermService;
    private final RepaymentService repaymentService;

    @Override
    public void processRepayment(Loan loan, Repayment repayment) {
        log.info("Processing repayment for Loan ID: {}", loan.getId());

        Set<PaymentTermStatus> paymentTermStatuses = new HashSet<>(Arrays.asList(PaymentTermStatus.PENDING, PaymentTermStatus.PARTIALLY_PAID));
        List<PaymentTerm> unpaidTerms = paymentTermService.getPaymentTermsForLoanByStatus(loan.getId(), paymentTermStatuses);
        List<PaymentTerm> updatedTerms = new ArrayList<>();
        loan.setStatus(LoanStatus.REPAY_IN_PROGRESS);

        BigDecimal remainingRepaymentAmount = repayment.getAmount();

        for (PaymentTerm term : unpaidTerms) {
            BigDecimal termDueAmount = term.getTermAmount();
            BigDecimal termRemainingAmount = termDueAmount.subtract(term.getAmountPaid());

            if (remainingRepaymentAmount.compareTo(termRemainingAmount) >= 0) {
                fullyRepayTerm(term, termDueAmount);
                remainingRepaymentAmount = remainingRepaymentAmount.subtract(termRemainingAmount);
            } else {
                partiallyRepayTerm(term, remainingRepaymentAmount);
                remainingRepaymentAmount = BigDecimal.ZERO;
            }
            updatedTerms.add(term);
            if (remainingRepaymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
        }

        updateLoanStatusAndAmountRepaid(loan, repayment);
        paymentTermService.updatePaymentTerms(updatedTerms);

        repaymentService.createRepayment(repayment);

        log.info("Repayment processed successfully for Loan ID: {}", loan.getId());
    }

    private void fullyRepayTerm(PaymentTerm term, BigDecimal termDueAmount) {
        term.setAmountPaid(termDueAmount);
        term.setStatus(PaymentTermStatus.PAID);
    }

    private void partiallyRepayTerm(PaymentTerm term, BigDecimal repaymentAmount) {
        BigDecimal updatedAmountPaid = term.getAmountPaid().add(repaymentAmount);
        term.setAmountPaid(updatedAmountPaid);

        if (updatedAmountPaid.compareTo(term.getTermAmount()) >= 0) {
            term.setStatus(PaymentTermStatus.PAID);
        } else {
            term.setStatus(PaymentTermStatus.PARTIALLY_PAID);
        }
    }

    private void updateLoanStatusAndAmountRepaid(Loan loan, Repayment repayment) {
        BigDecimal totalAmountRepaid = loan.getAmountRepaid().add(repayment.getAmount());
        loan.setAmountRepaid(totalAmountRepaid);

        if (loan.getAmountRepaid().equals(loan.getAmountSanctioned())) {
            loan.setStatus(LoanStatus.REPAID);
        }
    }
}
