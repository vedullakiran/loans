package com.aspire.loan.command;

import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.enums.LoanApplicationStatus;
import com.aspire.loan.request.AdminActionRequestDTO;

public interface StatusUpdateCommand {
    void execute(LoanApplication loanApplication, AdminActionRequestDTO requestDTO);

    LoanApplicationStatus getStatus();
}
