package com.aspire.loan.services.impl;


import com.aspire.loan.command.StatusUpdateCommandFactory;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.enums.LoanApplicationStatus;
import com.aspire.loan.exceptions.EntityNotFoundException;
import com.aspire.loan.repositories.LoanApplicationRepository;
import com.aspire.loan.request.AdminActionRequestDTO;
import com.aspire.loan.request.LoanApplicationRequestDTO;
import com.aspire.loan.response.LoanApplicationResponseDTO;
import com.aspire.loan.services.LoanApplicationService;
import com.aspire.loan.utils.RequestMapper;
import com.aspire.loan.validation.AccessControlValidation;
import com.aspire.loan.validation.LoanApplicationValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {
    private final LoanApplicationRepository repository;
    private final StatusUpdateCommandFactory statusUpdateCommandFactory;
    private final LoanApplicationValidation loanApplicationValidation;
    private final AccessControlValidation accessControlValidation;

    @Override
    public LoanApplicationResponseDTO createLoanApplication(LoanApplicationRequestDTO request) {
        loanApplicationValidation.validateLoanApplicationRequest(request);
        log.info("Creating a new loan application for user ID: {}", request.getUserId());

        LoanApplication loanApplication = RequestMapper.getLoanApplication(request);
        loanApplication.setStatus(LoanApplicationStatus.PENDING);
        loanApplication.setUserId(request.getUserId());
        loanApplication = repository.save(loanApplication);

        log.info("Loan application created with ID: {}", loanApplication.getId());
        return RequestMapper.getLoanApplicationResponseDTO(loanApplication);
    }

    @Override
    public LoanApplicationResponseDTO getLoanApplicationById(Long id, String userId) {
        log.info("Fetching loan application by ID: {}", id);
        LoanApplication loanApplication = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Loan application not found for ID:" + id));
        log.info("Loan application fetched successfully for ID: {}", id);
        accessControlValidation.validateUserAccessToLoan(userId, loanApplication);
        return RequestMapper.getLoanApplicationResponseDTO(loanApplication);
    }


    @Override
    public List<LoanApplicationResponseDTO> getUserLoanApplications(String userId) {
        log.info("Fetching loan applications for user ID: {}", userId);
        List<LoanApplication> userLoanApplications = repository.findByUserId(userId);
        log.info("Fetching loan applications for user ID: {}", userId);
        return RequestMapper.getLoanApplicationResponseDTO(userLoanApplications);
    }


    @Override
    @Transactional
    public LoanApplicationResponseDTO loanApplicationStatusUpdate(Long loanApplicationId, AdminActionRequestDTO requestDTO) {
        log.info("Updating status for loan application with ID: {} to {}", loanApplicationId, requestDTO.getStatus());

        LoanApplication loanApplication = repository.findById(loanApplicationId)
                .orElseThrow(() -> new EntityNotFoundException("Loan application not found for ID:" + loanApplicationId));
        loanApplicationValidation.validateAdminActionRequest(requestDTO, loanApplication);

        statusUpdateCommandFactory.getCommand(requestDTO.getStatus()).execute(loanApplication, requestDTO);
        repository.save(loanApplication);
        log.info("Loan application status updated successfully for ID: {} to {}", loanApplicationId, requestDTO.getStatus());
        return RequestMapper.getLoanApplicationResponseDTO(loanApplication);
    }

    @Override
    public List<LoanApplicationResponseDTO> getFilteredLoanApplications() {
        List<LoanApplication> filteredLoanApplications = repository.findAll();
        return RequestMapper.getLoanApplicationResponseDTO(filteredLoanApplications);
    }
}
