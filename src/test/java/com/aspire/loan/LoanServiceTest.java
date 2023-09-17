package com.aspire.loan;


import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.enums.LoanStatus;
import com.aspire.loan.repositories.LoanRepository;
import com.aspire.loan.request.RepaymentRequestDTO;
import com.aspire.loan.response.LoanResponseDTO;
import com.aspire.loan.services.LoanService;
import com.aspire.loan.services.impl.LoanServiceImpl;
import com.aspire.loan.strategy.repayment.RepaymentStrategy;
import com.aspire.loan.validation.AccessControlValidation;
import com.aspire.loan.validation.LoanValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    private LoanRepository loanRepository;

    private RepaymentStrategy repaymentStrategy;

    private LoanValidation validationService;

    private AccessControlValidation accessControlValidation;

    @BeforeEach
    public void setup() {
        accessControlValidation = mock(AccessControlValidation.class);
        validationService = mock(LoanValidation.class);
        repaymentStrategy = mock(RepaymentStrategy.class);
        loanRepository = mock(LoanRepository.class);
        loanService = new LoanServiceImpl(loanRepository, repaymentStrategy, validationService, accessControlValidation);
    }

    @Test
    public void testCreateLoan() {
        Loan loan = new Loan();
        when(loanRepository.save(loan)).thenReturn(loan);

        Loan createdLoan = loanService.createLoan(loan);

        assertNotNull(createdLoan);
        assertEquals(loan, createdLoan);
    }

    @Test
    public void testGetLoanById() {
        Long loanId = 1L;
        String userId = "user123";
        Loan loan = new Loan()
                .setUserId(userId)
                .setId(loanId);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        LoanResponseDTO loanResponseDTO = loanService.getLoanById(loanId, userId);

        assertNotNull(loanResponseDTO);
        assertEquals(loanId, loanResponseDTO.getId());
    }

    @Test
    public void testGetLoansForUser() {
        String userId = "user123";
        Set<LoanStatus> statusSet = new HashSet<>(Arrays.asList(LoanStatus.REPAY_IN_PROGRESS, LoanStatus.APPROVED));
        List<Loan> loans = new ArrayList<>();
        loans.add(new Loan());
        loans.add(new Loan());

        when(loanRepository.findByStatusIsInAndUserId(statusSet, userId)).thenReturn(loans);
        when(loanRepository.findByUserId(userId)).thenReturn(loans);

        List<LoanResponseDTO> loanResponseDTOs = loanService.getLoansForUser(userId, statusSet);

        assertNotNull(loanResponseDTOs);
        assertEquals(loans.size(), loanResponseDTOs.size());

        loanResponseDTOs = loanService.getLoansForUser(userId, new HashSet<>());
        assertNotNull(loanResponseDTOs);
        assertEquals(loans.size(), loanResponseDTOs.size());
    }

    @Test
    public void testRepayLoan() {
        Long loanId = 1L;
        RepaymentRequestDTO requestDTO = new RepaymentRequestDTO();
        Loan loan = new Loan();
        loan.setId(loanId);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        LoanResponseDTO loanResponseDTO = loanService.repayLoan(loanId, requestDTO);

        assertNotNull(loanResponseDTO);
        assertEquals(loanId, loanResponseDTO.getId());
    }
}
