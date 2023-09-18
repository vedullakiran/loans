package com.aspire.loan.services;

import com.aspire.loan.request.AdminActionRequestDTO;
import com.aspire.loan.request.LoanApplicationRequestDTO;
import com.aspire.loan.response.LoanApplicationResponseDTO;

import java.util.List;

public interface LoanApplicationService {

    LoanApplicationResponseDTO createLoanApplication(LoanApplicationRequestDTO request);

    LoanApplicationResponseDTO getLoanApplicationById(Long id, String userId);

    List<LoanApplicationResponseDTO> getUserLoanApplications(String request);

    LoanApplicationResponseDTO loanApplicationStatusUpdate(Long loanId, AdminActionRequestDTO requestDTO);

    List<LoanApplicationResponseDTO> getFilteredLoanApplications();
}

