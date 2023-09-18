package com.aspire.loan.command.impl;

import com.aspire.loan.command.StatusUpdateCommand;
import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.enums.LoanApplicationStatus;
import com.aspire.loan.request.AdminActionRequestDTO;
import com.aspire.loan.services.PaymentTermService;
import com.aspire.loan.services.impl.LoanServiceImpl;
import com.aspire.loan.utils.RequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class ApproveCommand implements StatusUpdateCommand {
    private final LoanServiceImpl loanService;
    private final PaymentTermService paymentTermService;
    @Override
    public void execute(LoanApplication loanApplication, AdminActionRequestDTO requestDTO) {
        loanApplication.setReviewedBy(requestDTO.getAdminId());
        loanApplication.setReviewedAt(new Date());
        loanApplication.approve();
        Loan loan = RequestMapper.getLoan(loanApplication);
        loanService.createLoan(loan);
        paymentTermService.createPaymentTerms(loan, loanApplication);
    }

    @Override
    public LoanApplicationStatus getStatus() {
        return LoanApplicationStatus.APPROVED;
    }
}
