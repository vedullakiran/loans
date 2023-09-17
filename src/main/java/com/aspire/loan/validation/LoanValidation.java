package com.aspire.loan.validation;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.enums.LoanStatus;
import com.aspire.loan.exceptions.InvalidLoanStatusException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class LoanValidation {

    public void validateLoanEligibility(Loan loan) {
        // Additional status checks
        if (loan.getStatus() == LoanStatus.REPAID) {
            throw new InvalidLoanStatusException("Loan has already been repaid.");
        }
    }
    public void validateLoanAmount(Loan loan, BigDecimal repaymentAmount) {
        BigDecimal totalAmountToBePaid = calculateTotalAmountToBePaid(loan);

        if (repaymentAmount.compareTo(totalAmountToBePaid) > 0) {
            throw new IllegalArgumentException("Repayment amount exceeds the total amount to be paid for the loan.");
        }
    }

    private BigDecimal calculateTotalAmountToBePaid(Loan loan) {
        return loan.getAmountSanctioned().subtract(loan.getAmountRepaid());
    }
}

