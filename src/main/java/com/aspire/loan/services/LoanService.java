package com.aspire.loan.services;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.enums.LoanStatus;
import com.aspire.loan.request.LoanFilterRequestDTO;
import com.aspire.loan.request.RepaymentRequestDTO;
import com.aspire.loan.response.LoanResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LoanService {
    Loan createLoan(Loan loan);

    LoanResponseDTO getLoanById(Long loanId, String userId);

    List<LoanResponseDTO> getLoansForUser(String userId, Set<LoanStatus> statusSet);

    LoanResponseDTO repayLoan(Long loanId, RepaymentRequestDTO requestDTO);
}
