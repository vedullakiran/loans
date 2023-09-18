package com.aspire.loan;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.PaymentTerm;
import com.aspire.loan.entities.Repayment;
import com.aspire.loan.entities.enums.LoanStatus;
import com.aspire.loan.entities.enums.PaymentTermStatus;
import com.aspire.loan.services.PaymentTermService;
import com.aspire.loan.services.RepaymentService;
import com.aspire.loan.services.impl.PaymentTermServiceImpl;
import com.aspire.loan.services.impl.RepaymentServiceImpl;
import com.aspire.loan.strategy.repayment.DefaultRepaymentStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultRepaymentStrategyTest {

    @InjectMocks
    private DefaultRepaymentStrategy repaymentStrategy;

    private PaymentTermService paymentTermService;

    private RepaymentService repaymentService;

    @BeforeEach
    public void setup() {
        paymentTermService = mock(PaymentTermServiceImpl.class);
        repaymentService = mock(RepaymentServiceImpl.class);
        repaymentStrategy = new DefaultRepaymentStrategy(paymentTermService, repaymentService);
    }

    @Test
    public void testProcessRepayment_FullyPayTerms() {
        // Create a sample loan, repayment, and unpaid payment terms
        Loan loan = new Loan();
        LoanApplication loanApplication = new LoanApplication().setId(1L).setAmountRequested(BigDecimal.valueOf(1000));
        loan.setId(1L);
        loan.setLoanApplication(loanApplication);
        loan.setStatus(LoanStatus.REPAY_IN_PROGRESS);
        loan.setAmountRepaid(new BigDecimal(250));

        Repayment repayment = new Repayment();
        repayment.setAmount(new BigDecimal(750));

        PaymentTerm term1 = new PaymentTerm();
        term1.setId(1L);
        term1.setLoanId(1L);
        term1.setTermAmount(new BigDecimal(250));
        term1.setAmountPaid(BigDecimal.ZERO);
        term1.setStatus(PaymentTermStatus.PENDING);

        PaymentTerm term2 = new PaymentTerm();
        term2.setId(2L);
        term2.setLoanId(1L);
        term2.setTermAmount(new BigDecimal(250));
        term2.setAmountPaid(BigDecimal.ZERO);
        term2.setStatus(PaymentTermStatus.PENDING);

        List<PaymentTerm> unpaidTerms = new ArrayList<>(Arrays.asList(term1, term2));

        // Mock the payment term service to return unpaid terms
        when(paymentTermService.getPaymentTermsForLoanByStatus(1L, new HashSet<>(Arrays.asList(PaymentTermStatus.PENDING, PaymentTermStatus.PARTIALLY_PAID))))
                .thenReturn(unpaidTerms);

        // Call the processRepayment method
        repaymentStrategy.processRepayment(loan, repayment);

        // Verify that the payment terms are fully paid
        assertEquals(PaymentTermStatus.PAID, term1.getStatus());
        assertEquals(PaymentTermStatus.PAID, term2.getStatus());

        // Verify that the loan status is updated to REPAYED
        assertEquals(LoanStatus.REPAID, loan.getStatus());

        // Verify that the loan's amount repaid is updated correctly
        assertEquals(new BigDecimal(1000), loan.getAmountRepaid());

    }

    @Test
    public void testProcessRepayment_PartiallyPayTerms() {
        // Create a sample loan, repayment, and unpaid payment terms
        Loan loan = new Loan();
        LoanApplication loanApplication = new LoanApplication().setId(1L).setAmountRequested(BigDecimal.valueOf(1000));

        loan.setId(1L);
        loan.setLoanApplication(loanApplication);
        loan.setStatus(LoanStatus.REPAY_IN_PROGRESS);
        loan.setAmountRepaid(new BigDecimal(250));

        Repayment repayment = new Repayment();
        repayment.setAmount(new BigDecimal(350));

        PaymentTerm term1 = new PaymentTerm();
        term1.setId(1L);
        term1.setLoanId(1L);
        term1.setTermAmount(new BigDecimal(250));
        term1.setAmountPaid(new BigDecimal(0));
        term1.setStatus(PaymentTermStatus.PENDING);

        PaymentTerm term2 = new PaymentTerm();
        term2.setId(2L);
        term2.setLoanId(1L);
        term2.setTermAmount(new BigDecimal(250));
        term2.setAmountPaid(new BigDecimal(0));
        term2.setStatus(PaymentTermStatus.PENDING);

        List<PaymentTerm> unpaidTerms = new ArrayList<>(Arrays.asList(term1, term2));

        // Mock the payment term service to return unpaid terms
        when(paymentTermService.getPaymentTermsForLoanByStatus(1L, new HashSet<>(Arrays.asList(PaymentTermStatus.PENDING, PaymentTermStatus.PARTIALLY_PAID))))
                .thenReturn(unpaidTerms);

        // Call the processRepayment method
        repaymentStrategy.processRepayment(loan, repayment);

        // Verify that the payment terms are partially paid
        assertEquals(PaymentTermStatus.PARTIALLY_PAID, term2.getStatus());
        assertEquals(PaymentTermStatus.PAID, term1.getStatus());

        // Verify that the loan status is still REPAY_IN_PROGRESS
        assertEquals(LoanStatus.REPAY_IN_PROGRESS, loan.getStatus());

        // Verify that the loan's amount repaid is updated correctly
        assertEquals(new BigDecimal(600), loan.getAmountRepaid());

    }
}
