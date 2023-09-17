package com.aspire.loan.services.impl;


import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.enums.LoanStatus;
import com.aspire.loan.exceptions.EntityNotFoundException;
import com.aspire.loan.repositories.LoanRepository;
import com.aspire.loan.request.RepaymentRequestDTO;
import com.aspire.loan.response.LoanResponseDTO;
import com.aspire.loan.services.LoanService;
import com.aspire.loan.strategy.repayment.RepaymentStrategy;
import com.aspire.loan.utils.RequestMapper;
import com.aspire.loan.validation.AccessControlValidation;
import com.aspire.loan.validation.LoanValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final RepaymentStrategy repaymentStrategy;
    private final LoanValidation validationService;
    private final AccessControlValidation accessControlValidation;

    @Override
    public Loan createLoan(Loan loan) {
        log.info("Creating a new loan: {}", loan);
        return loanRepository.save(loan);
    }

    @Override
    public LoanResponseDTO getLoanById(Long loanId, String userId) {
        log.info("Fetching loan by ID: {}", loanId);
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found for id: " + loanId));
        accessControlValidation.validateUserAccessToLoan(userId, loan);
        return RequestMapper.getLoanDTO(loan);
    }

    @Override
    public List<LoanResponseDTO> getLoansForUser(String userId, Set<LoanStatus> statusSet) {
        log.info("Fetching loans for user: {}", userId);
        List<Loan> loans;
        if (isNull(statusSet) || statusSet.isEmpty()) {
            loans = loanRepository.findByUserId(userId);
        } else
            loans = loanRepository.findByStatusIsInAndUserId(statusSet, userId);
        return loans.stream()
                .map(RequestMapper::getLoanDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LoanResponseDTO repayLoan(Long loanId, RepaymentRequestDTO requestDTO) {
        log.info("Processing repayment for loan ID: {}", loanId);
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new EntityNotFoundException("Loan not found for id: " + loanId));
        validationService.validateLoanEligibility(loan);
        validationService.validateLoanAmount(loan, requestDTO.getAmount());
        repaymentStrategy.processRepayment(loan, RequestMapper.getRepayment(loanId, requestDTO));
        loanRepository.save(loan);
        return RequestMapper.getLoanDTO(loan);
    }
}

