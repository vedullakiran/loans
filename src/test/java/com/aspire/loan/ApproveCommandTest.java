package com.aspire.loan;

import com.aspire.loan.command.impl.ApproveCommand;
import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.enums.LoanApplicationStatus;
import com.aspire.loan.request.AdminActionRequestDTO;
import com.aspire.loan.services.PaymentTermService;
import com.aspire.loan.services.impl.LoanServiceImpl;
import com.aspire.loan.services.impl.PaymentTermServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApproveCommandTest {

    @InjectMocks
    private ApproveCommand approveCommand;

    private LoanServiceImpl loanService;
    private PaymentTermService paymentTermService;

    @BeforeEach
    public void setup() {
        loanService = mock(LoanServiceImpl.class);
        paymentTermService = mock(PaymentTermServiceImpl.class);
        approveCommand = new ApproveCommand(loanService, paymentTermService);
    }

    @Test
    public void testExecute() {
        // Create a sample loan application and request DTO
        LoanApplication loanApplication = new LoanApplication().setStatus(LoanApplicationStatus.PENDING);
        AdminActionRequestDTO requestDTO = new AdminActionRequestDTO();
        requestDTO.setAdminId("admin123");

        // Mock the behavior of loanService and paymentTermService
        Loan loan = new Loan();
        when(loanService.createLoan(loan)).thenReturn(loan);
        when(paymentTermService.createPaymentTerms(loan, loanApplication)).thenReturn(null);

        // Execute the command
        approveCommand.execute(loanApplication, requestDTO);

        // Verify that the loan application and related entities were updated correctly
        assertEquals("admin123", loanApplication.getReviewedBy());
        assertNotNull(loanApplication.getReviewedAt());
        assertEquals(LoanApplicationStatus.APPROVED, loanApplication.getStatus());
        assertNotNull(loan);
    }

    @Test
    public void testGetStatus() {
        assertEquals(LoanApplicationStatus.APPROVED, approveCommand.getStatus());
    }
}

