package com.aspire.loan.utils;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.Repayment;
import com.aspire.loan.entities.enums.LoanStatus;
import com.aspire.loan.request.LoanApplicationRequestDTO;
import com.aspire.loan.request.RepaymentRequestDTO;
import com.aspire.loan.response.LoanApplicationResponseDTO;
import com.aspire.loan.response.LoanResponseDTO;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@UtilityClass
public class RequestMapper {
    public LoanApplication getLoanApplication(LoanApplicationRequestDTO request) {
        return new LoanApplication()
                .setAmountRequested(request.getAmount())
                .setPaymentTermCount(request.getPaymentTermCount())
                .setTermType(request.getTermType())
                .setUserId(request.getUserId());
    }

    public LoanApplicationResponseDTO getLoanApplicationResponseDTO(LoanApplication request) {
        return new LoanApplicationResponseDTO()
                .setId(request.getId())
                .setAmountRequested(request.getAmountRequested())
                .setPaymentTermCount(request.getPaymentTermCount())
                .setTermType(request.getTermType())
                .setAmountApproved(request.getAmountApproved())
                .setReviewedBy(request.getReviewedBy())
                .setStatus(request.getStatus());
    }

    public List<LoanApplicationResponseDTO> getLoanApplicationResponseDTO(List<LoanApplication> loanApplications) {
        if (isNull(loanApplications) || loanApplications.isEmpty()) {
            return new ArrayList<>();
        }
        return loanApplications.stream().map(RequestMapper::getLoanApplicationResponseDTO).collect(Collectors.toList());
    }

    public Loan getLoan(LoanApplication application) {
        return new Loan()
                .setLoanApplication(application)
                .setUserId(application.getUserId())
                .setStatus(LoanStatus.APPROVED)
                .setAmountRepaid(BigDecimal.ZERO)
                .setAmountSanctioned(application.getAmountApproved());
    }

    public static LoanResponseDTO getLoanDTO(Loan loan) {
        return new LoanResponseDTO()
                .setId(loan.getId())
                .setLoanApplicationId(loan.getLoanApplication().getId())
                .setStatus(loan.getStatus())
                .setUserId(loan.getUserId())
                .setAmountSanctioned(loan.getAmountSanctioned())
                .setAmountRepaid(loan.getAmountRepaid());
    }

    public static Repayment getRepayment(Long loanId, RepaymentRequestDTO requestDTO) {
        return new Repayment()
                .setAmount(requestDTO.getAmount())
                .setLoanId(loanId)
                .setCreatedBy(requestDTO.getUserId());

    }

    private LocalDate getDate(long milliSeconds) {
        return Instant.ofEpochMilli(milliSeconds)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
