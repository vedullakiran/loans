package com.aspire.loan.validation;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.exceptions.UserAccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessControlValidation {

    public void validateUserAccessToLoan(String userId, LoanApplication loanApplication) {
        if (!userId.equals(loanApplication.getUserId())) {
            throw new UserAccessDeniedException("User " + userId + " does not have access to this loanApplication " + loanApplication.getId());
        }
    }
}

