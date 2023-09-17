package com.aspire.loan.validation;

import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.enums.LoanApplicationStatus;
import com.aspire.loan.exceptions.EntityNotFoundException;
import com.aspire.loan.exceptions.ValidateRequestException;
import com.aspire.loan.request.AdminActionRequestDTO;
import com.aspire.loan.request.LoanApplicationRequestDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LoanApplicationValidation {

    public void validateLoanApplicationRequest(LoanApplicationRequestDTO request) {
        if (request == null) {
            throw new ValidateRequestException("Loan application request cannot be null.");
        }
        validateAmount(request.getAmount());
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidateRequestException("Loan amount requested/approved must be greater than zero.");
        }
    }

    public void validateAdminActionRequest(AdminActionRequestDTO request, LoanApplication loanApplication) {
        if (request == null) {
            throw new ValidateRequestException("Admin action request cannot be null.");
        }
        if (loanApplication == null) {
            throw new EntityNotFoundException("Loan application not found for the given ID.");
        }
        LoanApplicationStatus currentStatus = loanApplication.getStatus();

        // Validate status transitions (e.g., only certain transitions are allowed)
        if (!isValidStatusTransition(currentStatus, request.getStatus())) {
            throw new ValidateRequestException("Invalid status transition.");
        }

        validateAmount(request.getApprovedAmount());
        // Add more validations as needed
    }

    private boolean isValidStatusTransition(LoanApplicationStatus currentStatus, LoanApplicationStatus newStatus) {
        if (currentStatus == LoanApplicationStatus.PENDING) {
            return newStatus == LoanApplicationStatus.APPROVED || newStatus == LoanApplicationStatus.DECLINED;
        }
        return false; // Default to false for invalid transitions
    }

}
