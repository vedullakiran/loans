package com.aspire.loan.validation;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.enums.LoanStatus;
import com.aspire.loan.exceptions.InvalidLoanStatusException;
import com.aspire.loan.exceptions.UserAccessDeniedException;
import com.aspire.loan.exceptions.ValidateRequestException;
import com.aspire.loan.request.RepaymentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class LoanValidation {

    public void validateLoanEligibility(Loan loan, RepaymentRequestDTO requestDTO) {
        // Additional status checks
        if (loan.getStatus() == LoanStatus.REPAID) {
            throw new InvalidLoanStatusException("Loan has already been repaid.");
        }

        if (!loan.getLoanApplication().getUserId().equals(requestDTO.getUserId())) {
            throw new UserAccessDeniedException("User does not have access for this loan");
        }

        validateLoanAmount(loan, requestDTO.getAmount());
    }
    public void validateLoanAmount(Loan loan, BigDecimal amount) {
        BigDecimal totalAmountToBePaid = calculateTotalAmountToBePaid(loan);

        if (amount.compareTo(totalAmountToBePaid) > 0) {
            throw new ValidateRequestException("Repayment amount exceeds the total amount to be paid for the loan.");
        }
    }

    private BigDecimal calculateTotalAmountToBePaid(Loan loan) {
        return loan.getLoanApplication().getAmountRequested().subtract(loan.getAmountRepaid());
    }
}

