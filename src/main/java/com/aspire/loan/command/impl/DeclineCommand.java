package com.aspire.loan.command.impl;

import com.aspire.loan.command.StatusUpdateCommand;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.enums.LoanApplicationStatus;
import com.aspire.loan.request.AdminActionRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeclineCommand implements StatusUpdateCommand {
    @Override
    public void execute(LoanApplication loanApplication, AdminActionRequestDTO requestDTO) {
        loanApplication.decline();
    }

    @Override
    public LoanApplicationStatus getStatus() {
        return LoanApplicationStatus.DECLINED;
    }
}
