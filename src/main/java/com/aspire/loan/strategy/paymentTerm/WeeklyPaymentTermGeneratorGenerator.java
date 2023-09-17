package com.aspire.loan.strategy.paymentTerm;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.PaymentTerm;
import com.aspire.loan.entities.enums.PaymentTermStatus;
import com.aspire.loan.entities.enums.PaymentTermType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeeklyPaymentTermGeneratorGenerator implements PaymentTermGeneratorStrategy {

    @Override
    public PaymentTermType getPaymentTermType() {
        return PaymentTermType.WEEKLY;
    }

    @Override
    public List<PaymentTerm> generatePaymentTerms(Loan loan, LoanApplication loanApplication) {
        List<PaymentTerm> paymentTerms = new ArrayList<>();

        // Extract loan details
        Long loanId = loan.getId();
        String userId = loan.getUserId();
        BigDecimal totalAmount = loan.getAmountSanctioned();
        int termCount = loanApplication.getPaymentTermCount();
        BigDecimal amountPerTerm = totalAmount.divide(BigDecimal.valueOf(termCount), 2, RoundingMode.HALF_UP);

        long nextDueDate = loanApplication.getCreatedAt();
        log.debug("Generating {} weekly payment terms for loanId: {}, userId: {}", termCount, loanId, userId);

        for (int i = 0; i < termCount; i++) {
            PaymentTerm paymentTerm = new PaymentTerm()
                    .setLoanId(loanId)
                    .setTermAmount(amountPerTerm)
                    .setStatus(PaymentTermStatus.PENDING)
                    .setAmountPaid(BigDecimal.ZERO);

            nextDueDate = Instant.ofEpochMilli(nextDueDate).plus(7, ChronoUnit.DAYS).toEpochMilli();
            paymentTerm.setDueDate(nextDueDate);
            paymentTerms.add(paymentTerm);

            log.debug("Generated payment term {} - Due Date: {}, Due Amount: {}", i + 1, paymentTerm.getDueDate(), paymentTerm.getTermAmount());

        }
        log.info("Generated {} weekly payment terms for loanId: {}, userId: {}", termCount, loanId, userId);

        return paymentTerms;
    }
}
